package novel.server.member;

import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDto;
import novel.server.member.dto.MemberDefaultRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    String register(MemberDefaultRegisterDto registerDto);
    TokenInfo login(MemberDefaultLoginDto loginDto);
}
