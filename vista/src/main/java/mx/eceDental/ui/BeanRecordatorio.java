package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.eceDental.entity.Recordatorio;
import mx.eceDental.helper.HelperRecordatorio;

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

    public Recordatorio getRecordatorio(){ return recordatorio;}
    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }
}