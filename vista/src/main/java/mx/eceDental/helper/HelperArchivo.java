package mx.eceDental.helper;


import mx.eceDental.integration.ServiceFacadeLocator;
import java.util.List;

public class HelperArchivo {
    public boolean registrar(int idExpediente, String nombreArchivo, String tipo, String ruta){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().registrar(idExpediente, nombreArchivo, tipo, ruta);
    }

    public boolean eliminar(int id){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().eliminar(id);
    }

    public boolean descargar(int id){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().descargar(id);
    }

    public List<Archivo> buscarPorTipo(String tipo){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().buscarPorTipo(tipo);
    }

    public List<Archivo> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().consultar(idExpediente);
    }
}