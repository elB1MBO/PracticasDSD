import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;

public class Donacion extends UnicastRemoteObject implements iDonacion{
    private int total_donado;
    private String cliente;

    public Donacion() throws RemoteException{}

    public void registro(String nombre) throws RemoteException{
        cliente = nombre;
    }

    public String getCliente() throws RemoteException{
        return cliente;
    }

    public int getTotal() throws RemoteException{
        return total_donado;
    }

    public void addDonacion(int donacion) throws RemoteException{
        total_donado+=donacion;
    }
}
