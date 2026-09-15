package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.exception.ResourceNotFoundException;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("curso", cursoService.findById(id));
        return "course/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("curso", new Curso());
        return "course/form";
    }

    @PostMapping
    public String create(@ModelAttribute Curso curso, RedirectAttributes redirectAttributes) {
        cursoService.save(curso);
        redirectAttributes.addFlashAttribute("message", "Curso creado exitosamente");
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("curso", cursoService.findById(id));
        return "course/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Curso curso, RedirectAttributes redirectAttributes) {
        curso.setId(id);
        cursoService.save(curso);
        redirectAttributes.addFlashAttribute("message", "Curso actualizado exitosamente");
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cursoService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Curso eliminado exitosamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el curso");
        }
        return "redirect:/admin/courses";
    }
}
