-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: imbank
-- ------------------------------------------------------
-- Server version	8.0.37

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

--
-- Table structure for table `wicket`
--

DROP TABLE IF EXISTS `wicket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wicket` (
  `col_num` int DEFAULT NULL COMMENT '열 번호',
  `row_num` int DEFAULT NULL COMMENT '행 번호',
  `user_id` int DEFAULT NULL COMMENT '담당 직원 아이디',
  `wd_floor` int NOT NULL COMMENT '층 수',
  `wd_id` int NOT NULL AUTO_INCREMENT COMMENT '창구 아이디',
  `wd_num` int NOT NULL COMMENT '창구 번호',
  `dept_id` varchar(10) NOT NULL COMMENT '소속 코드',
  `wd_dvcd` varchar(100) NOT NULL COMMENT '창구 구분 코드',
  PRIMARY KEY (`wd_id`),
  KEY `FK43lpxgy5rutm0be3ie021pd7o` (`dept_id`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_wd_dvcd` (`wd_dvcd`),
  KEY `idx_wd_num` (`wd_num`),
  CONSTRAINT `FK43lpxgy5rutm0be3ie021pd7o` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wicket`
--

LOCK TABLES `wicket` WRITE;
/*!40000 ALTER TABLE `wicket` DISABLE KEYS */;
INSERT INTO `wicket` VALUES (0,0,1,1,1,1,'B001','00'),(1,0,1,1,2,2,'B001','01'),(2,0,2,1,3,3,'B001','02'),(3,0,3,1,4,4,'B001','03'),(0,1,1,1,5,5,'B001','00'),(1,1,2,2,6,6,'B001','01'),(2,1,2,2,7,7,'B001','02'),(0,2,1,2,9,9,'B001','00'),(1,2,2,3,10,10,'B001','01'),(2,2,2,3,11,11,'B001','02'),(3,2,3,3,12,12,'B001','03'),(0,0,4,1,13,1,'B002','00'),(1,0,4,1,14,2,'B002','01'),(2,0,5,1,15,3,'B002','02'),(3,0,5,1,16,4,'B002','03'),(0,1,4,1,17,5,'B002','00'),(1,1,5,2,18,6,'B002','01'),(2,1,5,2,19,7,'B002','02'),(3,1,5,2,20,8,'B002','03'),(0,2,4,2,21,9,'B002','00'),(1,2,5,3,22,10,'B002','01'),(2,2,5,3,23,11,'B002','02'),(3,2,5,3,24,12,'B002','03'),(0,0,6,1,25,1,'B003','00'),(1,0,6,1,26,2,'B003','01'),(2,0,6,1,27,3,'B003','02'),(3,0,6,1,28,4,'B003','03'),(0,1,6,1,29,5,'B003','00'),(1,1,6,2,30,6,'B003','01'),(2,1,6,2,31,7,'B003','02'),(3,1,6,2,32,8,'B003','03'),(0,2,6,2,33,9,'B003','00'),(1,2,6,3,34,10,'B003','01'),(2,2,6,3,35,11,'B003','02'),(3,2,6,3,36,12,'B003','03'),(0,0,7,1,37,1,'B004','00'),(1,0,7,1,38,2,'B004','01'),(2,0,8,1,39,3,'B004','02'),(3,0,8,1,40,4,'B004','03'),(0,1,7,1,41,5,'B004','00'),(1,1,8,2,42,6,'B004','01'),(2,1,8,2,43,7,'B004','02'),(3,1,8,2,44,8,'B004','03'),(0,2,7,2,45,9,'B004','00'),(1,2,8,3,46,10,'B004','01'),(2,2,8,3,47,11,'B004','02'),(3,2,8,3,48,12,'B004','03'),(0,0,9,1,49,1,'B005','00'),(1,0,9,1,50,2,'B005','01'),(2,0,10,1,51,3,'B005','02'),(3,0,10,1,52,4,'B005','03'),(0,1,9,1,53,5,'B005','00'),(1,1,10,2,54,6,'B005','01'),(2,1,10,2,55,7,'B005','02'),(3,1,10,2,56,8,'B005','03'),(0,2,9,2,57,9,'B005','00'),(1,2,10,3,58,10,'B005','01'),(2,2,10,3,59,11,'B005','02'),(3,2,10,3,60,12,'B005','03'),(0,0,1,3,61,4,'B001','00'),(0,0,1,3,62,5,'B001','00'),(0,0,1,3,63,6,'B001','00'),(0,0,1,3,64,7,'B001','00'),(0,0,1,3,65,8,'B001','00'),(0,0,1,3,66,9,'B001','00');
/*!40000 ALTER TABLE `wicket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-11 14:29:10
