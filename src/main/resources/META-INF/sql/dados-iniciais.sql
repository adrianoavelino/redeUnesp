INSERT INTO `grupoRede` VALUES (1,'redeUnesp');
INSERT INTO `vlan` VALUES (1,'telefonia',2,1),(2,'docentes',180,1);
INSERT INTO `rede` VALUES (1,'200.145.44');
INSERT INTO `subrede` VALUES (1,'255.255.255.192',1,1),(2,'255.255.255.192',1,2);

INSERT INTO `usuario` VALUES (1,'312312','adriano'),(3,'3219522','carlos');
INSERT INTO `tipo_host` (`id_tipo_host`, `tipo`) VALUES (1,'computador'),(2,'servidor'),(3,'switch');
INSERT INTO `host` (`id_host`, `macAddres`, `nome`, `id_tipo_host`, `id_usuario`) VALUES (1,'12:ee;ff','lee',1,3),(2,'ee:oo','stay',1,1);
