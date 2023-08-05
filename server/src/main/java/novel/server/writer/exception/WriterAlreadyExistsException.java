package novel.server.writer.exception;

public class WriterAlreadyExistsException extends RuntimeException {
    public WriterAlreadyExistsException(String message) {
        super(message);
    }
}
