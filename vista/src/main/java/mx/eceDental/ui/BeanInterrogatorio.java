package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Alergia;
import mx.eceDental.entity.Enfermedad;
import mx.eceDental.entity.Medicacion;
import mx.eceDental.helper.HelperInterrogatorio;
import mx.eceDental.entity.Interrogatorio;

import java.util.ArrayList;
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
        boolean modificado = helperInterrogatorio.modificar(idExpediente, interrogatorio.isHospitalizado(), interrogatorio.isAtencionMedica(), interrogatorio.getAlergias(),
                interrogatorio.isHemorragia(), interrogatorio.getEnfermedades(), interrogatorio.getMedicacion(), interrogatorio.getEmbarazada(),
                interrogatorio.getAmamantando(), interrogatorio.getTelefonoEmergencia(), interrogatorio.getTelefonoServicioMedico(), interrogatorio.getUltimaConsultaDental());
        if(modificado){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Antecedentes modificados:", "Se hizo la modificación correctamente."));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de modificación:", "Hubo un error al hacer la modificación en la BD."));
        }
    }

    public Interrogatorio buscar(int idExpediente){
        interrogatorio = helperInterrogatorio.buscar(idExpediente);
        return interrogatorio;
    }

    //METODOS DE ALERGIAS, MEDS Y ENFERMEDADES (LISTAS)
    public void limpiarAlergias() {
        interrogatorio.setAlergias(new ArrayList<>());
    }
    public void agregarAlergia(String nombre) {
        Alergia alergia = new Alergia();
        alergia.setNombre(nombre);
        alergia.setInterrogatorio(interrogatorio);
        interrogatorio.getAlergias().add(alergia);
    }
    public void limpiarMedicacion() {
        interrogatorio.setMedicacion(new ArrayList<>());
    }
    public void agregarMedicamento(String nombre) {
        Medicacion medicamento = new Medicacion();
        medicamento.setNombre(nombre);
        medicamento.setInterrogatorio(interrogatorio);
        interrogatorio.getMedicacion().add(medicamento);
    }
    public void limpiarEnfermedades() {
        interrogatorio.setEnfermedades(new ArrayList<>());
    }
    public void agregarEnfermedad(String nombre) {
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setNombre(nombre);
        enfermedad.setInterrogatorio(interrogatorio);
        interrogatorio.getEnfermedades().add(enfermedad);
    }

    //getters y setters
    public Interrogatorio getInterrogatorio(){ return interrogatorio;}
    public void setInterrogatorio(Interrogatorio interrogatorio) {
        this.interrogatorio = interrogatorio;
    }
}
