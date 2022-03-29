#Importamos lo necesario

from asyncio import protocols, transports
from http import client
from pydoc import cli
from calculadora import Calculadora

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

#Creamos el objeto cliente
transport = TSocket.TSocket("localhost", 9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Calculadora.Client(protocol)

#Y ahora usamos el objeto cliente
transport.open()

print("Hacemos ping al server")
client.ping()

operador = ""
salir = False
while salir != True:

    operador = input("Introduzca el operador (pulse C para salir): ")
    if operador == "C":
        salir=True
    else:
        n1 = input("Introduzca el primer número: ")
        n1 = float(n1)
        if operador != "!":
            n2 = input("Introduzca el segundo numero: ")
            n2 = float(n2)
        if operador == "+":
            resultado = client.suma(n1,n2)
            print(str(n1)+"+"+str(n2)+"="+str(resultado))
        if operador == "-":
            resultado = client.resta(n1,n2)
            print(str(n1)+"-"+str(n2)+"="+str(resultado))
        if operador == "x":
            resultado = client.mult(n1,n2)
            print(str(n1)+"*"+str(n2)+"="+str(resultado))
        if operador == "/":
            resultado = client.div(n1,n2)
            print(str(n1)+"/"+str(n2)+"="+str(resultado))
        if operador == "!":
            resultado = client.fact(n1)
            print(str(n1)+"! ="+str(resultado))
        if operador == "p":
            resultado = client.pot(n1,n2)
            print("Resultado potencia: "+str(resultado))
        

transport.close()