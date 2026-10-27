package mx.eceDental.facade;


import java.util.Date;
import mx.desarrollo.delegate.DelegateDiagnosticoYPlan;

public class FacadeDiagnosticoYPlan {

    private final DelegateDiagnosticoYPlan delegateDiagnosticoYPlan;

    public FacadeDiagnosticoYPlan() {
        this.delegateDiagnosticoYPlan = new DelegateDiagnosticoYPlan();
    }

    public boolean registrar(int idExpediente, String diagnostico, String procedimiento, Date fecha) {
        return delegateDiagnosticoYPlan.registrar(idExpediente, diagnostico, procedimiento, fecha);
    }
}