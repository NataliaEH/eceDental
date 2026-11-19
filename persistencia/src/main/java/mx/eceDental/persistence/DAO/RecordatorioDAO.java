package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Recordatorio;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class RecordatorioDAO extends RecordatorioDAO<Doctor> {
    private final EntityManager entityManager;

    public RecordatorioDAO(EntityManager em) {
        super(Recordatorio.class);
        this.entityManager = em;
    }

    public List<Recordatorio> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Recordatorio u", Recordatorio.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}