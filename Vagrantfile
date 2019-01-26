
Vagrant.configure("2") do |config|
  config.vm.box = "optiDB-server"
  config.vm.hostname = "projetMaster-server"
  config.vm.network "private_network", ip: "192.168.33.10"
  config.vm.network "forwarded_port", guest: 4000, host: 4000, host_ip: "127.0.0.1"
  config.vm.provider "virtualbox" do |vb|
      vb.name = "projetMaster-server"
      vb.memory = "2048"
  end
   config.vm.provision "shell", path: 'scripts/install.sh'
end
