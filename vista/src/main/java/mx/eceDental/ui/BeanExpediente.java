package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperExpediente;
import mx.eceDental.entity.Expediente;

import java.util.List;

@Named("expedienteUI")
@ViewScoped
public class BeanExpediente {
    private HelperExpediente helperExpediente;
    private Expediente expediente;

    public BeanExpediente() {
        helperExpediente = new HelperExpediente();
    }

    @PostConstruct
    public void init(){
        expediente = new Expediente();
    }

    public Expediente buscar(int id){
        return helperExpediente.buscar(id);
    }
    public Expediente buscarPorPaciente(int idPaciente){
        System.out.println("idpaciente "+idPaciente);
        return helperExpediente.buscarPorPaciente(idPaciente);
    }
    public List<Expediente> consultar(){
        return helperExpediente.consultar();
    }
    public void exportar(int idExpediente){
        boolean exportado = helperExpediente.exportar(idExpediente);
        if(exportado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Expediente exportado:", "El expediente se exportó correctamente."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de exportación:", "Hubo un error al hacer las consultas de la BD."));
        }
    }
    public void eliminar(int id){
        boolean eliminado = helperExpediente.eliminar(id);
        if(eliminado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Expediente eliminado:", "El expediente se eliminó correctamente."));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de eliminación:", "Hubo un error al hacer la eliminación en la BD."));
        }
    }
    public Expediente getExpediente(){ return expediente;}
}
