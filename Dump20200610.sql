CREATE DATABASE  IF NOT EXISTS `WEB_SCRAPPING` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `WEB_SCRAPPING`;
-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: WEB_SCRAPPING
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.22-MariaDB-1:10.3.22+maria~bionic

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
-- Table structure for table `SCHEDULE`
--

DROP TABLE IF EXISTS `SCHEDULE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCHEDULE` (
  `SCHEDULE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCHEDULE_NAME` varchar(50) DEFAULT NULL,
  `CRON_EXPRESSION` varchar(50) DEFAULT NULL,
  `METHOD_NAME` varchar(50) DEFAULT NULL,
  `PROJECT_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`SCHEDULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCHEDULE`
--

LOCK TABLES `SCHEDULE` WRITE;
/*!40000 ALTER TABLE `SCHEDULE` DISABLE KEYS */;
INSERT INTO `SCHEDULE` VALUES (1,'every 1 minute','0 0/1 * 1/1 * ?','start','project-final-start-bot');
/*!40000 ALTER TABLE `SCHEDULE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SWITCH_DATABASE`
--

DROP TABLE IF EXISTS `SWITCH_DATABASE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SWITCH_DATABASE` (
  `DATABASE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DATABASE_NAME` varchar(250) DEFAULT NULL,
  `DATABASE_STATUS` char(1) DEFAULT NULL,
  PRIMARY KEY (`DATABASE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SWITCH_DATABASE`
--

LOCK TABLES `SWITCH_DATABASE` WRITE;
/*!40000 ALTER TABLE `SWITCH_DATABASE` DISABLE KEYS */;
INSERT INTO `SWITCH_DATABASE` VALUES (1,'web-scrapping-db-1','1'),(2,'web-scrapping-db-2','0');
/*!40000 ALTER TABLE `SWITCH_DATABASE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `USER_ID` int(20) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES (1,'$2a$10$pJ1PL.TW4XCkuD4U5H2QdukPGBRrycwdzyXK.9/hcF1zC2kH5isSK','admin','mandarinkb'),(30,'$2a$10$6FlCzz0mSmX4rWZE.nPhPu42Uj1hyD/4OXLrNNnRLd9wezsKD68nO','admin','demo');
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WEB`
--

DROP TABLE IF EXISTS `WEB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WEB` (
  `Web_ID` int(11) NOT NULL AUTO_INCREMENT,
  `WEB_NAME` varchar(250) DEFAULT NULL,
  `WEB_URL` varchar(250) DEFAULT NULL,
  `WEB_STATUS` char(1) DEFAULT NULL,
  `ICON_URL` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Web_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WEB`
--

LOCK TABLES `WEB` WRITE;
/*!40000 ALTER TABLE `WEB` DISABLE KEYS */;
INSERT INTO `WEB` VALUES (49,'tescolotus','https://shoponline.tescolotus.com/groceries/th-TH/promotions','1','https://www.tescolotus.com/assets/theme2018/tl-theme/img/logo.png'),(66,'lazada','https://www.lazada.co.th/#','0','https://laz-img-cdn.alicdn.com/images/ims-web/TB1KB2laMFY.1VjSZFnXXcFHXXa.png'),(67,'makroclick','https://www.makroclick.com/th','1','https://www.makroclick.com/static/images/logo.png'),(68,'bigc','https://www.bigc.co.th/','1','https://www.bigc.co.th/_nuxt/img/108a02e.png');
/*!40000 ALTER TABLE `WEB` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-10 22:31:23
