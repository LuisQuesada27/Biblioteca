package com.Proyecto.Biblioteca.service;
import com.Proyecto.Biblioteca.dto.LibroDTO;
import com.Proyecto.Biblioteca.model.Libro;
import com.Proyecto.Biblioteca.repository.EjemplarRepository;
import com.Proyecto.Biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private EjemplarRepository ejemplarRepository;

    // Guardar un libro con validación de ISBN
    public Libro saveLibro(Libro libro) {
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new IllegalStateException("ISBN ya registrado");
        }
        return libroRepository.save(libro);
    }

    // Buscar libros con disponibilidad
    public Page<LibroDTO> buscarLibrosDisponibles(String titulo, Pageable pageable) {
        Page<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return libros.map(libro -> {
            int disponibles = ejemplarRepository.countByLibroIdAndPrestadoFalse(libro.getId());
            return new LibroDTO(libro, disponibles);
        });
    }
}