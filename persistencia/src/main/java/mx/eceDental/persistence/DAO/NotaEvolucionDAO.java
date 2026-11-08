package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class NotaEvolucionDAO extends AbstractDAO<NotaEvolucion> {
    private final EntityManager entityManager;

    public NotaEvolucionDAO(EntityManager em) {
        super(NotaEvolucion.class);
        this.entityManager = em;
    }

    public List<NotaEvolucion> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM NotaEvolucion u", NotaEvolucion.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}