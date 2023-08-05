package novel.server.writer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import novel.server.global.ResultCode;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResult {
    private ResultCode code;
    private List<String> messages;
}
