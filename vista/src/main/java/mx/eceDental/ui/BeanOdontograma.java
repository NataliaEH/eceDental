package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.helper.HelperOdontograma;
import mx.eceDental.entity.Odontograma;

import java.util.ArrayList;
import java.util.List;

@Named("odontogramaUI")
@ViewScoped
public class BeanOdontograma {
    private HelperOdontograma helperOdontograma;
    private Odontograma odontograma;
    private RegistroOdontograma registro;

    public BeanOdontograma() {
        helperOdontograma = new HelperOdontograma();
    }

    @PostConstruct
    public void init(){
        odontograma = new Odontograma();
        registro = new RegistroOdontograma();
    }

    public boolean registrarRegistro(){
        return helperOdontograma.registrarRegistro(odontograma.getExpediente().getIdExpediente(), registro.getPiezaDental(), registro.getCara(), registro.getColor(), registro.getDiagnostico(),registro.getProcedimiento());
    }
    public boolean modificarRegistro(){
        return helperOdontograma.modificarRegistro(registro.getIdRegistro(), registro.getPiezaDental(), registro.getCara(), registro.getColor(), registro.getDiagnostico(),registro.getProcedimiento());
    }
    public boolean eliminarRegistro(int id){
        return helperOdontograma.eliminarRegistro(id);
    }
    public List<RegistroOdontograma> consultarReg(int idExpediente){
        return helperOdontograma.consultarReg(idExpediente);
    }
    public RegistroOdontograma buscarRegistro(int id){
        return helperOdontograma.buscarRegistro(id);
    }
    public Odontograma getOdontograma(){ return odontograma;}
    public RegistroOdontograma getRegistro() {return registro;}

    public void agregarCara(String cara) {
        registro.getCara().add(cara);
    }
}