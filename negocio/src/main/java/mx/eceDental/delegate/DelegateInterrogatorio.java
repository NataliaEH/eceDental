package mx.eceDental.delegate;


import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import mx.eceDental.entity.*;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateInterrogatorio {
    public boolean registrar(int idExpediente){
        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null || expediente.getInterrogatorio() != null) {
                return false;
            }

            Interrogatorio interrogatorio = new Interrogatorio();
            interrogatorio.setExpediente(expediente);
            ServiceLocator.getInstanceInterrogatorioDAO().save(interrogatorio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idExpediente, boolean hospitalizado, boolean atencionMedica,
                             List<Alergia> alergias, boolean hemorragia, List<Enfermedad> enfermedades,
                             List<Medicacion> medicacion, String embarazada, String amamantando,
                             String telefonoEmergencia, String telefonoServicioMedico,
                             LocalDate ultimaConsultaDental) {

        if (idExpediente <= 0
                || telefonoEmergencia == null || telefonoEmergencia.trim().isEmpty()
                || telefonoEmergencia.trim().length() > 20
                || (telefonoServicioMedico == null && telefonoServicioMedico.trim().length() > 20)
                || (ultimaConsultaDental == null)) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            Interrogatorio interrogatorio = expediente.getInterrogatorio();
            interrogatorio.setHospitalizado(hospitalizado);
            interrogatorio.setAtencionMedica(atencionMedica);
            interrogatorio.setEmbarazada(embarazada);
            interrogatorio.setAmamantando(amamantando);
            interrogatorio.setTelefonoEmergencia(telefonoEmergencia.trim());
            interrogatorio.setTelefonoServicioMedico(telefonoServicioMedico);
            interrogatorio.setUltimaConsultaDental(ultimaConsultaDental);

            interrogatorio.getAlergias().clear();
            if (alergias != null) {
                for (Alergia alergia : alergias) {
                    alergia.setInterrogatorio(interrogatorio);
                    interrogatorio.getAlergias().add(alergia);
                }
            }
            interrogatorio.getEnfermedades().clear();
            if (enfermedades != null) {
                for (Enfermedad enfermedad : enfermedades) {
                    enfermedad.setInterrogatorio(interrogatorio);
                    interrogatorio.getEnfermedades().add(enfermedad);
                }
            }
            interrogatorio.getMedicacion().clear();
            if (medicacion != null) {
                for (Medicacion medicamento : medicacion) {
                    medicamento.setInterrogatorio(interrogatorio);
                    interrogatorio.getMedicacion().add(medicamento);
                }
            }

            ServiceLocator.getInstanceInterrogatorioDAO().update(interrogatorio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Interrogatorio buscar(int idExpediente){
        return ServiceLocator.getInstanceInterrogatorioDAO().findFromWhere("expediente","idExpediente", String.valueOf(idExpediente)).stream()
                .findFirst()
                .orElse(null);
    }
}