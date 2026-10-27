package mx.eceDental.helper;

import mx.eceDental.integration.ServiceFacadeLocator;
import mx.eceDental.entity.DiagnosticoYPlan;
import java.time.LocalTime;
import java.util.List;

public class HelperDiagnosticoYPlan {
    public boolean registrar(String diagnostico, String procedimiento, LocalTime fechaExpediente){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().registrar(diagnostico, procedimiento, fechaExpediente);
    }

    public List<DiagnosticoYPlan> consultar(){
        return ServiceFacadeLocator.getInstanceFacadeDiagnosticoYPlan().consultar();
    }
}
