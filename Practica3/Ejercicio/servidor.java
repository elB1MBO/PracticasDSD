import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

//import contador.contador;
public class servidor {
    public static void main(String[] args) {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea una instancia de contador
            // System.setProperty("java.rmi.server.hostname","192.168.1.107");
            Registry reg = LocateRegistry.createRegistry(1099);
            //Habría que declarar un objeto de la clase que se encargue del registro y de las donaciones, tipo:
            Registro registro = new Registro();
            Donacion donacion = new Donacion();
            // suma = 0;
            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}