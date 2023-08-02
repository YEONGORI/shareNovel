package novel.server.web.writer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import novel.server.ResultCode;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResult {
    private ResultCode code;
    private List<String> messages;
}
