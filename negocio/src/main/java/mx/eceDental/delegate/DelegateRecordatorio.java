package mx.eceDental.delegate;

import mx.eceDental.entity.Recordatorio;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<Recordatorio> buscarUrgentes() {
        List<Recordatorio> resultado = new ArrayList<>();

        try {
            List<Recordatorio> todos = ServiceLocator.getInstanceRecordatorioDAO().findAll();
            LocalDate limite = LocalDate.now().plusDays(2);

            for (Recordatorio recordatorio : todos) {
                if (recordatorio.getFechaLimite() == null) {
                    continue;
                }

                if (!recordatorio.getFechaLimite().isAfter(limite)) {
                    resultado.add(recordatorio);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Recordatorio> consultar() {
        try {
            return ServiceLocator.getInstanceRecordatorioDAO().findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}