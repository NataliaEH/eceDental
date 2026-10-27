package mx.eceDental.facade;

import java.util.List;
import mx.desarrollo.delegate.DelegateInterrogatorio;

public class FacadeInterrogatorio {

    private final DelegateInterrogatorio delegateInterrogatorio;

    public FacadeInterrogatorio() {
        this.delegateInterrogatorio = new DelegateInterrogatorio();
    }

    public boolean registrar(int idExpediente, boolean hospitalizado, boolean atencionMedica,
                             List<String> alergias, boolean hemorragia, List<String> enfermedades,
                             List<String> medicacion, boolean embarazada, boolean amamantando,
                             String telefonoEmergencia, String telefonoServicioMedico,
                             String ultimaConsultaDental) {
        return delegateInterrogatorio.registrar(idExpediente, hospitalizado, atencionMedica,
                alergias, hemorragia, enfermedades, medicacion, embarazada, amamantando,
                telefonoEmergencia, telefonoServicioMedico, ultimaConsultaDental);
    }
}