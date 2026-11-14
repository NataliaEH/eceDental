package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Archivo;
import mx.eceDental.helper.HelperDiagnosticoYPlan;
import mx.eceDental.entity.DiagnosticoYPlan;
import java.util.List;

@Named("diagYPlanUI")
@ViewScoped
public class BeanDiagnosticoYPlan {
    private HelperDiagnosticoYPlan helperDiagnosticoYPlan;
    private DiagnosticoYPlan diagnosticoYPlan;

    public BeanDiagnosticoYPlan() {
        helperDiagnosticoYPlan = new HelperDiagnosticoYPlan();
    }

    @PostConstruct
    public void init(){
        diagnosticoYPlan = new DiagnosticoYPlan();
    }

    public boolean registrar(){
        return helperDiagnosticoYPlan.registrar(diagnosticoYPlan.getExpediente().getIdExpediente(), diagnosticoYPlan.getDiagnostico(), diagnosticoYPlan.getProcedimiento());
    }
    public boolean modificar(){
        return helperDiagnosticoYPlan.modificar(diagnosticoYPlan.getIdDiagnosticoPlan(), diagnosticoYPlan.getDiagnostico(), diagnosticoYPlan.getProcedimiento());
    }
    public boolean eliminar(int id){
        return helperDiagnosticoYPlan.eliminar(id);
    }
    public List<DiagnosticoYPlan> consultar(int idExpediente){
        return helperDiagnosticoYPlan.consultar(idExpediente);
    }
    public DiagnosticoYPlan buscar(int id){
        return helperDiagnosticoYPlan.buscar(id);
    }

    public DiagnosticoYPlan getDiagnosticoYPlan(){ return diagnosticoYPlan;}
}
