package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Expediente")
public class Expediente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Expediente",nullable = false)
    private int idExpediente;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    private Paciente paciente;

    private Interrogatorio interrogatorio;

    private List<NotaEvolucion> notasEvolucion;

    private List<Archivos> archivos;

    private List<Abonos> abonos;

    private Odontograma odontograma;

    private List<DiagnosticoYPlan> diagnosticos;

    public int getIdExpediente() {
        return idExpediente;
    }
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Interrogatorio getInterrogatorio() {
        return interrogatorio;
    }
    public void setInterrogatorio(Interrogatorio interrogatorio) {
        this.interrogatorio = interrogatorio;
    }

    public List<NotaEvolucion> getNotasEvolucion() {
        return notasEvolucion;
    }
    public void setNotasEvolucion(List<NotaEvolucion> notasEvolucion) {
        this.notasEvolucion = notasEvolucion;
    }

    public List<Archivos> getArchivos() {
        return archivos;
    }
    public void setArchivos(List<Archivos> archivos) {
        this.archivos = archivos;
    }

    public List<Abonos> getAbonos() {
        return abonos;
    }
    public void setAbonos(List<Abonos> abonos) {
        this.abonos = abonos;
    }

    public Odontograma getOdontograma() {
        return odontograma;
    }
    public void setOdontograma(Odontograma odontograma) {
        this.odontograma = odontograma;
    }

    public List<DiagnosticoYPlan> getDiagnosticos() {
        return diagnosticos;
    }
    public void setDiagnosticos(List<DiagnosticoYPlan> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}
