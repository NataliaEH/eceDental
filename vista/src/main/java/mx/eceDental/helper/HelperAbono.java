package mx.eceDental.helper;

import mx.eceDental.entity.Abono;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.util.List;

public class HelperAbono {
    public boolean registrar(int idExpediente, double monto, String concepto){
        return ServiceFacadeLocator.getInstanceFacadeAbono().registrar(idExpediente, monto, concepto);
    }

    public boolean modificar(int idAbono, double monto, String concepto){
        return ServiceFacadeLocator.getInstanceFacadeAbono().modificar(idAbono, monto, concepto);
    }

    public boolean eliminar(int idAbono){
        return ServiceFacadeLocator.getInstanceFacadeAbono().eliminar(idAbono);
    }

    public List<Abono> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeAbono().consultar(idExpediente);
    }
    public Abono buscar(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeAbono().buscar(idRegistro);
    }
}