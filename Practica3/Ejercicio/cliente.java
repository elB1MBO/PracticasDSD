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
            //Por defecto, solicitaran a la replica 1
            iServerClient servidor = (iServerClient) mireg.lookup("replica1");
            System.out.println("Registrando al cliente ...");
            String cliente = args[1];
            servidor.registrar(cliente);
            //Si intentamos que se registre otra vez, te dirá que no.
            servidor.registrar(cliente);
            //Hace una donacion
            int donacion = Integer.parseInt(args[2]);
            System.out.println("Añadiendo donación  de "+donacion+" euros ...");
            servidor.donar(donacion, cliente);
            servidor.donar(100, cliente);
            //Y consulta el total donado
            System.out.println("Total donado: "+servidor.getTotalDonado(cliente));

        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}