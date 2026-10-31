package mx.eceDental.delegate;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import mx.eceDental.entity.Expediente;
import mx.eceDental.entity.Paciente;
import mx.eceDental.persistence.integration.ServiceLocator;

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
            expediente.setFechaCreacion(LocalDate.now());
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
            // Solo se pueden eliminar expedientes con 5 años o más (esto puede cambiar, checar NOM)
            LocalDate limite = LocalDate.now().minusYears(5);
            if (expediente.getFechaCreacion().isAfter(limite)) {
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
    public Expediente buscarPorPaciente(int idPaciente) {
        if(idPaciente <= 0){
            System.out.println("idpaciente menor a 0");
            return null;
        }
        try {
            return ServiceLocator.getInstanceExpedienteDAO().obtenerPorPaciente(idPaciente);
        } catch (Exception e) {
            System.err.println(e);
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