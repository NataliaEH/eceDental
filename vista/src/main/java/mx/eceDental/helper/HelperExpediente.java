package mx.eceDental.helper;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.io.Serializable;
import java.util.List;

public class HelperExpediente {
    public Expediente buscar(int id){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().buscar(id);
    }

    public boolean exportar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().exportar(idExpediente);
    }

    public List<Expediente> consultar(){
        return ServiceFacadeLocator.getInstanceFacadeExpediente().consultar();
    }
}
