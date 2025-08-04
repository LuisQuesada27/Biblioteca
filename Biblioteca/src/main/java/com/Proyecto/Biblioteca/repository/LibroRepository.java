package com.Proyecto.Biblioteca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.Biblioteca.model.Libro;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Búsqueda por título (paginada, insensible a mayúsculas)
    Page<Libro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    
    // Búsqueda por autor (usando relación ManyToMany)
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a.nombreCompleto LIKE %:nombreAutor%")
    List<Libro> findByAutorNombre(@Param("nombreAutor") String nombreAutor);
    
    // Contar ejemplares disponibles de un libro
    @Query("SELECT COUNT(e) FROM Ejemplar e WHERE e.libro.id = :libroId AND e.prestado = false")
    int countEjemplaresDisponibles(@Param("libroId") Long libroId);

     // Verificar existencia por ISBN
    boolean existsByIsbn(String isbn);
}