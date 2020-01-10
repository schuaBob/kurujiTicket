CREATE DATABASE  IF NOT EXISTS `missa` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `missa`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: missaproject.cfojfxuhc6dx.ap-northeast-1.rds.amazonaws.com    Database: missa
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `concert`
--

DROP TABLE IF EXISTS `concert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concert` (
  `idconcert` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `supplierid` int(11) NOT NULL,
  `location` varchar(45) NOT NULL,
  `picture` varchar(5000) NOT NULL,
  `seatpicture` varchar(5000) NOT NULL,
  `endsellingtime` datetime NOT NULL,
  `content` varchar(10000) NOT NULL,
  `ticketstatus` json NOT NULL,
  `concertstarttime` datetime NOT NULL,
  `concertendtime` datetime NOT NULL,
  `session` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idconcert`),
  KEY `concert_supplierid_idx` (`supplierid`),
  CONSTRAINT `concert_idsupplier` FOREIGN KEY (`supplierid`) REFERENCES `supplier` (`idsupplier`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concert`
--

LOCK TABLES `concert` WRITE;
/*!40000 ALTER TABLE `concert` DISABLE KEYS */;
INSERT INTO `concert` VALUES (1,'btsconcert',1,'TPE','no picture','no seatpicture','2020-12-11 13:17:17','no content','{\"data\": [{\"Area\": \"A\", \"Sold\": 5, \"Price\": \"1000\", \"SeatTotal\": \"200\"}, {\"Area\": \"B\", \"Sold\": \"0\", \"Price\": \"700\", \"SeatTotal\": \"400\"}]}','2019-12-20 12:40:33','2020-07-07 12:40:33','bts'),(2,'mayday',1,'TPE','no picture','no seatpicture','2020-06-11 13:17:17','no content','{\"data\": [{\"Area\": \"A\", \"Sold\": 30, \"Price\": \"1000\", \"SeatTotal\": \"300\"}, {\"Area\": \"B\", \"Sold\": 2, \"Price\": \"700\", \"SeatTotal\": \"250\"}]}','2019-12-21 10:59:17','2020-07-08 10:59:17','mayday'),(3,'Jay Chou',1,'location1','URL','URL','2020-01-11 00:00:00','內容','{\"data\": [{\"Area\": \"A\", \"Sold\": 3, \"Price\": \"1000\", \"SeatTotal\": \"300\"}, {\"Area\": \"B\", \"Sold\": \"0\", \"Price\": \"700\", \"SeatTotal\": \"250\"}]}','2019-01-10 19:00:00','2019-01-10 22:00:00','chou'),(4,'Jay Chou',1,'location2','URL','URL','2020-01-11 00:00:00','內容','{\"data\": []}','2019-01-09 19:00:00','2019-01-09 22:00:00','chou'),(7,'Jay Chou',1,'location3','URL','URL','2020-01-11 00:00:00','內容','{\"data\": [{\"Area\": \"A\", \"Sold\": 5, \"Price\": \"1000\", \"SeatTotal\": \"200\"}, {\"Area\": \"B\", \"Sold\": \"0\", \"Price\": \"700\", \"SeatTotal\": \"300\"}]}','2019-01-09 19:00:00','2019-01-09 22:00:00','chou'),(8,'Jay Chou',1,'location4','URL','URL','2020-01-11 00:00:00','內容','{\"data\": [{\"Area\": \"A\", \"Sold\": \"0\", \"Price\": \"1000\", \"SeatTotal\": \"200\"}, {\"Area\": \"B\", \"Sold\": \"0\", \"Price\": \"700\", \"SeatTotal\": \"300\"}]}','2019-01-09 19:00:00','2019-01-09 22:00:00','chou'),(16,'Final 1 test',1,'NCU','\\picture\\alena-aenami-lighthouse1k.jpg','\\picture\\sylvain-sarrailh-mendiondea.jpg','2020-01-08 00:00:00','FINAL TEST1','{\"data\": []}','2019-01-01 12:22:30','2019-01-01 12:22:30','Final'),(19,'Final 2 test',1,'NCU','\\picture\\78096099_p0_master1200.jpg','\\picture\\74707810_p1_master1200.jpg','2020-01-24 08:00:00','FINAL TEST2','{\"data\": [{\"Area\": \"A\", \"Sold\": 5, \"Price\": \"1000\", \"SeatTotal\": \"100\"}, {\"Area\": \"B\", \"Sold\": 5, \"Price\": \"2000\", \"SeatTotal\": \"100\"}, {\"Area\": \"C\", \"Sold\": 4, \"Price\": \"3000\", \"SeatTotal\": \"100\"}, {\"Area\": \"D\", \"Sold\": 4, \"Price\": \"4000\", \"SeatTotal\": \"100\"}, {\"Area\": \"E\", \"Sold\": 2, \"Price\": \"5000\", \"SeatTotal\": \"100\"}, {\"Area\": \"F\", \"Sold\": 2, \"Price\": \"6000\", \"SeatTotal\": \"100\"}]}','2020-01-25 08:00:00','2020-01-26 08:00:00','Final'),(20,'Final 3 test',1,'NCU','\\picture\\78096099_p0_master1200.jpg','\\picture\\74707810_p1_master1200.jpg','2020-01-24 08:00:00','lsfij lisjoi; jsjdoj osjd ','{\"data\": []}','2020-01-25 08:00:00','2020-01-26 08:00:00','Final'),(21,'Final 4 test',1,'NCU','\\picture\\alena-aenami-serenity-1k.jpg','\\picture\\74707810_p1_master1200.jpg','2020-01-31 00:00:00','s;fkgj klsjkl jsdkl;;fl sd','{\"data\": [{\"Area\": \"A\", \"Sold\": 0, \"Price\": \"100\", \"SeatTotal\": \"100\"}, {\"Area\": \"B\", \"Sold\": 0, \"Price\": \"200\", \"SeatTotal\": \"100\"}, {\"Area\": \"C\", \"Sold\": 0, \"Price\": \"300\", \"SeatTotal\": \"100\"}, {\"Area\": \"D\", \"Sold\": 0, \"Price\": \"400\", \"SeatTotal\": \"100\"}, {\"Area\": \"E\", \"Sold\": 0, \"Price\": \"500\", \"SeatTotal\": \"100\"}, {\"Area\": \"F\", \"Sold\": 0, \"Price\": \"600\", \"SeatTotal\": \"100\"}]}','2020-02-01 08:00:00','2020-02-02 08:00:00','Final');
/*!40000 ALTER TABLE `concert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `idmanager` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `lastlogintime` datetime NOT NULL,
  PRIMARY KEY (`idmanager`),
  UNIQUE KEY `account_UNIQUE` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'bobo','12345','2019-12-20 12:33:09');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `idmember` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `dateofbirth` date NOT NULL,
  `idnumber` varchar(255) NOT NULL,
  `phonenumber` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`idmember`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `idnumber_UNIQUE` (`idnumber`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'god','god123@gmail.com','god12345','1999-09-09','G123456789','0987878787','NCU'),(5,'華崧淇','schua1013@gmail.com','a29252097','1998-10-13','A123456789','0912345678','NCU'),(10,'Test1','test1@gmail.com','test1pwd','1998-10-12','B111111111','0988888888','NCU'),(11,'test2','test2@gmail.com','test2pwd','1998-10-14','A222222222','0977777777','NCU'),(12,'test3','test3@gmail.com','test3pwd','1998-10-14','A121121121','0977666555','NCU');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `idorder` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) NOT NULL,
  `payment` varchar(255) NOT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT '0',
  `ticketamount` int(11) NOT NULL,
  `createtime` timestamp NOT NULL,
  `totalprice` int(45) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idorder`),
  KEY `order_memberid_idx` (`memberid`),
  CONSTRAINT `order_idmember` FOREIGN KEY (`memberid`) REFERENCES `member` (`idmember`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,5,'line',0,3,'2020-01-06 21:22:42',27000),(2,12,'credit',0,1,'2020-01-06 21:22:56',6000),(3,12,'credit',0,1,'2020-01-06 21:22:56',6000),(4,5,'line',0,2,'2020-01-06 21:34:24',2000),(5,5,'credit',0,1,'2020-01-06 21:36:06',14000),(6,5,'credit',0,1,'2020-01-06 21:36:06',14000),(7,5,'credit',0,1,'2020-01-06 21:36:06',14000),(8,5,'credit',0,1,'2020-01-06 21:36:06',14000),(9,12,'credit',0,2,'2020-01-06 21:39:39',2000),(10,1,'line',0,4,'2020-01-06 22:28:11',40000),(11,11,'line',0,4,'2020-01-06 22:56:26',10000);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `idsupplier` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phonenumber` varchar(255) NOT NULL,
  `addedbywhom` int(11) NOT NULL,
  PRIMARY KEY (`idsupplier`),
  UNIQUE KEY `account_UNIQUE` (`account`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`),
  KEY `supplier_addedbywhom_idx` (`addedbywhom`),
  CONSTRAINT `supplier_idmanager` FOREIGN KEY (`addedbywhom`) REFERENCES `manager` (`idmanager`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'abcde','12345','bobo','0912345678',1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `idticket` int(11) NOT NULL AUTO_INCREMENT,
  `concertid` int(11) NOT NULL,
  `orderid` int(11) NOT NULL,
  `seatarea` varchar(255) NOT NULL,
  `seatid` int(11) NOT NULL,
  `isused` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idticket`),
  KEY `ticket_concertid_idx` (`concertid`),
  KEY `ticket_orderid_idx` (`orderid`),
  CONSTRAINT `ticket_idconcert` FOREIGN KEY (`concertid`) REFERENCES `concert` (`idconcert`),
  CONSTRAINT `ticket_idorder` FOREIGN KEY (`orderid`) REFERENCES `order` (`idorder`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,20,1,'B',1,0,'schua1013@gmail.com','0912345678','華崧淇'),(2,20,1,'C',1,0,'schua1013@gmail.com','0912345678','華崧淇'),(3,20,1,'D',1,0,'schua1013@gmail.com','0912345678','華崧淇'),(4,20,2,'B',2,0,'test3@gmail.com','0977666555','test3'),(5,20,3,'C',1,0,'test3@gmail.com','0977666555','test3'),(6,2,4,'A',29,0,'schua1013@gmail.com','0912345678','華崧淇'),(7,2,4,'A',30,0,'schua1013@gmail.com','0912345678','華崧淇'),(8,19,6,'B',4,0,'','',''),(9,19,6,'F',3,0,'','',''),(10,19,8,'A',2,0,'','',''),(11,19,7,'E',2,0,'','',''),(12,19,9,'A',2,0,'bobo@gmail.com','0917222169','bobo'),(13,19,9,'A',3,0,'bobo@gmail.com','0917222169','bobo'),(14,19,10,'A',4,0,'god123@gmail.com','0987878787','god'),(15,19,10,'B',4,0,'god123@gmail.com','0987878787','god'),(16,19,10,'C',3,0,'god123@gmail.com','0987878787','god'),(17,19,10,'D',3,0,'god123@gmail.com','0987878787','god'),(18,19,11,'A',5,0,'test2@gmail.com','0977777777','test2'),(19,19,11,'B',5,0,'test2@gmail.com','0977777777','test2'),(20,19,11,'C',4,0,'test2@gmail.com','0977777777','test2'),(21,19,11,'D',4,0,'test2@gmail.com','0977777777','test2');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'missa'
--

--
-- Dumping routines for database 'missa'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-06 23:27:10
