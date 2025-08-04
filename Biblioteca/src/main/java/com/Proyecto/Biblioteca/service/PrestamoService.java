package com.Proyecto.Biblioteca.service;
import com.Proyecto.Biblioteca.exceptions.BusinessException;
import com.Proyecto.Biblioteca.exceptions.EntityNotFoundException;
import com.Proyecto.Biblioteca.model.*;
import com.Proyecto.Biblioteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;


@Service
@Transactional
public class PrestamoService {
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private EjemplarRepository ejemplarRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Prestamo realizarPrestamo(Long usuarioId, String codigoEjemplar) {
        // Validar límite de préstamos
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        long prestamosActivos = prestamoRepository.countByUsuarioAndFechaDevolucionIsNull(usuario);
        if (prestamosActivos >= 5) {
            throw new BusinessException("Límite de 5 préstamos activos alcanzado");
        }

        // Validar disponibilidad del ejemplar
        Ejemplar ejemplar = ejemplarRepository.findById(codigoEjemplar)
            .orElseThrow(() -> new EntityNotFoundException("Ejemplar no encontrado"));
        if (ejemplar.isPrestado()) {
            throw new BusinessException("El ejemplar ya está prestado");
        }

        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaVencimiento(LocalDate.now().plusDays(15));
        
        ejemplar.setPrestado(true);
        ejemplarRepository.save(ejemplar);
        
        return prestamoRepository.save(prestamo);
    }
}