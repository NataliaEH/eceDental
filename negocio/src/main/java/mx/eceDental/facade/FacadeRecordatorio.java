package mx.eceDental.facade;

import java.time.LocalDate;
import java.util.List;
import mx.eceDental.delegate.DelegateRecordatorio;
import mx.eceDental.entity.Recordatorio;

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

    public List<Recordatorio> buscarPorUrgencia(String urgencia) {
        return delegateRecordatorio.buscarPorUrgencia(urgencia);
    }

    public List<Recordatorio> consultar() {
        return delegateRecordatorio.consultar();
    }
}