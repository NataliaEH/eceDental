package mx.eceDental.delegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.desarrollo.entity.Expediente;
import mx.desarrollo.entity.Paciente;
import mx.desarrollo.persistence.integration.ServiceLocator;

public class DelegateExpediente {

    public boolean registrar(int idPaciente) {
        if (idPaciente <= 0) {
            return false;
        }

        try {
            Paciente paciente = ServiceLocator.getInstancePacienteDAO().find(idPaciente).orElse(null);

            if (paciente == null || paciente.getExpediente() != null) {
                return false;
            }

            Expediente expediente = new Expediente();
            expediente.setFechaCreacion(new Date());
            expediente.setPaciente(paciente);

            ServiceLocator.getInstanceExpedienteDAO().save(expediente);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            Expediente expediente = buscar(id);

            if (expediente == null || expediente.getFechaCreacion() == null) {
                return false;
            }

            // Solo se pueden eliminar expedientes con 5 años o más
            Calendar limite = Calendar.getInstance();
            limite.add(Calendar.YEAR, -5);

            if (expediente.getFechaCreacion().after(limite.getTime())) {
                return false;
            }

            ServiceLocator.getInstanceExpedienteDAO().delete(expediente);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean exportar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            Expediente expediente = buscar(id);

            return expediente != null;

        } catch (Exception e) {
            return false;
        }
    }

    public Expediente buscar(int id) {
        if (id <= 0) {
            return null;
        }

        try {
            return ServiceLocator.getInstanceExpedienteDAO().find(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Expediente> consultar() {
        try {
            return ServiceLocator.getInstanceExpedienteDAO().findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}