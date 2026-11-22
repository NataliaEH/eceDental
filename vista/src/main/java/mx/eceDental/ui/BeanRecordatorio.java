package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Recordatorio;
import mx.eceDental.helper.HelperRecordatorio;

import java.time.LocalDate;
import java.util.List;

@Named("recordatorioUI")
@ViewScoped
public class BeanRecordatorio {
    private HelperRecordatorio helperRecordatorio;
    private Recordatorio recordatorio;

    public BeanRecordatorio() {
        helperRecordatorio = new HelperRecordatorio();
    }

    @PostConstruct
    public void init(){
        recordatorio = new Recordatorio();
    }

    public boolean registrar(){
        return helperRecordatorio.registrar(recordatorio.getContenido(), recordatorio.getFechaLimite());
    }

    public boolean eliminar(int id){
        return helperRecordatorio.eliminar(id);
    }

    public List<Recordatorio> buscarUrgentes(){
        return helperRecordatorio.buscarUrgentes();
    }

    public List<Recordatorio> buscarPorUrgencia(String urgencia){
        return helperRecordatorio.buscarPorUrgencia(urgencia);
    }

    public List<Recordatorio> consultar(){
        return helperRecordatorio.consultar();
    }

    public Recordatorio getRecordatorio(){ return recordatorio;}

    public boolean setFechaLimite(String fecha) {
        if (fecha == null || fecha.isBlank())
            return false;
        LocalDate fechaLimite = LocalDate.parse(fecha);
        if(fechaLimite.isBefore(LocalDate.now()))
            return false;
        recordatorio.setFechaLimite(fechaLimite);
        return true;
    }
}