package mx.eceDental.helper;

import mx.eceDental.entity.Recordatorio;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.time.LocalDate;
import java.util.List;

public class HelperRecordatorio {
    public boolean registrar(String contenido, LocalDate fechaLimite){
        return ServiceFacadeLocator.getInstanceFacadeRecordatorio().registrar(contenido, fechaLimite);
    }

    public boolean eliminar(int id){
        return ServiceFacadeLocator.getInstanceFacadeRecordatorio().eliminar(id);
    }

    public List<Recordatorio> buscarUrgentes(){
        return ServiceFacadeLocator.getInstanceFacadeRecordatorio().buscarUrgentes();
    }

    public List<Recordatorio> consultar(){
        return ServiceFacadeLocator.getInstanceFacadeRecordatorio().consultar();
    }
}