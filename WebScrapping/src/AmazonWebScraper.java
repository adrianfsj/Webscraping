import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class AmazonWebScraper {
    public static void main(String[] args) {
        try {
            
            Document document = Jsoup.connect("https://www.amazon.com/s?k=juegos").get();

            // Obtener los elementos de los productos en la página
            Elements productElements = document.select("div.s-result-item");

            // Crear un FileWriter para escribir en el archivo CSV
            FileWriter writer = new FileWriter("productos10_amazon.csv");

            // Escribir el encabezado del archivo CSV
            writer.write("Título,Precio (EUR)\n");

            // Recorrer los elementos de los productos y obtener los datos
            for (Element productElement : productElements) {
                // Obtener el título del producto
                Element titleElement = productElement.selectFirst("span.a-text-normal");
                String title = titleElement != null ? titleElement.text() : "";

                // Obtener el precio del producto
                Element priceElement = productElement.selectFirst("span.a-offscreen");
                String price = priceElement != null ? priceElement.text() : "";

                // Omitir las líneas nulas
                if (!title.isEmpty() && !price.isEmpty()) {
                    // Escapar las comas en el título para evitar conflictos en el archivo CSV
                    title = title.replace(",", "\\,");

                    // Escribir los datos en el archivo CSV
                    writer.write(title + "," + price + "\n");
                }
            }

            // Cerrar el FileWriter
            writer.close();

            System.out.println("Se han guardado los datos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
