package novel.server.writer.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenInfo {
    private String grantType;
    private String accessToken;
}
