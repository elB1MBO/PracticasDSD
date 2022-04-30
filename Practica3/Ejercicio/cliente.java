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
            iDonacion miDonador = (iDonacion) mireg.lookup("miDonador");
            //El cliente se registra
            System.out.println("Registrando al cliente ...");
            miDonador.registro(args[1]);
            // Añade la donacion del cliente
            System.out.println("Añadiendo la donación del cliente " + miDonador.getCliente());
            int donacion = Integer.parseInt(args[2]);
            miDonador.addDonacion(donacion);
            System.out.println("Donación de " + donacion + " euros añadida.");
            System.out.println("Total donado: " + miDonador.getTotal() + " rublos.");
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}