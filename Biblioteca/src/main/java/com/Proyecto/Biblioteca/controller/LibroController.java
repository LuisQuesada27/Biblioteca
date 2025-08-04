package com.Proyecto.Biblioteca.controller;

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