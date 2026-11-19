package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Doctor;
import mx.eceDental.helper.HelperDoctor;

@Named("doctorUI")
@ViewScoped
public class BeanDoctor {
    private HelperDoctor helperDoctor;
    private Doctor doctor;

    public BeanDoctor() {
        helperDoctor = new HelperDoctor();
    }

    @PostConstruct
    public void init(){
        doctor = helperDoctor.consultarDatos();
    }

    public boolean modificar(){
        return helperDoctor.modificar(doctor.getNombreCompleto(), doctor.getTitulo(), doctor.getCedula(), doctor.getRFC(), doctor.getDomicilioEstablecimiento(), doctor.getNombreEstablecimiento(), doctor.getTelefonoEstablecimiento(), doctor.getCorreo());
    }

    public Doctor consultarDatos(){
        doctor = helperDoctor.consultarDatos();
        return doctor;
    }

    public Doctor getDoctor(){ return doctor;}
}