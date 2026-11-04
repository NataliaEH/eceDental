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
        boolean registrado = helperDiagnosticoYPlan.registrar(diagnosticoYPlan.getExpediente().getIdExpediente(), diagnosticoYPlan.getDiagnostico(), diagnosticoYPlan.getProcedimiento());
    }

    public List<DiagnosticoYPlan> consultar(int idExpediente){
        return helperDiagnosticoYPlan.consultar(idExpediente);
    }

    public DiagnosticoYPlan getDiagnosticoYPlan(){ return diagnosticoYPlan;}
}
