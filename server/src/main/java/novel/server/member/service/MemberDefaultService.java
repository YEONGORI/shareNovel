package novel.server.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.MemberService;
import novel.server.member.auth.JwtTokenProvider;
import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import novel.server.member.exception.MemberAlreadyExistsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;


@Transactional
@RequiredArgsConstructor
public class MemberDefaultService implements MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     * @param registerDto
     * @return
     */
    public Member register(MemberDefaultRegisterDTO registerDto) {
        if (memberRepository.findMemberByPenName(registerDto.getPenName()).isPresent()) {
            throw new MemberAlreadyExistsException("사용중인 필명 입니다.");
        }
        Member member = memberRepository.save(registerDto.toEntity());
        return member;
    }


    public TokenInfo login(MemberDefaultLoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authenticate);
    }
}
