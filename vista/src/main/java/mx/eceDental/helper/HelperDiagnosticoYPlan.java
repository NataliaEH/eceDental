package mx.eceDental.helper;

import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.integration.ServiceFacadeLocator;
import mx.eceDental.entity.DiagnosticoYPlan;
import java.util.List;

public class HelperDiagnosticoYPlan {
    public boolean registrar(int idExpediente, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().registrar(idExpediente, diagnostico, procedimiento);
    }
    public boolean modificar(int idDiagnosticoPlan, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().modificar(idDiagnosticoPlan, diagnostico, procedimiento);
    }
    public boolean eliminar(int id){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().eliminar(id);
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().consultar(idExpediente);
    }
    public DiagnosticoYPlan buscar(int idRegistro){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().buscar(idRegistro);
    }
}