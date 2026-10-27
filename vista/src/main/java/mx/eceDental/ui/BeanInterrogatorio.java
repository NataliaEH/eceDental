package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperInterrogatorio;
import mx.eceDental.entity.Interrogatorio;

import java.util.List;

@Named("interrogatorioUI")
@ViewScoped
public class BeanInterrogatorio {
    private HelperInterrogatorio helperInterrogatorio;
    private Interrogatorio interrogatorio;

    public BeanInterrogatorio() {
        helperInterrogatorio = new HelperInterrogatorio();
    }

    @PostConstruct
    public void init(){
        interrogatorio = new Interrogatorio();
    }

    public void modificar(int idExpediente){
        boolean modificado = helperInterrogatorio.modificar(interrogatorio.getHospitalizado(), interrogatorio.getAtencionMedica(), interrogatorio.getAlergias(),
                interrogatorio.getHemorragia(), interrogatorio.getEnfermedades(), interrogatorio.getMedicacion(), interrogatorio.getEmbarazada(),
                interrogatorio.getAmamantando(), interrogatorio.getTelefonoEmergencia(), interrogatorio.getTelefonoServicioMedico(), interrogatorio.getUltimaConsultaDental());
        if(modificado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Antecedentes modificados:", "Se hizo la modificación correctamente."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de modificación:", "Hubo un error al hacer la modificación en la BD."));
        }
    }

    public Interrogatorio buscar(int idExpediente){
        return helperInterrogatorio.buscar(idExpediente);
    }

    public Interrogatorio getInterrogatorio(){ return interrogatorio;}
}
