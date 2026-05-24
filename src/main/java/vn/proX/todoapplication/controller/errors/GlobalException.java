package vn.proX.todoapplication.controller.errors;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.proX.todoapplication.entity.ApiResponse;
import vn.proX.todoapplication.entity.User;

@RestControllerAdvice
public class GlobalException {
        /*
         * Đây là một Exception riêng cho NoSuchElementException, tức là khi nào có
         * NoSuchElementException xảy ra thì sẽ vào handler này để xử lý lỗi, còn nếu có exception
         * khác xảy ra thì sẽ vào handler handleGlobalException để xử lý lỗi
         */
        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<ApiResponse<User>> handleUserNotFound(NoSuchElementException ex) {
                ApiResponse<User> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,
                                "handleUserNotFound", null, ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // global exception handler tức là sau này có thêm các exception khác, mình không cần phải
        // viết
        // thêm handler cho từng exception nữa, mà chỉ cần viết 1 handler chung cho tất cả các
        // exception, đó là handleGlobalException
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<User>> handleGlobalException(Exception ex) {
                ApiResponse<User> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage(), null, "INTERNAL_SERVER_ERROR");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(
                        MethodArgumentNotValidException ex) {
                List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());
                String errors = String.join("; ", errorList);

                ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, errors,
                                null, "VALIDATION_ERROR");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }



}
