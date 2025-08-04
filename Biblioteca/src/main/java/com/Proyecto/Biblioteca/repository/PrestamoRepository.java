package com.Proyecto.Biblioteca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Proyecto.Biblioteca.model.Prestamo;
import com.Proyecto.Biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    // Préstamos activos de un usuario
    List<Prestamo> findByUsuarioAndFechaDevolucionIsNull(Usuario usuario);
    
    // Préstamos vencidos no devueltos
    @Query("SELECT p FROM Prestamo p WHERE p.fechaVencimiento < :hoy AND p.fechaDevolucion IS NULL")
    List<Prestamo> findPrestamosVencidos(@Param("hoy") LocalDate fechaActual);
    
    // Histórico de préstamos por usuario
    List<Prestamo> findByUsuarioOrderByFechaPrestamoDesc(Usuario usuario);
}