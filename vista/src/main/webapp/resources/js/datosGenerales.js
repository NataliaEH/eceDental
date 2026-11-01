function inicializarDatosGenerales() {
    cargarDatosGenerales();
    guardarDatosGenerales();
}

function cargarDatosGenerales(){
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        alert("No se encontró el expediente seleccionado.");
        return;
    }

    try {
        const expediente = window.expedienteUI.buscar(parseInt(idExpediente));
        if (!expediente) {
            alert("No se encontró el expediente.");
            return;
        }

        const paciente = expediente.getPaciente();
        if (!paciente) {
            alert("El expediente no tiene un paciente asociado.");
            return;
        }

        document.getElementById("nombrePaciente").textContent = paciente.getNombre();
        document.getElementById("nombre").value = paciente.getNombre() || "";
        document.getElementById("edad").value = paciente.getEdad() ?? "";
        const sexo = paciente.getSexo();
        document.getElementById("sexoM").checked = sexo === "M";
        document.getElementById("sexoF").checked = sexo === "F";
        const estadosCivil = {
            "Soltero": "Soltero/a",
            "Casado": "Casado/a",
            "Divorciado": "Divorciado/a",
            "Viudo": "Viudo/a",
            "Union libre": "Unión libre"
        };
        document.getElementById("edoCivil").value = paciente.getEdoCivil() || "";
        document.getElementById("ocupacion").value = paciente.getOcupacion() || "";
        document.getElementById("domicilio").value = paciente.getDomicilio() || "";
        document.getElementById("correo").value = paciente.getCorreo() || "";

        const telefonos = paciente.getTelefonos();
        document.getElementById("tel").value = "";
        document.getElementById("telAlt").value = "";
        if (telefonos) {
            for (let i = 0; i < telefonos.size(); i++) {
                const telefono = telefonos.get(i);
                const tipo = String(telefono.getTipo());
                if (tipo === "Principal") {
                    document.getElementById("tel").value = telefono.getNumero() || "";
                } else if (tipo === "Alternativo") {
                    document.getElementById("telAlt").value = telefono.getNumero() || "";
                }
            }
        }

    } catch (error) {
        console.error("Error al cargar datos generales:", error);
        alert("No fue posible cargar los datos del paciente.");
    }
}

function guardarDatosGenerales() {
    const formulario = document.getElementById("formDatosGenerales");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();

        const datos = {
            nombre: document.getElementById("nombre").value.trim(),
            edad: parseInt(document.getElementById("edad").value),
            sexo: document.querySelector('input[name="sexo"]:checked')?.value,
            estadoCivil: document.getElementById("edoCivil").value,
            ocupacion: document.getElementById("ocupacion").value.trim(),
            domicilio: document.getElementById("domicilio").value.trim(),
            telefonoPrincipal: document.getElementById("tel").value.trim(),
            telefonoAlternativo: document.getElementById("telAlt").value.trim(),
            correo: document.getElementById("correo").value.trim(),
        };
        try {
            const idExpediente = sessionStorage.getItem("expedienteActualId");
            const expediente = window.expedienteUI.buscar(parseInt(idExpediente));
            const paciente = expediente.getPaciente();

            paciente.setNombre(datos.nombre);
            paciente.setEdad(datos.edad);
            paciente.setSexo(datos.sexo);
            paciente.setEdoCivil(datos.estadoCivil);
            paciente.setOcupacion(datos.ocupacion);
            paciente.setDomicilio(datos.domicilio);
            paciente.setCorreo(datos.correo);

            pacienteUI.setPaciente(paciente);

            pacienteUI.limpiarTelefonos();
            if (datos.telefonoPrincipal) {
                pacienteUI.agregarTelefono(datos.telefonoPrincipal, "Principal");
            }
            if (datos.telefonoAlternativo) {
                pacienteUI.agregarTelefono(datos.telefonoAlternativo, "Alternativo");
            }

            const modificado = pacienteUI.modificar(paciente.getIdPaciente());
            if(modificado){
                //
            }else{
                //
            }
        } catch (error) {
            //
        }
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        cargarDatosGenerales();
    });
}