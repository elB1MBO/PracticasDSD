import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;

public class cliente {
    public static void main(String[] args) {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea el stub para el cliente especificando el nombre del servidor
            Registry mireg = LocateRegistry.getRegistry(args[0]);
            /* iDonacion miDonador = (iDonacion) mireg.lookup("miDonador");
            //El cliente se registra
            System.out.println("Registrando al cliente ...");
            miDonador.registro(args[1]);
            // Añade la donacion del cliente
            System.out.println("Añadiendo la donación del cliente " + miDonador.getCliente());
            int donacion = Integer.parseInt(args[2]);
            miDonador.addDonacion(donacion);
            System.out.println("Donación de " + donacion + " euros añadida.");
            System.out.println("Total donado: " + miDonador.getTotal() + " rublos."); */

            iservidor servidor1 = (iservidor) mireg.lookup("servidor1");
            //El cliente se registra
            System.out.println("Registrando al cliente ...");
            String cliente = args[1];
            servidor1.registrarCliente(cliente);
            //Si intentamos registrar otra vez al mismo, falla
            servidor1.registrarCliente(cliente);
            //Añade la donación del cliente
            System.out.println("Añadiendo la donación del cliente");
            int donacion = Integer.parseInt(args[2]);
            servidor1.addDonacion(donacion, cliente);
            servidor1.addDonacion(200, cliente);
            System.out.println("Donación de " + donacion + " añadida correctamente.");
            //El cliente consulta lo donado
            System.out.println("Total donado: " + servidor1.getTotalDonado(cliente));
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}