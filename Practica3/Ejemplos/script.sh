#!/bin/sh -e
# ejecutar = Macro para compilacion y ejecucion del programa ejemplo
# en una sola máquina Unix de nombre localhost.

echo 
echo "Lanzando el ligador de RMI ..."
rmregistry &

echo 
echo "Compilando con javac ..."
javac *.java

sleep 2

echo 
echo "Lanzando el servidor ..."
java -cp . -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy Ejemplo

sleep 2

echo
echo "Lanzando el segundo cliente ..."
echo
java -cp . -Djava.security.policy=server.policy Cliente_Ejemplo localhost 3