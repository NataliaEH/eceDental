package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="Recordatorio")
public class Recordatorio {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Recordatorio",nullable = false)
    private int idRecordatorio;

    @NotNull
    @Column(name = "Fecha_Limite",nullable = false)
    private LocalDate fechaLimite;

    @Column(name = "Contenido",length = 500)
    private String contenido;

    public int getIdRecordatorio() {return idRecordatorio;}
    public void setIdRecordatorio(int idRecordatorio) {this.idRecordatorio = idRecordatorio;}

    public LocalDate getFechaLimite() {return fechaLimite;}
    public void setFechaLimite(LocalDate fechaLimite) {this.fechaLimite = fechaLimite;}

    public String getContenido() {return contenido;}
    public void setContenido(String contenido) {this.contenido = contenido;}
}