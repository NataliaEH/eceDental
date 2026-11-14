let ordenFecha = "desc";
let idDiagnosticoAEliminar = 0;
function inicializarDiagnosticos() {
    cargarDiagnosticos();
    guardarDiagnosticos();
    modificarDiagnosticos();
    inicializarOrden();
    inicializarEliminar();
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
        const registros = diagYPlanUI.consultar(idExpediente);
        tabla.innerHTML = "";

        const lista = [];
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            lista.push({
                id: Number(registro.getIdDiagnosticoPlan()),
                fecha: String(registro.getFecha()),
                diagnostico: String(registro.getDiagnostico()),
                procedimiento: String(registro.getProcedimiento())
            });
        }

        lista.sort(function (a, b) {
            let resultado = 0;
            if (a.fecha < b.fecha) {
                resultado = -1;
            } else if (a.fecha > b.fecha) {
                resultado = 1;
            }
            if (ordenFecha === "desc") {
                resultado = -resultado;
            }
            return resultado;
        });

        for (let i = 0; i < lista.length; i++) {
            const registro = lista[i];

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = registro.fecha;
            const tdDiagnostico = document.createElement("td");
            tdDiagnostico.textContent = registro.diagnostico;
            const tdPlan = document.createElement("td");
            tdPlan.textContent = registro.procedimiento;

            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                abrirModalModificar(diagYPlanUI.buscar(registro.id));
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                idDiagnosticoAEliminar = registro.id;
                document.getElementById("confirmar-eliminar").style.display = "flex";
            });

            tdAcciones.appendChild(botonModificar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdDiagnostico);
            fila.appendChild(tdPlan);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
        actualizarIconoOrden();
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

            const registrado = diagYPlanUI.registrar();
            if (registrado){
                mostrarMensaje("Diagnóstico y plan registrados correctamente.","success");
                cargarDiagnosticos();
                formulario.reset();
            }else
                mostrarMensaje("No fue posible registrar el diagnóstico.","danger");
        } catch (error) {
            mostrarMensaje("No fue posible registrar el diagnóstico.","danger");
        }
    });
}
function inicializarOrden() {
    const enlace = document.querySelector(".sort-link");
    if (!enlace) return;

    enlace.addEventListener("click", function (event) {
        event.preventDefault();
        ordenFecha = ordenFecha === "asc" ? "desc" : "asc";
        cargarDiagnosticos();
    });
}
function actualizarIconoOrden() {
    const icono = document.querySelector(".sort-link .sort-icon");
    if (icono) {
        icono.textContent = ordenFecha === "asc" ? "▲" : "▼";
    }
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

            const registro = diagYPlanUI.getDiagnosticoYPlan();
            registro.setIdDiagnosticoPlan(id);
            registro.setDiagnostico(diagnostico);
            registro.setProcedimiento(plan);
            const modificado = diagYPlanUI.modificar();
            if(modificado){
                cerrarModalModificar();
                mostrarMensaje("Diagnóstico y plan modificados correctamente.", "success");
                cargarDiagnosticos();
            }else
                mostrarMensaje("No fue posible modificar el diagnóstico.", "danger");
        } catch (error) {
            mostrarMensaje("No fue posible modificar el diagnóstico.", "danger");
        }
    });
}

//ELIMINACION
function inicializarEliminar(){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        try{
            const eliminado = window.diagYPlanUI.eliminar(idDiagnosticoAEliminar);
            if(eliminado){
                document.getElementById("confirmar-eliminar").style.display = "none";
                cargarDiagnosticos();
                mostrarMensaje("Diagnóstico y plan eliminados","success");
            }else
                mostrarMensaje("Error en la eliminación","danger");
        }catch (error){
            mostrarMensaje(error);
        }
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}