function inicializarDiagnosticos() {
    cargarDiagnosticos();
    guardarDiagnosticos();
}

function cargarDiagnosticos() {
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        alert("No se ha seleccionado ningún expediente.");
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
        const tabla = document.getElementById("tablaDiagnosticos");
        let registros = diagYPlanUI.consultar(idExpediente);
        tabla.innerHTML = "";

        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            const fila = document.createElement("tr");
            const fechaTexto = registro.getFecha();
            const tdFecha = document.createElement("td");
            tdFecha.textContent = fechaTexto;
            const tdDiagnostico = document.createElement("td");
            tdDiagnostico.textContent = registro.getDiagnostico();
            const tdPlan = document.createElement("td");
            tdPlan.textContent = registro.getProcedimiento();
            /*const tdAcciones = document.createElement("td");
            const botonEliminar = document.createElement("button");

            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-primary";
            botonEliminar.textContent = "Eliminar";

            botonEliminar.addEventListener("click", function () {
                //if (!confirm("¿Deseas eliminar este diagnóstico y plan?")) {
                //    return;
                //}

                window.diagYPlanUI.eliminar(registro.getIdDiagnosticoPlan());
                cargarDiagnosticos();
            });

            tdAcciones.appendChild(botonEliminar);*/

            fila.appendChild(tdFecha);
            fila.appendChild(tdDiagnostico);
            fila.appendChild(tdPlan);
            //fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
    } catch (error) {
        console.error("Error al cargar diagnosticos y planes de tratamiento:", error);
        alert("No fue posible cargar los datos del paciente.");
    }
}

function guardarDiagnosticos() {
    const formulario = document.getElementById("formDiagnostico");
    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const idExpediente = sessionStorage.getItem("expedienteActualId");
            if (!idExpediente) {
                alert("No se ha seleccionado ningún expediente.");
                return;
            }

            const expediente = expedienteUI.buscar(parseInt(idExpediente));
            const diagYPlan = diagYPlanUI.getDiagnosticoYPlan();
            const diagnostico = document.getElementById("diagnostico").value.trim();
            const plan = document.getElementById("planTratamiento").value.trim();

            diagYPlan.setDiagnostico(diagnostico);
            diagYPlan.setProcedimiento(plan);
            diagYPlan.setExpediente(expediente);

            diagYPlanUI.registrar();

            formulario.reset();
            cargarDiagnosticos();

            alert("Diagnóstico y plan registrados correctamente.");

        } catch (error) {
            console.error("Error al registrar diagnóstico:", error);
            alert("No fue posible registrar el diagnóstico.");
        }
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        formulario.reset();
        cargarDiagnosticos();
    });
}