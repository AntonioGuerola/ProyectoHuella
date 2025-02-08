package org.example.views;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.fxml.Initializable;
import org.example.App;
import org.example.model.entities.Huella;
import org.example.model.entities.Usuario;
import org.example.model.service.HuellaService;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class mainController extends Controller implements Initializable {

    private Usuario userLoggedIn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLoggedIn = userSingleton.getInstance().getCurrentUser();
    }

    public void goRegistrarHuella() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLA, null);
    }

    public void goRegistrarHabito() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHABITOS, null);
    }

    public void goConsultarHuellas() throws IOException {
        App.currentController.changeScene(Scenes.CONSULTARHUELLAS, null);
    }

    public void goConsultarHabitos() throws IOException {
        App.currentController.changeScene(Scenes.CONSULTARHABITOS, null);
    }

    public void goRecomendaciones() throws IOException {
        App.currentController.changeScene(Scenes.RECOMENDACIONES, null);
    }

    public void goComparacionImpacto() throws IOException {
        App.currentController.changeScene(Scenes.COMPARACIONIMPACTO, null);
    }

    public void generarPDF() {
        // Obtener los datos del usuario actual
        Usuario usuario = userSingleton.getInstance().getCurrentUser();
        String nombre = usuario.getNombre();
        String email = usuario.getEmail();

        // Crear la ruta para el archivo PDF (asegurándose de que los directorios existan)
        String path = System.getProperty("user.dir") + "/informacion_usuario.pdf";  // Usa el directorio actual
        File archivoPdf = new File(path);

        // Verificar y crear los directorios si no existen
        File parentDir = archivoPdf.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();  // Crea los directorios si no existen
        }

        // Crear el documento PDF
        try {
            PdfDocument pdfDocument = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(new FileOutputStream(archivoPdf)));
            Document document = new Document(pdfDocument);

            // Título principal
            document.add(new Paragraph("Información del Usuario"));
            document.add(new Paragraph("=========================================="));

            // Información del usuario
            document.add(new Paragraph("Nombre: " + nombre));
            document.add(new Paragraph("Email: " + email));
            document.add(new Paragraph("=========================================="));

            // Agregar las huellas si existen
            HuellaService huellaService = HuellaService.buildHuellaService();
            List<Huella> huellas = huellaService.getHuellasByUserId(usuario.getId()); // Obtener las huellas del usuario

            if (!huellas.isEmpty()) {
                document.add(new Paragraph("Huellas del Usuario:"));
                document.add(new Paragraph("=========================================="));

                // Recorrer las huellas y agregarlas al PDF
                for (Huella huella : huellas) {
                    document.add(new Paragraph("ID de Huella: " + huella.getId()));
                    document.add(new Paragraph("Actividad: " + huella.getActividad()));
                    document.add(new Paragraph("Valor: " + huella.getValor()));
                    document.add(new Paragraph("Fecha: " + huella.getFecha()));
                    document.add(new Paragraph("__________________________________________"));
                }
            } else {
                document.add(new Paragraph("No se encontraron huellas para este usuario."));
            }

            // Cerrar el documento
            document.close();

            // Mostrar mensaje de éxito
            JavaFXUtils.showInfoAlert("PDF generado", "El PDF se ha generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            JavaFXUtils.showErrorAlert("Error", "Hubo un error al generar el PDF.");
        }
    }

    public void goAjustes() throws IOException {
        App.currentController.changeScene(Scenes.AJUSTES, null);
    }

    public void goBack() throws IOException {
        userSingleton.closeSession();
        App.currentController.changeScene(Scenes.INICIO, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
}
