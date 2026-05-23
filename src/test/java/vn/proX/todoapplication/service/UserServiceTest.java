package vn.proX.todoapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.proX.todoapplication.entity.User;
import vn.proX.todoapplication.repository.UserRepository;
import vn.proX.todoapplication.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // ============ createUser ===============
    // fake data for testing
    @Mock
    private UserRepository userRepository;

    // inject the mock objects into the service being tested
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_shouldReturnUser_WhenEmailIsValid() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test

        User inputUser = new User(null, "John Doe", "john.doe@example.com");
        User outputUser = new User(1L, "John Doe", "john.doe@example.com");

        when(userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(outputUser);

        // act : thực hiện hành động cần test
        User result = this.userService.createUser(inputUser);

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertEquals(1L, result.getId());
    }


    @Test
    public void createUser_shouldThrowException_WhenEmailInvalid() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test

        User inputUser = new User(null, "John Doe", "john.doe@example.com");


        when(userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);

        // act : thực hiện hành động cần test
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            this.userService.createUser(inputUser);
        });

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertEquals("Email already exists", ex.getMessage());
    }


    // ============ getAllUsers ===============
    @Test
    public void getAllUsers_shouldReturnAllUsers() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test
        List<User> outputUsers = new ArrayList<>();
        outputUsers.add(new User(1L, "John Doe", "john.doe@example.com"));
        outputUsers.add(new User(2L, "Jane Doe", "jane.doe@example.com"));

        when(userRepository.findAll()).thenReturn(outputUsers);

        // act : thực hiện hành động cần test
        List<User> result = this.userService.getAllUsers();

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("jane.doe@example.com", result.get(1).getEmail());
    }



    // ============ getUserById ===============
    @Test
    public void getUserById_shouldReturnOptionalUser() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test
        Long inputId = 1L;
        User inputUser = new User(inputId, "John Doe", "john.doe@example.com");
        Optional<User> userOptionalOutput = Optional.of(inputUser);

        when(this.userRepository.findById(inputId)).thenReturn(userOptionalOutput);

        // act : thực hiện hành động cần test
        Optional<User> result = this.userService.getUserById(inputId);

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertTrue(result.isPresent());
        assertEquals(inputUser, result.get());
    }

    // ============ updateUser ===============
    @Test
    public void updateUser_shouldReturnUser_whenValid() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test
        Long inputId = 1L;
        User inputUser = new User(inputId, "old name Doe", "john.doe@example.com");
        User outputUpdatedUser = new User(inputId, "new name Smith", "john.smith@example.com");

        when(this.userRepository.findById(inputId)).thenReturn(Optional.of(inputUser));
        when(this.userRepository.save(any())).thenReturn(outputUpdatedUser);

        // act : thực hiện hành động cần test
        User result = this.userService.updateUser(inputId, outputUpdatedUser);

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertEquals(outputUpdatedUser, result);
        assertEquals("new name Smith", result.getName());
    }

    // ============ deleteUser ===============
    @Test
    public void deleteUser_shouldReturnVoid_WhenUserExists() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test
        Long inputId = 1L;

        when(this.userRepository.existsById(inputId)).thenReturn(true);

        // act : thực hiện hành động cần test
        this.userService.deleteUser(inputId);

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        // verify rằng phương thức deleteById đã được gọi với đúng tham số vì function deleteUser
        // trả về void nên không có giá trị nào để assert
        verify(this.userRepository).deleteById(inputId);
    }


    @Test
    public void deleteUser_shouldThrowException_WhenUserDoesNotExist() {
        // arrange : chuẩn bị dữ liệu và môi trường cho test
        Long inputId = 1L;

        when(this.userRepository.existsById(inputId)).thenReturn(false);

        // act : thực hiện hành động cần test
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.userService.deleteUser(inputId);
        });

        // assert : kiểm tra kết quả trả về có đúng như mong đợi hay không
        assertEquals("User not found", ex.getMessage());
    }
}
