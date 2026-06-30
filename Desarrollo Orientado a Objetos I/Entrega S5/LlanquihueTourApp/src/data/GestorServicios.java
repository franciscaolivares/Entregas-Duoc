package data;

import model.ExcursionCultural;
import model.PaseoLacustre;
import model.RutaGastronomica;

public class GestorServicios {

    public void mostrarServicios() {
        RutaGastronomica ruta1 = new RutaGastronomica("Sabores del Sur", 3, 4);
        RutaGastronomica ruta2 = new RutaGastronomica("Mercado Local", 2, 3);

        PaseoLacustre paseo1 = new PaseoLacustre("Ruta del Lago", 4, "Lancha turística");
        PaseoLacustre paseo2 = new PaseoLacustre("Navegación Llanquihue", 2, "Catamarán");

        ExcursionCultural excursion1 = new ExcursionCultural("Cultura Llanquihue", 3, "Museo local");
        ExcursionCultural excursion2 = new ExcursionCultural("Historia del Sur", 4, "Casa patrimonial");

        System.out.println("SERVICIOS TURÍSTICOS DISPONIBLES");
        System.out.println(ruta1);
        System.out.println(ruta2);
        System.out.println(paseo1);
        System.out.println(paseo2);
        System.out.println(excursion1);
        System.out.println(excursion2);
    }
}
