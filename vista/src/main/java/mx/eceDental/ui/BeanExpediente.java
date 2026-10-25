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
    public List<Expediente> consultar(){
        return helperExpediente.consultar();
    }

    public Expediente getExpediente(){ return expediente;}
}
