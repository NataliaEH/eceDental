package mx.eceDental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "telefonos_paciente")
public class TelefonosPaciente {

    public enum TipoTelefono {
        Principal,
        Alternativo
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Telefono")
    private int idTelefono;

    @Column(name = "Numero", length = 15)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo")
    private TipoTelefono tipo;

    @ManyToOne
    @JoinColumn(name = "Id_Paciente")
    private Paciente paciente;

    public TelefonosPaciente() {
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefono getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefono tipo) {
        this.tipo = tipo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}