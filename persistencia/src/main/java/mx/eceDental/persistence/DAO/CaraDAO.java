package mx.eceDental.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Cara;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class CaraDAO extends AbstractDAO<Cara> {
    private final EntityManager entityManager;

    public CaraDAO(EntityManager em) {
        super(Cara.class);
        this.entityManager = em;
    }

    public List<Cara> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Cara u", Cara.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}