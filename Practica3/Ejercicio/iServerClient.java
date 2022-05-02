//Interfaz del servidor con el cliente
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iServerClient extends Remote{
    public String registrar(String cliente) throws RemoteException;
    public String donar(int donacion, String cliente) throws RemoteException;
    public String getTotalCliente(String cliente) throws RemoteException;
    public String getTotalReplica() throws RemoteException;
    public String retirarDonacion(int cantidad, String cliente) throws RemoteException;
}   
