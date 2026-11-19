package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Archivo;
import mx.eceDental.helper.HelperAbono;
import mx.eceDental.entity.Abono;
import java.util.List;

@Named("abonoUI")
@ViewScoped
public class BeanAbono{
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
        return helperAbono.registrar(abono.getExpediente().getIdExpediente(), abono.getMonto(), abono.getConcepto());
    }

    public boolean modificar(){
        return helperAbono.modificar(abono.getIdAbono(), abono.getMonto(), abono.getConcepto());
    };

    public boolean eliminar(int id){
        return helperAbono.eliminar(id);
    };
    public List<Abono> consultar(int idExpediente){
        return helperAbono.consultar(idExpediente);
    }
    public Abono buscar(int id){
        return helperAbono.buscar(id);
    }

    public Abono getAbono(){ return abono;}
}