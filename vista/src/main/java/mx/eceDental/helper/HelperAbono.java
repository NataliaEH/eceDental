package mx.eceDental.helper;


import mx.eceDental.integration.ServiceFacadeLocator;
import java.util.List;

public class HelperAbono {
    public boolean registrar(int idExpediente, double abono, String concepto){
        return ServiceFacadeLocator.getInstanceFacadeAbono().registrar(idExpediente, abono, concepto);
    }

    public boolean modificar(int idAbono, double abono, String concepto){
        return ServiceFacadeLocator.getInstanceFacadeAbono().modificar(idAbono, abono, concepto);
    }

    public boolean eliminar(int idAbono){
        return ServiceFacadeLocator.getInstanceFacadeAbono().eliminar(idAbono);
    }

    public List<Abono> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeAbono().consultar(idExpediente);
    }
}