# DomusControl

Projeto desenvolvido no âmbito da unidade curricular de Programação Orientada aos Objetos (POO) — Universidade do Minho, 2025/2026.

## Descrição

O DomusControl é uma aplicação de simulação de automação doméstica inspirada em plataformas como o Home Assistant.

A aplicação permite:

* gestão de casas e divisões;
* criação e gestão de dispositivos inteligentes;
* controlo individual de dispositivos;
* criação de automações;
* criação de escalonamentos;
* execução de cenários;
* persistência de dados;
* geração de estatísticas de utilização.

O sistema foi desenvolvido segundo os princípios da Programação Orientada aos Objetos, utilizando encapsulamento, herança, polimorfismo e abstração.

---

## Funcionalidades Principais

### Gestão de Entidades

* Criação de utilizadores
* Criação de casas
* Criação de divisões
* Associação de dispositivos às divisões

### Dispositivos Implementados

* Lâmpadas
* Cortinas
* Ar condicionado
* Colunas de som
* Portões de garagem
* Tomadas inteligentes
* Sensores

### Automações

* Gatilhos temporais
* Gatilhos por sensores
* Ações automáticas sobre dispositivos

### Escalonamentos

* Operações programadas por horário

### Cenários

* Sair de Casa
* Jantar com Amigos
* Deitar
* Acordar

### Sugestões Inteligentes
* Deteção de padrões de utilização
* Sugestão automática de escalonamentos

### Estatísticas

* Casa com maior consumo
* Dispositivos mais utilizados
* Divisões com mais dispositivos

### Persistência

* Gravação e carregamento do estado da aplicação através de serialização binária

---

## Estrutura do Projeto

src/
├── app
├── model
├── service
├── persistence
├── ui
└── exception

---

## Execução

O projeto inclui um script `run.sh` que automatiza:
- limpeza de ficheiros `.class`;
- compilação completa do projeto;
- execução da aplicação;
- limpeza final após terminar a execução.

### Dar permissões ao script (apenas na primeira vez)

```bash
chmod +x run.sh
```

### Executar o projeto

```bash
./run.sh
```

---

## Persistência

A aplicação permite:

* guardar o estado completo do sistema em ficheiro binário;
* carregar estados previamente gravados;
* manter utilizadores, casas, dispositivos, automações, escalonamentos e interações entre execuções.

Foi incluído um ficheiro de demonstração para utilização durante a apresentação.

---

## Relatório

O relatório do projeto encontra-se incluído no repositório:

* `Relatório.pdf`

---

## Grupo

* Maria Fernandes da Silva — A106802
* Rafael Filipe Duarte de Andrade — A106918
* Tomás Branco Dias — A107323