package novel.server.like.exception;

public class AlreadyCanceledException extends RuntimeException {
    public AlreadyCanceledException(String message) {
        super(message);
    }
}
