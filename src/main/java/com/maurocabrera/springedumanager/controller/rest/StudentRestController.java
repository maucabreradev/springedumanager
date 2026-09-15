package com.maurocabrera.springedumanager.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    @GetMapping
    public ResponseEntity<Map<String, String>> findAll() {
        return ResponseEntity.ok(Map.of("message", "Endpoints de estudiantes"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("message", "Estudiante " + id));
    }
}