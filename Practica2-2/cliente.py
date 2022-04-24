#Importamos lo necesario

from locale import str
from builtins import float, input, print, range
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

    operador = input("Introduzca el operador (pulse V para operar con vectores, C para salir): ")
    if operador == "C":
        salir=True
    elif operador == "V":
        tam = input("Introduzca tamaño de los vectores: ")
        tam = int(tam)
        resultadoV = [float] * tam
        v1 = [float] * tam
        print("Introduzca los números del primer vector: ")
        for i in range(tam):
            num = input(str(i+1)+"º: ")
            num = float(num)
            v1[i] = num
        v2 = [float] * tam
        print("Introduzca los números del segundo vector: ")
        for i in range(tam):
            num = input(str(i+1)+"º: ")
            num = float(num)
            v2[i] = num
        operacion = input("Introduzca la operación que desea aplicar sobre estos vectores: ")
        if operacion == "+":
            resultadoV = client.sumaVectores(v1, v2)
            print("El vector resultado es: ")
            print(resultadoV)
        if operacion == "-":
            resultadoV = client.restaVectores(v1, v2)
            print("El vector resultado es: ")
            print(resultadoV)
        if operacion == "x":
            resultadoV = client.multVectores(v1, v2)
            print("El vector resultado es: ")
            print(resultadoV)
        if operacion == "/":
            resultadoV = client.divVectores(v1, v2)
            print("El vector resultado es: ")
            print(resultadoV)
    else:
        n1 = input("Introduzca el primer número: ")
        n1 = float(n1)
        if operador == "!" or operador == "r":
            if operador == "!":
                resultado = client.fact(n1)
                print(str(n1)+"! = "+str(resultado))
            if operador == "r":
                resultado = client.raiz(n1)
                print("La raíz cuadrada de "+str(n1)+" es: "+str(resultado))
            
        else:
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
            
            if operador == "p":
                resultado = client.pot(n1,n2)
                print(str(n1)+"^"+str(n2)+"="+str(resultado))
        

transport.close()