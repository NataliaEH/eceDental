package mx.eceDental.helper;

import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.util.List;

public class HelperNotaEvolucion {
    public boolean registrar(int idExpediente, String contenido){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().registrar(idExpediente, contenido);
    }

    public boolean modificar(int idNota, String contenido){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().modificar(idNota, contenido);
    }

    public boolean eliminar(int idNota){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().eliminar(idNota);
    }

    public NotaEvolucion buscarUltima(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().buscarUltima(idExpediente);
    }

    public List<NotaEvolucion> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().consultar(idExpediente);
    }
    public NotaEvolucion buscar(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeNotaEvolucion().buscar(idRegistro);
    }
}