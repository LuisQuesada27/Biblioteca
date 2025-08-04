package com.Proyecto.Biblioteca.service;

import com.Proyecto.Biblioteca.dto.LibroDTO;
import com.Proyecto.Biblioteca.exceptions.BusinessException;
import com.Proyecto.Biblioteca.exceptions.ResourceNotFoundException;
import com.Proyecto.Biblioteca.model.Libro;
import com.Proyecto.Biblioteca.repository.EjemplarRepository;
import com.Proyecto.Biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private EjemplarRepository ejemplarRepository;

    // ============ CRUD BÁSICO ============
    public Libro crearLibro(Libro libro) {
        validarISBNUnico(libro.getIsbn());
        return libroRepository.save(libro);
    }

    public Libro actualizarLibro(Long id, Libro libroActualizado) {
        Libro libroExistente = obtenerLibroPorId(id);
        if (!libroExistente.getIsbn().equals(libroActualizado.getIsbn())) {
            validarISBNUnico(libroActualizado.getIsbn());
        }
        libroActualizado.setId(id);
        return libroRepository.save(libroActualizado);
    }

    @Transactional(readOnly = true)
    public Libro obtenerLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
    }

    public void eliminarLibro(Long id) {
        if (ejemplarRepository.existsByLibroIdAndPrestadoTrue(id)) {
            throw new BusinessException("No se puede eliminar: Hay ejemplares prestados de este libro");
        }
        libroRepository.deleteById(id);
    }

    // ============ BÚSQUEDAS ============
    @Transactional(readOnly = true)
    public Page<LibroDTO> buscarLibrosDisponibles(String titulo, Pageable pageable) {
        Page<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return libros.map(this::mapearADTO);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarPorAutor(String nombreAutor) {
        return libroRepository.findByAutorNombre(nombreAutor);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria);
    }

    // ============ MÉTODOS AUXILIARES ============
    private void validarISBNUnico(String isbn) {
        if (libroRepository.existsByIsbn(isbn)) {
            throw new BusinessException("El ISBN " + isbn + " ya está registrado");
        }
    }

    private LibroDTO mapearADTO(Libro libro) {
        int disponibles = ejemplarRepository.countByLibroIdAndPrestadoFalse(libro.getId());
        return new LibroDTO(libro, disponibles);
    }

    // ============ MÉTODOS PARA EJEMPLARES ============
    @Transactional(readOnly = true)
    public int contarEjemplaresDisponibles(Long libroId) {
        return ejemplarRepository.countByLibroIdAndPrestadoFalse(libroId);
    }

    @Transactional(readOnly = true)
    public int contarEjemplaresPrestados(Long libroId) {
        return ejemplarRepository.countByLibroIdAndPrestadoTrue(libroId);
    }
}