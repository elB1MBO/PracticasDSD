echo
echo "Lanzando el primer cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente1
sleep 2

echo
echo "Lanzando el segundo cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente2

echo
echo "Lanzando el tercer cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente3

echo
echo "Lanzando el cuarto cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente4

echo
echo "Lanzando el quinto cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost cliente5