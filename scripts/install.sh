echo "mise à jour de la vm"
sudo apt-get update -y
sudo apt -y install openjdk-8-jdk
# Maven
sudo apt -y install maven
# Docker
docker pull mysql:8.0.14
# Docker MongoDB
docker pull mongo:3.4.19
# Création du rep historique
mkdir /vagrant/media
echo "installation terminé"
