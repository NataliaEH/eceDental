package mx.eceDental.persistence.DAO;

import jakarta.persistence.EntityManager;
import mx.eceDental.entity.Doctor;
import mx.eceDental.persistence.persistence.AbstractDAO;

import java.util.List;

public class DoctorDAO extends AbstractDAO<Doctor> {
    private final EntityManager entityManager;

    public DoctorDAO(EntityManager em) {
        super(Doctor.class);
        this.entityManager = em;
    }

    public List<Doctor> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Doctor u", Doctor.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}