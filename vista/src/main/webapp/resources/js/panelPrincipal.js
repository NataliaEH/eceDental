function inicializarPanelPrincipal() {
    const totalElement = document.getElementById("totalExpedientes");
    const viejosElement = document.getElementById("expedientesViejos");

    if (!totalElement || !viejosElement) return;

    const expedientes = expedienteUI.consultar();
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

    totalElement.textContent = total;
    viejosElement.textContent = viejos;
}
