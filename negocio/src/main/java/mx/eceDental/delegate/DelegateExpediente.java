
package mx.eceDental.delegate;

import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateExpediente {

    public boolean registrar(int idPaciente) {
        if (idPaciente <= 0) {
            return false;
        }

        try {
            Expediente expediente = new Expediente();
            expediente.setIdPaciente(idPaciente);

            ServiceLocator.getInstanceExpedienteDAO().save(expediente);

            return true;

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

    public boolean modificar(Expediente expediente) {
        if (expediente == null) {
            return false;
        }

        try {
            Expediente existente = buscar(expediente.getId());

            if (existente == null) {
                return false;
            }

            ServiceLocator.getInstanceExpedienteDAO().update(expediente);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
