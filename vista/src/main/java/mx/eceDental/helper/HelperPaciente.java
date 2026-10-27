package mx.eceDental.helper;

import mx.eceDental.integration.ServiceFacadeLocator;
import java.io.Serializable;
import java.util.List;

public class HelperPaciente {
    public boolean registrar(String nombre, int edad, String sexo, List<String> telefonos, String correo, String domicilio, String edoCivil, String ocupacion){
        int idPaciente = ServiceFacadeLocator.getInstanceFacadePaciente().registrar(nombre, edad, sexo, telefonos, correo, domicilio, edoCivil, ocupacion);
        if (idPaciente <= 0) {
            return false;
        }

        return ServiceFacadeLocator.getInstanceFacadeExpediente().registrar(idPaciente);
    }

    public boolean modificar(int idExpediente, String nombre, int edad, String sexo, List<String> telefonos, String correo, String domicilio, String edoCivil, String ocupacion){
        return ServiceFacadeLocator.getInstanceFacadePaciente().modificar(idExpediente);
    }

    public Paciente buscar(int id){
        return ServiceFacadeLocator.getInstanceFacadePaciente().buscar(id);
    }
    public List<Paciente> consultar(){
        return ServiceFacadeLocator.getInstanceFacadePaciente().consultar();
    }
}
