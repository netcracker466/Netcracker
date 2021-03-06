package com.netcracker.controllers.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.controllers.web.ApiError;
import com.netcracker.controllers.web.ResponseEntityBuilder;
import com.netcracker.exception.DaoAccessException;
import com.netcracker.exception.IllegalSumToPayException;
import com.netcracker.exception.InsufficientBalanceException;
import com.netcracker.exception.NotBelongToAccountException;
import com.netcracker.secutity.jwt.JwtAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<String>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()

                .map(error ->error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors",
                details, BigInteger.valueOf(909));

        return ResponseEntityBuilder.build(err);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors",
                Collections.singletonList("Not Readable Format"));
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {

            List<String> details = exception.getConstraintViolations().stream().map(error -> error.getMessageTemplate()).collect(Collectors.toList());
            ApiError err = new ApiError(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST,
                    "Validation Errors",
                    details);
            return ResponseEntityBuilder.build(err);

    }
        @ExceptionHandler({DaoAccessException.class, NotBelongToAccountException.class, JwtAuthenticationException.class})
    public final ResponseEntity<ApiError> handleCustomException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof DaoAccessException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            DaoAccessException accessException = (DaoAccessException) ex;

            return handleDaoAccessException(accessException, headers, status, request);
        } else if (ex instanceof NotBelongToAccountException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            NotBelongToAccountException accountException = (NotBelongToAccountException) ex;

            return handleNotBelongToAccountException(accountException, headers, status, request);
        } else if (ex instanceof AuthenticationServiceException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            AuthenticationServiceException authenticationException = (AuthenticationServiceException) ex;
            return handleAuthAccessException(authenticationException,headers,status,request);
        }else if (ex instanceof JwtAuthenticationException)
        {
            HttpStatus status = HttpStatus.FORBIDDEN;
            JwtAuthenticationException jwtAuthenticationException = (JwtAuthenticationException) ex;
            return handleJwtAccessException(jwtAuthenticationException,headers,status,request);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }


    protected ResponseEntity<ApiError> handleDaoAccessException(DaoAccessException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return handleExceptionInternal(ex, new ApiError(LocalDateTime.now(), status, "DAO ACCESS EXCEPTION",
                errors,ex.getErrorMessage()), headers, status, request);
    }


    protected ResponseEntity<ApiError> handleAuthAccessException(AuthenticationServiceException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(LocalDateTime.now(), status, "PASSWORD IS WRONG", errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleJwtAccessException(JwtAuthenticationException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(LocalDateTime.now(), status, "TOKEN IS WRONG", errors), headers, status, request);
    }


    protected ResponseEntity<ApiError> handleNullPointerException(NullPointerException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(LocalDateTime.now(), status, "NULL POINTER", errors), headers, status, request);
    }

    protected ResponseEntity<ApiError> handleNotBelongToAccountException(NotBelongToAccountException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(LocalDateTime.now(), status, "DAO exception", errors), headers, status, request);
    }


    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    public final ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException ex, WebRequest request) {


        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Insufficient Balance",
                Collections.singletonList("Not enough money"),
                BigInteger.valueOf(8092));
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({JsonProcessingException.class})
    public final ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex, WebRequest request) {


        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Parse exception",
                Collections.singletonList("Cant parse map to json"),
                BigInteger.valueOf(80999));
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({IllegalSumToPayException.class})
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalSumToPayException ex, WebRequest request) {


        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Collections.singletonList(ex.getMessage()),
                BigInteger.valueOf(777));
        return ResponseEntityBuilder.build(err);
    }
}

