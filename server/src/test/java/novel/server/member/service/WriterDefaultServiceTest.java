package novel.server.member.service;

import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.auth.JwtTokenProvider;
import novel.server.member.dto.MemberDefaultLoginDto;
import novel.server.member.dto.MemberDefaultRegisterDto;
import novel.server.member.MemberMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberDefaultServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원 가입")
    void register() {

        MemberDefaultRegisterDto registerDto = MemberMother.registerDto();
        Member member = registerDto.toEntity();
        memberRepository.save(member);

        Member member_ch = memberRepository.findMemberByPenName(member.getPenName()).get();
        assertThat(member).isEqualTo(member_ch);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        MemberDefaultRegisterDto registerDto = MemberMother.registerDto();
        Member member = registerDto.toEntity();

        MemberDefaultLoginDto loginDto = MemberDefaultLoginDto.builder()
                .penName(member.getPenName())
                .password(member.getPassword())
                .build();
        memberRepository.save(member);

        // when
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());

        // then
        Authentication authentication = assertDoesNotThrow(() -> authenticationManagerBuilder.getObject().authenticate(authenticationToken));
        assertDoesNotThrow(() -> jwtTokenProvider.createToken(authentication));
    }

    @Test
    @DisplayName("비인증 사용자 확인 [미등록 사용자]")
    void NotAuthorized1() {
        MemberDefaultLoginDto loginDto = MemberMother.loginDto();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPenName(), loginDto.getPassword());
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken));
    }

    @Test
    @DisplayName("비인증 사용자 확인 [필명, 비밀번호 불일치]")
    void NotAuthorized2() {
        MemberDefaultRegisterDto registerDto = MemberMother.registerDto();
        Member member = registerDto.toEntity();
        memberRepository.save(member);

        MemberDefaultLoginDto loginDto0 = MemberDefaultLoginDto.builder()
                .penName(member.getPenName())
                .password(member.getPassword())
                .build();

        MemberDefaultLoginDto loginDto1 = MemberDefaultLoginDto.builder()
                .penName(member.getPenName())
                .password("TEST")
                .build();

        MemberDefaultLoginDto loginDto2 = MemberDefaultLoginDto.builder()
                .penName("TEST")
                .password(member.getPassword())
                .build();

        UsernamePasswordAuthenticationToken authenticationToken0 = new UsernamePasswordAuthenticationToken(loginDto0.getPenName(), loginDto0.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken1 = new UsernamePasswordAuthenticationToken(loginDto1.getPenName(), loginDto1.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken2 = new UsernamePasswordAuthenticationToken(loginDto2.getPenName(), loginDto2.getPassword());

        assertDoesNotThrow(() -> authenticationManagerBuilder.getObject().authenticate(authenticationToken0));
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken1));
        assertThrows(AuthenticationException.class,
                () -> authenticationManagerBuilder.getObject().authenticate(authenticationToken2));
    }
}