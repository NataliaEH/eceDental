package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "diagnosticoyplan")
public class DiagnosticoYPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_DiagnosticoPlan")
    private int idDiagnosticoPlan;

    @Size(max = 50)
    @Column(name = "Diagnostico", length = 50)
    private String diagnostico;

    @Size(max = 50)
    @Column(name = "Procedimiento", length = 50)
    private String procedimiento;

    @Temporal(TemporalType.DATE)
    @Column(name = "Fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "Id_Expediente")
    private Expediente expediente;

    public int getIdDiagnosticoPlan() {
        return idDiagnosticoPlan;
    }
    public void setIdDiagnosticoPlan(int idDiagnosticoPlan) {
        this.idDiagnosticoPlan = idDiagnosticoPlan;
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

    public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}