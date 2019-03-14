[![Build Status](https://img.shields.io/travis/DaJaime/optiDB-server/master.svg?style=flat-square)](https://travis-ci.com/DaJaime/optiDB-server)
[![License](https://img.shields.io/github/license/DaJaime/optiDB-server.svg?style=flat-square)](LICENSE)
[![Version](https://img.shields.io/github/tag/DaJaime/optiDB-server.svg?label=version&style=flat-square)](build.gradle)

[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=coverage)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)
[![SonarCloud Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=sqale_index)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=code_smells)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)

[![Waffle.io - Columns and their card count](https://badge.waffle.io/DaJaime/optiDB-server.svg?columns=all)](https://waffle.io/DaJaime/optiDB-server)


# OptiDB-server - Projet Master

## Prérequis
* OptiDB-client, la partie client du projet - [Télécharger optiDB-client](https://github.com/ChaudhryHaseeb/optiDB-client)
* Virtualbox, outil de virtualisation - [Télécharger Virtualbox](https://www.virtualbox.org/wiki/Downloads)
* Vagrant, gestionnaire de machine virtuelle - [Télécharger vagrant](https://www.vagrantup.com/downloads.html)
* Télécharger la box du serveur - [Télécharger la box](https://github.com/jose-lpa/packer-ubuntu_lts/releases/download/v3.1/ubuntu-16.04.box)

## Description
OptiDB est un projet qui teste la performance de trois bases de données : 
* Mysql 
* MariaDB
* Postgres  

En indiquant le nombre de colonnes, de lignes et si la table doit contenir ou non une clé primaire, elle va générer un jeu de données dans un docker et retourner le temps d'excution des différentes requêtes.
Vous vous trouver sur OptiDB-server qui est l'API REST du projet. C'est cette API qui va retourner les résultats des tests qui seront envoyés vers le projet OptiDB-Client.

# Installer l'image avec vagrant

> Pour pouvoir lancer la machine virtuelle avec vagrant, il faut d'abord récupérer l'image de la box (lien dans les prérequis). 
Il faut aussi que vous ayez vagrant dans le path.

1. cd $chemin_vers_la_box
2. vagrant box add --name optiDB-server ubuntu-16.04.box
3. Cloner ou télécharger le projet
    - git clone https://github.com/DaJaime/optiDB-server.git 
    - télécharger directement le dépôt en cliquant [ici](https://github.com/DaJaime/optiDB-server/archive/master.zip)
4. cd optiDB-server
5. vagrant up

# Lancer le serveur

```bash
# Accéder au shell
6. vagrant ssh

# Accéder au répertoire du projet
7. cd /vagrant

# Executez le serveur
8. ./scripts/run.sh
```

> Vous devez désormais procéder à l'installation de la partie client du projet (liens dans les prérequis).
> Si c'est déjà fait, vous pouvez exécuter le client












