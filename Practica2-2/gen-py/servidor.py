from asyncio import transports
from cgitb import handler
import glob
from platform import processor
import sys

from calculadora import Calculadora
#from calculadora.ttypes import Operation
#Esto último seria necesario si hubieramos añadido tipos en el fichero .thrift

#hay que instalar antes el paquete thrift de python
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

#Para realizar ciertos cálculos
from math import factorial, sqrt

#ahora, tenemos que hacer que imprima cuando haya errores en el servidor para poder depurar:
import logging
logging.basicConfig(level=logging.DEBUG)

#Implementamos el handler:
class CalculadoraHandler:
    def __init__(self):
        self.log = {}
    def ping(self):
        print("Me han hecho ping()")
    def suma(self, n1, n2):
        print("sumando "+str(n1)+" con "+str(n2))
        return n1 + n2
    def resta(self, n1, n2):
        print("restando "+str(n1)+" con "+str(n2))
        return n1 - n2
    def mult(self, n1, n2):
        print("multiplicando "+str(n1)+" con "+str(n2))
        return n1 * n2
    def div(self, n1, n2):
        print("dividiendo "+str(n1)+" con "+str(n2))
        return n1 / n2
    def pot(self, n1, n2):
        print("elevando "+str(n1)+" a "+str(n2))
        return pow(n1,n2)
    def raiz(self, n1):
        print("Raíz cuadrada de "+str(n1))
        return sqrt(n1)
    def fact(self, n1):
        print("Factorial de "+str(n1))
        if n1 < 0:
            print("El factorial de un número negativo no existe!")
        elif n1 == 0:
            return 1
        else:
            factorial = 1
            while(n1 > 1):
                factorial *= n1
                n1 -= 1
            return factorial
            
#Y lanzamos el servidor
if __name__ == "__main__":
    handler = CalculadoraHandler()
    processor = Calculadora.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("Iniciando servidor...")
    server.serve()
    print("Realizado con éxito")