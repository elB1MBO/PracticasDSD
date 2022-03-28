//Importamos las cosas necesarias
import tutorial.*;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;



//Implementar clase handler
public class CalculadoraHandler implements Calculadora.Iface{
    public void ping(){System.out.println("Me han hecho ping");}
    public double sumar(double a, double b){
        return a+b;
    }
    public double restar(double a, double b){
        return a-b;
    }
    public double mult(double a, double b){
        return a*b;
    }
    public double div(double a, double b){
        return a/b;
    }
}

//Lanzar el servidor en el static void main()
public static void main(String [] args){
    try {
        TServerTransport serverTransport = new TServerSocket(9090);
        TServer server = new TSimpleServer(new Args(serverTransport), 
            processor(processor));
        System.out.println("Iniciando servidor...");
        server.serve();
    } catch (Exception e) {
        e.printStackTrace();
    }
}