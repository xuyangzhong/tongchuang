-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: tongchuang
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `revert`
--

DROP TABLE IF EXISTS `revert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `parent_root` int(11) NOT NULL,
  `revert_id` int(11) NOT NULL DEFAULT '-1',
  `revertTime` datetime NOT NULL,
  `senderPK` varchar(45) NOT NULL,
  `receivePK` varchar(45) DEFAULT NULL,
  `isdelete` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revert`
--

LOCK TABLES `revert` WRITE;
/*!40000 ALTER TABLE `revert` DISABLE KEYS */;
INSERT INTO `revert` VALUES (1,1,'我不好！',0,-1,'2018-01-02 23:00:00','234',NULL,0),(2,1,'hahaha',0,-1,'2018-01-06 18:18:48','234',NULL,0),(3,1,'klkk',0,-1,'2018-01-11 08:34:03','123','234',0),(4,1,'12314萨达',0,-1,'2018-01-11 08:35:55','123','234',0),(5,1,'啊啊啊啊',0,-1,'2018-01-11 08:37:49','123','234',0),(6,1,'ssss',0,-1,'2018-01-11 09:34:14','123','234',0),(7,1,'aaaa',0,-1,'2018-01-11 09:34:26','123','234',0);
/*!40000 ALTER TABLE `revert` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-18  3:03:26
