package co.edu.udea.vuelosback.services;

import co.edu.udea.vuelosback.application.UserService;
import co.edu.udea.vuelosback.core.dao.UserRepository;
import co.edu.udea.vuelosback.core.dto.UserResponseDto;
import co.edu.udea.vuelosback.core.models.AplicationRole;
import co.edu.udea.vuelosback.core.models.User;
import graphql.GraphQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("1234567890");
        // Assuming User has a method getAplicationRole() that returns an object with getRol() method
        user.setAplicationRole(AplicationRole.usuario);
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDto userResponseDto = userService.getUserById(userId);

        assertNotNull(userResponseDto);
        assertEquals(userId, userResponseDto.getId());
        assertEquals("John Doe", userResponseDto.getFullName());
        assertEquals("john.doe@example.com", userResponseDto.getEmail());
        assertEquals("1234567890", userResponseDto.getPhoneNumber());
        assertEquals("USER", userResponseDto.getRole());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(GraphQLException.class, () -> userService.getUserById(userId));
    }
}