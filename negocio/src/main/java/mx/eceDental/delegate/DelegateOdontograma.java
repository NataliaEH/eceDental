package mx.eceDental.delegate;

public class DelegateOdontograma {

    public boolean registrarRegistro(int idExpediente, int piezaDental, String cara,
                                     String diagnostico, String procedimiento, String fecha) {

        if (idExpediente <= 0
                || piezaDental <= 0
                || cara == null || cara.trim().isEmpty()
                || diagnostico == null || diagnostico.trim().isEmpty()
                || procedimiento == null || procedimiento.trim().isEmpty()
                || fecha == null || fecha.trim().isEmpty()) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            Odontograma odontograma = expediente.getOdontograma();

            if (odontograma == null) {
                odontograma = new Odontograma();
                odontograma.setFechaActualizacion(new Date());
                odontograma.setExpediente(expediente);
                ServiceLocator.getInstanceOdontogramaDAO().save(odontograma);
            } else {
                odontograma.setFechaActualizacion(new Date());
                ServiceLocator.getInstanceOdontogramaDAO().update(odontograma);
            }

            RegistroOdontograma registro = new RegistroOdontograma();
            registro.setPiezaDental(piezaDental);
            registro.setCara(cara.trim());
            registro.setDiagnostico(diagnostico.trim());
            registro.setProcedimiento(procedimiento.trim());
            registro.setFecha(fecha.trim());
            registro.setOdontograma(odontograma);

            ServiceLocator.getInstanceRegistroOdntDAO().save(registro);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}}