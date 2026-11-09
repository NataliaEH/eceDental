package mx.eceDental.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Odontograma")
public class Odontograma {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Odontograma",nullable = false)
    private int idOdontograma;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha_Actualizacion")
    private Date fechaActualizacion;

    @OneToOne
    @JoinColumn(name = "id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdOdontograma() {return idOdontograma;}
    public void setIdOdontograma(int idOdontograma) {this.idOdontograma = idOdontograma;}

    public Date getFechaActualizacion() {return fechaActualizacion;}
    public void setFechaActualizacion(Date fechaActualizacion) {this.fechaActualizacion = fechaActualizacion;}

    public Expediente getExpediente() {return expediente;}
    public void setExpediente(Expediente expediente) {this.expediente = expediente;}
}