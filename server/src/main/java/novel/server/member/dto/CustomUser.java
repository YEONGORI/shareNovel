package novel.server.member.dto;

import lombok.Getter;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private Long memberId;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

//    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }

    public CustomUser(Long memberId, String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

}
