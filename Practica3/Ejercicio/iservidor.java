import java.rmi.Remote;
import java.rmi.RemoteException;
//Interfaz de los servidores
public interface iservidor extends Remote{
    public int buscarCliente(String cliente) throws RemoteException;
    public void registrarCliente(String cliente) throws RemoteException;
    public void addDonacion(int donacion, String cliente) throws RemoteException;
    public int getTotalDonado(String cliente) throws RemoteException;
}   
