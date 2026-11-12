package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.RegistroOdont;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class RegistroOdontDAO extends AbstractDAO<RegistroOdont> {
    private final EntityManager entityManager;

    public RegistroOdontDAO(EntityManager em) {
        super(RegistroOdont.class);
        this.entityManager = em;
    }

    public List<RegistroOdont> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM RegistroOdont u", RegistroOdont.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}