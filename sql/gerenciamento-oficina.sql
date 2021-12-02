-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: gerenciamento-oficina
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cod_cliente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf_cnpj` varchar(15) DEFAULT NULL,
  `uf` varchar(2) NOT NULL,
  `num_contato` varchar(30) NOT NULL,
  `endereco` varchar(50) NOT NULL,
  PRIMARY KEY (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'teste3','12345','SC','123','rua 1'),(2,'teste2','1234','RS','1234','rua 2'),(4,'Leonardo Sakaguti','57465464','SC','475555555','teste teste teste'),(16,'teste','069.614.669-07','SC','123','bbbb');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_produto`
--

DROP TABLE IF EXISTS `itens_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_produto` (
  `cod_ordem` int NOT NULL,
  `qtde` int DEFAULT NULL,
  `cod_prod` int DEFAULT NULL,
  `vlr_unit` double DEFAULT NULL,
  KEY `cod_ordem_idx` (`cod_ordem`),
  KEY `cod_prod` (`cod_prod`),
  CONSTRAINT `cod_ordem_produto` FOREIGN KEY (`cod_ordem`) REFERENCES `ordem_servico` (`cod_ordem`),
  CONSTRAINT `itens_produto_ibfk_1` FOREIGN KEY (`cod_prod`) REFERENCES `produto` (`cod_prod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_produto`
--

LOCK TABLES `itens_produto` WRITE;
/*!40000 ALTER TABLE `itens_produto` DISABLE KEYS */;
INSERT INTO `itens_produto` VALUES (2,5,1,NULL),(2,2,2,NULL),(3,10,2,1.5);
/*!40000 ALTER TABLE `itens_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_servico`
--

DROP TABLE IF EXISTS `itens_servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_servico` (
  `cod_servico` int NOT NULL,
  `cod_ordem` int NOT NULL,
  `qtde` int DEFAULT NULL,
  `vlr_servico` double DEFAULT NULL,
  KEY `cod_servico_idx` (`cod_servico`),
  KEY `cod_ordem_idx` (`cod_ordem`),
  CONSTRAINT `cod_ordem` FOREIGN KEY (`cod_ordem`) REFERENCES `ordem_servico` (`cod_ordem`),
  CONSTRAINT `cod_servico` FOREIGN KEY (`cod_servico`) REFERENCES `servico` (`cod_servico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_servico`
--

LOCK TABLES `itens_servico` WRITE;
/*!40000 ALTER TABLE `itens_servico` DISABLE KEYS */;
INSERT INTO `itens_servico` VALUES (1,2,4,5);
/*!40000 ALTER TABLE `itens_servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordem_servico`
--

DROP TABLE IF EXISTS `ordem_servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordem_servico` (
  `cod_ordem` int NOT NULL AUTO_INCREMENT,
  `cod_usuario` int NOT NULL,
  `cod_veiculo` int NOT NULL,
  `status_ordem` int NOT NULL,
  `data_emissao` date NOT NULL,
  PRIMARY KEY (`cod_ordem`),
  KEY `cod_usuario_idx` (`cod_usuario`),
  KEY `cod_veiculo_idx` (`cod_veiculo`),
  CONSTRAINT `cod_usuario` FOREIGN KEY (`cod_usuario`) REFERENCES `usuario` (`cod_usuario`),
  CONSTRAINT `cod_veiculo` FOREIGN KEY (`cod_veiculo`) REFERENCES `veiculo` (`cod_veiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordem_servico`
--

LOCK TABLES `ordem_servico` WRITE;
/*!40000 ALTER TABLE `ordem_servico` DISABLE KEYS */;
INSERT INTO `ordem_servico` VALUES (2,1,2,1,'2021-10-12'),(3,1,4,0,'2021-10-12');
/*!40000 ALTER TABLE `ordem_servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `cod_prod` int NOT NULL AUTO_INCREMENT,
  `vlr_unit` double DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  `fornecedor` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`cod_prod`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,24,'testeeee','teste'),(2,1.5,'teste2','teste');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico`
--

DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servico` (
  `cod_servico` int NOT NULL AUTO_INCREMENT,
  `descricao_servico` varchar(50) NOT NULL,
  `valor_servico` double DEFAULT NULL,
  PRIMARY KEY (`cod_servico`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico`
--

LOCK TABLES `servico` WRITE;
/*!40000 ALTER TABLE `servico` DISABLE KEYS */;
INSERT INTO `servico` VALUES (1,'teste',5);
/*!40000 ALTER TABLE `servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `cod_usuario` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `senha` varchar(256) NOT NULL,
  `nome` varchar(15) NOT NULL,
  `is_admin` int DEFAULT NULL,
  `logado` int DEFAULT NULL,
  PRIMARY KEY (`cod_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'teste','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','teste',1,0),(5,'teste2','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','teste2',1,0),(6,'teste4','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','testeee',1,0),(7,'naoadmin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','naoadmin',0,0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veiculo` (
  `cod_veiculo` int NOT NULL AUTO_INCREMENT,
  `cod_cliente` int NOT NULL,
  `placa_carro` varchar(7) NOT NULL,
  `cor_carro` varchar(30) NOT NULL,
  `marca_modelo` varchar(30) NOT NULL,
  `ano` int NOT NULL,
  `nome_cliente` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cod_veiculo`),
  KEY `cod_cliente_idx` (`cod_cliente`),
  CONSTRAINT `cod_cliente` FOREIGN KEY (`cod_cliente`) REFERENCES `cliente` (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veiculo`
--

LOCK TABLES `veiculo` WRITE;
/*!40000 ALTER TABLE `veiculo` DISABLE KEYS */;
INSERT INTO `veiculo` VALUES (2,1,'abcd','Vermelho','Fiat Palio',2018,'teste3'),(3,1,'bbbbb','Preto','Ford Fusion',2019,'teste3'),(4,4,'454ASDD','Branco','Volkswagen Fox',2013,'Leonardo Sakaguti');
/*!40000 ALTER TABLE `veiculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-01 20:23:47
