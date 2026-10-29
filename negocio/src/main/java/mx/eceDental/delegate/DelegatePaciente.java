package mx.desarrollo.delegate;

import java.util.ArrayList;
import java.util.List;

import mx.desarrollo.entity.Paciente;
import mx.desarrollo.persistence.integration.ServiceLocator;

public class DelegatePaciente {

    public int registrar(String nombre, int edad, String sexo, List<String> telefonos,
                         String correo, String domicilio, String edoCivil, String ocupacion, String notas) {

        if (nombre == null || nombre.trim().isEmpty() || nombre.trim().length() > 50
                || edad < 0 || edad > 120
                || (sexo != null && sexo.trim().length() > 20)
                || (correo != null && correo.trim().length() > 100)
                || (domicilio != null && domicilio.trim().length() > 150)
                || (edoCivil != null && edoCivil.trim().length() > 30)
                || (ocupacion != null && ocupacion.trim().length() > 50)
                || (notas != null && notas.length() > 500)) {
            return -1;
        }

        try {
            Paciente paciente = new Paciente();
            paciente.setNombre(nombre.trim());
            paciente.setEdad(edad);
            paciente.setSexo(sexo);

            if (telefonos != null) {
                paciente.setTelefonos(telefonos);
            } else {
                paciente.setTelefonos(new ArrayList<>());
            }

            paciente.setCorreo(correo);
            paciente.setDomicilio(domicilio);
            paciente.setEdoCivil(edoCivil);
            paciente.setOcupacion(ocupacion);
            paciente.setNotas(notas);

            ServiceLocator.getInstancePacienteDAO().save(paciente);

            return paciente.getIdPaciente();

        } catch (Exception e) {
            return -1;
        }
    }

    public boolean modificar(int idPaciente, String nombre, int edad, String sexo, List<String> telefonos,
                             String correo, String domicilio, String edoCivil, String ocupacion, String notas) {

        if (idPaciente <= 0
                || nombre == null || nombre.trim().isEmpty() || nombre.trim().length() > 50
                || edad < 0 || edad > 120
                || (sexo != null && sexo.trim().length() > 20)
                || (correo != null && correo.trim().length() > 100)
                || (domicilio != null && domicilio.trim().length() > 150)
                || (edoCivil != null && edoCivil.trim().length() > 30)
                || (ocupacion != null && ocupacion.trim().length() > 50)
                || (notas != null && notas.length() > 500)) {
            return false;
        }

        try {
            Paciente paciente = ServiceLocator.getInstancePacienteDAO().find(idPaciente).orElse(null);

            if (paciente == null) {
                return false;
            }

            paciente.setNombre(nombre.trim());
            paciente.setEdad(edad);
            paciente.setSexo(sexo);
            if (telefonos != null) {
                paciente.setTelefonos(telefonos);
            } else {
                paciente.setTelefonos(new ArrayList<>());
            }
            paciente.setCorreo(correo);
            paciente.setDomicilio(domicilio);
            paciente.setEdoCivil(edoCivil);
            paciente.setOcupacion(ocupacion);
            paciente.setNotas(notas);

            ServiceLocator.getInstancePacienteDAO().update(paciente);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean agregarNotas(int idPaciente, String texto) {
        if (idPaciente <= 0 || texto == null || texto.trim().isEmpty()) {
            return false;
        }

        try {
            Paciente paciente = ServiceLocator.getInstancePacienteDAO().find(idPaciente).orElse(null);

            if (paciente == null) {
                return false;
            }

            String actual = paciente.getNotas();
            String nuevas;

            if (actual == null || actual.trim().isEmpty()) {
                nuevas = texto.trim();
            } else {
                nuevas = actual + "\n" + texto.trim();
            }

            if (nuevas.length() > 500) {
                return false;
            }

            paciente.setNotas(nuevas);

            ServiceLocator.getInstancePacienteDAO().update(paciente);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Paciente buscar(int id) {
        if (id <= 0) {
            return null;
        }

        try {
            return ServiceLocator.getInstancePacienteDAO().find(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Paciente> consultar() {
        try {
            return ServiceLocator.getInstancePacienteDAO().findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}