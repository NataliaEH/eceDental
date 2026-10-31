package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Enfermedad;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class EnfermedadDAO extends AbstractDAO<Enfermedad> {
    private final EntityManager entityManager;

    public EnfermedadDAO(EntityManager em) {
        super(Enfermedad.class);
        this.entityManager = em;
    }

    public List<Enfermedad> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Enfermedad u", Enfermedad.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}