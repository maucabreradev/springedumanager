package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Practica;
import com.maurocabrera.springedumanager.service.PracticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/practices")
public class PracticeController {

    private final PracticaService practicaService;

    public PracticeController(PracticaService practicaService) {
        this.practicaService = practicaService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("practicas", practicaService.findAll());
        return "practice/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("practica", practicaService.findById(id));
        return "practice/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("practica", new Practica());
        return "practice/form";
    }

    @PostMapping
    public String create(@ModelAttribute Practica practica, RedirectAttributes redirectAttributes) {
        practicaService.save(practica);
        redirectAttributes.addFlashAttribute("message", "Práctica creada exitosamente");
        return "redirect:/admin/practices";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("practica", practicaService.findById(id));
        return "practice/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Practica practica, RedirectAttributes redirectAttributes) {
        practica.setId(id);
        practicaService.save(practica);
        redirectAttributes.addFlashAttribute("message", "Práctica actualizada exitosamente");
        return "redirect:/admin/practices";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        practicaService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Práctica eliminada exitosamente");
        return "redirect:/admin/practices";
    }
}
