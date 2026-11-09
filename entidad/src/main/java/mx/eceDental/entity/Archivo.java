package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name="Archivo")
public class Archivo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Archivo",nullable = false)
    private int idArchivo;

    @Size(max=100)
    @NotNull
    @Column(name = "Nombre_Archivo",nullable = false,length = 100)
    private String nombreArchivo;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha")
    private Date fecha;

    @Size(max=30)
    @Column(name = "Tipo",length = 30)
    private String tipo;

    @Size(max=255)
    @Column(name = "Ruta",length = 255)
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdArchivo() {
        return idArchivo;
    }
    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}