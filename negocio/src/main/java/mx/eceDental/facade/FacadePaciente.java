package mx.eceDental.facade;

import java.util.List;
import mx.desarrollo.delegate.DelegatePaciente;
import mx.desarrollo.entity.Paciente;

public class FacadePaciente {

    private final DelegatePaciente delegatePaciente;

    public FacadePaciente() {
        this.delegatePaciente = new DelegatePaciente();
    }

    public int registrar(String nombre, int edad, String sexo, List<String> telefonos,
                         String correo, String domicilio, String edoCivil, String ocupacion, String notas) {

        return delegatePaciente.registrar(nombre, edad, sexo, telefonos,
                correo, domicilio, edoCivil, ocupacion, notas);
    }

    public boolean modificar(int idPaciente, String nombre, int edad, String sexo, List<String> telefonos,
                             String correo, String domicilio, String edoCivil, String ocupacion, String notas) {

        return delegatePaciente.modificar(idPaciente, nombre, edad, sexo, telefonos,
                correo, domicilio, edoCivil, ocupacion, notas);
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