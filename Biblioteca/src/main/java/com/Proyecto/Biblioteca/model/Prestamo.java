package com.Proyecto.Biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    private Ejemplar ejemplar;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    private LocalDate fechaDevolucion;

    private Double multaGenerada;

    // Constructor por defecto
    public Prestamo() {}

    // Constructor con parámetros esenciales
    public Prestamo(Usuario usuario, Ejemplar ejemplar, LocalDate fechaPrestamo, int diasPrestamo) {
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaPrestamo.plusDays(diasPrestamo);
    }

    // ---- Getters ----
    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public Double getMultaGenerada() {
        return multaGenerada;
    }

    // ---- Setters ----
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
        if (fechaDevolucion != null) {
            this.calcularMulta();
        }
    }

    public void setMultaGenerada(Double multaGenerada) {
        this.multaGenerada = multaGenerada;
    }

    // ---- Métodos de Negocio ----
    /**
     * Calcula la multa si hay retraso en la devolución
     */
    public void calcularMulta() {
        if (fechaDevolucion != null && fechaDevolucion.isAfter(fechaVencimiento)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaVencimiento, fechaDevolucion);
            this.multaGenerada = diasRetraso * 1.0; // $1 por día de retraso
        } else {
            this.multaGenerada = 0.0;
        }
    }

    /**
     * Verifica si el préstamo está activo (no devuelto)
     */
    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    // ---- Sobrescritura de toString ----
    @Override
    public String toString() {
        return "Préstamo [ID: " + id + ", Usuario: " + usuario.getUsername() + 
               ", Ejemplar: " + ejemplar.getCodigoEjemplar() + 
               ", Estado: " + (estaActivo() ? "ACTIVO" : "DEVUELTO") + "]";
    }
}