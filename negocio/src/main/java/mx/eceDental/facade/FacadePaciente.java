package mx.eceDental.facade;

import java.util.List;
import mx.eceDental.delegate.DelegatePaciente;
import mx.eceDental.entity.Paciente;
import mx.eceDental.entity.TelefonosPaciente;

public class FacadePaciente {

    private final DelegatePaciente delegatePaciente;

    public FacadePaciente() {
        this.delegatePaciente = new DelegatePaciente();
    }

    public int registrar(String nombre, int edad, String sexo,
                         String correo, String domicilio, String edoCivil, String ocupacion) {

        return delegatePaciente.registrar(nombre, edad, sexo, correo, domicilio, edoCivil, ocupacion);
    }

    public boolean registrarTelefonos(int idPaciente, String telefonoPrincipal, String telefonoAlternativo){
        return delegatePaciente.registrarTelefonos(idPaciente, telefonoPrincipal, telefonoAlternativo);
    }

    public boolean modificar(int idPaciente, String nombre, int edad, String sexo,
                             String correo, String domicilio, String edoCivil, String ocupacion) {

        return delegatePaciente.modificar(idPaciente, nombre, edad, sexo, correo, domicilio, edoCivil, ocupacion);
    }

    public boolean agregarNotas(int idPaciente, String texto) {

        return delegatePaciente.agregarNotas(idPaciente, texto);
    }

    public Paciente buscar(int id) {
        return delegatePaciente.buscar(id);
    }

    public List<Paciente> consultar() {
        return delegatePaciente.consultar();
    }
}