package mx.eceDental.delegate;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.*;
import mx.eceDental.entity.*;
import mx.eceDental.persistence.integration.ServiceLocator;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.BaseFont;
import java.io.FileOutputStream;

public class DelegateExpediente {
    private static BaseFont cargar(String archivo) {
        try (InputStream in = DelegateExpediente.class.getResourceAsStream("/fuentes/" + archivo)) {
            if (in == null) {
                throw new RuntimeException("No se encontró /fuentes/" + archivo);
            }
            byte[] bytes = in.readAllBytes();
            return BaseFont.createFont(archivo, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, bytes, null);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando fuente " + archivo, e);
        }
    }

    private static final Font FUENTE_NORMAL = new Font(cargar("SourceSans3-Regular.ttf"), 10);
    private static final Font FUENTE_TITULO = new Font(cargar("LibreBaskerville-Bold.ttf"), 13);

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

    public boolean exportar(int id, File destino, List<String> modulos, LocalDate desde, LocalDate hasta, String imagenOdontograma) {
        if (id <= 0 || destino == null) {
            return false;
        }
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            return false;
        }

        Document documento = new Document();
        File carpetaExp = new File(destino, "Exp" + id);
        File carpetaArchivos = new File(carpetaExp, "Archivos");
        File archivoPdf = new File(carpetaExp, "Exp" + id + ".pdf");

        try {
            Expediente expediente = buscar(id);
            Doctor doctor = new DelegateDoctor().consultarDatos();

            if (expediente == null || doctor == null) {
                return false;
            }

            boolean todos = modulos == null || modulos.isEmpty();
            boolean incluirPaciente = todos || modulos.contains("datosGenerales");
            boolean incluirInterrogatorio = todos || modulos.contains("interrogatorio");
            boolean incluirOdontograma = todos || modulos.contains("odontograma");
            boolean incluirDiagnosticos = todos || modulos.contains("diagnosticos");
            boolean incluirNotas = todos || modulos.contains("notas");
            boolean incluirAbonos = todos || modulos.contains("pagos");
            boolean incluirArchivos = todos || modulos.contains("archivos");

            carpetaExp.mkdirs();
            PdfWriter.getInstance(documento, new FileOutputStream(archivoPdf));
            documento.open();

            String[] datos = {
                    doctor.getNombreCompleto(),
                    doctor.getTitulo(),
                    "Ced. Prof. " + doctor.getCedula()+"   R.F.C. " + doctor.getRfc(),
                    doctor.getNombreEstablecimiento(),
                    doctor.getDomicilioEstablecimiento(),
                    "Correo " + doctor.getCorreo(),
                    "Teléfono " + doctor.getTelefonoEstablecimiento()
            };

            for (String dato : datos) {
                Paragraph linea = new Paragraph(dato, FUENTE_NORMAL);
                linea.setAlignment(Element.ALIGN_CENTER);
                documento.add(linea);
            }

            documento.add(new Paragraph(" ", FUENTE_NORMAL));
            documento.add(new Paragraph("Expediente No. " + expediente.getIdExpediente(), FUENTE_NORMAL));
            documento.add(new Paragraph("Fecha de creación: " + expediente.getFechaCreacion(), FUENTE_NORMAL));

            if (incluirPaciente && expediente.getPaciente() != null) {
                Paciente paciente = expediente.getPaciente();
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Datos del Paciente", FUENTE_TITULO));
                documento.add(new Paragraph("Nombre: " + paciente.getNombre(), FUENTE_NORMAL));
                documento.add(new Paragraph("Edad: " + paciente.getEdad(), FUENTE_NORMAL));
                documento.add(new Paragraph("Sexo: " + paciente.getSexo(), FUENTE_NORMAL));
                StringBuilder telefonos = new StringBuilder();
                for (TelefonosPaciente t : paciente.getTelefonos()) {
                    if (!telefonos.isEmpty()) {
                        telefonos.append(", ");
                    }
                    telefonos.append(t.getNumero());
                }
                documento.add(new Paragraph("Teléfonos: " + telefonos, FUENTE_NORMAL));
                documento.add(new Paragraph("Correo: " + paciente.getCorreo(), FUENTE_NORMAL));
                documento.add(new Paragraph("Domicilio: " + paciente.getDomicilio(), FUENTE_NORMAL));
                documento.add(new Paragraph("Estado civil: " + paciente.getEdoCivil(), FUENTE_NORMAL));
                documento.add(new Paragraph("Ocupación: " + paciente.getOcupacion(), FUENTE_NORMAL));
                String notas;
                if(paciente.getNotas() == null || paciente.getNotas().isBlank())
                    notas = "Sin notas";
                else
                    notas = paciente.getNotas();
                documento.add(new Paragraph("Notas: " + notas, FUENTE_NORMAL));
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
                StringBuilder alergias = new StringBuilder();
                if(!interrogatorio.getAlergias().isEmpty()){
                    for (Alergia a : interrogatorio.getAlergias()) {
                        if (!alergias.isEmpty()) {
                            alergias.append(", ");
                        }
                        alergias.append(a.getNombre());
                    }
                }else
                    alergias.append("Ninguna");
                StringBuilder enfermedades = new StringBuilder();
                if(!interrogatorio.getEnfermedades().isEmpty()){
                for (Enfermedad a : interrogatorio.getEnfermedades()) {
                    if (!enfermedades.isEmpty()) {
                        enfermedades.append(", ");
                    }
                    enfermedades.append(a.getNombre());
                }
                }else
                    enfermedades.append("Ninguna");
                StringBuilder medicacion = new StringBuilder();
                if(!interrogatorio.getMedicacion().isEmpty()){
                    for (Medicacion m : interrogatorio.getMedicacion()) {
                        if (!medicacion.isEmpty()) {
                            medicacion.append(", ");
                        }
                        medicacion.append(m.getNombre());
                    }
                }else
                    medicacion.append("Ninguna");


                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Antecedentes", FUENTE_TITULO));
                documento.add(new Paragraph("Hospitalizado: " + hospitalizado, FUENTE_NORMAL));
                documento.add(new Paragraph("Atención médica: " + atencionMedica, FUENTE_NORMAL));
                documento.add(new Paragraph("Alergias: " + alergias, FUENTE_NORMAL));
                documento.add(new Paragraph("Hemorragia: " + hemorragia, FUENTE_NORMAL));
                documento.add(new Paragraph("Enfermedades: " + enfermedades, FUENTE_NORMAL));
                documento.add(new Paragraph("Medicación: " + medicacion, FUENTE_NORMAL));
                documento.add(new Paragraph("Embarazada: " + interrogatorio.getEmbarazada(), FUENTE_NORMAL));
                documento.add(new Paragraph("Amamantando: " + interrogatorio.getAmamantando(), FUENTE_NORMAL));
                documento.add(new Paragraph("Teléfono de emergencia: " + interrogatorio.getTelefonoEmergencia(), FUENTE_NORMAL));
                documento.add(new Paragraph("Teléfono servicio médico: " + interrogatorio.getTelefonoServicioMedico(), FUENTE_NORMAL));
                documento.add(new Paragraph("Última consulta dental: " + interrogatorio.getUltimaConsultaDental(), FUENTE_NORMAL));
            }

            if (incluirOdontograma && expediente.getOdontograma() != null
                    && expediente.getOdontograma().getRegistros() != null) {
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Odontograma", FUENTE_TITULO));

                if (imagenOdontograma != null && imagenOdontograma.contains(",")) {
                    try {
                        String base64 = imagenOdontograma.substring(imagenOdontograma.indexOf(",") + 1);
                        byte[] bytes = java.util.Base64.getDecoder().decode(base64);

                        Image imagen = Image.getInstance(bytes);
                        imagen.scaleToFit(500, 200);
                        imagen.setAlignment(Element.ALIGN_CENTER);
                        documento.add(imagen);
                    } catch (Exception e) {
                        e.printStackTrace();
                        for (RegistroOdontograma registro : expediente.getOdontograma().getRegistros()) {
                            documento.add(new Paragraph("Pieza " + registro.getPiezaDental()
                                    + " | Cara: " + registro.getCara()
                                    + " | Color: " + registro.getColor()
                                    + " | " + registro.getDiagnostico()
                                    + " | " + registro.getProcedimiento()
                                    + " | " + registro.getFecha(), FUENTE_NORMAL));
                        }
                    }
                }
            }

            if (incluirDiagnosticos && expediente.getDiagnosticos() != null) {
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Diagnósticos y Planes de Tratamiento", FUENTE_TITULO));
                for (DiagnosticoYPlan d : expediente.getDiagnosticos()) {
                    LocalDate f = d.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | " + d.getDiagnostico()
                                + " | " + d.getProcedimiento(), FUENTE_NORMAL));
                    }
                }
            }

            if (incluirNotas && expediente.getNotasEvolucion() != null) {
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Notas de Evolución", FUENTE_TITULO));
                for (NotaEvolucion n : expediente.getNotasEvolucion()) {
                    LocalDate f = n.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | " + n.getContenido(), FUENTE_NORMAL));
                    }
                }
            }

            if (incluirAbonos && expediente.getAbono() != null) {
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Pagos", FUENTE_TITULO));
                for (Abono a : expediente.getAbono()) {
                    LocalDate f = a.getFecha();
                    if ((desde == null || (f != null && !f.isBefore(desde)))
                            && (hasta == null || (f != null && !f.isAfter(hasta)))) {
                        documento.add(new Paragraph(f + " | $" + a.getMonto()
                                + " | " + a.getConcepto(), FUENTE_NORMAL));
                    }
                }
            }

            if (incluirArchivos && expediente.getArchivo() != null) {
                documento.add(new Paragraph(" ", FUENTE_NORMAL));
                documento.add(new Paragraph("Archivos", FUENTE_TITULO));

                if (!expediente.getArchivo().isEmpty()) {
                    carpetaArchivos.mkdirs();
                }

                for (Archivo f : expediente.getArchivo()) {
                    documento.add(new Paragraph(f.getFecha() + " | " + f.getNombre()
                            + " | " + f.getTipo(), FUENTE_NORMAL));
                    try {
                        String nombreSeguro = f.getNombre().replaceAll("[\\\\/:*?\"<>|]", "_");
                        Path copia = carpetaArchivos.toPath().resolve(nombreSeguro);
                        Files.copy(Paths.get(f.getRuta()), copia, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            documento.close();
            return archivoPdf.exists() && archivoPdf.length() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (documento.isOpen()) {
                documento.close();
            }
            archivoPdf.delete();
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