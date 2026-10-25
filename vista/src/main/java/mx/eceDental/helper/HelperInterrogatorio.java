package mx.eceDental.helper;

import mx.eceDental.integration.ServiceFacadeLocator;
import java.time.LocalTime;
import java.util.List;

public class HelperInterrogatorio {
    public boolean modificar(boolean hospitalizado, boolean atencionMedica, List<String> alergias, boolean hemorragia, List<String> enfermedades, List<String> medicacion, boolean embarazada, boolean amamantando, String telefonoEmergencia, String telefonoServicioMedico, LocalTime ultimaConsultaDental){
        return ServiceFacadeLocator.getInstanceFacadeInterrogatorio().registrar(hospitalizado, atencionMedica, alergias, hemorragia,
                enfermedades, medicacion, embarazada, amamantando, telefonoEmergencia, telefonoServicioMedico, ultimaConsultaDental);
    }

    public Interrogatorio buscar(int idExpediente){
        return ServiceFacadeLocator.getInstanceFacadeInterrogatorio().buscar(idExpediente);
    }
}
