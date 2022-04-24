//Implementa la interfaz remota

package Ejemplo2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Thread;

public class Ejemplo implements Ejemplo_II{
    public Ejemplo() {
        super();
    }

    //Probar asi y cambiandolo por:
    //public synchronized void escribir_mensaje(String mensaje){ //Tratando de entender las diferencias entre ambos
    public void escribir_mensaje(String mensaje) {
        System.out.println("\nEntra Hebra " + mensaje);
        //Buscamos los procesos 0, 10, 20, ...
        if(mensaje.endsWith("0")){
            try {
                System.out.println("Empezamos a dormir");
                Thread.sleep(5000);
                System.out.println("Terminamos de dormir");
            } catch (Exception e) {
                System.err.println("Ejemplo exception:");
                e.printStackTrace();
            }
        }
        System.out.println("Sale hebra " + mensaje);
    }

    public static void main(String[] args) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        } 

        try {
            String nombre_objeto_remoto = "Ejemplo_II";
            Ejemplo_II prueba = new Ejemplo();
            Ejemplo_II stub = (Ejemplo_II) UnicastRemoteObject.exportObject(prueba, 0);   
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(nombre_objeto_remoto, stub);
            System.out.println("Ejemplo bound");
        } catch (Exception e) {
            System.err.println("Ejemplo exception");
            e.printStackTrace();
        }
    }

}
