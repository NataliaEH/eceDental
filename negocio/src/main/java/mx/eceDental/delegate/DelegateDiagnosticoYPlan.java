package mx.eceDental.delegate;

import java.time.LocalDate;
import java.util.List;
import mx.eceDental.entity.DiagnosticoYPlan;
import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateDiagnosticoYPlan {


    public boolean registrar(int idExpediente, String diagnostico, String procedimiento) {
        if (idExpediente <= 0
                || diagnostico == null || diagnostico.trim().isEmpty()
                || diagnostico.trim().length() > 50
                || procedimiento == null || procedimiento.trim().isEmpty()
                || procedimiento.trim().length() > 50) {
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
            diagnosticoPlan.setFecha(LocalDate.now());
            diagnosticoPlan.setExpediente(expediente);

            ServiceLocator.getInstanceDiagnosticoYPlanDAO().save(diagnosticoPlan);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idDiagnosticoPlan, String diagnostico, String procedimiento) {
        if (idDiagnosticoPlan <= 0
                || diagnostico == null || diagnostico.trim().isEmpty()
                || procedimiento == null || procedimiento.trim().isEmpty()) {
            return false;
        }

        try {
            DiagnosticoYPlan diagnosticoPlan = ServiceLocator.getInstanceDiagnosticoYPlanDAO()
                    .find(idDiagnosticoPlan).orElse(null);

            if (diagnosticoPlan == null) {
                return false;
            }

            diagnosticoPlan.setDiagnostico(diagnostico.trim());
            diagnosticoPlan.setProcedimiento(procedimiento.trim());

            ServiceLocator.getInstanceDiagnosticoYPlanDAO().update(diagnosticoPlan);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            DiagnosticoYPlan diagnosticoPlan = ServiceLocator.getInstanceDiagnosticoYPlanDAO()
                    .find(id).orElse(null);

            if (diagnosticoPlan == null) {
                return false;
            }

            ServiceLocator.getInstanceDiagnosticoYPlanDAO().delete(diagnosticoPlan);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public List<DiagnosticoYPlan> consultar(int idExpediente) {
        if (idExpediente <= 0) {
            return null;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return null;
            }

            return ServiceLocator.getInstanceDiagnosticoYPlanDAO()
                    .findFromWhere("expediente", "idExpediente", String.valueOf(idExpediente));

        } catch (Exception e) {
            return null;
        }
    }
}