package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.stage.DirectoryChooser;
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
    public boolean exportar(int idExpediente, String modulos, LocalDate desde, LocalDate hasta, String imagenOdontograma){
        List<String> lista = Arrays.asList(modulos.split(","));

        DirectoryChooser selector = new DirectoryChooser();
        selector.setTitle("Selecciona la carpeta de exportación");
        File destino = selector.showDialog(null);
        if (destino == null) {
            return false;
        }
        return helperExpediente.exportar(idExpediente, destino, lista, desde, hasta, imagenOdontograma);
    }
    public boolean eliminar(int id){
        return helperExpediente.eliminar(id);
    }
    public Expediente getExpediente(){ return expediente;}
}