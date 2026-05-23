package vn.proX.todoapplication.controller.errors;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.proX.todoapplication.entity.ApiResponse;
import vn.proX.todoapplication.entity.User;

@RestControllerAdvice
public class GlobalException {
    /*
     * Đây là một Exception riêng cho NoSuchElementException, tức là khi nào có
     * NoSuchElementException xảy ra thì sẽ vào handler này để xử lý lỗi, còn nếu có exception khác
     * xảy ra thì sẽ vào handler handleGlobalException để xử lý lỗi
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<User>> handleUserNotFound(NoSuchElementException ex) {
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,
                "handleUserNotFound", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // global exception handler tức là sau này có thêm các exception khác, mình không cần phải viết
    // thêm handler cho từng exception nữa, mà chỉ cần viết 1 handler chung cho tất cả các
    // exception, đó là handleGlobalException
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<User>> handleGlobalException(Exception ex) {
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,
                "handleGlobalException", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
