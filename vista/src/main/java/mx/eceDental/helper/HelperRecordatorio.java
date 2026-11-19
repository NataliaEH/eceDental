package mx.eceDental.helper;

import mx.eceDental.integration.ServiceFacadeLocator;
import java.time.LocalDate;

public class HelperRecordatorio {
    public boolean registrar(String contenido, LocalDate fechaLimite){
        return ServiceFacadeLocator.getInstanceFacadeRecordatorio().registrar(contenido, fechaLimite);
    }
}