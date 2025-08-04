package com.Proyecto.Biblioteca.repository;

import com.Proyecto.Biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Para autenticación
    Optional<Usuario> findByUsername(String username);
    
    // Verificar existencia de email (para registro)
    boolean existsByEmail(String email);
    
    // Búsqueda por DNI
    Optional<Usuario> findByDni(String dni);
}