package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Expediente;
import mx.eceDental.helper.HelperPaciente;
import mx.eceDental.entity.Paciente;

import java.util.List;

@Named("pacienteUI")
@ViewScoped
public class BeanPaciente {
    private HelperPaciente helperPaciente;
    private Paciente paciente;

    public BeanPaciente() {
        helperPaciente = new HelperPaciente();
    }

    @PostConstruct
    public void init(){
        paciente = new Paciente();
    }

    public boolean registrar(){
        int idPaciente = helperPaciente.registrar(paciente.getNombre(), paciente.getEdad(), paciente.getSexo(), paciente.getCorreo(), paciente.getDomicilio(), paciente.getEdoCivil(), paciente.getOcupacion());
        if (idPaciente <= 0) {
            return false;
        }

        paciente.setIdPaciente(idPaciente);
        return true;
    }

    public boolean registrarTelefonos(String telefonoPrincipal, String telefonoAlternativo){
        return helperPaciente.registrarTelefonos(paciente.getIdPaciente(), telefonoPrincipal, telefonoAlternativo);
    }

    public void modificar(int idPaciente){
        boolean modificado = helperPaciente.modificar(idPaciente, paciente.getNombre(), paciente.getEdad(), paciente.getSexo(), paciente.getCorreo(), paciente.getDomicilio(), paciente.getEdoCivil(), paciente.getOcupacion());
        if(modificado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Expediente registrado:", "El expediente se agregó al sistema."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de registro:", "Hubo un error al hacer el registro en la BD."));
        }
    }

    public Paciente buscar(int id){
        return helperPaciente.buscar(id);
    }
    public List<Paciente> consultar(){
        return helperPaciente.consultar();
    }

    public Paciente getPaciente(){ return paciente;}
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
