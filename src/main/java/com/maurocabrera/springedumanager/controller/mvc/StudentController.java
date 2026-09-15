package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.entity.Practica;
import com.maurocabrera.springedumanager.entity.Evaluacion;
import com.maurocabrera.springedumanager.entity.Matricula;
import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.service.MatriculaService;
import com.maurocabrera.springedumanager.service.PracticaService;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class StudentController {

    private final CursoServiceImpl cursoService;
    private final PracticaService practicaService;
    private final EvaluacionService evaluacionService;
    private final MatriculaService matriculaService;

    public StudentController(CursoServiceImpl cursoService, PracticaService practicaService, EvaluacionService evaluacionService, MatriculaService matriculaService) {
        this.cursoService = cursoService;
        this.practicaService = practicaService;
        this.evaluacionService = evaluacionService;
        this.matriculaService = matriculaService;
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

    @GetMapping("/course/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        Curso curso = cursoService.findById(id);
        model.addAttribute("curso", curso);
        return "student/course-detail";
    }

    @GetMapping("/practices")
    public String practices(Model model) {
        List<Practica> practicas = practicaService.findAll();
        model.addAttribute("practicas", practicas);
        return "student/practices";
    }

    @GetMapping("/evaluations")
    public String evaluations(Model model) {
        List<Evaluacion> evaluaciones = evaluacionService.findAll();
        model.addAttribute("evaluaciones", evaluaciones);
        return "student/evaluations";
    }

    @GetMapping("/enroll/{cursoId}")
    public String enroll(@PathVariable Long cursoId, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Estudiante estudiante = (Estudiante) authentication.getPrincipal();
            matriculaService.enrollStudent(estudiante.getId(), cursoId);
            redirectAttributes.addFlashAttribute("message", "Matriculado exitosamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/estudiante/courses";
    }

    @GetMapping("/myCourses")
    public String myCourses(Authentication authentication, Model model) {
        Estudiante estudiante = (Estudiante) authentication.getPrincipal();
        List<Matricula> matriculas = matriculaService.findByEstudianteId(estudiante.getId());
        model.addAttribute("matriculas", matriculas);
        return "student/my-courses";
    }

    @GetMapping("/myPractices")
    public String myPractices(Authentication authentication, Model model) {
        Estudiante estudiante = (Estudiante) authentication.getPrincipal();
        List<Practica> practicas = practicaService.findByCursoIdIn(
            matriculaService.findByEstudianteId(estudiante.getId()).stream()
                .map(m -> m.getCurso().getId()).toList());
        model.addAttribute("practicas", practicas);
        return "student/practices";
    }

    @GetMapping("/myEvaluations")
    public String myEvaluations(Authentication authentication, Model model) {
        Estudiante estudiante = (Estudiante) authentication.getPrincipal();
        List<Evaluacion> evaluaciones = evaluacionService.findByCursoIdIn(
            matriculaService.findByEstudianteId(estudiante.getId()).stream()
                .map(m -> m.getCurso().getId()).toList());
        model.addAttribute("evaluaciones", evaluaciones);
        return "student/evaluations";
    }

}
