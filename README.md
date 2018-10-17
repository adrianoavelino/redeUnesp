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
select concat(r.endereco, ".", i.enderecoIp) as teste from ip i inner join rede r on r.rede_id = i.rede_id; //resolve problema de atualizar rede
select * from ip i left outer join host h on  h.ip_ip_id = i.ip_id left outer join usuario u on h.id_usuario = u.id limit 10; //remover bidirecional de ip

- TipoHost
    - verificar TipoHost utilizado antes de deletar

-Host
    - colocar mascara para mac-address?
    - validar mac-address
    - verificar os if no método editar do controller

- Rede
    - adicionar mascara
    - verificar ip válido
    - otimizar performance devido a adição da classe TipoEndereco

- GrupoRede

- Vlan
    - validar campo número com converter - traduzir mensagem ou trocar por String
    - verificar a possibilidade somente numero
    - adicionar subrede no mesmo form?

- Subrede
    - ordenar opções do h:selectOneMenu de máscara de rede
    - organizar tabela
    - otimizar eager das tabelas
    - remover métodos com parâmetros (getgateway ...)

- Usuario
    - colocar mascara na matrícula?
    - verificar se usuário está associado algum host antes de deletar

- IP
    - renomear para IPV4?

- IPV6
    - ordenar listas de ipv6

- Home

- Geral
    - remover css inline (style)
    - corrigir Doctype para html5
    - alterar selectOneMenu para a versão primefaces
    - adicionar validação jsf
    - remover relacionamento bidirecional da classe IP
    - melhorar/criar interface de  validação (SOLID)
    - adicionar teste E2E
    - adicionar TDD
    - adicionar teste de integração
    - verificar o uso de interface para ip
    - verificar exceptions dos daos