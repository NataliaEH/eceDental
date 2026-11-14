function inicializarDiagnosticos() {
    cargarDiagnosticos();
    guardarDiagnosticos();
    modificarDiagnosticos();
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

            //ACCIONES (MODIFICAR Y ELIMINAR)
            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                const id = registro.getIdDiagnosticoPlan();
                const registroModificar = diagYPlanUI.buscar(id);
                abrirModalModificar(registroModificar);
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                document.getElementById("confirmar-eliminar").style.display = "flex";
                inicializarEliminar(registro);
            });

            tdAcciones.appendChild(botonModificar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdDiagnostico);
            fila.appendChild(tdPlan);
            fila.appendChild(tdAcciones);

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
//ESTAS SON PARA LA MODIFICACION
function abrirModalModificar(registro) {
    document.getElementById("modIdDiagnostico").value = registro.getIdDiagnosticoPlan();
    document.getElementById("modDiagnostico").value = registro.getDiagnostico();
    document.getElementById("modPlanTratamiento").value = registro.getProcedimiento();
    document.getElementById("modalModificar").style.display = "flex";
}

function cerrarModalModificar() {
    document.getElementById("modalModificar").style.display = "none";
}

function modificarDiagnosticos() {
    const modal = document.getElementById("modalModificar");
    const formulario = document.getElementById("formModificar");

    document.getElementById("btnCancelarModificar").addEventListener("click", cerrarModalModificar);

    modal.addEventListener("click", function (event) {
        if (event.target === modal) {
            cerrarModalModificar();
        }
    });

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const id = parseInt(document.getElementById("modIdDiagnostico").value);
            const diagnostico = document.getElementById("modDiagnostico").value.trim();
            const plan = document.getElementById("modPlanTratamiento").value.trim();

            const registro = diagYPlanUI.buscar(id);
            registro.setDiagnostico(diagnostico);
            registro.setProcedimiento(plan);
            diagYPlanUI.modificar(registro);

            cerrarModalModificar();
            mostrarMensaje("Diagnóstico y plan modificados correctamente.", "success");
            cargarDiagnosticos();
        } catch (error) {
            mostrarMensaje("No fue posible modificar el diagnóstico.", "danger");
        }
    });
}

//ELIMINACION
function inicializarEliminar(registro){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.diagYPlanUI.eliminarRegistro(registro.getIdDiagnosticoPlan());
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarDiagnosticos();
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}