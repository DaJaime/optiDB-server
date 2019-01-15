
Vagrant.configure("2") do |config|
  config.vm.box = "optiDB-server"
  config.vm.hostname = "projetMaster-server"
  config.vm.provider "virtualbox" do |vb|
      vb.name = "projetMaster-server"
      vb.memory = "1024"
  end
   config.vm.provision "shell", path: 'scripts/install.sh'
end
