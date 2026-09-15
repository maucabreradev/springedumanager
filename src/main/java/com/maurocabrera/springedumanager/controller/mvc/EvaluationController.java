package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Evaluacion;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/evaluations")
public class EvaluationController {

    private final CursoServiceImpl cursoService;

    public EvaluationController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("evaluaciones", cursoService.findAll());
        return "evaluation/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluation/form";
    }

    @PostMapping
    public String create(@ModelAttribute Evaluacion evaluacion) {
        return "redirect:/admin/evaluations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return "evaluation/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Evaluacion evaluacion) {
        return "redirect:/admin/evaluations";
    }
}