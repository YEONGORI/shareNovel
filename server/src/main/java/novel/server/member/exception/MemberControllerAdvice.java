package novel.server.member.exception;

import lombok.extern.slf4j.Slf4j;
import novel.server.global.ErrorResult;
import novel.server.global.ResultCode;
import novel.server.member.MemberController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;


@Slf4j
@RestControllerAdvice(assignableTypes = MemberController.class)
public class MemberControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResult methodArgumentExHandle(MethodArgumentNotValidException e) {
        log.error("Member Method Exception Handler = ", e);

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
        log.error("Member Illegal Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MemberAlreadyExistsException.class)
    protected ErrorResult memberExHandle(MemberAlreadyExistsException e) {
        log.error("Member Exception Handler", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ErrorResult handleAuthenticationException(AuthenticationException e) {
        log.error("Authentication Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList("아이디 또는 비밀번호를 확인해주세요"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResult defaultExHandle(Exception e) {
        log.error("Member Default Exception Handler = ", e);
        return new ErrorResult(ResultCode.ERROR, singletonList(e.getMessage()));
    }
}
