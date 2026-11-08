package mx.eceDental.helper;

import mx.eceDental.integration.ServiceFacadeLocator;
import mx.eceDental.entity.DiagnosticoYPlan;
import java.time.LocalDate;
import java.util.List;

public class HelperDiagnosticoYPlan {
    public boolean registrar(int idExpediente, String diagnostico, String procedimiento){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().registrar(idExpediente, diagnostico, procedimiento);
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().consultar(idExpediente);
    }
}
