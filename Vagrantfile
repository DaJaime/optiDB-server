
Vagrant.configure("2") do |config|
  config.vm.box = "optiDB-server"
  config.vm.hostname = "projetMaster-server"
  config.vm.network "public_network"
  config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"
  config.vm.network "forwarded_port", guest: 2375, host: 2375, host_ip: "127.0.0.1"
  config.vm.network "forwarded_port", guest: 2376, host: 2376, host_ip: "127.0.0.1"
config.vm.network "forwarded_port", guest: 80, host: 9000, host_ip: "127.0.0.1"
  config.vm.provider "virtualbox" do |vb|
      vb.name = "projetMaster-server"
      vb.memory = "2048"
  end
   config.vm.provision "shell", path: 'scripts/install.sh'
end
