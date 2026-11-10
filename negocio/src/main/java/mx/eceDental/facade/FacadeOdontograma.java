package mx.eceDental.facade;

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
}