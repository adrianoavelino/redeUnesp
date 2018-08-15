# RedeUnesp

- aplicar SOLID
- adicionar throws exceptions nos daos
- adicionar chave estrangeira na persistencia
- adicionar not null na prsistencia dos campos
- endereço da tabela rede deve ser único

# Mysql
drop database redeUnesp;create database redeUnesp;use redeUnesp;
mysqldump -u root -p redeUnesp --complete-insert > redeUnesp.sql
grep INSERT redeUnesp.sql #pega todos insert do arquivo

-Host
    - verificar nome duplicado
    - verificar mac-address duplicado
    - colocar mascara para mac-address
    - validar mac-address

- Rede
    - verificar endereco duplicado
    - adicionar mascara
    - verificar ip válido
    - atualizar os ips ao modificar a rede.Obs: o ip pega o endereço de rede para formar a rede) ou remover o editar ou guardar somente o ultimo octeto
    - corrigir bug ao deletar rede. Obs: o deletar prenche o campo endereço da rede

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

- Usuario
    - colocar mascara na matrícula

- Geral
    - remover css inline (style)
    - corrigir Doctype para html5
    - alterar selectOneMenu para a versão primefaces
    - adicionar validação jsf
