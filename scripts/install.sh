echo "mise à jour de la vm"
sudo apt-get update -y
sudo apt -y install openjdk-8-jdk
# Maven
sudo apt -y install maven
# Docker
docker pull mysql:8.0.14

echo "########"
