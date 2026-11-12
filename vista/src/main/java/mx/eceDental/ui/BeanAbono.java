package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperAbono;
import mx.eceDental.entity.Abono;
import java.util.List;

@Named("abonoUI")
@ViewScoped
public class BeanAbono implements Serializable {
    private HelperAbono helperAbono;
    private Abono abono;


    public BeanAbono() {
        helperAbono = new HelperAbono();
    }

    @PostConstruct
    public void init(){
        abono = new Abono();
    }

    public boolean registrar(){
        boolean registrado = helperAbono.registrar(abono.getExpediente().getIdExpediente());
        return registrado;
    }

    public boolean modificar(int id){
        return helperAbono.modificar(id);
    };

    public boolean eliminar(int id){
        return helperAbono.eliminar(id);
    };

    public List<Abono> consultar(int idExpediente){
        return helperAbono.consultar(idExpediente);
    }

    public Abono getAbono(){ return abono;}
}