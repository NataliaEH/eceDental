function inicializarDiagnosticos() {
    cargarDiagnosticos();
    guardarDiagnosticos();
}

function cargarDiagnosticos() {
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        mostrarMensaje("No se ha seleccionado ningún expediente.","danger");
        return;
    }

    try {
        const expediente = window.expedienteUI.buscar(parseInt(idExpediente));
        if (!expediente) {
            mostrarMensaje("No se encontró el expediente.","danger");
            return;
        }

        const paciente = expediente.getPaciente();
        if (!paciente) {
            mostrarMensaje("El expediente no tiene un paciente asociado.","danger");
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
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
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
                mostrarMensaje("No se ha seleccionado ningún expediente.","danger");
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

            mostrarMensaje("Diagnóstico y plan registrados correctamente.","success");
            cargarDiagnosticos();
            formulario.reset();
        } catch (error) {
            mostrarMensaje("No fue posible registrar el diagnóstico.","danger");
        }
    });
}