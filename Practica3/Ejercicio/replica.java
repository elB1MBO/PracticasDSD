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
    public String salida = "";

    public replica(String id_replica) throws RemoteException{
        id = id_replica;
        clientes = new ArrayList<>();
        donaciones = new ArrayList<>();
        totalDonado = 0;
    }

    
    //INTERFAZ SERVIDOR-SERVIDOR

    public String getSalida() throws RemoteException{
        return salida;
    }

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
        if(buscarCliente(cliente) == -1 && replica.buscarCliente(cliente) == -1){ //Comprueba que no esté ya registrado en alguna réplica
            if(getNumeroClientes() <= replica.getNumeroClientes()){
                //Un cliente no puede registrarse 2 veces
                salida = "Cliente "+cliente+" registrado en la "+getId()+"\n";
                System.out.println(salida);
                clientes.add(cliente);  
                System.out.println("Número de clientes en la "+getId()+": "+clientes.size()+"\n");
                //Cuando se registra un cliente, hay que asociarle un indice en el array de donaciones
                int indice = buscarCliente(cliente);
                donaciones.add(indice, 0);

                
            }
            else{
                //Si la otra réplica tiene menos clientes que esta, la manda a registrarlo
                replica.registrarCliente(cliente);
            }
        } else {
            salida = "Error en "+getId()+": El cliente ya está registrado.\n";
            System.out.println(salida);
        }
    }

    public void addDonacion(int donacion, String cliente) throws RemoteException{
        if(donacion > 0){
            if(buscarCliente(cliente) != -1){ //Si el cliente está en esta replica, opera
                //Busca el indice del cliente
                int i = buscarCliente(cliente);
                int actual = donaciones.get(i);
                //Y cambia el valor en su respectiva posicion de donaciones
                donaciones.set(i, actual+=donacion);
                System.out.println("Donacion de "+donacion+" añadida en la "+getId()+" por parte del "+cliente+"\n");
                totalDonado += donacion;

                salida = cliente+" has donado "+donacion+" en la "+getId();
            } else { //Si no, manda a la otra réplica que haga la donacion
                replica.addDonacion(donacion, cliente);
                salida = replica.getSalida(); //Para mostrar la salida cuando redirige la donacion
            }
        } else {
            salida = "No puedes robar dinero del servidor, introduce un valor positivo por favor.";
        }
    }
    //Devuelve el total donado por el cliente
    public void getTotalDonadoCliente(String cliente) throws RemoteException{
        //Un cliente solo puede hacer esta consulta si esta es la replica en la que esta registrado
        if(buscarCliente(cliente) != -1){
            int i = buscarCliente(cliente);
            if(donaciones.get(i) > 0){
                System.out.println("Donacion total del cliente "+cliente+" en la "+getId()+": "+donaciones.get(i)+"\n");
                salida = cliente+" has donado un total de "+donaciones.get(i)+" en la "+getId();
            } else {
                salida = "Error en "+getId()+": El "+cliente+" no ha realizado ninguna donación aún.\n";
                System.out.println(salida);
            }
        } else {
            salida = "Error en "+getId()+" : Ésta no es la réplica en la que está registrado el "+cliente+"\n";
            System.out.println(salida);
        }
    }
    //Devuelve el total donado en la réplica
    public void getTotalDonadoReplica() throws RemoteException{
        salida = "Total donado en la "+getId()+": "+totalDonado;
        System.out.println(salida);
    }

    public void retirarDonacionCliente(int cantidad, String cliente) throws RemoteException{
        if(cantidad > 0){
            if(buscarCliente(cliente) != -1){ //Si el cliente está en esta replica, opera
                //Busca el indice del cliente
                int i = buscarCliente(cliente);
                int actual = donaciones.get(i);
                if(cantidad<=actual){
                    //Y cambia el valor en su respectiva posicion de donaciones
                    donaciones.set(i, actual-=cantidad);
                    System.out.println("Retirada de "+cantidad+" restada en la "+getId()+" por parte del "+cliente+"\n");
                    totalDonado -= cantidad;

                    salida = cliente+" has sacado "+cantidad+" de la "+getId();
                } else {
                    salida = "Error: la cantidad que desea sacar es mayor que la que ha donado.";
                }
            } else { //Si no, manda a la otra réplica que haga la donacion
                replica.retirarDonacionCliente(cantidad, cliente);
                salida = replica.getSalida(); //Para mostrar la salida cuando redirige la donacion
            }
        } else {
            salida = "Introduce un valor positivo por favor.";
        }
    }

    //INTERFAZ SERVIDOR-CLIENTE

    public String registrar(String cliente) throws RemoteException {
        registrarCliente(cliente);
        return salida;
    }

    public String donar(int donacion, String cliente) throws RemoteException {
        addDonacion(donacion, cliente);
        return salida;
    }

    public String getTotalCliente(String cliente) throws RemoteException{
        getTotalDonadoCliente(cliente);
        return salida;
    }
    
    public String getTotalReplica() throws RemoteException{
        getTotalDonadoReplica();
        return salida;
    }

    public String getId() throws RemoteException{
        return id;
    }

    public String retirarDonacion(int cantidad, String cliente) throws RemoteException{
        retirarDonacionCliente(cantidad, cliente);
        return salida;
    }

}
