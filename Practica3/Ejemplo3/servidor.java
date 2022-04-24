//Código del servidor

package Ejemplo3;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class servidor {
    public static void main(String[] args) {
        //Crea e instala el gestor de seguridad
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            //Crea una instancia de contador
            Registry registry = LocateRegistry.createRegistry(1099);
            contador micontador = new contador();
            Naming.rebind("micontador", micontador);
            //suma=0;
            System.out.println("Servidor RemoteException | MalformedURLException preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
