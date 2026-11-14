package mx.eceDental.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="odontograma")
public class Odontograma {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Odontograma",nullable = false)
    private int idOdontograma;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha_Actualizacion")
    private LocalDate fechaActualizacion;

    @OneToMany(mappedBy = "odontograma")
    private List<RegistroOdontograma> registros;

    @OneToOne
    @JoinColumn(name = "id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdOdontograma() {return idOdontograma;}
    public void setIdOdontograma(int idOdontograma) {this.idOdontograma = idOdontograma;}

    public LocalDate getFechaActualizacion() {return fechaActualizacion;}
    public void setFechaActualizacion(LocalDate fechaActualizacion) {this.fechaActualizacion = fechaActualizacion;}

    public List<RegistroOdontograma> getRegistros() {return registros;}
    public void setRegistros(List<RegistroOdontograma> registros) {this.registros = registros;}

    public Expediente getExpediente() {return expediente;}
    public void setExpediente(Expediente expediente) {this.expediente = expediente;}
}