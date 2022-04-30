import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iDonacion extends Remote{
    //Se registra el cliente
    void registro(String nombre) throws RemoteException;
    String getCliente() throws RemoteException;
    //Devuelve el total de donaciones
    int getTotal() throws RemoteException;
    //Añade la donación
    void addDonacion(int donacion) throws RemoteException;
    
}
