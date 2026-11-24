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

const FILAS_ODONTOGRAMA = [
    [18, 17, 16, 15, 14, 13, 12, 11, 21, 22, 23, 24, 25, 26, 27, 28],
    [48, 47, 46, 45, 44, 43, 42, 41, 31, 32, 33, 34, 35, 36, 37, 38]
];

function generarSvgOdontograma(lista) {
    const TAM = 40, SEP = 6;
    const ancho = 16 * (TAM + SEP) + 10;
    const alto = 2 * (TAM + 30) + 20;

    // mismo orden que pintarOdontograma: el registro más reciente pinta al final
    const ordenados = lista.slice().sort(function (a, b) {
        if (a.fecha !== b.fecha) return a.fecha < b.fecha ? -1 : 1;
        return a.id - b.id;
    });

    let xml = '<svg xmlns="http://www.w3.org/2000/svg" width="' + ancho + '" height="' + alto +
        '" viewBox="0 0 ' + ancho + ' ' + alto + '">' +
        '<rect width="100%" height="100%" fill="#ffffff"/>';

    FILAS_ODONTOGRAMA.forEach(function (fila, f) {
        fila.forEach(function (pieza, i) {
            const x = 5 + i * (TAM + SEP) + (i >= 8 ? 14 : 0);   // hueco central
            const y = 10 + f * (TAM + 40) + 16;

            // colores por posición de cada diente
            const relleno = { top: RELLENO_VACIO, right: RELLENO_VACIO, bottom: RELLENO_VACIO,
                left: RELLENO_VACIO, center: RELLENO_VACIO };
            ordenados.forEach(function (reg) {
                if (Number(reg.pieza) !== pieza) return;
                const color = COLORES_ODONTOGRAMA[reg.color];
                if (!color) return;
                reg.carasLista.forEach(function (cara) {
                    const pos = posicionDeCara(cara, pieza);
                    if (pos) relleno[pos] = color;
                });
            });

            const partes = {
                top:    sectorAnillo(20, 20, 8, 18, -135, -45),
                right:  sectorAnillo(20, 20, 8, 18, -45, 45),
                bottom: sectorAnillo(20, 20, 8, 18, 45, 135),
                left:   sectorAnillo(20, 20, 8, 18, 135, 225),
                center: "M 28 20 A 8 8 0 1 1 12 20 A 8 8 0 1 1 28 20 Z"
            };

            xml += '<text x="' + (x + 20) + '" y="' + (y - 4) + '" font-size="11" ' +
                'font-family="sans-serif" text-anchor="middle" fill="#333">' + pieza + '</text>';
            xml += '<g transform="translate(' + x + ',' + y + ')">';
            Object.keys(partes).forEach(function (pos) {
                xml += '<path d="' + partes[pos] + '" fill="' + relleno[pos] +
                    '" stroke="#333" stroke-width="1.2"/>';
            });
            xml += '</g>';
        });
    });

    return xml + '</svg>';
}
function odontogramaAPng(lista) {
    return new Promise(function (resolve) {
        const xml = generarSvgOdontograma(lista);
        const img = new Image();
        img.onload = function () {
            const canvas = document.createElement("canvas");
            canvas.width = img.width * 2;     // más resolución para el PDF
            canvas.height = img.height * 2;
            const ctx = canvas.getContext("2d");
            ctx.fillStyle = "#ffffff";
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
            try {
                resolve(canvas.toDataURL("image/png"));
            } catch (e) {
                console.error(e);
                resolve("");
            }
        };
        img.onerror = function () { resolve(""); };
        img.src = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(xml);
    });
}
function obtenerListaOdontograma(idExpediente) {
    const registros = window.odontogramaUI.consultarReg(idExpediente);
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
    return lista;
}