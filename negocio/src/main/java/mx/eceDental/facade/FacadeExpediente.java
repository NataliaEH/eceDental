package mx.eceDental.facade;

import java.util.List;
import mx.desarrollo.delegate.DelegateExpediente;
import mx.desarrollo.entity.Expediente;

public class FacadeExpediente {

    private final DelegateExpediente delegateExpediente;

    public FacadeExpediente() {
        this.delegateExpediente = new DelegateExpediente();
    }

    public boolean registrar(int idPaciente) {
        return delegateExpediente.registrar(idPaciente);
    }

    public boolean eliminar(int id) {
        return delegateExpediente.eliminar(id);
    }

    public boolean exportar(int id) {
        return delegateExpediente.exportar(id);
    }

    public Expediente buscar(int id) {
        return delegateExpediente.buscar(id);
    }

    public List<Expediente> consultar() {
        return delegateExpediente.consultar();
    }
}