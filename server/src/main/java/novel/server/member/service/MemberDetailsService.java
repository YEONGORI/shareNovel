package novel.server.member.service;

import lombok.RequiredArgsConstructor;
import novel.server.member.Member;
import novel.server.member.MemberRepository;
import novel.server.member.dto.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByPenName(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 필명입니다."));

        return new CustomUser(
                member.getId(),
                member.getPenName(),
                passwordEncoder.encode(member.getPassword()),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
