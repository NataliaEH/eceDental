package mx.eceDental.helper;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import mx.eceDental.entity.Expediente;
import mx.eceDental.integration.ServiceFacadeLocator;

public class HelperExpediente {
    public Expediente buscar(int id){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().buscar(id);
    }
    public Expediente buscarPorPaciente(int idPaciente){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().buscarPorPaciente(idPaciente);
    }
    public boolean exportar(int idExpediente, File destino, List<String> modulos, LocalDate desde, LocalDate hasta){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().exportar(idExpediente, destino, modulos, desde, hasta);
    }

    public boolean eliminar(int id){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().eliminar(id);
    }

    public List<Expediente> consultar(){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().consultar();
    }
}