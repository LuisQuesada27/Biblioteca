package com.Proyecto.Biblioteca.repository;

import com.Proyecto.Biblioteca.model.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
    // Buscar ejemplares no prestados de un libro específico
    List<Ejemplar> findByLibroIdAndPrestadoFalse(Long libroId);

    // Verificar disponibilidad
    @Query("SELECT e FROM Ejemplar e WHERE e.codigo = :codigo AND e.prestado = false")
    Ejemplar findDisponibleByCodigo(@Param("codigo") String codigo);
    
    // Verificar si un ejemplar está prestado
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Prestamo p WHERE p.ejemplar.codigo = :codigoEjemplar AND p.fechaDevolucion IS NULL")
    boolean isEjemplarPrestado(@Param("codigoEjemplar") String codigoEjemplar);
}