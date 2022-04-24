//Define la interfaz remota

package Ejemplo3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

public interface icontador extends Remote{
    int sumar() throws RemoteException;
    void sumar(int valor) throws RemoteException;
    public int incrementar() throws RemoteException;
}
