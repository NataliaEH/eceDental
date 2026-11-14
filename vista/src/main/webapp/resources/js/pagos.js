function inicializarPagos() {
    cargarPagos();
    guardarPagos();
    modificarPagos();
}

function cargarPagos() {
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
        const tabla = document.getElementById("tablaPagos");
        let registros = abonoUI.consultar(idExpediente);
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
            const tdMonto = document.createElement("td");
            tdMonto.textContent = registro.getMonto();
            const tdConcepto = document.createElement("td");
            tdConcepto.textContent = registro.getConcepto();

            //ACCIONES (MODIFICAR Y ELIMINAR)
            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                const id = registro.getIdAbono();
                const registroModificar = abonoUI.buscar(id);
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
            fila.appendChild(tdMonto);
            fila.appendChild(tdConcepto);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
        mostrarMensaje(error, "danger")
    }
}

function guardarPagos() {
    const formulario = document.getElementById("formPago");
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
            const pago = abonoUI.getAbono();
            const monto = document.getElementById("monto").value.trim();
            const concepto = document.getElementById("concepto").value.trim();

            pago.setMonto(monto);
            pago.setConcepto(concepto);
            pago.setExpediente(expediente);

            abonoUI.registrar();

            mostrarMensaje("Diagnóstico y plan registrados correctamente.","success");
            cargarPagos();
            formulario.reset();
        } catch (error) {
            mostrarMensaje("No fue posible registrar el pago.","danger");
            mostrarMensaje(error, "danger")
        }
    });
}
//ESTAS SON PARA LA MODIFICACION
function abrirModalModificar(registro) {
    try{
        document.getElementById("modIdPago").value = registro.getIdAbono();
        document.getElementById("modMonto").value = registro.getMonto();
        document.getElementById("modConcepto").value = registro.getConcepto();
        document.getElementById("modalModificar").style.display = "flex";
    }catch (error){
        mostrarMensaje(error);
    }
}

function cerrarModalModificar() {
    document.getElementById("modalModificar").style.display = "none";
}

function modificarPagos() {
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
            const id = parseInt(document.getElementById("modIdPago").value);
            const monto = document.getElementById("modMonto").value.trim();
            const concepto = document.getElementById("modConcepto").value.trim();

            const registro = abonoUI.buscar(id);
            registro.setMonto(monto);
            registro.setConcepto(concepto);
            abonoUI.modificar(registro);

            cerrarModalModificar();
            mostrarMensaje("Pago modificado correctamente.", "success");
            cargarPagos();
        } catch (error) {
            mostrarMensaje("No fue posible modificar el pago.", "danger");
        }
    });
}

//ELIMINACION
function inicializarEliminar(registro){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.abonoUI.eliminar(registro.getIdAbono());
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarPagos();
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}