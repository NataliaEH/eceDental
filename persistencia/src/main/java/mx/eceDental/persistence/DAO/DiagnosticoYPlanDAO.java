package mx.eceDental.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.DiagnosticoYPlan;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class DiagnosticoYPlanDAO extends AbstractDAO<DiagnosticoYPlan> {
    private final EntityManager entityManager;

    public DiagnosticoYPlanDAO(EntityManager em) {
        super(DiagnosticoYPlan.class);
        this.entityManager = em;
    }

    public List<DiagnosticoYPlan> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM DiagnosticoYPlan u", DiagnosticoYPlan.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}