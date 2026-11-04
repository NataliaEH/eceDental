function inicializarBuscarExpedientes() {
    cargarExpedientes();
    inicializarBuscador();
}

function cargarExpedientes() {
    const tabla = document.getElementById("tablaExpedientes");
    try {
        const expedientesJava = window.expedienteUI.consultar();
        tabla.innerHTML = "";
        if (expedientesJava.size() === 0) {
            tabla.innerHTML = `
                <tr>
                    <td colspan="4">No hay expedientes registrados.</td>
                </tr>
            `;
            return;
        }

        for (let i = 0; i < expedientesJava.size(); i++) {
            const expediente = expedientesJava.get(i);
            const paciente = expediente.getPaciente();
            const nombrePaciente = paciente.getNombre();
            const fechaUltimaConsulta = "Sin consultas";
            const fechaCreacion = expediente.getFechaCreacion();
            const fila = document.createElement("tr");
            const idExpediente = expediente.getIdExpediente();

            fila.innerHTML = `
                <td>${nombrePaciente}</td>
                <td>${fechaUltimaConsulta}</td>
                <td>${fechaCreacion}</td>
                <td>
                    <a href="dashboardExpediente.html"
                        onclick="sessionStorage.setItem('expedienteActualId', '${idExpediente}')"
                       class="btn btn-sm btn-secondary">
                        Ver
                    </a>
                </td>
            `;
            tabla.appendChild(fila);
        }
    } catch (error) {
        //
    }
}

function inicializarBuscador() {
    const formulario = document.getElementById("formBuscarExpediente");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();

        const nombre = document.getElementById("nombrePaciente").value.trim();
        const tabla = document.getElementById("tablaExpedientes");

        try {
            const expedientesJava = expedienteUI.consultar();
            tabla.innerHTML = "";
            for (let i = 0; i < expedientesJava.size(); i++) {
                const expediente = expedientesJava.get(i);
                const paciente = expediente.getPaciente();
                if (paciente.getNombre().toLowerCase().includes(nombre.toLowerCase())) {
                    const idExpediente = expediente.getIdExpediente();
                    const fila = document.createElement("tr");
                    fila.innerHTML = `
                        <td>${paciente.getNombre()}</td>
                        <td>Sin consultas</td>
                        <td>${expediente.getFechaCreacion()}</td>
                        <td>
                            <a href="dashboardExpediente.html?id=${idExpediente}"
                               onclick="sessionStorage.setItem('expedienteActualId', '${idExpediente}')"
                               class="btn btn-sm btn-secondary">
                                Ver
                            </a>
                        </td>
                    `;
                    tabla.appendChild(fila);
                }
            }

            if (tabla.innerHTML === "") {
                tabla.innerHTML = `
                    <tr>
                        <td colspan="4">No se encontraron expedientes.</td>
                    </tr>
                `;
            }
        } catch (error) {
            //
        }
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        cargarExpedientes()
        document.getElementById("nombrePaciente").value = "";
        document.getElementById("fechaUltimaConsulta").value = "";
    });
}

