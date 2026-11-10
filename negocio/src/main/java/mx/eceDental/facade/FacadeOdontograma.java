package mx.eceDental.facade;

import mx.eceDental.delegate.DelegateOdontograma;

public class FacadeOdontograma {

    private final DelegateOdontograma delegateOdontograma;

    public FacadeOdontograma() {
        this.delegateOdontograma = new DelegateOdontograma();
    }

    public boolean registrarRegistro(int idExpediente, int piezaDental, String cara,
                                     String diagnostico, String procedimiento, String fecha) {
        return delegateOdontograma.registrarRegistro(idExpediente, piezaDental, cara,
                diagnostico, procedimiento, fecha);
    }

    public boolean modificarRegistro(int idRegistro, int piezaDental, String cara,
                                     String diagnostico, String procedimiento, String fecha) {
        return delegateOdontograma.modificarRegistro(idRegistro, piezaDental, cara,
                diagnostico, procedimiento, fecha);
    }

    public boolean eliminarRegistro(int id) {
        return delegateOdontograma.eliminarRegistro(id);
    }


}