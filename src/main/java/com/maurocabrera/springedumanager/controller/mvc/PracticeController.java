package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Practica;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/practices")
public class PracticeController {

    private final CursoServiceImpl cursoService;

    public PracticeController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("practicas", cursoService.findAll());
        return "practice/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("practica", new Practica());
        return "practice/form";
    }

    @PostMapping
    public String create(@ModelAttribute Practica practica) {
        return "redirect:/admin/practices";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return "practice/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Practica practica) {
        return "redirect:/admin/practices";
    }
}