echo "mise à jour de la vm"
sudo apt-get update -y
sudo apt -y install openjdk-8-jdk
# Maven
sudo apt -y install maven
# Docker mysql
docker pull mysql:8.0.14
# Docker postgres
docker pull postgres:11.2
# Docker mariaDB
docker pull mariadb:10.4
echo "########"
