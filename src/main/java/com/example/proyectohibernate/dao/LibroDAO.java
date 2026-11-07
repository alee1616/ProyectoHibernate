package com.example.proyectohibernate.dao;

import com.example.proyectohibernate.database.JPAUtil;
import com.example.proyectohibernate.model.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class LibroDAO {

    // Implementación para guardar un libro en la base de datos
    public void guardar(Libro libro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(libro);
            tx.commit();
        } catch (RuntimeException e){
            if(tx.isActive()){
                tx.rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }

    public List<Libro> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l ORDER BY l.id ", Libro.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean modificar(Libro libro){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            //Opción 1
            //em.merge(libro);
            //Opción 2
            Libro libroBD = em.find(Libro.class, libro.getId());

            if(libroBD == null){
                tx.rollback();
                return false;
            }

            libroBD.setTitulo(libro.getTitulo());
            libroBD.setAutor(libro.getAutor());
            libroBD.setAnioPublicacion(libro.getAnioPublicacion());
            libroBD.setPrecio(libro.getPrecio());
            tx.commit();

            return true;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }finally {
            em.close();

        }
    }

    public boolean eliminar(long id){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro libroBD = em.find(Libro.class, id);

            if(libroBD == null){
                tx.rollback();
                return false;
            }

            em.remove(libroBD);
            tx.commit();

            return true;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }finally {
            em.close();

        }
    }



}
