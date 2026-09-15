package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.entity.Role;
import com.maurocabrera.springedumanager.repository.EstudianteRepository;
import com.maurocabrera.springedumanager.repository.RoleRepository;
import com.maurocabrera.springedumanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void whenRegisterStudent_thenReturnStudent() {
        when(estudianteRepository.existsByEmail("test@email.com")).thenReturn(false);
        Role studentRole = new Role();
        studentRole.setName("ESTUDIANTE");
        when(roleRepository.findByName("ESTUDIANTE")).thenReturn(Optional.of(studentRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        Estudiante saved = new Estudiante();
        saved.setId(1L);
        saved.setEmail("test@email.com");
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(saved);

        Estudiante result = userService.register("Test", "test@email.com", "password123");

        assertNotNull(result);
        assertEquals("test@email.com", result.getEmail());
        verify(estudianteRepository, times(1)).save(any(Estudiante.class));
    }

    @Test
    void whenRegisterWithExistingEmail_thenThrowException() {
        when(estudianteRepository.existsByEmail("test@email.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
            userService.register("Test", "test@email.com", "password123"));
    }
}
