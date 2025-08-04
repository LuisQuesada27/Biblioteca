package com.Proyecto.Biblioteca.repository;

import com.Proyecto.Biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Búsqueda por nombre (exacto)
    Autor findByNombreCompleto(String nombreCompleto);
    
    // Búsqueda aproximada
    List<Autor> findByNombreCompletoContainingIgnoreCase(String nombre);
}