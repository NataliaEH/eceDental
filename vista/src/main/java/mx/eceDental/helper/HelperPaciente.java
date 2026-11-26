package mx.eceDental.helper;

import mx.eceDental.entity.Paciente;
import mx.eceDental.entity.TelefonosPaciente;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.util.List;

public class HelperPaciente {
    public int registrar(String nombre, int edad, String sexo, String correo, String domicilio, String edoCivil, String ocupacion){
        int idPaciente = ServiceFacadeLocator.getInstanceFacadePaciente().registrar(nombre, edad, sexo, correo, domicilio, edoCivil, ocupacion);
        if (idPaciente <= 0) {
            return -1;
        }
        if (!ServiceFacadeLocator.getInstanceFacadeExpediente().registrar(idPaciente)) {
            return -1;
        }
        int idExpediente = -1;
        try {
            idExpediente = ServiceFacadeLocator.getInstanceFacadeExpediente().buscarPorPaciente(idPaciente).getIdExpediente();

            if (!ServiceFacadeLocator.getInstanceFacadeInterrogatorio().registrar(idExpediente)) {
                throw new IllegalStateException("Falló interrogatorio");
            }if (!ServiceFacadeLocator.getInstanceFacadeOdontograma().registrar(idExpediente)) {
                throw new IllegalStateException("Falló odontograma");
            }
            return idPaciente;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ServiceFacadeLocator.getInstanceFacadeExpediente().eliminar(idExpediente);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return -1;
        }
    }

    public boolean registrarTelefonos(int idPaciente, String telefonoPrincipal, String telefonoAlternativo){
        return ServiceFacadeLocator.getInstanceFacadePaciente().registrarTelefonos(idPaciente, telefonoPrincipal, telefonoAlternativo);
    }

    public boolean agregarNotas(int idPaciente, String texto){
        return ServiceFacadeLocator.getInstanceFacadePaciente().agregarNotas(idPaciente, texto);
    }

    public boolean modificar(int idPaciente, String nombre, int edad, String sexo, String correo, String domicilio, String edoCivil, String ocupacion, List<TelefonosPaciente> telefonos){
        return ServiceFacadeLocator.getInstanceFacadePaciente().modificar(idPaciente, nombre, edad, sexo, correo, domicilio, edoCivil, ocupacion, telefonos);
    }

    public Paciente buscar(int id){
        return ServiceFacadeLocator.getInstanceFacadePaciente().buscar(id);
    }
    public List<Paciente> consultar(){
        return ServiceFacadeLocator.getInstanceFacadePaciente().consultar();
    }
}