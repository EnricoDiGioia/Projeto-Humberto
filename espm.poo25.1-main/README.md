# espm.poo25.1

Exemplo de Hello World!
```java
package poo.aula01;

public class Hello {

    public static void main(String[] args) {
        System.out.println("Ola Mundo!");
    }
    
}
```


## Docker

Baixe o Docker Desktop no site oficial: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

Docker é um gerenciador de containers, onde cada containers é uma emulação de uma máquina virtual, porém mais leve e mais rápido.

Exemplo de Docker file:
```Dockerfile
FROM ubuntu:latest
RUN apt-get update
RUN apt-get install -y iputils-ping
CMD ["tail", "-f", "/dev/null"]
```

### Comandos do terminal

Para criar uma imagem a partir de um Dockerfile, utilize o comando:
```bash
docker build -t espm-ubuntu .
```

Para executar um container a partir de uma imagem, utilize o comando:
```bash
docker run espm-ubuntu
```

Para trabalhar com cluster de containers, utilize o Docker Compose. Exemplo de arquivo compose.yaml:
```yaml
name: espm-store

services:

  db:
    image: postgres:latest
```

Para subir o cluster de containers, utilize o comando:
```bash
docker compose up -d
````

Para parar o cluster de containers, utilize o comando:
```bash
docker compose down
```

### Exercício Selmini 1

Identifique os objetos no problema abaixo:

- Um cinema pode ter muitas salas, sendo necessário, portanto, registrar informações a respeito de cada sala, como sua capacidade (número de lugares disponíveis).

- O cinema apresenta vários filmes. Um filme tem informações como título e duração. Sempre que um filme for adquirido deverá ser registrado;

- Um filme pode ter vários atores.

``` mermaid
classDiagram
    class Sala {
        - String nome
        - int capacidade
        + abrir()
        + fechar()
    }
    class Filme {
        - String titulo
        - int duracao
        - String classificacao
        - Genero genero
        - List< Ator> atores
        + registrar() Filme
    }
    class Genero {
        - String nome
        + registrar() Genero
    }
    class Ator {
        - String nome
        - Date dtNascimento
    }
    class Sessao {
        - Filme Filme
        - DateTime datetime
        - Sala sala
    }
    Filme <|-- Genero
    Filme <|-- Ator
    Sessao <|-- Filme
    Sessao <|-- Sala
```


### Exercício Selmini 2

Identifique os objetos no problema abaixo:

- Um clube tem muitos sócios e precisa manter informações referente a eles, como o número
do seu cartão de sócio, endereço, telefone e e-mail.

- Um sócio pode ter nenhum ou vários dependentes.

- Um sócio deve pagar mensalidades para poder frequentar o clube. Serão cobrados juros sobre o valor da mensalidade relativos ao atraso do pagamento. As informações pertinentes a cada mensalidade são a data de pagamento, o valor, a data em que foi efetivamente paga e juros aplicados.


### Diagrama de Classes do Banco

``` mermaid
classDiagram
    class Conta {
        - String id
        # double saldo
        - Cliente cliente
        + sacar(double valor)
        + depositar(double valor)
    }
    class Cliente {
        - String id
        - String nome
        - List<Conta> contas
    }
    class PessoaFisica {
        - String cpf
    }
    class PessoaJuridica {
        - String cnpj
    }
    class ContaCorrente {
        - double limite
        + sacar(double valor)
    }
    class ContaPoupanca {
        + sacar(double valor)
    }
    Conta *-- Cliente
    Conta <|-- ContaCorrente
    Conta <|-- ContaPoupanca
    Cliente <|-- PessoaFisica
    Cliente <|-- PessoaJuridica
```