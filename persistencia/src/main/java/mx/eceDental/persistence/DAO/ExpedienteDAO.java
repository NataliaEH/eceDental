package mx.eceDental.persistence.dao;

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

    public Expediente obtenerPorPaciente(int idPaciente){
        System.out.println("ID recibido: " + idPaciente);
        String jpql = "SELECT e FROM Expediente e WHERE e.paciente.idPaciente = :idPaciente";
        return execute(em ->
                em.createQuery(jpql, Expediente.class)
                        .setParameter("idPaciente", idPaciente)
                        .getSingleResult()
        );
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}