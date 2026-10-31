package mx.eceDental.helper;

import mx.eceDental.entity.Alergia;
import mx.eceDental.entity.Enfermedad;
import mx.eceDental.entity.Interrogatorio;
import mx.eceDental.entity.Medicacion;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.time.LocalDate;
import java.util.List;

public class HelperInterrogatorio {
    public boolean modificar(int idExpediente, boolean hospitalizado, boolean atencionMedica, List<Alergia> alergias, boolean hemorragia, List<Enfermedad> enfermedades, List<Medicacion> medicacion, String embarazada, String amamantando, String telefonoEmergencia, String telefonoServicioMedico, LocalDate ultimaConsultaDental){
        return ServiceFacadeLocator.getInstanceFacadeInterrogatorio().modificar(idExpediente, hospitalizado, atencionMedica, alergias, hemorragia,
                enfermedades, medicacion, embarazada, amamantando, telefonoEmergencia, telefonoServicioMedico, ultimaConsultaDental);
    }

    public Interrogatorio buscar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeInterrogatorio().buscar(idExpediente);
    }
}
