import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

//import contador.contador;
/**
 * Este es nuestro servidor master, el cual se encargará de redirigir
 * al cliente al sub-servidor correspondiente, tanto para registrarse
 * como para otra operación.
 */
public class servidor {
    public static void main(String[] args) {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea una instancia de donacion
            // System.setProperty("java.rmi.server.hostname","192.168.1.107");
            Registry reg = LocateRegistry.createRegistry(1099);
            /* Donacion miDonador = new Donacion();
            Naming.rebind("miDonador", miDonador); */
            servidor1 server1 = new servidor1();
            servidor2 server2 = new servidor2();
            Naming.rebind("servidor1", server1);
            Naming.rebind("servidor2", server2);
            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}