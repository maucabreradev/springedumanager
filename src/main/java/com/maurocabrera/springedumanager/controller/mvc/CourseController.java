package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    private final CursoServiceImpl cursoService;

    public CourseController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "course/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("curso", new Curso());
        return "course/form";
    }

    @PostMapping
    public String create(@ModelAttribute Curso curso) {
        cursoService.save(curso);
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("curso", cursoService.findById(id));
        return "course/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Curso curso) {
        curso.setId(id);
        cursoService.save(curso);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cursoService.findById(id);
        // TODO: implement soft delete or cascade
        return "redirect:/admin/courses";
    }
}