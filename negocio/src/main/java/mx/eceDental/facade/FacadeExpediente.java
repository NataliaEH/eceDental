package mx.eceDental.facade;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
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

    public boolean eliminar(int id) {
        return delegateExpediente.eliminar(id);
    }

    public boolean exportar(int id, File destino, List<String> modulos, LocalDate desde, LocalDate hasta) {
        return delegateExpediente.exportar(id, destino, modulos, desde, hasta);
    }

    public Expediente buscar(int id) {
        return delegateExpediente.buscar(id);
    }

    public Expediente buscarPorPaciente(int idPaciente) {
        return delegateExpediente.buscarPorPaciente(idPaciente);
    }

    public List<Expediente> consultar() {
        return delegateExpediente.consultar();
    }
}