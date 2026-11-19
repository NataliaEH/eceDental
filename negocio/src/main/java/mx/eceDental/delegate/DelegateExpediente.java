package mx.eceDental.delegate;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mx.eceDental.entity.Abono;
import mx.eceDental.entity.Archivo;
import mx.eceDental.entity.DiagnosticoYPlan;
import mx.eceDental.entity.Doctor;
import mx.eceDental.entity.Expediente;
import mx.eceDental.entity.Interrogatorio;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.entity.Paciente;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.persistence.integration.ServiceLocator;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class DelegateExpediente {
    public boolean registrar(int idPaciente) {
        if (idPaciente <= 0) {
            return false;
        }
        try {
            Paciente paciente = ServiceLocator.getInstancePacienteDAO().find(idPaciente).orElse(null);
            if (paciente == null || paciente.getExpediente() != null) {
                return false;
            }
            Expediente expediente = new Expediente();
            expediente.setFechaCreacion(LocalDate.now());
            expediente.setPaciente(paciente);
            ServiceLocator.getInstanceExpedienteDAO().save(expediente);
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
            Expediente expediente = buscar(id);
            if (expediente == null || expediente.getFechaCreacion() == null) {
                return false;
            }

            LocalDate limite = LocalDate.now().minusYears(5);
            if (expediente.getFechaCreacion().isAfter(limite)) {
                return false;
            }
            ServiceLocator.getInstanceExpedienteDAO().delete(expediente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean exportar(int id, File destino, List<String> modulos, LocalDate desde, LocalDate hasta) {
        if (id <= 0 || destino == null) {
            return false;
        }
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            return false;
        }

        Document documento = new Document();

        try {
            Expediente expediente = buscar(id);
            Doctor doctor = new DelegateDoctor().consultarDatos();

            if (expediente == null || doctor == null) {
                return false;
            }

            boolean todos = modulos == null || modulos.isEmpty();
            boolean incluirPaciente = todos || modulos.contains("paciente");
            boolean incluirInterrogatorio = todos || modulos.contains("interrogatorio");
            boolean incluirOdontograma = todos || modulos.contains("odontograma");
            boolean incluirDiagnosticos = todos || modulos.contains("diagnosticos");
            boolean incluirNotas = todos || modulos.contains("notas");
            boolean incluirAbonos = todos || modulos.contains("abonos");
            boolean incluirArchivos = todos || modulos.contains("archivos");

            PdfWriter.getInstance(documento, new FileOutputStream(destino));
            documento.open();

            String[] datos = {
                    doctor.getNombreCompleto(),
                    doctor.getTitulo(),
                    "Cédula: " + doctor.getCedula(),
                    "RFC: " + doctor.getRFC(),
                    doctor.getNombreEstablecimiento(),
                    doctor.getDomicilioEstablecimiento(),
                    doctor.getCorreo(),
                    "Tel: " + doctor.getTelefonoEstablecimiento()
            };

            for (String dato : datos) {
                Paragraph linea = new Paragraph(dato);
                linea.setAlignment(Element.ALIGN_CENTER);
                documento.add(linea);
            }

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Expediente No. " + expediente.getIdExpediente()));
            documento.add(new Paragraph("Fecha de creación: " + expediente.getFechaCreacion()));

            if (incluirPaciente && expediente.getPaciente() != null) {
                Paciente paciente = expediente.getPaciente();
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("DATOS DEL PACIENTE"));
                documento.add(new Paragraph("Nombre: " + paciente.getNombre()));
                documento.add(new Paragraph("Edad: " + paciente.getEdad()));
                documento.add(new Paragraph("Sexo: " + paciente.getSexo()));
                documento.add(new Paragraph("Teléfonos: " + paciente.getTelefonos()));
                documento.add(new Paragraph("Correo: " + paciente.getCorreo()));
                documento.add(new Paragraph("Domicilio: " + paciente.getDomicilio()));
                documento.add(new Paragraph("Estado civil: " + paciente.getEdoCivil()));
                documento.add(new Paragraph("Ocupación: " + paciente.getOcupacion()));
                documento.add(new Paragraph("Notas: " + paciente.getNotas()));
            }

            if (incluirInterrogatorio && expediente.getInterrogatorio() != null) {
                Interrogatorio interrogatorio = expediente.getInterrogatorio();

                String hospitalizado;
                if (interrogatorio.isHospitalizado()) {
                    hospitalizado = "Sí";
                } else {
                    hospitalizado = "No";
                }

                String atencionMedica;
                if (interrogatorio.isAtencionMedica()) {
                    atencionMedica = "Sí";
                } else {
                    atencionMedica = "No";
                }

                String hemorragia;
                if (interrogatorio.isHemorragia()) {
                    hemorragia = "Sí";
                } else {
                    hemorragia = "No";
                }

                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("ANTECEDENTES"));
                documento.add(new Paragraph("Hospitalizado: " + hospitalizado));
                documento.add(new Paragraph("Atención médica: " + atencionMedica));
                documento.add(new Paragraph("Alergias: " + interrogatorio.getAlergias()));
                documento.add(new Paragraph("Hemorragia: " + hemorragia));
                documento.add(new Paragraph("Enfermedades: " + interrogatorio.getEnfermedades()));
                documento.add(new Paragraph("Medicación: " + interrogatorio.getMedicacion()));
                documento.add(new Paragraph("Embarazada: " + interrogatorio.getEmbarazada()));
                documento.add(new Paragraph("Amamantando: " + interrogatorio.getAmamantando()));
                documento.add(new Paragraph("Teléfono de emergencia: " + interrogatorio.getTelefonoEmergencia()));
                documento.add(new Paragraph("Teléfono servicio médico: " + interrogatorio.getTelefonoServicioMedico()));
                documento.add(new Paragraph("Última consulta dental: " + interrogatorio.getUltimaConsultaDental()));
            }

            if (incluirOdontograma && expediente.getOdontograma() != null
                    && expediente.getOdontograma().getRegistros() != null) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("ODONTOGRAMA"));
                for (RegistroOdontograma registro : expediente.getOdontograma().getRegistros()) {
                    documento.add(new Paragraph("Pieza " + registro.getPiezaDental()
                            + " | Cara: " + registro.getCara()
                            + " | Color: " + registro.getColor()
                            + " | " + registro.getDiagnostico()
                            + " | " + registro.getProcedimiento()
                            + " | " + registro.getFecha()));
                }
            }

            if (incluirDiagnosticos && expediente.getDiagnosticos() != null) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("DIAGNÓSTICO Y PLAN"));
                for (DiagnosticoYPlan d : expediente.getDiagnosticos()) {
                    LocalDate f = d.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | " + d.getDiagnostico()
                                + " | " + d.getProcedimiento()));
                    }
                }
            }

            if (incluirNotas && expediente.getNotasEvolucion() != null) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("NOTAS DE EVOLUCIÓN"));
                for (NotaEvolucion n : expediente.getNotasEvolucion()) {
                    LocalDate f = n.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | " + n.getContenido()));
                    }
                }
            }

            if (incluirAbonos && expediente.getAbonos() != null) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("PAGOS Y ABONOS"));
                for (Abono a : expediente.getAbonos()) {
                    LocalDate f = a.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | $" + a.getAbono()
                                + " | " + a.getConcepto()));
                    }
                }
            }

            if (incluirArchivos && expediente.getArchivo() != null) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("ARCHIVOS"));
                for (Archivo f : expediente.getArchivo()) {
                    documento.add(new Paragraph(f.getFecha() + " | " + f.getNombre()
                            + " | " + f.getTipo()));
                }
            }

            documento.close();

            return destino.exists() && destino.length() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            if (documento.isOpen()) {
                documento.close();
            }
            destino.delete();
            return false;
        }
    }

    public Expediente buscar(int id) {
        if (id <= 0) {
            return null;
        }
        try {
            return ServiceLocator.getInstanceExpedienteDAO().find(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public Expediente buscarPorPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            return null;
        }
        try {
            return ServiceLocator.getInstanceExpedienteDAO().obtenerPorPaciente(idPaciente);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public List<Expediente> consultar() {
        try {
            return ServiceLocator.getInstanceExpedienteDAO().findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}