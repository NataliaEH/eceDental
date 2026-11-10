package mx.eceDental.facade;

import java.util.Date;
import java.util.List;
import mx.eceDental.delegate.DelegateArchivo;


public class FacadeArchivo {

    private final DelegateArchivo delegateArchivo;

    public FacadeArchivo() {
        this.delegateArchivo = new DelegateArchivo();
    }

    public boolean registrar(int idExpediente, String nombreArchivo, Date fecha, String tipo, String ruta) {
        return delegateArchivo.registrar(idExpediente, nombreArchivo, fecha, tipo, ruta);
    }

    public boolean eliminar(int id) {
        return delegateArchivo.eliminar(id);
    }

    public boolean descargar(int id) {
        return delegateArchivo.descargar(id);
    }

    public List<Archivo> buscarPorTipo(String tipo) {
        return delegateArchivo.buscarPorTipo(tipo);
    }

    public List<Archivo> consultar(int idExpediente) {
        return delegateArchivo.consultar(idExpediente);
    }
}