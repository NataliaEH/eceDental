package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import javafx.stage.FileChooser;
import mx.eceDental.entity.Expediente;
import mx.eceDental.helper.HelperExpediente;

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
        return helperExpediente.buscarPorPaciente(idPaciente);
    }
    public List<Expediente> consultar(){
        return helperExpediente.consultar();
    }
    public boolean exportar(int idExpediente, List<String> modulos, LocalDate desde, LocalDate hasta){
        FileChooser selector = new FileChooser();
        selector.setInitialFileName("expediente_" + idExpediente + ".pdf");
        File destino = selector.showSaveDialog(null);
        if (destino == null) {
            return false;
        }
        return helperExpediente.exportar(idExpediente, destino, modulos, desde, hasta);
    }
    public boolean eliminar(int id){
        return helperExpediente.eliminar(id);
    }
    public Expediente getExpediente(){ return expediente;}
}