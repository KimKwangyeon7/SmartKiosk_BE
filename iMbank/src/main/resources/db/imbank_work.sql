-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: imbank
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
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work` (
  `color` int DEFAULT '0' COMMENT '색',
  `loc` int DEFAULT '0' COMMENT '순서',
  `work_id` int NOT NULL AUTO_INCREMENT COMMENT '업무 아이디',
  `dept_id` varchar(10) NOT NULL COMMENT '소속 코드',
  `dept_nm` varchar(100) NOT NULL COMMENT '조직 축약명',
  `left_high` varchar(20) DEFAULT NULL COMMENT '왼쪽 위 좌표',
  `right_low` varchar(20) DEFAULT NULL COMMENT '오른쪽 아래 좌표',
  `work_dvcd` varchar(2) NOT NULL COMMENT '업무 구분 코드',
  `work_dvcd_nm` varchar(10) NOT NULL COMMENT '업무 구분 코드명',
  PRIMARY KEY (`work_id`),
  KEY `FK8n2s9ol8p1wxss8jeib265cvi` (`dept_id`),
  CONSTRAINT `FK8n2s9ol8p1wxss8jeib265cvi` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` VALUES (1,0,1,'B001','강남','0,0','100,100','00','일반업무'),(2,0,2,'B001','강남','100,100','200,200','01','대출업무'),(3,0,3,'B001','강남','300,300','300,300','02','상담업무'),(0,0,4,'B001','강남','300,300','400,400','03','기타업무'),(1,0,5,'B002','홍대','0,0','100,100','00','일반업무'),(1,0,6,'B002','홍대','100,100','200,200','01','대출업무'),(1,0,7,'B002','홍대','200,200','300,300','02','상담업무'),(1,0,8,'B002','홍대','300,300','400,400','03','기타업무'),(1,0,9,'B003','부산','0,0','100,100','00','일반업무'),(1,0,10,'B003','부산','100,100','200,200','01','대출업무'),(1,0,11,'B003','부산','200,200','300,300','02','상담업무'),(1,0,12,'B003','부산','300,300','400,400','03','기타업무'),(1,0,13,'B004','대전','0,0','100,100','00','일반업무'),(2,0,14,'B004','대전','100,100','200,200','01','대출업무'),(3,0,15,'B004','대전','200,200','300,300','02','상담업무'),(4,0,16,'B004','대전','300,300','400,400','03','기타업무'),(1,0,17,'B005','인천','0,0','100,100','00','일반업무'),(2,0,18,'B005','인천','100,100','200,200','01','대출업무'),(3,0,19,'B005','인천','200,200','300,300','02','상담업무'),(4,0,20,'B005','인천','300,300','400,400','03','기타업무'),(1,0,21,'C001','대구','0,0','100,100','00','일반업무'),(2,0,22,'C001','대구','100,100','200,200','01','대출업무'),(3,0,23,'C001','대구','200,200','300,300','02','상담업무'),(4,0,24,'C001','대구','300,300','400,400','03','기타업무');
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-08 16:44:30
