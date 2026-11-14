package mx.eceDental.facade;

import java.util.List;
import mx.eceDental.delegate.DelegateNotaEvolucion;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.entity.RegistroOdontograma;


public class FacadeNotaEvolucion {

    private final DelegateNotaEvolucion delegateNotaEvolucion;

    public FacadeNotaEvolucion() {
        this.delegateNotaEvolucion = new DelegateNotaEvolucion();
    }

    public boolean registrar(int idExpediente, String contenido) {
        return delegateNotaEvolucion.registrar(idExpediente, contenido);
    }

    public boolean modificar(int idNota, String contenido) {
        return delegateNotaEvolucion.modificar(idNota, contenido);
    }

    public boolean eliminar(int id) {
        return delegateNotaEvolucion.eliminar(id);
    }

    public NotaEvolucion buscarUltima(int idExpediente) {
        return delegateNotaEvolucion.buscarUltima(idExpediente);
    }
    public NotaEvolucion buscar(int idRegistro){
        return delegateNotaEvolucion.buscar(idRegistro);
    }
    public List<NotaEvolucion> consultar(int idExpediente) {
        return delegateNotaEvolucion.consultar(idExpediente);
    }
}