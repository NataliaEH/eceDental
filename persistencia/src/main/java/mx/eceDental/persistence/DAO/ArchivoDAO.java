package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Archivo;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class ArchivoDAO extends AbstractDAO<Archivo> {
    private final EntityManager entityManager;

    public ArchivoDAO(EntityManager em) {
        super(Archivo.class);
        this.entityManager = em;
    }

    public List<Archivo> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Archivo u", Archivo.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}