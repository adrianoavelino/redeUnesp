# RedeUnesp

- aplicar SOLID
- adicionar throws exceptions nos daos
- adicionar chave estrangeira na persistencia
- adicionar not null na prsistencia dos campos
- endereço da tabela rede deve ser único

# Mysql
drop database redeUnesp;create database redeUnesp;use redeUnesp;

-Host
    - verificar nome duplicado
    - verificar mac-address duplicado
    - colocar mascara para mac-address
    - validar mac-address

- Rede
    - verificar endereco duplicado
    - adicionar mascara
    - verificar ip válido

- Vlan
    - validar campo número com converter - traduzir mensagem ou trocar por String
    - verificar numero duplicado
    - verificar a possibilidade somente numero
    - adicionar subrede no mesmo form

- Subrede
    - ordenar opções do h:selectOneMenu de máscara de rede
    - organizar tabela
    - otimizar eager das tabelas
    - remover métodos com parâmetros (getgateway ...)

- 

- Geral
    - remover css inline (style)
    - corrigir Doctype para html5
    - alterar selectOneMenu para a versão primefaces