#!/bin/bash

echo ">> Limpar .class antigos..."
find . -name "*.class" -delete

echo ">> Compilar projeto..."
find . -name "*.java" > sources.txt
javac -encoding UTF-8 @sources.txt

if [ $? -eq 0 ]; then
    echo ">> Execução..."
    java pt.domuscontrol.app.Main

    echo ""
    echo ">> Limpeza final..."
    find . -name "*.class" -delete
    rm -f sources.txt

    echo ">> Projeto limpo."
else
    echo ">> Erros de compilação."
fi