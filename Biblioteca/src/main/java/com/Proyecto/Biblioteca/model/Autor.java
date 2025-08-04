package com.Proyecto.Biblioteca.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombreCompleto;
    
    private String paisOrigen;
    
    @ManyToMany(mappedBy = "autores")
    private Set<Libro> libros = new HashSet<>();

    // Constructores
    public Autor() {}
    
    public Autor(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    // Método para imprimir
    @Override
    public String toString() {
        return nombreCompleto;
    }
}