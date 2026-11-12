package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.helper.HelperNotaEvolucion;
import mx.eceDental.entity.NotaEvolucion;
import java.util.List;

@Named("notaEvolucionUI")
@ViewScoped
public class BeanNotaEvolucion {

    private HelperNotaEvolucion helperNotaEvolucion;
    private NotaEvolucion NotaEvolucion;

   // private int idExpediente;

    public BeanNotaEvolucion() {
        helperNotaEvolucion = new HelperNotaEvolucion();
    }

    @PostConstruct
    public void init(){
        NotaEvolucion = new NotaEvolucion();
    }

    public boolean registrar(int id){
            return helperNotaEvolucion.registrar(id);
    }

    public boolean modificar(int id){
        return helperNotaEvolucion.modificar(id);
    }

    public boolean eliminar(int id){
        return helperNotaEvolucion.modificar(id);
    }

    public NotaEvolucion buscarUltima(int id){
        return helperNotaEvolucion.modificar(id);
    }

    public List<NotaEvolucion> consultar(int idExpediente){
        return helperNotaEvolucion.consultar(idExpediente);
    }

   public NotaEvolucion getNotaEvolucion(){ return NotaEvolucion;}

}