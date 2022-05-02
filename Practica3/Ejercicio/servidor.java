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
            replica replica1 = new replica("replica1");
            replica replica2 = new replica("replica2");
            reg.rebind("replica1", replica1);
            reg.rebind("replica2", replica2);
            //Una vez tenemos nuestras 2 réplicas, tenemos que añadirla una a la otra para que se comuniquen
            replica1.addReplica(replica2);
            replica2.addReplica(replica1);

            System.out.println("Servidor RemoteException preparado");
        } catch (RemoteException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}