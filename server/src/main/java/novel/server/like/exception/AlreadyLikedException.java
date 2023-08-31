package novel.server.like.exception;

public class AlreadyLikedException extends RuntimeException{
    public AlreadyLikedException(String message) {
        super(message);
    }
}
