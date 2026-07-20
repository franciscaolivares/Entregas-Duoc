package data;
import model.ExcursionCultural;
import model.PaseoLacustre;
import model.RutaGastronomica;
import model.ServicioTuristico;
import java.util.ArrayList;
import java.util.List;

public class GestorServicios {
    private List<ServicioTuristico> servicios;

    public GestorServicios() {
        servicios = new ArrayList<>();
        }

        public List<ServicioTuristico> crearServicios() {

        servicios.clear();

        servicios.add(
                new RutaGastronomica(
                        "Sabores del Sur",
                        3,
                        4)
        );

        servicios.add(
                new RutaGastronomica(
                        "Mercado Local",
                        2,
                        3)
        );

        servicios.add(
                new PaseoLacustre(
                        "Ruta del Lago",
                        4,
                        "Lancha turística")
                );

        servicios.add(
                new PaseoLacustre(
                        "Navegación Llanquihue",
                        2,
                        "Catamarán")
                );

        servicios.add(
                new ExcursionCultural(
                        "Cultura Llanquihue",
                        3,
                        "Museo local")
                );

        servicios.add(
                new ExcursionCultural(
                        "Historia del Sur",
                        4,
                        "Casa patrimonial")
                );

        return servicios;
    }

    public void mostrarServicios() {

        List<ServicioTuristico> listaServicios = crearServicios();

        System.out.println("======================================");
        System.out.println("     SERVICIOS TURÍSTICOS");
        System.out.println("======================================");

        for (ServicioTuristico servicio : listaServicios) {
            servicio.mostrarInformacion();
        }
    }
}
