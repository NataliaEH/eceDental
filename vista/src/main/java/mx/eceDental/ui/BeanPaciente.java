package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
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

    public void registrar(){
        boolean registrado = helperPaciente.registrar(paciente.getNombre(), paciente.getEdad(), paciente.getSexo(), paciente.getTelefonos(), paciente.getCorreo(), paciente.getDomicilio(), paciente.getEdoCivil(), paciente.getOcupacion());
        if(registrado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Expediente registrado:", "El expediente se agregó al sistema."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de registro:", "Hubo un error al hacer el registro en la BD."));
        }
    }

    public void modificar(int idExpediente){
        boolean modificado = helperPaciente.modificar(idExpediente, paciente.getNombre(), paciente.getEdad(), paciente.getSexo(), paciente.getTelefonos(), paciente.getCorreo(), paciente.getDomicilio(), paciente.getEdoCivil(), paciente.getOcupacion());
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

    public Expediente getExpediente(){ return expediente;}
}
