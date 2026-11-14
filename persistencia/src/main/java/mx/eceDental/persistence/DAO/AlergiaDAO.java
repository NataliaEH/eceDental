package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Alergia;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class AlergiaDAO extends AbstractDAO<Alergia> {
    private final EntityManager entityManager;

    public AlergiaDAO(EntityManager em) {
        super(Alergia.class);
        this.entityManager = em;
    }

    public List<Alergia> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Alergia u", Alergia.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}