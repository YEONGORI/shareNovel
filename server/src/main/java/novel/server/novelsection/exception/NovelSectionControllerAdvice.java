package novel.server.novelsection.exception;

import lombok.extern.slf4j.Slf4j;
import novel.server.global.ErrorResult;
import novel.server.global.ResultCode;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novelsection.NovelSectionController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

import static java.util.Collections.singletonList;

@Slf4j
@RestControllerAdvice(assignableTypes = NovelSectionController.class)
public class NovelSectionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NovelSectionNotExistsException.class)
    protected ErrorResult novelSectionExHandle(NovelSectionNotExistsException e) {
        log.error("NovelSection Not Exists Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MemberNotExistsException.class)
    protected ErrorResult memberExHandle(MemberNotExistsException e) {
        log.error("Member Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }
}
