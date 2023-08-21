package novel.server.member;

import novel.server.member.auth.TokenInfo;
import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member register(MemberDefaultRegisterDTO registerDto);
    TokenInfo login(MemberDefaultLoginDTO loginDto);
}
