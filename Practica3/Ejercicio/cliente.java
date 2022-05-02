import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) throws IOException {
        //Para la entrada de datos:
        Scanner entrada = new Scanner(System.in);
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea el stub para el cliente especificando el nombre del servidor
            Registry mireg = LocateRegistry.getRegistry(args[0]);
            //Por defecto, solicitaran a la replica 1
            //iServerClient servidor = (iServerClient) mireg.lookup("replica1");
            //iServerClient servidor2 = (iServerClient) mireg.lookup("replica2");
            iServerClient servidor = (iServerClient) mireg.lookup("replica1");
            String cliente = args[1];
            int eleccion;
            boolean salir = false;
            String salida;
            System.out.println("Bienvenido al servicio de donaciones de DSD, "+cliente+".\n");
            while(!salir){
                System.out.println("Eliga la réplica a la que quiere conectarse:");
                System.out.println("1.- Réplica 1\n2.- Réplica 2");
                eleccion = entrada.nextInt();
                switch (eleccion) {
                    case 1:
                        servidor = (iServerClient) mireg.lookup("replica1");
                        break;
                    case 2:
                        servidor = (iServerClient) mireg.lookup("replica2");
                        break;
                    default:
                        System.out.println("Error: valor introducido no válido. Introduzca 1 o 2 para seleccionar la réplica.");
                        System.exit(0);
                }
                System.out.println("Seleccione la operación deseada: ");
                System.out.println("1.- Registrarse\n2.- Donar\n3.- Mi Total Donado\n4.- Total Donado réplica\n5.- Retirar dinero\nOtro valor para salir.");
                eleccion = entrada.nextInt();
                switch (eleccion) {
                    case 1:
                        salida = servidor.registrar(cliente);
                        System.out.println(salida);
                        break;
                    case 2:
                        System.out.println("Introduzca la cantidad a donar:\n");
                        int donacion = entrada.nextInt();
                        salida = servidor.donar(donacion, cliente);
                        System.out.println(salida);
                        break;
                    case 3:
                        salida = servidor.getTotalCliente(cliente);
                        System.out.println(salida);
                        break;
                    case 4:
                        salida = servidor.getTotalReplica();
                        System.out.println(salida);
                        break;
                    case 5:
                        System.out.println("Introduzca la cantidad a sacar:\n");
                        int cantidad = entrada.nextInt();
                        salida = servidor.retirarDonacion(cantidad, cliente);
                        System.out.println(salida);
                        break;
                    default:
                        System.out.println(cliente+" saliendo ...");
                        salir = true;
                        break;
                }
            }
            
            /* System.out.println("Registrando al cliente ...");
            
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
            System.out.println("Añadiendo donación en la replica");
            servidor2.donar(donacion, cliente);
            //Y consulta el total donado
            System.out.println("Total donado por el "+cliente+" en la replica1: "+servidor1.getTotalDonadoCliente(cliente));
            System.out.println("Total donado por el "+cliente+" en la replica2: "+servidor2.getTotalDonadoCliente(cliente));

            //Consulta el total donado en cada réplica:
            System.out.println("Total donado en la réplica 1: "+servidor1.getTotalDonadoReplica());
            System.out.println("Total donado en la réplica 2: "+servidor2.getTotalDonadoReplica());
 */
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}