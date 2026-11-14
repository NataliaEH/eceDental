package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name="abono")
public class Abono {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Abono",nullable = false)
    private int idAbono;

    @NotNull
    @Column(name = "Fecha",nullable = false)
    private LocalDate fecha;

    @Column(name = "Abono",nullable = false)
    private double monto;

    @Size(max=100)
    @Column(name = "Concepto",length = 100)
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "Id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdAbono() {return idAbono;}
    public void setIdAbono(int idAbono) {this.idAbono = idAbono;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public double getMonto() {return monto;}
    public void setMonto(double monto) {this.monto = monto;}

    public String getConcepto() {return concepto;}
    public void setConcepto(String concepto) {this.concepto = concepto;}

    public Expediente getExpediente() {return expediente;}
    public void setExpediente(Expediente expediente) {this.expediente = expediente;}
}