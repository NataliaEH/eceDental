let ordenFecha = "desc";
let idRegistroAEliminar = 0;
function inicializarOdontograma() {
    dibujarSimbolos();
    cargarOdontograma();
    guardarOdontograma();
    modificarOdontograma();
    inicializarCaras();
    inicializarCarasModal();
    inicializarOrden();
    inicializarEliminar();
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

        const lista = [];
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getOdontograma().getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            const carasJava = registro.getCara();
            const nombresCaras = [];
            for (let j = 0; j < carasJava.size(); j++) {
                nombresCaras.push(String(carasJava.get(j)));
            }

            lista.push({
                id: Number(registro.getIdRegistro()),
                fecha: String(registro.getFecha()),
                pieza: String(registro.getPiezaDental()),
                caras: nombresCaras.join(", "),
                carasLista: nombresCaras,
                color: String(registro.getColor()),
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
            const reg = lista[i];

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = reg.fecha;
            const tdPieza = document.createElement("td");
            tdPieza.textContent = reg.pieza;
            const tdCara = document.createElement("td");
            tdCara.textContent = reg.caras;
            const tdColor = document.createElement("td");
            tdColor.textContent = reg.color;
            const tdDiagnostico = document.createElement("td");
            tdDiagnostico.textContent = reg.diagnostico;
            const tdProcedimiento = document.createElement("td");
            tdProcedimiento.textContent = reg.procedimiento;

            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                abrirModalModificar(odontogramaUI.buscarRegistro(reg.id));
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                idRegistroAEliminar = reg.id;
                document.getElementById("confirmar-eliminar").style.display = "flex";
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
        pintarOdontograma(lista);
        actualizarIconoOrden();
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
            registro.getCara().clear();
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

            const registro = odontogramaUI.getRegistro();
            registro.setIdRegistro(id);
            registro.setPiezaDental(pieza);
            registro.getCara().clear();
            cara.forEach(function (c) {
                odontogramaUI.agregarCara(c);
            });
            registro.setColor(color);
            registro.setDiagnostico(diagnostico);
            registro.setProcedimiento(procedimiento);
            const modificado = odontogramaUI.modificarRegistro();
            if(modificado){
                cerrarModalModificar();
                mostrarMensaje("Registro modificado correctamente.", "success");
                cargarOdontograma();
            }else
                mostrarMensaje("No fue posible modificar el registro.", "danger");
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

function inicializarOrden() {
    const enlace = document.querySelector(".sort-link");
    if (!enlace) return;

    enlace.addEventListener("click", function (event) {
        event.preventDefault();
        ordenFecha = ordenFecha === "asc" ? "desc" : "asc";
        cargarOdontograma();
    });
}

function actualizarIconoOrden() {
    const icono = document.querySelector(".sort-link .sort-icon");
    if (icono) {
        icono.textContent = ordenFecha === "asc" ? "▲" : "▼";
    }
}
//ELIMINACION
function inicializarEliminar(){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.odontogramaUI.eliminarRegistro(idRegistroAEliminar);
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarOdontograma();
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}

//EL SIGUIENTE CODIGO ES PARA LA VISUALIZACION DEL ODONTOGRAMA, LOS SIMBOLOS, ETC.
const COLORES_ODONTOGRAMA = {
    "Rojo": "#c0392b",
    "Azul": "#2c6fa8",
    "Amarillo": "#e0b528",
    "Negro": "#1c1c1c",
    "Verde": "#3d8b4f"
};
const RELLENO_VACIO = "#fdfcfa";
function punto(cx, cy, r, grados) {
    const rad = grados * Math.PI / 180;
    return (cx + r * Math.cos(rad)).toFixed(2) + " " + (cy + r * Math.sin(rad)).toFixed(2);
}
function sectorAnillo(cx, cy, rInterno, rExterno, a1, a2) {
    return "M " + punto(cx, cy, rExterno, a1) +
        " A " + rExterno + " " + rExterno + " 0 0 1 " + punto(cx, cy, rExterno, a2) +
        " L " + punto(cx, cy, rInterno, a2) +
        " A " + rInterno + " " + rInterno + " 0 0 0 " + punto(cx, cy, rInterno, a1) + " Z";
}
function crearSimboloDiente(pieza) {
    const NS = "http://www.w3.org/2000/svg";
    const svg = document.createElementNS(NS, "svg");
    svg.setAttribute("viewBox", "0 0 40 40");
    svg.setAttribute("width", "34");
    svg.setAttribute("height", "34");
    svg.dataset.pieza = pieza;

    const partes = {
        top:    sectorAnillo(20, 20, 8, 18, -135, -45),
        right:  sectorAnillo(20, 20, 8, 18, -45, 45),
        bottom: sectorAnillo(20, 20, 8, 18, 45, 135),
        left:   sectorAnillo(20, 20, 8, 18, 135, 225),
        center: "M 28 20 A 8 8 0 1 1 12 20 A 8 8 0 1 1 28 20 Z"
    };

    Object.keys(partes).forEach(function (posicion) {
        const path = document.createElementNS(NS, "path");
        path.setAttribute("d", partes[posicion]);
        path.setAttribute("fill", RELLENO_VACIO);
        path.setAttribute("stroke", "#333");
        path.setAttribute("stroke-width", "1.2");
        path.dataset.pos = posicion;
        svg.appendChild(path);
    });
    return svg;
}
function dibujarSimbolos() {
    const spans = document.querySelectorAll("#contenedorOdontograma .odonto-fila span");
    spans.forEach(function (span) {
        const pieza = span.textContent.trim();
        if (span.querySelector("svg")) return;   // evita duplicar
        span.appendChild(crearSimboloDiente(pieza));
    });
}
function posicionDeCara(cara, pieza) {
    const cuadrante = Math.floor(Number(pieza) / 10);
    const superior = cuadrante === 1 || cuadrante === 2;
    const mesialADerecha = cuadrante === 1 || cuadrante === 4;

    if (cara === "Oclusal/Incisal") return "center";
    if (cara === "Mesial") return mesialADerecha ? "right" : "left";
    if (cara === "Distal") return mesialADerecha ? "left" : "right";
    if (cara === "Bucal") return superior ? "top" : "bottom";
    if (cara === "Lingual") return superior ? "bottom" : "top";
    return "";
}

// toma la lista de registros para sacar colores y caras de piezas
function pintarOdontograma(lista) {
    document.querySelectorAll("#contenedorOdontograma svg path").forEach(function (p) {
        p.setAttribute("fill", RELLENO_VACIO);
    });
    const ordenados = lista.slice().sort(function (a, b) {
        if (a.fecha !== b.fecha) return a.fecha < b.fecha ? -1 : 1;
        return a.id - b.id;
    });
    ordenados.forEach(function (reg) {
        const svg = document.querySelector('#contenedorOdontograma svg[data-pieza="' + reg.pieza + '"]');
        if (!svg) return;
        const color = COLORES_ODONTOGRAMA[reg.color];
        if (!color) return;

        reg.carasLista.forEach(function (cara) {
            const pos = posicionDeCara(cara, reg.pieza);
            const path = svg.querySelector('path[data-pos="' + pos + '"]');
            if (path) path.setAttribute("fill", color);
        });
    });
}