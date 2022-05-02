/* import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.net.MalformedURLException;

public class servidor1 extends UnicastRemoteObject implements iServerServer{
    
    //Tendremos dos arrays, uno de clientes y el otro de las donaciones.
    //Entonces: donaciones[i] es el total donado del cliente i -> clientes[i]
    private ArrayList<String> clientes;
    private ArrayList<Integer> donaciones;
    
    public servidor1() throws RemoteException{
        clientes = new ArrayList<>();
        donaciones = new ArrayList<>();
    }
    public int buscarCliente(String cliente) throws RemoteException{
        return clientes.indexOf(cliente);
    }

    public void registrarCliente(String cliente) throws RemoteException{
        
        if(buscarCliente(cliente) == -1){ //Un cliente no puede registrarse 2 veces
            clientes.add(cliente);
            System.out.println("Número de clientes en servidor1 "+clientes.size());  
            //Cuando se registra un cliente, hay que asociarle un indice en el array de donaciones
            int indice = buscarCliente(cliente);
            donaciones.add(indice, 0);
        }
        else
            System.out.println("Error: El cliente ya está registrado.");
    }

    public void addDonacion(int donacion, String cliente) throws RemoteException{
        //Busca el indice del cliente
        int i = buscarCliente(cliente);
        int actual = donaciones.get(i);
        System.out.println("Donacion: "+actual+", donaciones: "+donaciones.get(i));
        //Y cambia el valor en su respectiva posicion de donaciones
        donaciones.set(i, actual+=donacion);
    }

    public int getTotalDonado(String cliente) throws RemoteException{
        int i = buscarCliente(cliente);
        return donaciones.get(i);
    }

}
 */