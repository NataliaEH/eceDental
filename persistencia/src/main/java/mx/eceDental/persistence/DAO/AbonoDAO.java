package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Abono;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class AbonoDAO extends AbstractDAO<Abono> {
    private final EntityManager entityManager;

    public AbonoDAO(EntityManager em) {
        super(Abono.class);
        this.entityManager = em;
    }

    public List<Abono> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Abono u", Abono.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}