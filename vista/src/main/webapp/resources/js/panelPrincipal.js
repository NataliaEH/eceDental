function inicializarPanelPrincipal() {
    const totalElement = document.getElementById("totalExpedientes");
    const viejosElement = document.getElementById("expedientesViejos");
    const recPendientes = document.getElementById("recordatoriosPendientes");

    if (!totalElement || !viejosElement || !recPendientes) return;

    const expedientes = expedienteUI.consultar();
    const recordatoriosUrgentes = recordatorioUI.buscarUrgentes();
    const hoy = new Date();

    let total = expedientes.size();
    let viejos = 0;

    for (let i = 0; i < expedientes.size(); i++) {
        const expediente = expedientes.get(i);
        const fechaCreacion = expediente.getFechaCreacion().toString();

        if (!fechaCreacion) continue;

        const fecha = new Date(fechaCreacion + "T00:00:00");
        const fechaLimite = new Date(fecha);
        fechaLimite.setFullYear(fechaLimite.getFullYear() + 5);

        if (hoy >= fechaLimite) {
            viejos++;
        }
    }

    const tabla = document.getElementById("tablaPagos");
    tabla.innerHTML = "";
    for(let i = 0; i < recordatoriosUrgentes.size(); i++){
        const registro = registros.get(i);

        const fila = document.createElement("tr");
        const tdFecha = document.createElement("td");
        tdFecha.textContent = registro.getFecha();
        const tdUrgencia = document.createElement("td");
        tdUrgencia.textContent = registro.getUrgencia();
        const tdContenido = document.createElement("td");
        tdContenido.textContent = registro.getContenido();

        fila.appendChild(tdFecha);
        fila.appendChild(tdUrgencia);
        fila.appendChild(tdContenido);
        tabla.appendChild(fila);
    }

    totalElement.textContent = total;
    viejosElement.textContent = viejos;
    recPendientes.textContent = recordatorioUI.consultar().size();
}
