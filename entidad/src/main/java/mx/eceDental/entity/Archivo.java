package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="archivo")
public class Archivo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Archivo",nullable = false)
    private int idArchivo;

    @Size(max=100)
    @NotNull
    @Column(name = "Nombre",nullable = false,length = 100)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha")
    private LocalDate fecha;

    @Size(max=30)
    @Column(name = "Tipo",length = 30)
    private String tipo;

    @Size(max=255)
    @Column(name = "Ruta",length = 255)
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "Id_Expediente",nullable = false)
    private Expediente expediente;

    public int getIdArchivo() {
        return idArchivo;
    }
    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
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