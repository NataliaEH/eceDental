package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateNotaEvolucion;

import java.util.Date;
import java.util.List;


public class FacadeNotaEvolucion {

    private final DelegateNotaEvolucion delegateNotaEvolucion;

    public FacadeNotaEvolucion() {
        this.delegateNotaEvolucion = new DelegateNotaEvolucion();
    }

    public boolean registrar(int idExpediente, Date fecha, String contenido) {
        return delegateNotaEvolucion.registrar(idExpediente, fecha, contenido);
    }

    public List<NotaEvolucion> buscarPorFecha(Date fecha) {
        return delegateNotaEvolucion.buscarPorFecha(fecha);
    }

    public NotaEvolucion buscarUltima() {
        return delegateNotaEvolucion.buscarUltima();
    }

    public List<NotaEvolucion> consultar(int idExpediente) {
        return delegateNotaEvolucion.consultar(idExpediente);
    }
}
