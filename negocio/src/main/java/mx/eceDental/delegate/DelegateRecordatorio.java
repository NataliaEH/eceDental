package mx.eceDental.delegate;

import mx.eceDental.entity.Recordatorio;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.time.LocalDate;

public class DelegateRecordatorio {

    public boolean registrar(String contenido, LocalDate fechaLimite) {
        if (contenido == null || contenido.trim().isEmpty()
                || contenido.trim().length() > 100
                || fechaLimite == null) {
            return false;
        }

        try {
            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setContenido(contenido.trim());
            recordatorio.setFechaLimite(fechaLimite);

            ServiceLocator.getInstanceRecordatorioDAO().save(recordatorio);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}