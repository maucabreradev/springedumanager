package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.entity.Role;
import com.maurocabrera.springedumanager.repository.EstudianteRepository;
import com.maurocabrera.springedumanager.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final EstudianteRepository estudianteRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(EstudianteRepository estudianteRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.estudianteRepository = estudianteRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Estudiante register(String nombre, String email, String rawPassword) {
        if (estudianteRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }
        Role estudianteRole = roleRepository.findByName("ESTUDIANTE")
            .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no encontrado"));
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setEmail(email);
        estudiante.setPassword(passwordEncoder.encode(rawPassword));
        estudiante.setRoles(java.util.Set.of(estudianteRole));
        return estudianteRepository.save(estudiante);
    }
}