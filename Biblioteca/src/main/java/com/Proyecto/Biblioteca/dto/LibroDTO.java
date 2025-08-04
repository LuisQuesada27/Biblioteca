package com.Proyecto.Biblioteca.dto;

import com.Proyecto.Biblioteca.model.Autor;
import com.Proyecto.Biblioteca.model.Categoria;
import java.util.List;
import java.util.stream.Collectors;

public class LibroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private String editorial;
    private String descripcion;
    private String imagenPortada;
    private List<String> autores;
    private String categoria;
    private Integer ejemplaresDisponibles;

    // Constructor para mapear desde la entidad Libro
    public LibroDTO(Libro libro, int ejemplaresDisponibles) {
        this.id = libro.getId();
        this.titulo = libro.getTitulo();
        this.isbn = libro.getIsbn();
        this.anioPublicacion = libro.getAnioPublicacion();
        this.editorial = libro.getEditorial();
        this.descripcion = libro.getDescripcion();
        this.imagenPortada = libro.getImagenPortada();
        this.autores = libro.getAutores().stream()
                          .map(Autor::getNombreCompleto)
                          .collect(Collectors.toList());
        this.categoria = libro.getCategoria() != null ? libro.getCategoria().getNombre() : null;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    // Getters (no necesitamos setters para este caso de uso)
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIsbn() { return isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public String getEditorial() { return editorial; }
    public String getDescripcion() { return descripcion; }
    public String getImagenPortada() { return imagenPortada; }
    public List<String> getAutores() { return autores; }
    public String getCategoria() { return categoria; }
    public Integer getEjemplaresDisponibles() { return ejemplaresDisponibles; }

    // Método para mostrar autores como String (útil para Thymeleaf)
    public String getAutoresFormateados() {
        return String.join(", ", autores);
    }
}