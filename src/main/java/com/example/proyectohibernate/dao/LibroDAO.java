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
}
