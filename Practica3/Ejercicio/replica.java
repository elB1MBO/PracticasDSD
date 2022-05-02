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
    

    public replica(String id_replica) throws RemoteException{
        id = id_replica;
        clientes = new ArrayList<>();
        donaciones = new ArrayList<>();
    }

    //INTERFAZ SERVIDOR-SERVIDOR

    @Override
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
        
        if(getNumeroClientes() <= replica.getNumeroClientes()){
            if(buscarCliente(cliente) == -1){ //Un cliente no puede registrarse 2 veces
                clientes.add(cliente);
                System.out.println("Número de clientes en la "+id+": "+clientes.size());  
                //Cuando se registra un cliente, hay que asociarle un indice en el array de donaciones
                int indice = buscarCliente(cliente);
                donaciones.add(indice, 0);
                System.out.println("Cliente "+cliente+" registrado en la replica "+id);
            }
            else
                System.out.println("Error: El cliente ya está registrado.");
        } else {
            //Si la otra réplica tiene menos clientes que esta, la manda a registrarlo
            replica.registrarCliente(cliente);
        }

        
    }

    public void addDonacion(int donacion, String cliente) throws RemoteException{
        if(buscarCliente(cliente) != -1){ //Si el cliente está en esta replica, opera
            //Busca el indice del cliente
            int i = buscarCliente(cliente);
            int actual = donaciones.get(i);
            System.out.println("Donacion: "+actual+", donaciones: "+donaciones.get(i));
            //Y cambia el valor en su respectiva posicion de donaciones
            donaciones.set(i, actual+=donacion);
            System.out.println("Donacion añadida en la replica "+id);
        } else { //Si no, manda a la otra réplica que haga la donacion
            replica.addDonacion(donacion, cliente);
        }
    }

    public int getTotalDonado(String cliente) throws RemoteException{
        if(buscarCliente(cliente) != -1){
            int i = buscarCliente(cliente);
            System.out.println("Donacion añadida en la replica "+id);
            return donaciones.get(i);
        } else {
            return replica.getTotalDonado(cliente);
        }
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
