package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiante")
public class StudentController {

    private final CursoServiceImpl cursoService;

    public StudentController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "student/dashboard";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "student/courses";
    }

    @GetMapping("/practices")
    public String practices(Model model) {
        return "student/practices";
    }

    @GetMapping("/evaluations")
    public String evaluations(Model model) {
        return "student/evaluations";
    }

    @GetMapping("/course/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        Curso curso = cursoService.findById(id);
        model.addAttribute("curso", curso);
        return "student/course-detail";
    }
}