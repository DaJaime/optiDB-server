
Vagrant.configure("2") do |config|
  config.vm.box = "optiDB-server"
  config.vm.hostname = "projetMaster-server"
  config.vm.network "public_network"
  config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"
  config.vm.provider "virtualbox" do |vb|
      vb.name = "projetMaster-server"
      vb.memory = "2048"
  end
   config.vm.provision "shell", path: 'scripts/install.sh'
end
