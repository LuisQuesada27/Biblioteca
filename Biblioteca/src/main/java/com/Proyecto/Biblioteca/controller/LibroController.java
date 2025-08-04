package com.Proyecto.Biblioteca.controller;
import com.Proyecto.Biblioteca.dto.LibroDTO;
import com.Proyecto.Biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarLibros(
        @RequestParam(required = false) String titulo,
        @RequestParam(defaultValue = "0") int page,
        Model model
    ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("titulo"));
        Page<LibroDTO> libros = libroService.buscarLibrosDisponibles(titulo, pageable);
        
        model.addAttribute("libros", libros);
        model.addAttribute("titulo", titulo);
        return "libros/list";
    }
}