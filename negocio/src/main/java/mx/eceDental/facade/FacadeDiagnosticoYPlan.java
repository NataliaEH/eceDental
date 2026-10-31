package mx.eceDental.facade;


import java.time.LocalDate;
import java.util.List;

import mx.eceDental.delegate.DelegateDiagnosticoYPlan;
import mx.eceDental.entity.DiagnosticoYPlan;

public class FacadeDiagnosticoYPlan {

    private final DelegateDiagnosticoYPlan delegateDiagnosticoYPlan;

    public FacadeDiagnosticoYPlan() {
        this.delegateDiagnosticoYPlan = new DelegateDiagnosticoYPlan();
    }

    public boolean registrar(int idExpediente, String diagnostico, String procedimiento, LocalDate fecha) {
        return delegateDiagnosticoYPlan.registrar(idExpediente, diagnostico, procedimiento, fecha);
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente) {
        return delegateDiagnosticoYPlan.consultar(idExpediente);
    }
}