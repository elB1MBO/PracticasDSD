#Importamos lo necesario

from asyncio import protocols, transports
from http import client
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

resultado = client.suma(4,2)
print("4+2="+str(resultado))
resultado = client.resta(1,3)
print("1-3="+str(resultado))
resultado = client.mult(3,2)
print("2*2="+str(resultado))
resultado = client.div(10,2)
print("10/5="+str(resultado))

transport.close()