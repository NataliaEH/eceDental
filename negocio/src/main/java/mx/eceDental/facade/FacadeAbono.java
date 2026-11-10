package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateAbono;

public class FacadeAbono {

    private final DelegateAbono delegateAbono;

    public FacadeAbono() {
        this.delegateAbono = new DelegateAbono();
    }

    public boolean registrar(int idExpediente, double abono, String concepto) {
        return delegateAbono.registrar(idExpediente, abono, concepto);
    }

    public boolean modificar(int idAbono, double abono, String concepto) {
        return delegateAbono.modificar(idAbono, abono, concepto);
    }

    public boolean eliminar(int id) {
        return delegateAbono.eliminar(id);
    }

    public List<Abono> consultar(int idExpediente) {
        return delegateAbono.consultar(idExpediente);
    }
}
