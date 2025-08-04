package com.Proyecto.Biblioteca.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String codigoEjemplar;
    
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    
    @OneToMany(mappedBy = "ejemplar")
    private Set<Prestamo> prestamos = new HashSet<>();

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public void setCodigoEjemplar(String codigoEjemplar) {
        this.codigoEjemplar = codigoEjemplar;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}