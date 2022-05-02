import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.net.MalformedURLException;

public class replica extends UnicastRemoteObject implements iServerServer, iServerClient{
    
    //Tendremos dos arrays, uno de clientes y el otro de las donaciones.
    //Entonces: donaciones[i] es el total donado del cliente i -> clientes[i]
    private ArrayList<String> clientes;
    private ArrayList<Integer> donaciones;
    private String id;
    private iServerServer replica;
    private int totalDonado; //Total donado en la replica
    

    public replica(String id_replica) throws RemoteException{
        id = id_replica;
        clientes = new ArrayList<>();
        donaciones = new ArrayList<>();
        totalDonado = 0;
    }

    //INTERFAZ SERVIDOR-SERVIDOR

    public void addReplica(replica rep) throws RemoteException {
        //Para hacerlo con más réplicas, habria que usar un array de replicas
        replica = rep;
    }
    //Comprueba el número de clientes que tiene esta réplica.
    public int getNumeroClientes() throws RemoteException {
        return clientes.size();
    }

    public int buscarCliente(String cliente) throws RemoteException{
        return clientes.indexOf(cliente);
    }

    public void registrarCliente(String cliente) throws RemoteException{
        if(buscarCliente(cliente) == -1 && replica.buscarCliente(cliente) == -1){
            if(getNumeroClientes() <= replica.getNumeroClientes()){
                //Un cliente no puede registrarse 2 veces
                System.out.println("Cliente "+cliente+" registrado en la "+id+"\n");
                clientes.add(cliente);  
                System.out.println("Número de clientes en la "+id+": "+clientes.size()+"\n");
                //Cuando se registra un cliente, hay que asociarle un indice en el array de donaciones
                int indice = buscarCliente(cliente);
                donaciones.add(indice, 0);
            }
            else{
                //Si la otra réplica tiene menos clientes que esta, la manda a registrarlo
                replica.registrarCliente(cliente);
            }
        } else {
            System.out.println("Error en "+id+": El cliente ya está registrado.\n");
        }
    }

    public void addDonacion(int donacion, String cliente) throws RemoteException{
        if(buscarCliente(cliente) != -1){ //Si el cliente está en esta replica, opera
            //Busca el indice del cliente
            int i = buscarCliente(cliente);
            int actual = donaciones.get(i);
            //System.out.println("Donacion: "+actual+", donaciones: "+donaciones.get(i));
            //Y cambia el valor en su respectiva posicion de donaciones
            donaciones.set(i, actual+=donacion);
            System.out.println("Donacion de "+donacion+" añadida en la "+id+" del "+cliente+"\n");
            totalDonado += donacion;
        } else { //Si no, manda a la otra réplica que haga la donacion
            replica.addDonacion(donacion, cliente);
        }
    }
    //Devuelve el total donado por el cliente
    public int getTotalDonadoCliente(String cliente) throws RemoteException{
        //Un cliente solo puede hacer esta consulta si esta es la replica en la que esta registrado
        if(buscarCliente(cliente) != -1){
            int i = buscarCliente(cliente);
            if(donaciones.get(i) > 0){
                System.out.println("Donacion total del cliente "+cliente+" en la "+id+": "+donaciones.get(i)+"\n");
                return donaciones.get(i);
            } else {
                System.out.println("Error en "+id+": El "+cliente+" no ha realizado ninguna donación aún.\n");
                return -1;
            }
        } else {
            System.out.println("Error en "+id+" : Ésta no es la réplica en la que está registrado el "+cliente+"\n");
            return -1;
        }
    }
    //Devuelve el total donado en la réplica
    public int getTotalDonadoReplica() throws RemoteException{
        return totalDonado;
    }

    //INTERFAZ SERVIDOR-CLIENTE

    @Override
    public void registrar(String cliente) throws RemoteException {
        registrarCliente(cliente);
        
    }

    @Override
    public void donar(int donacion, String cliente) throws RemoteException {
        addDonacion(donacion, cliente);
    }

}
