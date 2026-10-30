package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Interrogatorio")
public class Interrogatorio {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Interrogatorio",nullable = false)
    private int idInterrogatorio;

    @Column(name = "hospitalizado")
    private boolean hospitalizado;

    @Column(name = "atencion_medica")
    private boolean atencionMedica;

    @Column(name = "alergia",length = 100)
    private List<String> alergias;

    @Column(name = "hemorragia")
    private boolean hemorragia;

    @Column(name = "enfermedad",length = 100)
    private List<String> enfermedades;

    @Column(name = "medicamento",length = 100)
    private List<String> medicacion;

    @Column(name = "embarazada")
    private boolean embarazada;

    @Column(name = "amamantando")
    private boolean amamantando;

    @Size(max=20)
    @Column(name = "telefono_emergencia",length = 20)
    private String telefonoEmergencia;

    @Size(max=20)
    @Column(name = "telefono_servicio_medico",length = 20)
    private String telefonoServicioMedico;

    @Size(max=50)
    @Column(name = "ultima_consulta_dental",length = 50)
    private String ultimaConsultaDental;

    /*@OneToOne
    @JoinColumn(name = "Id_Expediente")
    private Expediente expediente;*/

    public int getIdInterrogatorio() {
        return idInterrogatorio;
    }
    public void setIdInterrogatorio(int idInterrogatorio) {
        this.idInterrogatorio = idInterrogatorio;
    }

    public boolean isHospitalizado() {
        return hospitalizado;
    }
    public void setHospitalizado(boolean hospitalizado) {
        this.hospitalizado = hospitalizado;
    }

    public boolean isAtencionMedica() {
        return atencionMedica;
    }
    public void setAtencionMedica(boolean atencionMedica) {
        this.atencionMedica = atencionMedica;
    }

    public List<String> getAlergias() {
        return alergias;
    }
    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public boolean isHemorragia() {
        return hemorragia;
    }
    public void setHemorragia(boolean hemorragia) {
        this.hemorragia = hemorragia;
    }

    public List<String> getEnfermedades() {
        return enfermedades;
    }
    public void setEnfermedades(List<String> enfermedades) {
        this.enfermedades = enfermedades;
    }

    public List<String> getMedicacion() {
        return medicacion;
    }
    public void setMedicacion(List<String> medicacion) {
        this.medicacion = medicacion;
    }

    public boolean isEmbarazada() {
        return embarazada;
    }
    public void setEmbarazada(boolean embarazada) {
        this.embarazada = embarazada;
    }

    public boolean isAmamantando() {
        return amamantando;
    }
    public void setAmamantando(boolean amamantando) {
        this.amamantando = amamantando;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }
    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public String getTelefonoServicioMedico() {
        return telefonoServicioMedico;
    }
    public void setTelefonoServicioMedico(String telefonoServicioMedico) {
        this.telefonoServicioMedico = telefonoServicioMedico;
    }

    public String getUltimaConsultaDental() {
        return ultimaConsultaDental;
    }
    public void setUltimaConsultaDental(String ultimaConsultaDental) {
        this.ultimaConsultaDental = ultimaConsultaDental;
    }

    /*public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }*/
}
