package mx.eceDental.delegate;

import mx.eceDental.entity.Doctor;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegateDoctor {
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
            Doctor doctor = ServiceLocator.getInstanceDoctorDAO().findFirst();

            if (doctor == null) {
                doctor = new Doctor();
                doctor.setNombreCompleto(nombreCompleto.trim());
                doctor.setTitulo(titulo.trim());
                doctor.setCedula(cedula.trim());
                doctor.setRfc(rfc.trim());
                doctor.setDomicilioEstablecimiento(domicilioEstablecimiento.trim());
                doctor.setNombreEstablecimiento(nombreEstablecimiento.trim());
                doctor.setTelefonoEstablecimiento(telefonoEstablecimiento.trim());
                doctor.setCorreo(correo.trim());

                ServiceLocator.getInstanceDoctorDAO().save(doctor);
            }else{
                doctor.setNombreCompleto(nombreCompleto.trim());
                doctor.setTitulo(titulo.trim());
                doctor.setCedula(cedula.trim());
                doctor.setRfc(rfc.trim());
                doctor.setDomicilioEstablecimiento(domicilioEstablecimiento.trim());
                doctor.setNombreEstablecimiento(nombreEstablecimiento.trim());
                doctor.setTelefonoEstablecimiento(telefonoEstablecimiento.trim());
                doctor.setCorreo(correo.trim());

                ServiceLocator.getInstanceDoctorDAO().update(doctor);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Doctor consultarDatos() {
        try {
            Doctor doctor = ServiceLocator.getInstanceDoctorDAO().findFirst();
            if(doctor==null)
                return new Doctor();
            return doctor;
        } catch (Exception e) {
            e.printStackTrace();
            return new Doctor();
        }
    }
}