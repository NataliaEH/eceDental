package mx.eceDental.helper;


import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.integration.ServiceFacadeLocator;

import java.util.List;

public class HelperOdontograma {
    public boolean registrarRegistro(int idExpediente, String piezaDental, List<String> cara, String color, String diagnostico, String procedimiento){
        System.out.println("helper");
        try{
            return ServiceFacadeLocator.getInstanceFacadeOdontograma().registrarRegistro(idExpediente, piezaDental, cara, color, diagnostico, procedimiento);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarRegistro(int idRegistro, String piezaDental, List<String> cara, String color, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().modificarRegistro(idRegistro, piezaDental, cara, color, diagnostico, procedimiento);
    }

    public boolean eliminarRegistro(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().eliminarRegistro(idRegistro);
    }

    public List<RegistroOdontograma> consultarReg(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().consultarReg(idExpediente);
    }
    public RegistroOdontograma buscarRegistro(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeOdontograma().buscarRegistro(idRegistro);
    }
}