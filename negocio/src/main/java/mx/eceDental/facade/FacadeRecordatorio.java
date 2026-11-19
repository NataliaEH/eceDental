package mx.eceDental.facade;

import java.time.LocalDate;
import mx.eceDental.delegate.DelegateRecordatorio;

public class FacadeRecordatorio {

    private final DelegateRecordatorio delegateRecordatorio;

    public FacadeRecordatorio() {
        this.delegateRecordatorio = new DelegateRecordatorio();
    }

    public boolean registrar(String contenido, LocalDate fechaLimite) {
        return delegateRecordatorio.registrar(contenido, fechaLimite);
    }

    public boolean eliminar(int id) {
        return delegateRecordatorio.eliminar(id);
    }

    public List<Recordatorio> buscarUrgentes() {
        return delegateRecordatorio.buscarUrgentes();
    }

    public List<Recordatorio> consultar() {
        return delegateRecordatorio.consultar();
    }

}