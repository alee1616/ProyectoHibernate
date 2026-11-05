package com.example.proyectohibernate.model;

import jakarta.persistence.*;

@Entity //representa una figura de base de datos
@Table(name = "libros", schema = "catalogos") //su equivalente en la base de datos

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
     private String precio;

    public Libro() {
    }

    public Libro(String titulo, String autor, int anioPublicacion, String precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

}
