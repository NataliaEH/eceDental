package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name="Nota_Evolucion")
public class NotaEvolucion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Nota",nullable = false)
    private int idNota;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha",nullable = false)
    private Date fecha;

    @Column(name = "Contenido",length = 1000)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdNota() {return idNota;}
    public void setIdNota(int idNota) {this.idNota = idNota;}

    public Date getFecha() {return fecha;}
    public void setFecha(Date fecha) {this.fecha = fecha;}

    public String getContenido() {return contenido;}
    public void setContenido(String contenido) {this.contenido = contenido;}

    public Expediente getExpediente() {return expediente;}
    public void setExpediente(Expediente expediente) {this.expediente = expediente;}
}