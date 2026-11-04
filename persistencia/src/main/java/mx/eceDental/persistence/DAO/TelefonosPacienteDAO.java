package mx.eceDental.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.TelefonosPaciente;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class TelefonosPacienteDAO extends AbstractDAO<TelefonosPaciente> {
    private final EntityManager entityManager;

    public TelefonosPacienteDAO(EntityManager em) {
        super(TelefonosPaciente.class);
        this.entityManager = em;
    }

    public List<TelefonosPaciente> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM TelefonosPaciente u", TelefonosPaciente.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}