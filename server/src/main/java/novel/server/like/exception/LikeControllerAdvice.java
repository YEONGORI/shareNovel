package novel.server.like.exception;

import lombok.extern.slf4j.Slf4j;
import novel.server.global.ErrorResult;
import novel.server.global.ResultCode;
import novel.server.like.LikeController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.Collections.singletonList;

@Slf4j
@RestControllerAdvice(assignableTypes = LikeController.class)
public class LikeControllerAdvice {
    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(AlreadyLikedException.class)
    protected ErrorResult alreadyLikedExHandler(AlreadyLikedException e) {
        log.error("Already Liked Exception Handelr = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(AlreadyCanceledException.class)
    protected ErrorResult alreadyLikedExHandler(AlreadyCanceledException e) {
        log.error("Already canceled Exception Handelr = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }
}
