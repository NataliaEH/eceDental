package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name="Abono")
public class Abono {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Abono",nullable = false)
    private int idAbono;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha",nullable = false)
    private Date fecha;

    @Column(name = "Abono",nullable = false)
    private double abono;

    @Size(max=100)
    @Column(name = "Concepto",length = 100)
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdAbono() {return idAbono;}
    public void setIdAbono(int idAbono) {this.idAbono = idAbono;}

    public Date getFecha() {return fecha;}
    public void setFecha(Date fecha) {this.fecha = fecha;}

    public double getAbono() {return abono;}
    public void setAbono(double abono) {this.abono = abono;}

    public String getConcepto() {return concepto;}
    public void setConcepto(String concepto) {this.concepto = concepto;}

    public Expediente getExpediente() {return expediente;}
    public void setExpediente(Expediente expediente) {this.expediente = expediente;}
}