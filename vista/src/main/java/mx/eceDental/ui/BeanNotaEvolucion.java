package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Archivo;
import mx.eceDental.helper.HelperNotaEvolucion;
import mx.eceDental.entity.NotaEvolucion;
import java.util.List;

@Named("notaEvolucionUI")
@ViewScoped
public class BeanNotaEvolucion {
    private HelperNotaEvolucion helperNotaEvolucion;
    private NotaEvolucion notaEvolucion;

    public BeanNotaEvolucion() {
        helperNotaEvolucion = new HelperNotaEvolucion();
    }

    @PostConstruct
    public void init(){
        notaEvolucion = new NotaEvolucion();
    }

    public boolean registrar(){
        return helperNotaEvolucion.registrar(notaEvolucion.getExpediente().getIdExpediente(), notaEvolucion.getContenido());
    }
    public boolean modificar(){
        return helperNotaEvolucion.modificar(notaEvolucion.getIdNota(), notaEvolucion.getContenido());
    }

    public boolean eliminar(int id){
        return helperNotaEvolucion.eliminar(id);
    }
    public NotaEvolucion buscarUltima(int idExpediente){
        return helperNotaEvolucion.buscarUltima(idExpediente);
    }
    public List<NotaEvolucion> consultar(int idExpediente){
        return helperNotaEvolucion.consultar(idExpediente);
    }
    public NotaEvolucion buscar(int id){
        return helperNotaEvolucion.buscar(id);
    }

    public NotaEvolucion getNotaEvolucion(){ return notaEvolucion;}

}