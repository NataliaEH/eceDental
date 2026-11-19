package mx.eceDental.facade;

import java.util.List;
import mx.eceDental.delegate.DelegateOdontograma;
import mx.eceDental.entity.RegistroOdontograma;


public class FacadeOdontograma {

    private final DelegateOdontograma delegateOdontograma;

    public FacadeOdontograma() {
        this.delegateOdontograma = new DelegateOdontograma();
    }

    public boolean registrarRegistro(int idExpediente, String piezaDental, List<String> cara,
                                     String color, String diagnostico, String procedimiento) {
        return delegateOdontograma.registrarRegistro(idExpediente, piezaDental, cara, color, diagnostico, procedimiento);
    }

    public boolean modificarRegistro(int idRegistro, String piezaDental, List<String> cara,
                                     String color, String diagnostico, String procedimiento) {
        return delegateOdontograma.modificarRegistro(idRegistro, piezaDental, cara, color, diagnostico, procedimiento);
    }

    public boolean eliminarRegistro(int id) {
        return delegateOdontograma.eliminarRegistro(id);
    }

    public List<RegistroOdontograma> consultarReg(int idExpediente) {
        return delegateOdontograma.consultarReg(idExpediente);
    }
    public RegistroOdontograma buscarRegistro(int idRegistro){
        return delegateOdontograma.buscarRegistro(idRegistro);
    }
}