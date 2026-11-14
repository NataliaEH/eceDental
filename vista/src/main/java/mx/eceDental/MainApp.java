package mx.eceDental;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;
import javafx.concurrent.Worker;
import mx.eceDental.ui.*;
import netscape.javascript.JSObject;

public class MainApp extends Application{
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        BeanExpediente beanExpediente = new BeanExpediente();
        beanExpediente.init();
        BeanPaciente beanPaciente = new BeanPaciente();
        beanPaciente.init();
        BeanInterrogatorio beanInterrogatorio = new BeanInterrogatorio();
        beanInterrogatorio.init();
        BeanDiagnosticoYPlan beanDiagnosticoYPlan = new BeanDiagnosticoYPlan();
        beanDiagnosticoYPlan.init();
        BeanAbono beanAbono = new BeanAbono();
        beanAbono.init();
        BeanArchivo beanArchivo = new BeanArchivo();
        beanArchivo.init();
        BeanNotaEvolucion beanNotaEvolucion = new BeanNotaEvolucion();
        beanNotaEvolucion.init();
        BeanOdontograma beanOdontograma = new BeanOdontograma();
        beanOdontograma.init();

        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (observable, estadoAnterior, estadoNuevo) -> {
                    if (estadoNuevo == Worker.State.SUCCEEDED) {
                        JSObject window = (JSObject) webView.getEngine().executeScript("window");

                        window.setMember("expedienteUI", beanExpediente);
                        window.setMember("pacienteUI", beanPaciente);
                        window.setMember("diagYPlanUI", beanDiagnosticoYPlan);
                        window.setMember("interrogatorioUI", beanInterrogatorio);
                        window.setMember("abonoUI", beanAbono);
                        window.setMember("archivoUI", beanArchivo);
                        window.setMember("notaEvolucionUI", beanNotaEvolucion);
                        window.setMember("odontogramaUI", beanOdontograma);

                        System.out.println("Página actual: " + webView.getEngine().getLocation());

                        if(webView.getEngine().getLocation().endsWith("buscarExpediente.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarBuscarExpedientes === 'function') {" + "    inicializarBuscarExpedientes();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("dashboardExpediente.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarDashboardExpediente === 'function') {" + "    inicializarDashboardExpediente();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("diagnosticos.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarDiagnosticos === 'function') {" + "    inicializarDiagnosticos();" + "}");
                        }else if (webView.getEngine().getLocation().endsWith("registrarPaciente.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarRegistrarPaciente === 'function') {" + "    inicializarRegistrarPaciente();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("datosGenerales.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarDatosGenerales === 'function') {" + "    inicializarDatosGenerales();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("interrogatorio.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarInterrogatorio === 'function') {" + "    inicializarInterrogatorio();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("panelPrincipal.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarPanelPrincipal === 'function') {" + "    inicializarPanelPrincipal();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("pagos.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarPagos === 'function') {" + "    inicializarPagos();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("archivos.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarArchivos === 'function') {" + "    inicializarArchivos();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("notas.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarNotas === 'function') {" + "    inicializarNotas();" + "}");
                        }else if(webView.getEngine().getLocation().endsWith("odontograma.html")) {
                            webView.getEngine().executeScript("if (typeof inicializarOdontograma === 'function') {" + "    inicializarOdontograma();" + "}");
                        }
                    }
                }
        );


        URL pagina = getClass().getResource("/panelPrincipal.html");

        if (pagina == null)
            throw new IllegalStateException("No se encontró la pagina");

        webView.getEngine().load(pagina.toExternalForm());

        Scene scene = new Scene(webView, 1200, 800);

        stage.setTitle("eceDental");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
