function inicializarInterrogatorio() {
    const formulario = document.getElementById("formInterrogatorio");
    if (!formulario) return;

    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        alert("No se encontró un expediente seleccionado.");
        return;
    }

    const listaAlergias = document.getElementById("listaAlergias");
    const listaMedicamentos = document.getElementById("listaMedicamentos");

    function crearCampo(contenedor, valor = "", placeholder = "") {
        const fila = document.createElement("div");
        fila.className = "input-row";

        const input = document.createElement("input");
        input.type = "text";
        input.value = valor;
        input.placeholder = placeholder;

        const boton = document.createElement("button");
        boton.type = "button";
        boton.className = "btn btn-sm btn-primary";
        boton.textContent = "−";

        boton.addEventListener("click", () => fila.remove());

        fila.appendChild(input);
        fila.appendChild(boton);
        contenedor.appendChild(fila);
    }

    document.getElementById("agregarAlergia").addEventListener("click", () => {
        crearCampo(listaAlergias, "", "Nombre de la alergia");
    });

    document.getElementById("agregarMedicamento").addEventListener("click", () => {
        crearCampo(listaMedicamentos, "", "Nombre del medicamento");
    });

    // Cargar interrogatorio existente
    const interrogatorio = interrogatorioUI.buscar(parseInt(idExpediente));

    if (interrogatorio) {
        document.getElementById("hemorragias").value = interrogatorio.isHemorragia() ? "Si" : "No";
        document.getElementById("atencionMedica").value = interrogatorio.isAtencionMedica() ? "Si" : "No";
        document.getElementById("hospitalizacion").value = interrogatorio.isHospitalizado() ? "Si" : "No";
        document.getElementById("embarazada").value = interrogatorio.getEmbarazada() || "NA";
        document.getElementById("amamantando").value = interrogatorio.getAmamantando() || "NA";
        document.getElementById("telefonoEmergencia").value = interrogatorio.getTelefonoEmergencia() || "";
        document.getElementById("telefonoServicioMedico").value = interrogatorio.getTelefonoServicioMedico() || "";
        if (interrogatorio.getUltimaConsultaDental()) {
            const fecha = interrogatorio.getUltimaConsultaDental();
            document.getElementById("ultimaConsultaDental").value =
                fecha.toISOString().split("T")[0];
        }
        // Enfermedades
        const enfermedades = interrogatorio.getEnfermedades();
        if (enfermedades) {
            for (let i = 0; i < enfermedades.size(); i++) {
                const nombre = enfermedades.get(i).getNombre();
                document.querySelectorAll('input[name="enfermedades"]').forEach(check => {
                    if (check.value === nombre) {
                        check.checked = true;
                    }
                });
            }
        }

        // Alergias
        const alergias = interrogatorio.getAlergias();
        if (alergias) {
            for (let i = 0; i < alergias.size(); i++) {
                crearCampo(listaAlergias, alergias.get(i).getNombre(), "Nombre de la alergia");
            }
        }

        // Medicamentos
        const medicamentos = interrogatorio.getMedicacion();
        if (medicamentos) {
            for (let i = 0; i < medicamentos.size(); i++) {
                crearCampo(listaMedicamentos, medicamentos.get(i).getNombre(), "Nombre del medicamento");
            }
        }
    }

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        try {
            const interrogatorio = interrogatorioUI.getInterrogatorio();

            // Datos generales
            interrogatorio.setHemorragia(document.getElementById("hemorragias").value === "Si");
            interrogatorio.setAtencionMedica(document.getElementById("atencionMedica").value === "Si");
            interrogatorio.setHospitalizado(document.getElementById("hospitalizacion").value === "Si");
            interrogatorio.setEmbarazada(document.getElementById("embarazada").value);
            interrogatorio.setAmamantando(document.getElementById("amamantando").value);
            interrogatorio.setTelefonoEmergencia(document.getElementById("telefonoEmergencia").value.trim());
            interrogatorio.setTelefonoServicioMedico(document.getElementById("telefonoServicioMedico").value.trim());

            // Enfermedades
            interrogatorioUI.limpiarEnfermedades();
            document.querySelectorAll('input[name="enfermedades"]:checked')
                .forEach(check => {
                    interrogatorioUI.agregarEnfermedad(check.value);
                });

            // Alergias
            interrogatorioUI.limpiarAlergias();
            listaAlergias.querySelectorAll("input").forEach(input => {
                const nombre = input.value.trim();
                if (nombre) interrogatorioUI.agregarAlergia(nombre);
            });

            // Medicamentos
            interrogatorioUI.limpiarMedicacion();
            listaMedicamentos.querySelectorAll("input").forEach(input => {
                const nombre = input.value.trim();
                if (nombre) interrogatorioUI.agregarMedicamento(nombre);
            });

            const idExpediente = parseInt(sessionStorage.getItem("expedienteActualId"));
            const registrado = interrogatorioUI.modificar(idExpediente);
            if (registrado) {
                alert("Interrogatorio actualizado correctamente.");
            } else {
                alert("No se pudo actualizar el interrogatorio.");
            }

        } catch (error) {
            console.error("Error al modificar interrogatorio:", error);
            alert("Ocurrió un error al guardar el interrogatorio.");
        }
    });
}
