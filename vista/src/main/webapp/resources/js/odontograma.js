function inicializarOdontograma() {
    cargarOdontograma();
    guardarOdontograma();
    modificarOdontograma();
    inicializarCaras();
    inicializarCarasModal();
}

function cargarOdontograma() {
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
        const tabla = document.getElementById("tablaOdontograma");
        let registros = odontogramaUI.consultarReg(idExpediente);
        tabla.innerHTML = "";
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getOdontograma().getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            const fila = document.createElement("tr");
            const fechaTexto = registro.getFecha();
            const tdFecha = document.createElement("td");
            tdFecha.textContent = fechaTexto;
            const tdPieza = document.createElement("td");
            tdPieza.textContent = registro.getPiezaDental();
            const tdCara = document.createElement("td");
            const caras = registro.getCara();
            const nombresCaras = [];
            for (let i = 0; i < caras.size(); i++) {
                nombresCaras.push(caras.get(i));
            }
            tdCara.textContent = nombresCaras.join(", ");
            const tdColor = document.createElement("td");
            tdColor.textContent = registro.getColor();
            const tdDiagnostico = document.createElement("td");
            tdDiagnostico.textContent = registro.getDiagnostico();
            const tdProcedimiento = document.createElement("td");
            tdProcedimiento.textContent = registro.getProcedimiento();
            //ACCIONES (MODIFICAR Y ELIMINAR)
            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                const id = registro.getIdRegistro();
                const registroModificar = odontogramaUI.buscarRegistro(id);
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
            fila.appendChild(tdPieza);
            fila.appendChild(tdCara);
            fila.appendChild(tdColor);
            fila.appendChild(tdDiagnostico);
            fila.appendChild(tdProcedimiento);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
        mostrarMensaje(error);
    }
}

function guardarOdontograma() {
    const formulario = document.getElementById("formRegistroOdontograma");
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
            const odontograma = odontogramaUI.getOdontograma();
            const registro = odontogramaUI.getRegistro();
            const pieza = document.getElementById("piezaDental").value.trim();
            const caras = obtenerCaras();
            const color = document.getElementById("color").value.trim();
            const diagnostico = document.getElementById("diagnostico").value.trim();
            const procedimiento = document.getElementById("procedimiento").value.trim();

            registro.setPiezaDental(pieza);
            caras.forEach(cara => {
                odontogramaUI.agregarCara(cara);
            });
            registro.setColor(color);
            registro.setDiagnostico(diagnostico);
            registro.setProcedimiento(procedimiento);
            odontograma.setExpediente(expediente);
            const registrado = odontogramaUI.registrarRegistro();
            if(registrado){
                mostrarMensaje("Registro dado de alta correctamente.","success");
                cargarOdontograma();
                formulario.reset();
            }else{
                mostrarMensaje("No fue posible hacer el alta del registro.","danger");
            }
        } catch (error) {
            mostrarMensaje("No fue posible hacer el alta del registro.","danger");
            mostrarMensaje(error, "danger");
        }
    });
}
//ESTAS SON PARA LA MODIFICACION
function abrirModalModificar(registro) {
    try{
        document.getElementById("modIdRegistro").value = registro.getIdRegistro();
        document.getElementById("modPiezaDental").value = registro.getPiezaDental();
        document.getElementById("modColor").value = registro.getColor();
        document.getElementById("modDiagnostico").value = registro.getDiagnostico();
        document.getElementById("modProcedimiento").value = registro.getProcedimiento();

        const contenedor = document.getElementById("modListaCaras");
        contenedor.innerHTML = "";
        const caras = registro.getCara();
        for (let i = 0; i < caras.size(); i++) {
            agregarCaraModal(caras.get(i));
        }
        if (caras.size() === 0) {
            agregarCaraModal();
        }

        document.getElementById("modalModificar").style.display = "flex";
    }catch(error){
        mostrarMensaje(error, "danger");
    }
}

function cerrarModalModificar() {
    document.getElementById("modalModificar").style.display = "none";
}

function modificarOdontograma() {
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
            const id = parseInt(document.getElementById("modIdRegistro").value);
            const pieza = document.getElementById("modPiezaDental").value.trim();
            const cara = obtenerCarasModal();
            const color = document.getElementById("modColor").value.trim();
            const diagnostico = document.getElementById("modDiagnostico").value.trim();
            const procedimiento = document.getElementById("modProcedimiento").value.trim();

            if (cara.length === 0) {
                mostrarMensaje("Debes seleccionar al menos una cara.", "warn");
                return;
            }

            const registro = odontogramaUI.buscarRegistro(id);
            registro.setPieza(pieza);
            caras.forEach(cara => {
                odontogramaUI.agregarCara(cara);
            });
            registro.setColor(color);
            registro.setDiagnostico(diagnostico);
            registro.setProcedimiento(procedimiento);
            odontogramaUI.modificarRegistro(registro.getIdRegistro);

            cerrarModalModificar();
            mostrarMensaje("Registro modificado correctamente.", "success");
            cargarOdontograma();
        } catch (error) {
            mostrarMensaje("No fue posible modificar el registro.", "danger");
        }
    });
}

//METODOS PARA CARA
function agregarCara() {
    const plantilla = document.getElementById("plantillaCara");
    const contenedor = document.getElementById("listaCaras");

    const nuevaCara = plantilla.content.cloneNode(true);
    contenedor.appendChild(nuevaCara);
}

function inicializarCaras() {
    const contenedor = document.getElementById("listaCaras");
    const botonAgregar = document.getElementById("agregarCara");
    if (contenedor.querySelectorAll(".cara-select").length === 0) {
        agregarCara();
    }
    botonAgregar.addEventListener("click", agregarCara);
    contenedor.addEventListener("click", function (event) {
        if (event.target.matches('[data-accion="quitar-cara"]')) {
            const filas = contenedor.querySelectorAll(".input-row");
            if (filas.length > 1) {
                event.target.closest(".input-row").remove();
            }
        }
    });
}
function obtenerCaras() {
    const selects = document.querySelectorAll("#listaCaras .cara-select");
    const caras = [];
    selects.forEach(function (select) {
        const cara = select.value.trim();
        if (cara) {
            caras.push(cara);
        }
    });
    if (new Set(caras).size !== caras.length) {
        mostrarMensaje("No puedes seleccionar la misma cara más de una vez.", "warn");
    }
    return caras;
}
//estos tambien pero para el modal de MODIFICACION
function agregarCaraModal(valor = "") {
    const plantilla = document.getElementById("modPlantillaCara");
    const contenedor = document.getElementById("modListaCaras");
    const nuevaCara = plantilla.content.cloneNode(true);
    const select = nuevaCara.querySelector(".cara-select");
    select.value = valor;
    contenedor.appendChild(nuevaCara);
}

function inicializarCarasModal() {
    const contenedor = document.getElementById("modListaCaras");
    const botonAgregar = document.getElementById("modAgregarCara");
    botonAgregar.addEventListener("click", function () {
        agregarCaraModal();
    });
    contenedor.addEventListener("click", function (event) {
        if (event.target.matches('[data-accion="quitar-cara"]')) {
            const filas = contenedor.querySelectorAll(".input-row");
            if (filas.length > 1) {
                event.target.closest(".input-row").remove();
            }
        }
    });
}

function obtenerCarasModal() {
    const selects = document.querySelectorAll("#modListaCaras .cara-select");
    const caras = [];
    selects.forEach(function (select) {
        const cara = select.value.trim();
        if (cara) {
            caras.push(cara);
        }
    });
    if (new Set(caras).size !== caras.length) {
        mostrarMensaje("No puedes seleccionar la misma cara más de una vez.", "warn");
    }
    return caras;
}

//ELIMINACION
function inicializarEliminar(registro){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.odontogramaUI.eliminarRegistro(registro.getIdRegistro());
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarOdontograma();
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}