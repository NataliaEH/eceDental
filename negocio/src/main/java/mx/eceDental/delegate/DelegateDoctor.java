package mx.eceDental.delegate;

import mx.eceDental.entity.Doctor;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateDoctor {

    private static final int ID_DOCTOR = 1;

    public boolean modificar(String nombreCompleto, String titulo, String cedula, String rfc,
                             String domicilioEstablecimiento, String nombreEstablecimiento,
                             String telefonoEstablecimiento, String correo) {

        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()
                || nombreCompleto.trim().length() > 50
                || titulo == null || titulo.trim().isEmpty()
                || cedula == null || cedula.trim().isEmpty()
                || !cedula.trim().matches("[0-9]+")
                || rfc == null || rfc.trim().isEmpty()
                || !rfc.trim().matches("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}")
                || correo == null || correo.trim().isEmpty()
                || correo.trim().length() > 50
                || !correo.trim().contains("@")
                || telefonoEstablecimiento == null || telefonoEstablecimiento.trim().isEmpty()
                || nombreEstablecimiento == null || nombreEstablecimiento.trim().isEmpty()
                || domicilioEstablecimiento == null || domicilioEstablecimiento.trim().isEmpty()) {
            return false;
        }

        try {
            Doctor doctor = ServiceLocator.getInstanceDoctorDAO().find(ID_DOCTOR).orElse(null);

            if (doctor == null) {
                return false;
            }

            doctor.setNombreCompleto(nombreCompleto.trim());
            doctor.setTitulo(titulo.trim());
            doctor.setCedula(cedula.trim());
            doctor.setRFC(rfc.trim());
            doctor.setDomicilioEstablecimiento(domicilioEstablecimiento.trim());
            doctor.setNombreEstablecimiento(nombreEstablecimiento.trim());
            doctor.setTelefonoEstablecimiento(telefonoEstablecimiento.trim());
            doctor.setCorreo(correo.trim());

            ServiceLocator.getInstanceDoctorDAO().update(doctor);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Doctor consultarDatos() {
        try {
            return ServiceLocator.getInstanceDoctorDAO().find(ID_DOCTOR).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}