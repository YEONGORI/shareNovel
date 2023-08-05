package novel.server.writer.service;

import lombok.RequiredArgsConstructor;
import novel.server.writer.Writer;
import novel.server.writer.WriterRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class WriterDetailService implements UserDetailsService {
    private final WriterRepository writerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Writer writer = writerRepository.findWriterByPenName(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 필명입니다."));

        return new User(writer.getPenName(), passwordEncoder.encode(writer.getPassword()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
