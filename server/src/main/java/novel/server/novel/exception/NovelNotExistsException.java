package novel.server.novel.exception;

public class NovelNotExistsException extends RuntimeException{
    public NovelNotExistsException(String message) {
        super(message);
    }
}
