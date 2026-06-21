package com.ayushPay.microservices.transaction_service.exception;

import com.ayushPay.microservices.transaction_service.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            WalletUnavailableException.class
    )

    public ResponseEntity<ErrorResponse>
    walletException(

            WalletUnavailableException ex,

            HttpServletRequest req
    ){

        return ResponseEntity
                .status(503)

                .body(

                        ErrorResponse.builder()

                                .status(503)

                                .message(
                                        ex.getMessage()
                                )

                                .path(
                                        req.getRequestURI()
                                )

                                .timestamp(
                                        LocalDateTime.now()
                                )

                                .build()

                );

    }

    @ExceptionHandler(
            Exception.class
    )

    public ResponseEntity<ErrorResponse>
    generic(

            Exception ex,

            HttpServletRequest req
    ){

        return ResponseEntity
                .internalServerError()

                .body(

                        ErrorResponse.builder()

                                .status(500)

                                .message(
                                        ex.getMessage()
                                )

                                .path(
                                        req.getRequestURI()
                                )

                                .timestamp(
                                        LocalDateTime.now()
                                )

                                .build()

                );

    }

}