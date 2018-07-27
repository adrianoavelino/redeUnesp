[1mdiff --git a/README.md b/README.md[m
[1mindex 7649ac9..29f8b0b 100644[m
[1m--- a/README.md[m
[1m+++ b/README.md[m
[36m@@ -18,4 +18,9 @@[m [mdrop database redeUnesp;create database redeUnesp;use redeUnesp;[m
 - Rede[m
     - verificar endereco duplicado[m
     - adicionar mascara[m
[31m-    - verificar ip válido     [m
\ No newline at end of file[m
[32m+[m[32m    - verificar ip válido[m
[32m+[m
[32m+[m[32m- Vlan[m
[32m+[m[32m    - verificar numero duplicado[m
[32m+[m[32m    - verificar a possibilidade somente numero[m
[32m+[m[32m    - adicionar subrede no mesmo form[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/br/com/unesp/controller/VlanController.java b/src/main/java/br/com/unesp/controller/VlanController.java[m
[1mindex f6406c8..95541dd 100644[m
[1m--- a/src/main/java/br/com/unesp/controller/VlanController.java[m
[1m+++ b/src/main/java/br/com/unesp/controller/VlanController.java[m
[36m@@ -1,17 +1,25 @@[m
 package br.com.unesp.controller;[m
 [m
 import br.com.unesp.dao.VlanDao;[m
[32m+[m[32mimport br.com.unesp.jsf.message.FacesMessages;[m
 import br.com.unesp.model.GrupoRede;[m
 import br.com.unesp.model.Vlan;[m
[31m-import javax.ejb.EJB;[m
[32m+[m[32mimport java.util.List;[m
 import javax.enterprise.context.RequestScoped;[m
[32m+[m[32mimport javax.faces.event.ActionEvent;[m
[32m+[m[32mimport javax.inject.Inject;[m
 import javax.inject.Named;[m
 [m
 @Named[m
 @RequestScoped[m
 public class VlanController {[m
[31m-    @EJB[m
[32m+[m
[32m+[m[32m    @Inject[m
     private VlanDao dao;[m
[32m+[m[32m    @Inject[m
[32m+[m[32m    private FacesMessages message;[m
[32m+[m[32m    private Vlan vlan = new Vlan();[m
[32m+[m[32m    private List<Vlan> listaVlan;[m
 [m
     public VlanController() {[m
     }[m
[36m@@ -23,15 +31,62 @@[m [mpublic class VlanController {[m
     public void setDao(VlanDao dao) {[m
         this.dao = dao;[m
     }[m
[31m-    [m
[31m-    public void salvar() {[m
[32m+[m
[32m+[m[32m    public Vlan getVlan() {[m
[32m+[m[32m        return vlan;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setVlan(Vlan vlan) {[m
[32m+[m[32m        this.vlan = vlan;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public List<Vlan> getListaVlan() {[m
[32m+[m[32m        return listaVlan;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setListaVlan(List<Vlan> listaVlan) {[m
[32m+[m[32m        this.listaVlan = listaVlan;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void salvar2() {[m
         GrupoRede grupoRede = new GrupoRede();[m
         grupoRede.setId(1);[m
[31m-        Vlan vlan = new Vlan(2,"telefonia", grupoRede);[m
[31m-        this.dao.salvar(vlan);[m
[32m+[m[32m        Vlan vlanLocal = new Vlan(2, "telefonia", grupoRede);[m
[32m+[m[32m        this.dao.salvar(vlanLocal);[m
         System.out.println("Salvando vlan ...");[m
     }[m
[31m-    [m
[31m-    [m
[31m-    [m
[32m+[m
[32m+[m[32m    public void salvar() {[m
[32m+[m[32m        if (vlan.getId() == null) {[m
[32m+[m[32m            dao.salvar(this.vlan);[m
[32m+[m[32m            System.out.println("==========Salva Vlan" + vlan);[m
[32m+[m[32m            vlan = new Vlan();[m
[32m+[m[32m            message.info("Cadastro realizado com sucesso!");[m
[32m+[m[32m        } else {[m
[32m+[m[32m            try {[m
[32m+[m[32m                System.out.println("Altera Vlan" + vlan);[m
[32m+[m[32m                dao.atualizar(vlan);[m
[32m+[m[32m                vlan = new Vlan();[m
[32m+[m[32m                message.info("Alterado com sucesso!");[m
[32m+[m[32m            } catch (Exception ex) {[m
[32m+[m[32m                message.error("Erro ao alterar vlan");[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void deletar(ActionEvent evento) {[m
[32m+[m[32m        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");[m
[32m+[m[32m        try {[m
[32m+[m[32m            dao.deletar(vlan);[m
[32m+[m[32m            message.info("Deletada com sucesso!");[m
[32m+[m[32m        } catch (Exception ex) {[m
[32m+[m[32m            message.error("Erro ao deletar vlan");[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void editar(ActionEvent evento) {[m
[32m+[m[32m        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");[m
[32m+[m[32m        System.out.println("=========== Editar ");[m
[32m+[m[32m    }[m
[32m+[m
 }[m
[1mdiff --git a/src/main/java/br/com/unesp/dao/VlanDao.java b/src/main/java/br/com/unesp/dao/VlanDao.java[m
[1mindex dc786de..aaa49e9 100644[m
[1m--- a/src/main/java/br/com/unesp/dao/VlanDao.java[m
[1m+++ b/src/main/java/br/com/unesp/dao/VlanDao.java[m
[36m@@ -1,9 +1,11 @@[m
 package br.com.unesp.dao;[m
 [m
 import br.com.unesp.model.Vlan;[m
[32m+[m[32mimport java.util.List;[m
 import javax.ejb.Stateless;[m
 import javax.persistence.EntityManager;[m
 import javax.persistence.PersistenceContext;[m
[32m+[m[32mimport javax.persistence.Query;[m
 [m
 @Stateless[m
 public class VlanDao {[m
[36m@@ -21,5 +23,23 @@[m [mpublic class VlanDao {[m
     public void salvar(Vlan vlan) {[m
         this.em.persist(vlan);[m
     }[m
[32m+[m
[32m+[m[32m    public List<Vlan> listar() throws Exception {[m
[32m+[m[32m        Query query = this.em.createQuery("from vlan v");[m
[32m+[m[32m        List<Vlan> vlans = query.getResultList();[m
[32m+[m[32m        return vlans;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void atualizar(Vlan vlan) throws Exception {[m
[32m+[m[32m        Vlan vlanModificada = em.find(Vlan.class, vlan.getId());[m
[32m+[m[32m        vlanModificada.setDescricao(vlan.getDescricao());[m
[32m+[m[32m        vlanModificada.setNumero(vlan.getNumero());[m
[32m+[m[32m        vlanModificada.setGrupoRede(vlan.getGrupoRede());[m
[32m+[m[32m        em.merge(vlan);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void deletar(Vlan vlan) throws Exception {[m
[32m+[m[32m        this.em.remove(this.em.merge(vlan));[m
[32m+[m[32m    }[m
     [m
 }[m
[1mdiff --git a/src/main/java/br/com/unesp/model/Vlan.java b/src/main/java/br/com/unesp/model/Vlan.java[m
[1mindex 680780f..1146a39 100644[m
[1m--- a/src/main/java/br/com/unesp/model/Vlan.java[m
[1m+++ b/src/main/java/br/com/unesp/model/Vlan.java[m
[36m@@ -5,20 +5,29 @@[m [mimport java.util.Objects;[m
 import java.util.Set;[m
 import javax.persistence.Column;[m
 import javax.persistence.Entity;[m
[32m+[m[32mimport javax.persistence.GeneratedValue;[m
[32m+[m[32mimport javax.persistence.GenerationType;[m
 import javax.persistence.Id;[m
 import javax.persistence.JoinColumn;[m
 import javax.persistence.OneToMany;[m
 import javax.persistence.OneToOne;[m
[32m+[m[32mimport javax.validation.constraints.NotNull;[m
[32m+[m[32mimport org.hibernate.validator.constraints.NotBlank;[m
 [m
 @Entity(name = "vlan")[m
 public class Vlan implements Serializable {[m
 [m
     @Id[m
[32m+[m[32m    @GeneratedValue(strategy = GenerationType.IDENTITY)[m
     @Column(name = "id_vlan")[m
     private Integer id;[m
[32m+[m[32m    @NotNull(message = "O campo número está vazio")[m
[32m+[m[32m    private Integer numero;[m
[32m+[m[32m    @NotBlank(message = "O campo Descrição está em branco")[m
     private String descricao;[m
     @OneToOne[m
     @JoinColumn(name = "id_grupoRede")[m
[32m+[m[32m    @NotNull(message = "Selecione um Grupo de Rede")[m
     private GrupoRede grupoRede;[m
 [m
     @OneToMany[m
[36m@@ -50,6 +59,16 @@[m [mpublic class Vlan implements Serializable {[m
         this.id = id;[m
     }[m
 [m
[32m+[m[32m    public Integer getNumero() {[m
[32m+[m[32m        return numero;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setNumero(Integer numero) {[m
[32m+[m[32m        this.numero = numero;[m
[32m+[m[32m    }[m
[32m+[m[41m    [m
[32m+[m[41m    [m
[32m+[m
     public String getDescricao() {[m
         return descricao;[m
     }[m
[36m@@ -93,7 +112,7 @@[m [mpublic class Vlan implements Serializable {[m
 [m
     @Override[m
     public String toString() {[m
[31m-        return "Vlan{" + "id=" + id + ", descricao=" + descricao + ", grupoRede=" + grupoRede + '}';[m
[32m+[m[32m        return "Vlan{" + "id=" + id + ", numero=" + numero + ", descricao=" + descricao + ", grupoRede=" + grupoRede + ", listaSubrede=" + listaSubrede + '}';[m
     }[m
 [m
 }[m
[1mdiff --git a/src/main/webapp/app/host.xhtml b/src/main/webapp/app/host.xhtml[m
[1mindex a6b5e25..70b7be0 100644[m
[1m--- a/src/main/webapp/app/host.xhtml[m
[1m+++ b/src/main/webapp/app/host.xhtml[m
[36m@@ -12,7 +12,9 @@[m
             <h:inputHidden value="#{hostController.host.id}" />[m
             <h:panelGrid columns="2">[m
                 <p:outputLabel value="Nome:" for="nome" />[m
[31m-                <p:inputText id="nome" value="#{hostController.host.nome}" />[m
[32m+[m[32m                <p:inputText id="nome" value="#{hostController.host.nome}">[m
[32m+[m[32m                    <f:convertNumber type="number" />[m
[32m+[m[32m                </p:inputText>[m
                 <p:outputLabel value="Mac-addres:" for="macAddress" />[m
                 <p:inputText id="macAddress" value="#{hostController.host.macAddres}" />[m
                 <p:outputLabel value="Tipo do host:" />[m
[1mdiff --git a/src/main/webapp/app/vlan.xhtml b/src/main/webapp/app/vlan.xhtml[m
[1mindex d202a5c..a601dca 100644[m
[1m--- a/src/main/webapp/app/vlan.xhtml[m
[1m+++ b/src/main/webapp/app/vlan.xhtml[m
[36m@@ -7,7 +7,73 @@[m
                 xmlns:ui="http://xmlns.jcp.org/jsf/facelets"[m
                 xmlns:p="http://primefaces.org/ui">[m
     <ui:define name="dados">[m
[31m-        vlan[m
[32m+[m[32m        <h:form id="vlan-cadastro">[m
[32m+[m[32m            <p:messages />[m
[32m+[m[32m            <h:inputHidden value="#{vlanController.vlan.id}" />[m
[32m+[m[32m            <h:panelGrid columns="2">[m
[32m+[m[32m                <p:outputLabel value="Número da Vlan:" for="numeroVlan" />[m
[32m+[m[32m                <p:inputText id="numeroVlan" value="#{vlanController.vlan.numero}">[m
[32m+[m[32m                    <f:convertNumber type="number" />[m
[32m+[m[32m                </p:inputText>[m
[32m+[m[32m                <p:outputLabel value="Descrição:" for="descricao" />[m
[32m+[m[32m                <p:inputText id="descricao" value="#{vlanController.vlan.descricao}" />[m
[32m+[m[32m                <p:outputLabel value="Grupo de Rede:" />[m
[32m+[m[32m                <h:selectOneMenu id="grupoRede"[m
[32m+[m[32m                                 converter="grupoRedeConverter"[m
[32m+[m[32m                                 value="#{vlanController.vlan.grupoRede}">[m
[32m+[m[32m                    <f:selectItem itemLabel="Selecione um Grupo de Rede" />[m
[32m+[m[32m                    <f:selectItems value="#{grupoRedeController.dao.listar()}"[m
[32m+[m[32m                                   var="grupo"[m
[32m+[m[32m                                   itemLabel="#{grupo.nome}"[m
[32m+[m[32m                                   itemValue="#{grupo}" />[m
[32m+[m[32m                </h:selectOneMenu>[m
[32m+[m[32m                <p:outputLabel />[m
[32m+[m[32m                <p:commandButton value="Salvar"[m[41m [m
[32m+[m[32m                                 action="#{vlanController.salvar()}"[m[41m [m
[32m+[m[32m                                 update="vlan-cadastro :vlan-tabela" />[m
[32m+[m[32m            </h:panelGrid>[m
[32m+[m[32m        </h:form>[m
[32m+[m[32m        <h:form id="vlan-tabela">[m
[32m+[m[32m            <p:dataTable value="#{vlanController.dao.listar()}" var="vlan" style="width: 900px">[m
[32m+[m[32m                <p:column headerText="Id">[m
[32m+[m[32m                    <h:outputText value="#{vlan.id}" />[m
[32m+[m[32m                </p:column>[m
[32m+[m[32m                <p:column headerText="Número da Vlan">[m
[32m+[m[32m                    <h:outputText value="#{vlan.numero}" />[m
[32m+[m[32m                </p:column>[m
[32m+[m[32m                <p:column headerText="Descrição">[m
[32m+[m[32m                    <h:outputText value="#{vlan.descricao}" />[m
[32m+[m[32m                </p:column>[m
[32m+[m[32m                <p:column headerText="Grupo de Rede">[m
[32m+[m[32m                    <h:outputText value="#{vlan.grupoRede.nome}" />[m
[32m+[m[32m                </p:column>[m
[32m+[m[32m                <p:column headerText="Opções">[m
[32m+[m[32m                    <p:commandButton icon="ui-icon-trash"[m[41m [m
[32m+[m[32m                                     actionListener="#{vlanController.deletar}"[m
[32m+[m[32m                                     update=":vlan-tabela"[m
[32m+[m[32m                                     immediate="true">[m
[32m+[m[32m                        <p:confirm header="Confirmação" message="Tem certeza que quer excluir o Host?"[m
[32m+[m[32m                                   icon="ui-icon-alert" />[m
[32m+[m[32m                        <p:resetInput target=":vlan-cadastro" />[m
[32m+[m[32m                        <f:attribute name="vlanSelecionada" value="#{vlan}" />[m
[32m+[m[32m                    </p:commandButton>[m
[32m+[m[32m                    <p:commandButton icon="ui-icon-pencil"[m[41m [m
[32m+[m[32m                                     actionListener="#{vlanController.editar}"[m[41m [m
[32m+[m[32m                                     update=":vlan-cadastro"[m[41m [m
[32m+[m[32m                                     immediate="true">[m
[32m+[m[32m                        <p:resetInput target=":vlan-cadastro" />[m
[32m+[m[32m                        <f:attribute name="vlanSelecionada" value="#{vlan}" />[m
[32m+[m[32m                    </p:commandButton>[m
[32m+[m[32m                </p:column>[m
[32m+[m[32m            </p:dataTable>[m
[32m+[m[32m            <p:confirmDialog global="true">[m
[32m+[m[32m                <p:commandButton value="Não" type="button"[m
[32m+[m[32m                                 styleClass="ui-confirmdialog-no" icon="ui-icon-close" />[m
[32m+[m[32m                <p:commandButton value="Sim" type="button"[m
[32m+[m[32m                                 styleClass="ui-confirmdialog-yes" icon="ui-icon-check" />[m
[32m+[m[32m            </p:confirmDialog>[m[41m  [m
[32m+[m[32m        </h:form>[m
[32m+[m
     </ui:define>[m
 </ui:composition>[m
 [m
