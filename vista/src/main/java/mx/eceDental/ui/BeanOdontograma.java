package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperOdontograma;
import mx.eceDental.entity.Odontograma;
import java.util.List;

@Named("odontogramaUI")
@ViewScoped
public class BeanOdontograma implements Serializable {
    private HelperOdontograma helperOdontograma;
    private Odontograma odontograma;

    public BeanOdontograma() {
        helperOdontograma = new HelperOdontograma();
    }

    @PostConstruct
    public void init(){
        odontograma = new Odontograma();
    }

    public void registrarRegistro(){
        boolean registrado = helperOdontograma.registrar(odontograma);
    }

    public boolean modificarRegistro(int id){
        return helperOdontograma.modificarRegistro(id);
    }

    public boolean eliminarRegistro(int id){
        return helperOdontograma.eliminarRegistro(id);
    }

    public List<Odontograma> consultarReg(int idExpediente){
        return helperOdontograma.consultar(idExpediente);
    }

    public Odontograma getOdontograma(){ return odontograma;}
}