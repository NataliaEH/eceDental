package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class RegistroOdontDAO extends AbstractDAO<RegistroOdontograma> {
    private final EntityManager entityManager;

    public RegistroOdontDAO(EntityManager em) {
        super(RegistroOdontograma.class);
        this.entityManager = em;
    }

    public List<RegistroOdontograma> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM RegistroOdontograma u", RegistroOdontograma.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}