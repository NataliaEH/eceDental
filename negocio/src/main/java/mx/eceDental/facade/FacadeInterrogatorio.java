package mx.eceDental.facade;

import java.time.LocalDate;
import java.util.List;
import mx.eceDental.delegate.DelegateInterrogatorio;
import mx.eceDental.entity.Alergia;
import mx.eceDental.entity.Enfermedad;
import mx.eceDental.entity.Interrogatorio;
import mx.eceDental.entity.Medicacion;

public class FacadeInterrogatorio {

    private final DelegateInterrogatorio delegateInterrogatorio;

    public FacadeInterrogatorio() {
        this.delegateInterrogatorio = new DelegateInterrogatorio();
    }

    public boolean registrar(int idExpediente){
        return delegateInterrogatorio.registrar(idExpediente);
    }
    public boolean modificar(int idExpediente, boolean hospitalizado, boolean atencionMedica,
                             List<Alergia> alergias, boolean hemorragia, List<Enfermedad> enfermedades,
                             List<Medicacion> medicacion, String embarazada, String amamantando,
                             String telefonoEmergencia, String telefonoServicioMedico,
                             LocalDate ultimaConsultaDental) {
        return delegateInterrogatorio.modificar(idExpediente, hospitalizado, atencionMedica,
                alergias, hemorragia, enfermedades, medicacion, embarazada, amamantando,
                telefonoEmergencia, telefonoServicioMedico, ultimaConsultaDental);
    }

    public Interrogatorio buscar(int idExpediente){
        return delegateInterrogatorio.buscar(idExpediente);
    }
}