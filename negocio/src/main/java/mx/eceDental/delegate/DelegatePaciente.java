package mx.eceDental.delegate;

import java.util.ArrayList;
import java.util.List;

import mx.eceDental.entity.Paciente;
import mx.eceDental.entity.TelefonosPaciente;
import mx.eceDental.persistence.integration.ServiceLocator;

public class DelegatePaciente {

    public int registrar(String nombre, int edad, String sexo,
                         String correo, String domicilio, String edoCivil, String ocupacion) {

        if (nombre == null || nombre.trim().isEmpty() || nombre.trim().length() > 50
                || edad < 0 || edad > 120
                || (sexo != null && sexo.trim().length() > 20)
                || (correo != null && correo.trim().length() > 100)
                || (domicilio != null && domicilio.trim().length() > 150)
                || (edoCivil != null && edoCivil.trim().length() > 30)
                || (ocupacion != null && ocupacion.trim().length() > 50)) {
            return -1;
        }

        try {
            Paciente paciente = new Paciente();
            paciente.setNombre(nombre.trim());
            paciente.setEdad(edad);
            paciente.setSexo(sexo);
            paciente.setCorreo(correo);
            paciente.setDomicilio(domicilio);
            paciente.setEdoCivil(edoCivil);
            paciente.setOcupacion(ocupacion);
            paciente.setNotas(null);

            ServiceLocator.getInstancePacienteDAO().save(paciente);

            return paciente.getIdPaciente();

        } catch (Exception e) {
            return -1;
        }
    }

    public boolean registrarTelefonos(int idPaciente, String telefonoPrincipal, String telefonoAlternativo){
        try {
            Paciente paciente = ServiceLocator.getInstancePacienteDAO().find(idPaciente).orElse(null);
            if (telefonoPrincipal != null && !telefonoPrincipal.trim().isEmpty()) {
                TelefonosPaciente principal = new TelefonosPaciente();
                principal.setNumero(telefonoPrincipal);
                principal.setTipo(TelefonosPaciente.TipoTelefono.Principal);
                principal.setPaciente(paciente);
                ServiceLocator.getInstanceTelefonosPacienteDAO().save(principal);
            }
            if (telefonoAlternativo != null && !telefonoAlternativo.trim().isEmpty()) {
                TelefonosPaciente alternativo = new TelefonosPaciente();
                alternativo.setNumero(telefonoAlternativo);
                alternativo.setTipo(TelefonosPaciente.TipoTelefono.Alternativo);
                alternativo.setPaciente(paciente);
                ServiceLocator.getInstanceTelefonosPacienteDAO().save(alternativo);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idPaciente, String nombre, int edad, String sexo,
                             String correo, String domicilio, String edoCivil, String ocupacion, List<TelefonosPaciente> telefonos) {
        if (idPaciente <= 0
                || nombre == null || nombre.trim().isEmpty() || nombre.trim().length() > 50
                || edad < 0 || edad > 120
                || (sexo != null && sexo.trim().length() > 20)
                || (correo != null && correo.trim().length() > 100)
                || (domicilio != null && domicilio.trim().length() > 150)
                || (edoCivil != null && edoCivil.trim().length() > 30)
                || (ocupacion != null && ocupacion.trim().length() > 50)) {
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
            paciente.setCorreo(correo);
            paciente.setDomicilio(domicilio);
            paciente.setEdoCivil(edoCivil);
            paciente.setOcupacion(ocupacion);

            if (telefonos != null) {
                for (TelefonosPaciente nuevo : telefonos) {
                    TelefonosPaciente existente = paciente.getTelefonos().stream()
                            .filter(t -> t.getTipo() == nuevo.getTipo())
                            .findFirst()
                            .orElse(null);
                    if (existente != null) {
                        existente.setNumero(nuevo.getNumero());
                    } else {
                        nuevo.setPaciente(paciente);
                        paciente.getTelefonos().add(nuevo);
                    }
                }
            }

            ServiceLocator.getInstancePacienteDAO().update(paciente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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