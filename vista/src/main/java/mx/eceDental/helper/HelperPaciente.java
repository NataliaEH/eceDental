package mx.eceDental.helper;

import mx.eceDental.entity.Paciente;
import mx.eceDental.entity.TelefonosPaciente;
import mx.eceDental.integration.ServiceFacadeLocator;
import java.io.Serializable;
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
        int idExpediente = ServiceFacadeLocator.getInstanceFacadeExpediente().buscarPorPaciente(idPaciente).getIdExpediente();
        if (!ServiceFacadeLocator.getInstanceFacadeInterrogatorio().registrar(idExpediente)) {
            return -1;
        }
        return idPaciente;
    }

    public boolean registrarTelefonos(int idPaciente, String telefonoPrincipal, String telefonoAlternativo){
        return ServiceFacadeLocator.getInstanceFacadePaciente().registrarTelefonos(idPaciente, telefonoPrincipal, telefonoAlternativo);
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
