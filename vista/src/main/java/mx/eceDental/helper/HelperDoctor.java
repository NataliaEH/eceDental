package mx.eceDental.helper;

import mx.eceDental.entity.Doctor;
import mx.eceDental.integration.ServiceFacadeLocator;

public class HelperDoctor {
    public boolean modificar(String nombreCompleto, String titulo, String cedula, String rfc, String domicilioEstablecimiento, String nombreEstablecimiento, String telefonoEstablecimiento, String correo){
        return ServiceFacadeLocator.getInstanceFacadeDoctor().modificar(nombreCompleto, titulo, cedula, rfc, domicilioEstablecimiento, nombreEstablecimiento, telefonoEstablecimiento, correo);
    }

    public Doctor consultarDatos(){
        return ServiceFacadeLocator.getInstanceFacadeDoctor().consultarDatos();
    }
}