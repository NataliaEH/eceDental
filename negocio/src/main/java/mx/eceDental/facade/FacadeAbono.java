package mx.eceDental.facade;

import java.util.Date;
import java.util.List;
import mx.eceDental.delegate.DelegateAbono;
import mx.eceDental.entity.Abono;

public class FacadeAbono {

    private final DelegateAbono delegateAbono;

    public FacadeAbono() {
        this.delegateAbono = new DelegateAbono();
    }

    public boolean registrar(int idExpediente, Date fecha, double abono, String concepto) {
        return delegateAbono.registrar(idExpediente, fecha, abono, concepto);
    }

    public boolean modificar(int idAbono, Date fecha, double abono, String concepto) {
        return delegateAbono.modificar(idAbono, fecha, abono, concepto);
    }

    public boolean eliminar(int id) {
        return delegateAbono.eliminar(id);
    }

    public List<Abono> consultar(int idExpediente) {
        return delegateAbono.consultar(idExpediente);
    }
}