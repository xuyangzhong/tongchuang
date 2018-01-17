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
-- Table structure for table `province_detail`
--

DROP TABLE IF EXISTS `province_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provin_id` int(11) NOT NULL,
  `introduce` text,
  `scenic` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province_detail`
--

LOCK TABLES `province_detail` WRITE;
/*!40000 ALTER TABLE `province_detail` DISABLE KEYS */;
INSERT INTO `province_detail` VALUES (1,1,'北京是首批国家历史文化名城和世界上拥有世界文化遗产数最多的城市，三千多年的历史孕育了故宫、天坛、八达岭长城、颐和园等众多名胜古迹。早在七十万年前，北京周口店地区就出现了原始人群部落“北京人”。公元前1045年，北京成为蓟、燕等诸侯国的都城。公元938年以来，北京先后成为辽陪都、金中都、元大都、明、清国都。1949年10月1日成为中华人民共和国首都。\n北京被全球最权威的世界城市研究机构之一GaWC评为世界一线城市 。联合国报告指出，北京人类发展指数居中国城市第二位  。2016年，北京人均可支配收入达到52530元，社会消费品零售总额11005.1亿元  。截至2016年末，北京常住人口2172.9万人，比上年末增加2.4万人，增长0.1%，增量比上年减少16.5万人，增速比上年回落0.8个百分点  。','长城、故宫博物馆、天安门、颐和园'),(2,2,'aaaaa','cccc');
/*!40000 ALTER TABLE `province_detail` ENABLE KEYS */;
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
