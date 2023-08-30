package novel.server.like.exception;

public class DuplicatedLikeException extends RuntimeException {
    public DuplicatedLikeException(String message) {
        super(message);
    }
}
