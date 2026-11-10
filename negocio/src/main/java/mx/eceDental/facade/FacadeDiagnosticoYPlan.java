package mx.eceDental.facade;


import mx.eceDental.delegate.DelegateDiagnosticoYPlan;
import mx.eceDental.entity.DiagnosticoYPlan;

public class FacadeDiagnosticoYPlan {

    private final DelegateDiagnosticoYPlan delegateDiagnosticoYPlan;

    public FacadeDiagnosticoYPlan() {
        this.delegateDiagnosticoYPlan = new DelegateDiagnosticoYPlan();
    }

    public boolean registrar(int idExpediente, String diagnostico, String procedimiento) {
        return delegateDiagnosticoYPlan.registrar(idExpediente, diagnostico, procedimiento);
    }

    public boolean modificar(int idDiagnosticoPlan, String diagnostico, String procedimiento) {
        return delegateDiagnosticoYPlan.modificar(idDiagnosticoPlan, diagnostico, procedimiento);
    }

    public boolean eliminar(int id) {
        return delegateDiagnosticoYPlan.eliminar(id);
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente) {
        return delegateDiagnosticoYPlan.consultar(idExpediente);
    }
}