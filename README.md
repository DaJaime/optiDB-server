[![Build Status](https://img.shields.io/travis/DaJaime/optiDB-server/master.svg?style=flat-square)](https://travis-ci.com/DaJaime/optiDB-server)
[![License](https://img.shields.io/github/license/DaJaime/optiDB-server.svg?style=flat-square)](LICENSE)
[![Version](https://img.shields.io/github/tag/DaJaime/optiDB-server.svg?label=version&style=flat-square)](build.gradle)

[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=coverage)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)
[![SonarCloud Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=sqale_index)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=code_smells)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=optidb.server%3Aoptidb-server&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=optidb.server%3Aoptidb-server)

[![Waffle.io - Columns and their card count](https://badge.waffle.io/DaJaime/optiDB-server.svg?columns=all)](https://waffle.io/DaJaime/optiDB-server)


# OptiDB côté serveur - Projet Master

## Prérequis
* Git, gestionnaire de version - [Télécharger git](https://git-scm.com/downloads)
* Virtualbox, outil de virtualisation - [Télécharger Virtualbox](https://www.virtualbox.org/wiki/Downloads)
* Vagrant, gestionnaire de machine virtuelle - [Télécharger vagrant](https://www.vagrantup.com/downloads.html)
* Télécharger la box du serveur - [Télécharger la box](https://github.com/jose-lpa/packer-ubuntu_lts/releases/download/v3.1/ubuntu-16.04.box)

## Installation

Pour pouvoir lancer la machine virtuelle avec vagrant il faut d'abord récupérer l'image de la box (lien dans les prérequis)
Il faut aussi que vous ayez vagrant dans le path

```bash
# Installer l'image avec vagrant
cd $chemin_vers_la_box

vagrant box add --name optiDB-server ubuntu-16.04.box

git clone https://github.com/DaJaime/optiDB-server.git

vagrant up
```

## Autres commandes

```
# Démarrrer la VM
vagrant up

# Eteindre la VM
vagrant halt

# Supprimer la VM
vagrant destroy

# Se connecter en ssh à la VM
vagrant ssh

# Supprimer une box
vagrant box remove nomDeLaBox

```
