-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
drop database sugestoes;
create database sugestoes;
use sugestoes;
-- Host: localhost    Database: sugestoes
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comentarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) DEFAULT NULL,
  `idSugestao` int(11) DEFAULT NULL,
  `comentario` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentarios`
--

LOCK TABLES `comentarios` WRITE;
/*!40000 ALTER TABLE `comentarios` DISABLE KEYS */;
INSERT INTO `comentarios` VALUES (13,21,2,'Concordo totalmente, temos o mesmo problema no quarto andar.'),(14,20,6,'Também estou com o mesmo problema. Poderiam me indicar como posso resolver isso?'),(18,18,1,'boa sugestão!');
/*!40000 ALTER TABLE `comentarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidade`
--

DROP TABLE IF EXISTS `especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `especialidade` (
  `idEspecialidade` int(11) NOT NULL AUTO_INCREMENT,
  `nomeEspecialidade` varchar(100) NOT NULL,
  `corEspecialidade` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idEspecialidade`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidade`
--

LOCK TABLES `especialidade` WRITE;
/*!40000 ALTER TABLE `especialidade` DISABLE KEYS */;
INSERT INTO `especialidade` VALUES (1,'Financeiro','#00bf93'),(2,'TI','#005ebf'),(3,'Segurança','#7600bf'),(4,'Administração','#00e6e6'),(5,'Limpeza','#e0e0e0');
/*!40000 ALTER TABLE `especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugestao`
--

DROP TABLE IF EXISTS `sugestao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugestao` (
  `idSugestao` int(11) NOT NULL AUTO_INCREMENT,
  `Colaborador` int(11) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `Sugestao` text,
  `Avaliador` int(11) DEFAULT NULL,
  `Especialidade` int(11) DEFAULT NULL,
  `Positivos` int(11) DEFAULT NULL,
  `Negativos` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `Data` datetime NOT NULL,
  `FeedBack` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idSugestao`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugestao`
--

LOCK TABLES `sugestao` WRITE;
/*!40000 ALTER TABLE `sugestao` DISABLE KEYS */;
INSERT INTO `sugestao` VALUES (1,1,'Qualquer um pode entrar na universidade','Por falta de um sistema de validação de alunos, qualquer pessoal pode entrar na faculdade hoje. O que se torna muito perioso. Recomendo um sistema de catraca aonde somente quem tiver a carterinha da faculdade poderá entrar.',NULL,3,NULL,NULL,'ativo','2016-11-06 18:59:29',NULL),(2,1,'O wi-fi não funciona no segundo andar da faculdade.','A utilização da internet via wi-fi no segundo andar da universidade é muito ruim. Não conseguimos acessar e quando conseguimos a navegação é muito ruim. Acredito que disponibilizar um roteador para cada andar seja mais adequado para o melhor uso do recurso.',NULL,2,NULL,NULL,'ativo','2016-11-06 00:00:00',NULL),(4,18,'Impossivel de usar os banheiros','A cada dia os banheiros estão mais sujos. Em alguns dias a utilização fica impossível. Acredito que seria muito bom se o mesmo fosse limpado mais de uma vez por dia, devido ao número alto de pessoas que utilizam.',NULL,5,NULL,NULL,'recusado','2016-11-06 00:00:00','Infelizmente não temos essa possibilidade no momento. Hoje fazemos 3 turnos de limpeza em todos os banheiros na univesidade, um de manhã um a tarde e um a noite.'),(5,1,'Dificuldade para baixar os boletos do SOL','Estamos tendo dificuldades para baixar os boletos via sistema SOL. Acredito que seria mais interessante se todo dia primeiro, os boletos fossem encaminhados para os e-mails dos alunos.',NULL,1,NULL,NULL,'ativo','2016-11-06 19:35:43',NULL),(6,15,'Dificuldade para entrega dos documentos.','Fiquei devendo alguns documentos no momento da matrícula e não estou conseguindo informações sobre como devo fazer para entregar os documentos. Me parece recomendavel um novo treinamento da equipe do CAA.',NULL,4,NULL,NULL,'ativo','2016-11-13 19:50:36',null);
/*!40000 ALTER TABLE `sugestao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(250) NOT NULL,
  `curso` varchar(100) DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  `Email` varchar(250) NOT NULL,
  `cpf` varchar(30) NOT NULL,
  `tipo` int(11) DEFAULT NULL,
  `idEspecialidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (10,'Lucas dos Reis',NULL,'c4ca4238a0b923820dcc509a6f75849b','adm@adm.com','38242227871',3,NULL),(12,'Bruno Borges',NULL,'c4ca4238a0b923820dcc509a6f75849b','limpeza@adm.com','10507058313',2,5),(13,'Thiago Carpes',NULL,'c4ca4238a0b923820dcc509a6f75849b','administracao@adm.com','14267592314',2,4),(15,'Ray Kennedy',NULL,'c4ca4238a0b923820dcc509a6f75849b','ti@adm.com','38242227870',2,2), (16,'Vinicius Fernando',NULL,'c4ca4238a0b923820dcc509a6f75849b','financeiro@adm.com','38242227870',2,1), (17,'Lucas dos Reis',NULL,'c4ca4238a0b923820dcc509a6f75849b','seguranca@adm.com','38242227870',2,3), (18,'Marcos Antônio',NULL,'c4ca4238a0b923820dcc509a6f75849b','marcosa@gmail.com','38242227870',1,null), (20,'Carlos Eduardo',NULL,'c4ca4238a0b923820dcc509a6f75849b','caedu@gmail.com','38242227870',1,null), (21,'Marcos Paulo',NULL,'c4ca4238a0b923820dcc509a6f75849b','mpaulo@gmail.com','38242227870',1,null);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-23  2:08:07
