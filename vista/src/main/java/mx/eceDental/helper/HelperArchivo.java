package mx.eceDental.helper;

import mx.eceDental.entity.Archivo;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.integration.ServiceFacadeLocator;

import java.io.File;
import java.util.List;

public class HelperArchivo {
    public boolean registrar(int idExpediente, String nombreArchivo, String tipo, File file){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().registrar(idExpediente, nombreArchivo, tipo, file);
    }
    public boolean eliminar(int id){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().eliminar(id);
    }

    public boolean descargar(int id, File destino){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().descargar(id,destino);
    }
    public boolean abrirArchivo(int id){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().abrirArchivo(id);
    }

    public List<Archivo> buscarPorTipo(String tipo){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().buscarPorTipo(tipo);
    }
    public List<Archivo> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().consultar(idExpediente);
    }
    public Archivo buscar(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeArchivo().buscar(idRegistro);
    }
}