package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperDiagnosticoYPlan;
import mx.eceDental.entity.DiagnosticoYPlan;
import java.util.List;

@Named("diagYPlanUI")
@ViewScoped
public class BeanDiagnosticoYPlan {
    private HelperDiagnosticoYPlan helperDiagnosticoYPlan;
    private DiagnosticoYPlan diagnosticoYPlan;
    private int idExpediente;

    public BeanDiagnosticoYPlan() {
        helperDiagnosticoYPlan = new HelperDiagnosticoYPlan();
    }

    @PostConstruct
    public void init(){
        diagnosticoYPlan = new DiagnosticoYPlan();
    }

    public void registrar(){
        boolean registrado = helperDiagnosticoYPlan.registrar(idExpediente, diagnosticoYPlan.getDiagnostico(), diagnosticoYPlan.getProcedimiento(), diagnosticoYPlan.getFecha());
        if(registrado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Diagnóstico y Plan de Tratamiento registrado:", "Se hizo el registro correctamente en el sistema."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de registro:", "Hubo un error al hacer el registro en la BD."));
        }
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente){
        return helperDiagnosticoYPlan.consultar(idExpediente);
    }

    public DiagnosticoYPlan getDiagnosticoYPlan(){ return diagnosticoYPlan;}
}
