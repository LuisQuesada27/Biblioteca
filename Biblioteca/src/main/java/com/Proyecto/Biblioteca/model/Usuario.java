package com.Proyecto.Biblioteca.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // ----- Constructores -----
    public Usuario() {}

    public Usuario(String username, String password, String email, Rol rol) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    // ----- UserDetails Methods -----
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambiar a false para cuentas expiradas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambiar a false para cuentas bloqueadas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambiar a false para credenciales expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambiar a false para cuentas desactivadas
    }

    // ----- Getters y Setters -----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // ----- Métodos Auxiliares -----
    @Override
    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", rol=" + rol +
               '}';
    }

    public enum Rol {
    ADMIN,  // Acceso completo
    USER    // Acceso limitado
}
}