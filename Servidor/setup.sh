#!/bin/bash
# Refs: 
# - http://www.ejbtutorial.com/java-rmi/new-easy-tutorial-for-java-rmi-using-eclipse
# - https://docs.oracle.com/javase/8/docs/technotes/tools/unix/rmiregistry.html

cd bin

echo "[+] Compilando o stub do servidor.."
rmic network.Servidor # Compila o stub

echo "[+] Copiando o arquivo stub para a pasta do programa cliente..."
cp network/Servidor_Stub.class /home/franz/UEFS/2017.1/MI/Problema4/Decolagem/Cliente/bin/network

echo "[+] Iniciando registro de objeto remoto:"
rmiregistry # Inicia o registro do objeto remoto no endereco default
