import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;

public class servidor2 extends UnicastRemoteObject implements iservidor{
    //Tendremos dos arrays, uno de clientes y el otro de las donaciones.
    //Entonces: donaciones[i] es el total donado del cliente i -> clientes[i]
    private ArrayList<String> clientes;
    private ArrayList<Integer> donaciones;
    
    public servidor2() throws RemoteException{
        clientes = new ArrayList<>();
        donaciones = new ArrayList<>();
    }

    public int buscarCliente(String cliente) throws RemoteException{
        return cliente.indexOf(cliente);
    }

    public void registrarCliente(String cliente) throws RemoteException{
        clientes.add(cliente);
    }

    public void addDonacion(int donacion, String cliente) throws RemoteException{
        //Busca el indice del cliente
        int i = buscarCliente(cliente);
        int actual = donaciones.get(i);
        //Y cambia el valor en su respectiva posicion de donaciones
        donaciones.set(i, actual+=donacion);
    }

    public int getTotalDonado(String cliente) throws RemoteException{
        int i = buscarCliente(cliente);
        return donaciones.get(i);
    }
}
