//Define la interfaz remota

package Ejemplo2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Ejemplo_II extends Remote{
    public void escribir_mensaje(String mensaje) throws RemoteException;
}
