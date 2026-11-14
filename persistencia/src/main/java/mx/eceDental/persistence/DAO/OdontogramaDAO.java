package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Odontograma;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class OdontogramaDAO extends AbstractDAO<Odontograma> {
    private final EntityManager entityManager;

    public OdontogramaDAO(EntityManager em) {
        super(Odontograma.class);
        this.entityManager = em;
    }

    public List<Odontograma> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Odontograma u", Odontograma.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}