package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "DiagnosticoYPlan")
public class DiagnosticoYPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDiagnosticoPlan;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "procedimiento")
    private String procedimiento;

    private Date fecha;

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

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}
