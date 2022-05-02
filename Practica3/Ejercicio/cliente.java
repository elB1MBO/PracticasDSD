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
            iServerClient servidor1 = (iServerClient) mireg.lookup("replica1");
            iServerClient servidor2 = (iServerClient) mireg.lookup("replica2");
            System.out.println("Registrando al cliente ...");
            String cliente = args[1];
            servidor1.registrar(cliente);
            //Si intentamos que se registre otra vez, te dirá que no.
            servidor1.registrar(cliente);
            //Si intenta consultar lo donado antes de donar, mostrará un mensaje de error.
            servidor1.getTotalDonadoCliente(cliente);
            servidor2.getTotalDonadoCliente(cliente);
            //Hace una donacion
            int donacion = Integer.parseInt(args[2]);
            System.out.println("Añadiendo donación de "+donacion+" euros ...");
            servidor1.donar(donacion, cliente);
            servidor1.donar(100, cliente);
            System.out.println("Añadiendo donación en la replica 2");
            servidor2.donar(donacion, cliente);
            //Y consulta el total donado
            System.out.println("Total donado por el "+cliente+" en la replica1: "+servidor1.getTotalDonadoCliente(cliente));
            System.out.println("Total donado por el "+cliente+" en la replica2: "+servidor2.getTotalDonadoCliente(cliente));

            //Consulta el total donado en cada réplica:
            System.out.println("Total donado en la réplica 1: "+servidor1.getTotalDonadoReplica());
            System.out.println("Total donado en la réplica 2: "+servidor2.getTotalDonadoReplica());

        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}