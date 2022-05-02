//Interfaz de los servidores
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iServerServer extends Remote{
    public void addReplica(replica rep) throws RemoteException;
    public int getNumeroClientes() throws RemoteException;
    public int buscarCliente(String cliente) throws RemoteException;
    public void registrarCliente(String cliente) throws RemoteException;
    public void addDonacion(int donacion, String cliente) throws RemoteException;
    public int getTotalDonadoCliente(String cliente) throws RemoteException;
    public int getTotalDonadoReplica() throws RemoteException;
}   
