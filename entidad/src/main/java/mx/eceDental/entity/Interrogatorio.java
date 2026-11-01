package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "interrogatorio")
public class Interrogatorio {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Interrogatorio",nullable = false)
    private int idInterrogatorio;

    @Column(name = "Hospitalizado")
    private boolean hospitalizado;

    @Column(name = "Hospitalizacion_Desc")
    private String hospitalizacionDesc;

    @Column(name = "Atencion_Medica")
    private boolean atencionMedica;

    @OneToMany(mappedBy = "interrogatorio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Alergia> alergias;

    @Column(name = "Hemorragia")
    private boolean hemorragia;

    @OneToMany(mappedBy = "interrogatorio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Enfermedad> enfermedades;

    @OneToMany(mappedBy = "interrogatorio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Medicacion> medicacion;

    @Column(name = "Embarazada")
    private String embarazada;

    @Column(name = "Amamantando")
    private String amamantando;

    @Size(max=15)
    @Column(name = "Tel_Emergencia",length = 15)
    private String telefonoEmergencia;

    @Size(max=15)
    @Column(name = "Tel_Servicio_Med",length = 15)
    private String telefonoServicioMedico;

    @Temporal(TemporalType.DATE)
    @Column(name = "Ultima_Consulta")
    private LocalDate ultimaConsultaDental;

    @OneToOne
    @JoinColumn(name = "Id_Expediente")
    private Expediente expediente;

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

    public String getHospitalizacionDesc() {
        return hospitalizacionDesc;
    }
    public void setHospitalizacionDesc(String hospitalizacionDesc) {
        this.hospitalizacionDesc = hospitalizacionDesc;
    }

    public boolean isAtencionMedica() {
        return atencionMedica;
    }
    public void setAtencionMedica(boolean atencionMedica) {
        this.atencionMedica = atencionMedica;
    }

    public List<Alergia> getAlergias() {
        return alergias;
    }
    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public boolean isHemorragia() {
        return hemorragia;
    }
    public void setHemorragia(boolean hemorragia) {
        this.hemorragia = hemorragia;
    }

    public List<Enfermedad> getEnfermedades() {
        return enfermedades;
    }
    public void setEnfermedades(List<Enfermedad> enfermedades) {
        this.enfermedades = enfermedades;
    }

    public List<Medicacion> getMedicacion() {
        return medicacion;
    }
    public void setMedicacion(List<Medicacion> medicacion) {
        this.medicacion = medicacion;
    }

    public String getEmbarazada() {
        return embarazada;
    }
    public void setEmbarazada(String embarazada) {
        this.embarazada = embarazada;
    }

    public String getAmamantando() {
        return amamantando;
    }
    public void setAmamantando(String amamantando) {
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

    public LocalDate getUltimaConsultaDental() {
        return ultimaConsultaDental;
    }
    public void setUltimaConsultaDental(LocalDate ultimaConsultaDental) {
        this.ultimaConsultaDental = ultimaConsultaDental;
    }

    public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}