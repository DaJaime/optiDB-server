echo "Installation des dépendences"
mvn clean install
echo "Lancement du server"
java -jar target/optidb-server-1.0-Beta.jar
