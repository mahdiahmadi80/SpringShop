package org.example.springshop.exception;

import org.example.springshop.exception.Address.AddressException;
import org.example.springshop.exception.cartException.CartNotFoundException;
import org.example.springshop.exception.categoryException.CategoryNotFoundException;
import org.example.springshop.exception.commentException.CommentNotfoundException;
import org.example.springshop.exception.contactusException.MessageNotFoundException;
import org.example.springshop.exception.itemNotFoundException.ItemNotFoundException;
import org.example.springshop.exception.orderException.OrderNotFoundException;
import org.example.springshop.exception.productException.ProductException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.exception.userException.VerifyException;
import org.example.springshop.exception.walletException.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handelWalletException(WalletException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelUserNotFoundException(UserNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelVerifyException(VerifyException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelProductNotFoundException(ProductNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelProductException(ProductException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelOrderNotFoundException(OrderNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelMessageNotFoundException(MessageNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelCategoryNotfound(CategoryNotFoundException exception) {
        return buildResponse((exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelCommentNotFoundException(CommentNotfoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelAddressException(AddressException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelCartNotFound(CartNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handelItemNotFound(ItemNotFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus httpStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", httpStatus.value());
        body.put("error", httpStatus.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, httpStatus);
    }
}
