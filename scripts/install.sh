echo "mise à jour de la vm"
sudo apt-get update -y
sudo apt -y install openjdk-8-jdk
# Gradle
wget https://services.gradle.org/distributions/gradle-5.1.1-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-5.1.1-bin.zip
read -r -d '' ENV_VARS <<- EOM
export PATH=$PATH:/opt/gradle/gradle-5.1.1/bin
EOM
echo "$ENV_VARS" >> /home/vagrant/.bashrc
source /home/vagrant/.bashrc

echo "########"
