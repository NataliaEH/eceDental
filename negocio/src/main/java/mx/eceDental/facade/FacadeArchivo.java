package mx.eceDental.facade;

import java.io.File;
import java.util.List;
import mx.eceDental.delegate.DelegateArchivo;
import mx.eceDental.entity.Archivo;
import mx.eceDental.entity.NotaEvolucion;


public class FacadeArchivo {

    private final DelegateArchivo delegateArchivo;

    public FacadeArchivo() {
        this.delegateArchivo = new DelegateArchivo();
    }

    public boolean registrar(int idExpediente, String nombreArchivo, String tipo, File file) {
        return delegateArchivo.registrar(idExpediente, nombreArchivo, tipo, file);
    }

    public boolean eliminar(int id) {
        return delegateArchivo.eliminar(id);
    }

    public boolean descargar(int id,File destino) {
        return delegateArchivo.descargar(id,destino);
    }
    public boolean abrirArchivo(int id) {
        return delegateArchivo.abrirArchivo(id);
    }

    public List<Archivo> buscarPorTipo(String tipo) {
        return delegateArchivo.buscarPorTipo(tipo);
    }
    public Archivo buscar(int idRegistro){
        return delegateArchivo.buscar(idRegistro);
    }
    public List<Archivo> consultar(int idExpediente) {
        return delegateArchivo.consultar(idExpediente);
    }
}