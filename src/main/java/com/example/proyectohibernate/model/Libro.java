package com.example.proyectohibernate.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity //representa una figura de base de datos
@Table(name = "libro", schema = "catalogos") //su equivalente en la base de datos
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     @Column(name = "titulo", nullable = false, length = 150)
     private String titulo;

     @Column(name = "autor", nullable = false, length = 120)
     private String autor;

     @Column(name = "anio_publicacion", nullable = false)
     private int anioPublicacion;

     @Column(name = "precio", nullable = false, precision = 10, scale = 2)
     private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false) // Nombre de la columna clave foránea en tu tabla libro
    private Categoria categoria;

    public Libro() {
    }

    public Libro(String titulo, String autor, int anioPublicacion, BigDecimal precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio( BigDecimal precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
