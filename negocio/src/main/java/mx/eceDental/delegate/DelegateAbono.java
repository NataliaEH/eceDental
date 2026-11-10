package mx.eceDental.delegate;



import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DelegateAbono {


    public boolean registrar(int idExpediente, Date fecha, double abono, String concepto) {

        if (idExpediente <= 0
                || fecha == null || fecha.after(new Date())
                || abono <= 0
                || concepto == null || concepto.trim().isEmpty()
                || concepto.trim().length() > 100) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            Abono pago = new Abono();
            pago.setFecha(fecha);
            pago.setAbono(abono);
            pago.setConcepto(concepto.trim());
            pago.setExpediente(expediente);

            ServiceLocator.getInstanceAbonoDAO().save(pago);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idAbono, Date fecha, double abono, String concepto) {

        if (idAbono <= 0
                || fecha == null || fecha.after(new Date())
                || abono <= 0
                || concepto == null || concepto.trim().isEmpty()) {
            return false;
        }

        try {
            Abono pago = ServiceLocator.getInstanceAbonoDAO().find(idAbono).orElse(null);

            if (pago == null) {
                return false;
            }

            pago.setFecha(fecha);
            pago.setAbono(abono);
            pago.setConcepto(concepto.trim());

            ServiceLocator.getInstanceAbonoDAO().update(pago);

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
            Abono pago = ServiceLocator.getInstanceAbonoDAO().find(id).orElse(null);

            if (pago == null) {
                return false;
            }

            ServiceLocator.getInstanceAbonoDAO().delete(pago);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Abono> consultar(int idExpediente) {
        List<Abono> resultado = new ArrayList<>();

        if (idExpediente <= 0) {
            return resultado;
        }

        try {
            List<Abono> todos = ServiceLocator.getInstanceAbonoDAO().findAll();

            for (Abono pago : todos) {
                if (pago.getExpediente() != null
                        && pago.getExpediente().getIdExpediente() == idExpediente) {
                    resultado.add(pago);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}