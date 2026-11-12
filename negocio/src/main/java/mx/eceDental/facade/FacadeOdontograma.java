package mx.eceDental.facade;

import java.util.List;
import mx.eceDental.delegate.DelegateOdontograma;


public class FacadeOdontograma {

    private final DelegateOdontograma delegateOdontograma;

    public FacadeOdontograma() {
        this.delegateOdontograma = new DelegateOdontograma();
    }

    public boolean registrarRegistro(int idExpediente, int piezaDental, String cara,
                                     String diagnostico, String procedimiento) {
        return delegateOdontograma.registrarRegistro(idExpediente, piezaDental, cara,
                diagnostico, procedimiento);
    }

    public boolean modificarRegistro(int idRegistro, int piezaDental, String cara,
                                     String diagnostico, String procedimiento) {
        return delegateOdontograma.modificarRegistro(idRegistro, piezaDental, cara,
                diagnostico, procedimiento);
    }

    public boolean eliminarRegistro(int id) {
        return delegateOdontograma.eliminarRegistro(id);
    }

    public List<RegistroOdontograma> consultarReg(int idExpediente) {
        return delegateOdontograma.consultarReg(idExpediente);
    }
}