package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateDoctor;
import mx.eceDental.entity.Doctor;

public class FacadeDoctor {

    private final DelegateDoctor delegateDoctor;

    public FacadeDoctor() {
        this.delegateDoctor = new DelegateDoctor();
    }

    public boolean modificar(String nombreCompleto, String titulo, String cedula, String rfc,
                             String domicilioEstablecimiento, String nombreEstablecimiento,
                             String telefonoEstablecimiento, String correo) {
        return delegateDoctor.modificar(nombreCompleto, titulo, cedula, rfc,
                domicilioEstablecimiento, nombreEstablecimiento, telefonoEstablecimiento, correo);
    }

    public Doctor consultarDatos() {
        return delegateDoctor.consultarDatos();
    }
}