echo
echo "Lanzando el primer cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente1 10
sleep 2

echo
echo "Lanzando el segundo cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente2 1000

echo
echo "Lanzando el tercer cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente3 100