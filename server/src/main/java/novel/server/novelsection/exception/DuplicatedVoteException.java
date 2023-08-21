package novel.server.novelsection.exception;

public class DuplicatedVoteException extends RuntimeException {
    public DuplicatedVoteException(String message) {
        super(message);
    }
}
