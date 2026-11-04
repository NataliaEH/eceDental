package mx.eceDental.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Paciente;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class PacienteDAO extends AbstractDAO<Paciente> {
    private final EntityManager entityManager;

    public PacienteDAO(EntityManager em) {
        super(Paciente.class);
        this.entityManager = em;
    }

    public List<Paciente> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Paciente u", Paciente.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}