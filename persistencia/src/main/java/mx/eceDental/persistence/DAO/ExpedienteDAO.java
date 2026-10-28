package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class ExpedienteDAO extends AbstractDAO<Expediente> {
    private final EntityManager entityManager;

    public ExpedienteDAO(EntityManager em) {
        super(Expediente.class);
        this.entityManager = em;
    }

    public List<Expediente> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Expediente u", Expediente.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}