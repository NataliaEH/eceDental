package mx.eceDental.helper;


import mx.eceDental.integration.ServiceFacadeLocator;

public class HelperOdontograma {
    public boolean registrarRegistro(int idExpediente, int piezaDental, String cara, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().registrarRegistro(idExpediente, piezaDental, cara, diagnostico, procedimiento);
    }
    public boolean modificarRegistro(int idRegistro, int piezaDental, String cara, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().modificarRegistro(idRegistro, piezaDental, cara, diagnostico, procedimiento);
    }

    public boolean eliminarRegistro(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().eliminarRegistro(idRegistro);
    }
}