package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Medicacion;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class MedicacionDAO extends AbstractDAO<Medicacion> {
    private final EntityManager entityManager;

    public MedicacionDAO(EntityManager em) {
        super(Medicacion.class);
        this.entityManager = em;
    }

    public List<Medicacion> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Medicacion u", Medicacion.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}