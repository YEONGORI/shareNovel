package novel.server.web.writer.exception;

import lombok.extern.slf4j.Slf4j;
import novel.server.ResultCode;
import novel.server.domain.writer.exception.WriterAlreadyExistsException;
import novel.server.web.writer.WriterController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;


@Slf4j
@RestControllerAdvice(assignableTypes = WriterController.class)
public class WriterControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResult methodArgumentExHandle(MethodArgumentNotValidException e) {
        log.error("Writer Method Exception Handler = ", e);

        List<String> errorMsgs = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ErrorResult(ResultCode.ERROR, errorMsgs);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("Writer Illegal Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WriterAlreadyExistsException.class)
    protected ErrorResult writerExHandle(WriterAlreadyExistsException e) {
        log.error("Writer Exception Handler", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResult defaultExHandle(Exception e) {
        log.error("Writer Default Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }
}
