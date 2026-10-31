function inicializarDiagnosticos() {
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        alert("No se ha seleccionado ningún expediente.");
        return;
    }

    const nombrePaciente = document.getElementById("nombrePaciente");
    const tabla = document.getElementById("tablaDiagnosticos");
    const formulario = document.getElementById("formDiagnostico");
    const fecha = document.getElementById("fechaRegistro");
    const diagnostico = document.getElementById("diagnostico");
    const plan = document.getElementById("planTratamiento");

    let registros = [];

    function cargarNombrePaciente() {
        const expediente = window.expedienteUI.buscar(parseInt(idExpediente));

        if (!expediente || !expediente.getPaciente()) {
            alert("No se encontró el paciente.");
            return;
        }

        nombrePaciente.textContent = expediente.getPaciente().getNombre();
    }

    function cargarDiagnosticos() {
        try {
            registros = window.diagYPlanUI.consultar();
            tabla.innerHTML = "";

            for (let i = 0; i < registros.size(); i++) {
                const registro = registros.get(i);

                if (registro.getExpediente().getIdExpediente() !== parseInt(idExpediente)) {
                    continue;
                }

                const fila = document.createElement("tr");

                const fechaTexto = registro.getFechaRegistro();

                const tdFecha = document.createElement("td");
                tdFecha.textContent = fechaTexto;

                const tdDiagnostico = document.createElement("td");
                tdDiagnostico.textContent = registro.getDiagnostico();

                const tdPlan = document.createElement("td");
                tdPlan.textContent = registro.getPlanTratamiento();

                const tdAcciones = document.createElement("td");
                const botonEliminar = document.createElement("button");

                botonEliminar.type = "button";
                botonEliminar.className = "btn btn-sm btn-primary";
                botonEliminar.textContent = "Eliminar";

                botonEliminar.addEventListener("click", function () {
                    if (!confirm("¿Deseas eliminar este diagnóstico y plan?")) {
                        return;
                    }

                    window.diagYPlanUI.eliminar(registro.getIdDiagnosticoPlan());
                    cargarDiagnosticos();
                });

                tdAcciones.appendChild(botonEliminar);

                fila.appendChild(tdFecha);
                fila.appendChild(tdDiagnostico);
                fila.appendChild(tdPlan);
                fila.appendChild(tdAcciones);

                tabla.appendChild(fila);
            }

        } catch (error) {
            console.error("Error al cargar diagnósticos:", error);
            alert("No fue posible cargar los diagnósticos.");
        }
    }

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const expediente = window.expedienteUI.buscar(parseInt(idExpediente));

            const nuevo = {
                fechaRegistro: fecha.value,
                diagnostico: diagnostico.value.trim(),
                planTratamiento: plan.value.trim(),
                expediente: expediente
            };

            window.diagYPlanUI.registrar(nuevo);

            formulario.reset();
            cargarDiagnosticos();

            alert("Diagnóstico y plan registrados correctamente.");

        } catch (error) {
            console.error("Error al registrar diagnóstico:", error);
            alert("No fue posible registrar el diagnóstico.");
        }
    });

    cargarNombrePaciente();
    cargarDiagnosticos();
}