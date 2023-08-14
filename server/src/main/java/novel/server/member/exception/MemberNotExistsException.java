package novel.server.member.exception;

public class MemberNotExistsException extends RuntimeException {
    public MemberNotExistsException(String message) {
        super(message);
    }
}
