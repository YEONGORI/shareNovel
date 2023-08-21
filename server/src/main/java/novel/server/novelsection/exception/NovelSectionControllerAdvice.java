package novel.server.novelsection.exception;

import lombok.extern.slf4j.Slf4j;
import novel.server.global.ErrorResult;
import novel.server.global.ResultCode;
import novel.server.member.exception.MemberNotExistsException;
import novel.server.novel.exception.NovelNotExistsException;
import novel.server.novelsection.NovelSectionController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Slf4j
@RestControllerAdvice(assignableTypes = NovelSectionController.class)
public class NovelSectionControllerAdvice {
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResult methodArgumentExHandle(MethodArgumentNotValidException e) {
        log.error("NovelSection Method Exception Handler = ", e);

        List<String> errorMsgs = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResult(ResultCode.ERROR, errorMsgs);
    }

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NovelNotExistsException.class)
    protected ErrorResult novelExHandle(NovelNotExistsException e) {
        log.error("Novel Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ClassCastException.class)
    protected ErrorResult jwtExHandle(ClassCastException e) {
        log.error("Jwt Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(DuplicatedVoteException.class)
    protected ErrorResult voteExHandle(DuplicatedVoteException e) {
        log.error("Vote Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

}
