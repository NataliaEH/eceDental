package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="registroodnt")
public class RegistroOdontograma {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Registro",nullable = false)
    private int idRegistro;

    @Column(name = "Pieza_Dental")
    private String piezaDental;

    @ElementCollection
    @CollectionTable(name = "cara",joinColumns = @JoinColumn(name = "Id_Registro_Odnt"))
    @Column(name = "Cara",length = 20)
    private List<String> caras = new ArrayList<>();

    @Size(max=30)
    @Column(name = "Color",length = 30)
    private String color;

    @Column(name = "Diagnostico",length = 500)
    private String diagnostico;

    @Column(name = "Procedimiento",length = 500)
    private String procedimiento;

    @Column(name = "Fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "Id_Odontograma",nullable = false)
    private Odontograma odontograma;

    public int getIdRegistro() {
        return idRegistro;
    }
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getPiezaDental() {
        return piezaDental;
    }
    public void setPiezaDental(String piezaDental) {
        this.piezaDental = piezaDental;
    }

    public List<String> getCara() {
        return caras;
    }
    public void setCara(List<String> caras) {
        this.caras = caras;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProcedimiento() {
        return procedimiento;
    }
    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Odontograma getOdontograma() {
        return odontograma;
    }
    public void setOdontograma(Odontograma odontograma) {
        this.odontograma = odontograma;
    }
}