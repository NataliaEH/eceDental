
package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateExpediente;
import mx.eceDental.entity.Expediente;

public class FacadeExpediente {

    private final DelegateExpediente delegateExpediente;

    public FacadeExpediente() {
        this.delegateExpediente = new DelegateExpediente();
    }

    public boolean registrar(int idPaciente) {
        return delegateExpediente.registrar(idPaciente);
    }

    public Expediente buscar(int id) {
        return delegateExpediente.buscar(id);
    }

    public boolean modificar(Expediente expediente) {
        return delegateExpediente.modificar(expediente);

    }

    public Expediente exportar(int id) {
        return delegateExpediente.exportar(id);
    }
}
