package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Odontrograma;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class OdontrogramaDAO extends AbstractDAO<Odontrograma> {
    private final EntityManager entityManager;

    public OdontrogramaDAO(EntityManager em) {
        super(Odontrograma.class);
        this.entityManager = em;
    }

    public List<Odontrograma> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Odontrograma u", Odontrograma.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}