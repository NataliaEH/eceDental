package mx.eceDental.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Interrogatorio;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class InterrogatorioDAO extends AbstractDAO<Interrogatorio> {
    private final EntityManager entityManager;

    public InterrogatorioDAO(EntityManager em) {
        super(Interrogatorio.class);
        this.entityManager = em;
    }

    public List<Interrogatorio> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Interrogatorio u", Interrogatorio.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}