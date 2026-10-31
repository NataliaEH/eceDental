package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="expediente")
public class Expediente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Expediente",nullable = false)
    private int idExpediente;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha_Creacion")
    private LocalDate fechaCreacion;

    @OneToOne
    @JoinColumn(name = "Id_Paciente")
    private Paciente paciente;

    @OneToOne(mappedBy = "expediente")
    private Interrogatorio interrogatorio;

    //@OneToMany(mappedBy = "expediente")
    //private List<NotaEvolucion> notasEvolucion;

    //@OneToMany(mappedBy = "expediente")
    //private List<Archivos> archivos;

    //@OneToMany(mappedBy = "expediente")
    //private List<Abonos> abonos;

    //@OneToOne(mappedBy = "expediente")
    //private Odontograma odontograma;

    @OneToMany(mappedBy = "expediente")
    private List<DiagnosticoYPlan> diagnosticos;

    public int getIdExpediente() {
        return idExpediente;
    }
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
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

    /*public List<NotaEvolucion> getNotasEvolucion() {
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
    }*/

    public List<DiagnosticoYPlan> getDiagnosticos() {
        return diagnosticos;
    }
    public void setDiagnosticos(List<DiagnosticoYPlan> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}