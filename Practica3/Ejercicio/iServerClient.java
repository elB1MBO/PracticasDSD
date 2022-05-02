//Interfaz del servidor con el cliente
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iServerClient extends Remote{
    public void registrar(String cliente) throws RemoteException;
    public void donar(int donacion, String cliente) throws RemoteException;
    public int getTotalDonadoCliente(String cliente) throws RemoteException;
    public int getTotalDonadoReplica() throws RemoteException;
}   
