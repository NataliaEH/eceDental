package mx.desarrollo.delegate;

import java.util.Date;

import mx.desarrollo.entity.DiagnosticoYPlan;
import mx.desarrollo.entity.Expediente;
import mx.desarrollo.persistence.integration.ServiceLocator;

public class DelegateDiagnosticoYPlan {

    public boolean registrar(int idExpediente, String diagnostico, String procedimiento, Date fecha) {
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
}