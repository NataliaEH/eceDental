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
                || (telefonoServicioMedico != null && telefonoServicioMedico.trim().length() > 20)
                || (ultimaConsultaDental != null)) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null || expediente.getInterrogatorio() != null) {
                return false;
            }

            Interrogatorio interrogatorio = new Interrogatorio();
            interrogatorio.setHospitalizado(hospitalizado);
            interrogatorio.setAtencionMedica(atencionMedica);
            if (alergias != null) {
                interrogatorio.setAlergias(alergias);
            } else {
                interrogatorio.setAlergias(new ArrayList<>());
            }

            if (enfermedades != null) {
                interrogatorio.setEnfermedades(enfermedades);
            } else {
                interrogatorio.setEnfermedades(new ArrayList<>());
            }

            if (medicacion != null) {
                interrogatorio.setMedicacion(medicacion);
            } else {
                interrogatorio.setMedicacion(new ArrayList<>());
            }
            interrogatorio.setEmbarazada(embarazada);
            interrogatorio.setAmamantando(amamantando);
            interrogatorio.setTelefonoEmergencia(telefonoEmergencia.trim());
            interrogatorio.setTelefonoServicioMedico(telefonoServicioMedico);
            interrogatorio.setUltimaConsultaDental(ultimaConsultaDental);
            interrogatorio.setExpediente(expediente);

            ServiceLocator.getInstanceInterrogatorioDAO().save(interrogatorio);

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