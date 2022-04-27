echo
echo "Lanzando el primer cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente
sleep 2

echo
echo "Lanzando el segundo cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente