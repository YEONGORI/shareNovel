package novel.server.global;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResult {
    private ResultCode code;
    private List<String> messages;
}
