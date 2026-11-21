package mx.eceDental.delegate;

import mx.eceDental.entity.Expediente;
import mx.eceDental.entity.Interrogatorio;
import mx.eceDental.entity.Odontograma;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DelegateOdontograma {

    public boolean registrarRegistro(int idExpediente, String piezaDental, List<String> cara,
                                     String color, String diagnostico, String procedimiento) {

        if (idExpediente <= 0
                || piezaDental == null || piezaDental.isEmpty()
                || cara == null || cara.isEmpty()
                || color == null || color.isEmpty()
                || diagnostico == null || diagnostico.trim().isEmpty()
                || procedimiento == null || procedimiento.trim().isEmpty()) {
            System.out.println("fisrt if false");
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                System.out.println("expediente null false");
                return false;
            }

            Odontograma odontograma = expediente.getOdontograma();

            if (odontograma == null) {
                odontograma = new Odontograma();
                odontograma.setFechaActualizacion(LocalDate.now());
                odontograma.setExpediente(expediente);
                ServiceLocator.getInstanceOdontogramaDAO().save(odontograma);
            } else {
                odontograma.setFechaActualizacion(LocalDate.now());
                ServiceLocator.getInstanceOdontogramaDAO().update(odontograma);
            }

            RegistroOdontograma registro = new RegistroOdontograma();
            registro.setPiezaDental(piezaDental);
            registro.setCara(cara);
            registro.setColor(color);
            registro.setDiagnostico(diagnostico.trim());
            registro.setProcedimiento(procedimiento.trim());
            registro.setFecha(LocalDate.now());
            registro.setOdontograma(odontograma);

            ServiceLocator.getInstanceRegistroOdntDAO().save(registro);
            System.out.println("returning true delegate");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("returning error delegate");
            return false;
        }
    }

    public boolean modificarRegistro(int idRegistro, String piezaDental, List<String> cara,
                                     String color, String diagnostico, String procedimiento) {

        if (idRegistro <= 0
                || piezaDental == null || piezaDental.isEmpty()
                || cara == null || cara.isEmpty()
                || color == null || color.isEmpty()
                || diagnostico == null || diagnostico.trim().isEmpty()
                || procedimiento == null || procedimiento.trim().isEmpty()) {
            return false;
        }

        try {
            RegistroOdontograma registro = ServiceLocator.getInstanceRegistroOdntDAO()
                    .find(idRegistro).orElse(null);

            if (registro == null) {
                return false;
            }

            registro.setPiezaDental(piezaDental);
            registro.setCara(cara);
            registro.setColor(color);
            registro.setDiagnostico(diagnostico.trim());
            registro.setProcedimiento(procedimiento.trim());

            ServiceLocator.getInstanceRegistroOdntDAO().update(registro);

            Odontograma odontograma = registro.getOdontograma();

            if (odontograma != null) {
                odontograma.setFechaActualizacion(LocalDate.now());
                ServiceLocator.getInstanceOdontogramaDAO().update(odontograma);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarRegistro(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            RegistroOdontograma registro = ServiceLocator.getInstanceRegistroOdntDAO()
                    .find(id).orElse(null);

            if (registro == null) {
                return false;
            }

            Odontograma odontograma = registro.getOdontograma();

            ServiceLocator.getInstanceRegistroOdntDAO().delete(registro);

            if (odontograma != null) {
                odontograma.setFechaActualizacion(LocalDate.now());
                ServiceLocator.getInstanceOdontogramaDAO().update(odontograma);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<RegistroOdontograma> consultarReg(int idExpediente) {
        if (idExpediente <= 0) {
            return new ArrayList<>();
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null || expediente.getOdontograma() == null
                    || expediente.getOdontograma().getRegistros() == null) {
                return new ArrayList<>();
            }

            return new ArrayList<>(expediente.getOdontograma().getRegistros());

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public RegistroOdontograma buscarRegistro(int idRegistro){
        if (idRegistro <= 0) {
            return null;
        }
        try {
            return ServiceLocator.getInstanceRegistroOdntDAO().find(idRegistro).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}