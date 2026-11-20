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

    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            Recordatorio recordatorio = ServiceLocator.getInstanceRecordatorioDAO().find(id).orElse(null);

            if (recordatorio == null) {
                return false;
            }

            ServiceLocator.getInstanceRecordatorioDAO().delete(recordatorio);

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

    public List<Recordatorio> buscarPorUrgencia(String urgencia) {
        List<Recordatorio> resultado = new ArrayList<>();

        if (urgencia == null || urgencia.trim().isEmpty()) {
            return resultado;
        }

        try {
            List<Recordatorio> todos = ServiceLocator.getInstanceRecordatorioDAO().findAll();
            LocalDate hoy = LocalDate.now();

            for (Recordatorio recordatorio : todos) {
                if (recordatorio.getFechaLimite() == null) {
                    continue;
                }

                int diferencia = Math.toIntExact(recordatorio.getFechaLimite().toEpochDay() - hoy.toEpochDay());
                String calculada;

                if (diferencia < 0) {
                    calculada = "Vencida";
                } else if (diferencia <= 2) {
                    calculada = "Alta";
                } else if (diferencia > 7) {
                    calculada = "Baja";
                } else {
                    calculada = "Media";
                }

                if (calculada.equalsIgnoreCase(urgencia.trim())) {
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