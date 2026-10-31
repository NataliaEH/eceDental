package mx.eceDental.delegate;

import java.time.LocalDate;
import java.util.List;
import mx.eceDental.entity.DiagnosticoYPlan;
import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateDiagnosticoYPlan {

    public boolean registrar(int idExpediente, String diagnostico, String procedimiento, LocalDate fecha) {
        if (idExpediente <= 0
                || diagnostico == null || diagnostico.trim().isEmpty()
                || procedimiento == null || procedimiento.trim().isEmpty()
                || fecha == null) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            DiagnosticoYPlan diagnosticoPlan = new DiagnosticoYPlan();
            diagnosticoPlan.setDiagnostico(diagnostico.trim());
            diagnosticoPlan.setProcedimiento(procedimiento.trim());
            diagnosticoPlan.setFecha(fecha);
            diagnosticoPlan.setExpediente(expediente);

            ServiceLocator.getInstanceDiagnosticoYPlanDAO().save(diagnosticoPlan);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente){
        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return null;
            }

            return ServiceLocator.getInstanceDiagnosticoYPlanDAO().findFromWhere("expediente","idExpediente", String.valueOf(idExpediente));
        } catch (Exception e) {
            return null;
        }
    }
}