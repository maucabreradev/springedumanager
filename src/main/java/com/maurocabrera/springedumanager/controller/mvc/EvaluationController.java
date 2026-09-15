package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Evaluacion;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/evaluations")
public class EvaluationController {

    private final EvaluacionService evaluacionService;

    public EvaluationController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("evaluaciones", evaluacionService.findAll());
        return "evaluation/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("evaluacion", evaluacionService.findById(id));
        return "evaluation/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluation/form";
    }

    @PostMapping
    public String create(@ModelAttribute Evaluacion evaluacion, RedirectAttributes redirectAttributes) {
        evaluacionService.save(evaluacion);
        redirectAttributes.addFlashAttribute("message", "Evaluación creada exitosamente");
        return "redirect:/admin/evaluations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("evaluacion", evaluacionService.findById(id));
        return "evaluation/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Evaluacion evaluacion, RedirectAttributes redirectAttributes) {
        evaluacion.setId(id);
        evaluacionService.save(evaluacion);
        redirectAttributes.addFlashAttribute("message", "Evaluación actualizada exitosamente");
        return "redirect:/admin/evaluations";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        evaluacionService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Evaluación eliminada exitosamente");
        return "redirect:/admin/evaluations";
    }
}
