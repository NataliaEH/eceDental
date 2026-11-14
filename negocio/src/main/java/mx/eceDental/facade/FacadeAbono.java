package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateAbono;
import mx.eceDental.entity.Abono;
import mx.eceDental.entity.NotaEvolucion;

import java.util.List;

public class FacadeAbono {

    private final DelegateAbono delegateAbono;

    public FacadeAbono() {
        this.delegateAbono = new DelegateAbono();
    }

    public boolean registrar(int idExpediente, double monto, String concepto) {
        return delegateAbono.registrar(idExpediente, monto, concepto);
    }

    public boolean modificar(int idAbono, double monto, String concepto) {
        return delegateAbono.modificar(idAbono, monto, concepto);
    }

    public boolean eliminar(int id) {
        return delegateAbono.eliminar(id);
    }
    public Abono buscar(int idRegistro){
        return delegateAbono.buscar(idRegistro);
    }
    public List<Abono> consultar(int idExpediente) {
        return delegateAbono.consultar(idExpediente);
    }
}
