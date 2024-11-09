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
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('B001', '강남', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('B001', '강남', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('B001', '강남', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('B001', '강남', '03', '기타업무', '300,300', '40,400', 4);

-- 홍대 지점 업무
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('B002', '홍대', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('B002', '홍대', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('B002', '홍대', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('B002', '홍대', '03', '기타업무', '300,300', '400,400', 4);

-- 부산 지점 업무
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('B003', '부산', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('B003', '부산', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('B003', '부산', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('B003', '부산', '03', '기타업무', '300,300', '400,400', 4);

-- 대전 지점 업무
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('B004', '대전', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('B004', '대전', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('B004', '대전', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('B004', '대전', '03', '기타업무', '300,300', '400,400', 4);

-- 인천 지점 업무
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('B005', '인천', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('B005', '인천', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('B005', '인천', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('B005', '인천', '03', '기타업무', '300,300', '400,400', 4);

-- 대구 지점 업무
INSERT INTO Work (dept_id, dept_nm, work_dvcd, work_dvcd_nm, left_high, right_low, color) VALUES
                                                                                              ('C001', '대구', '00', '일반업무', '0,0', '100,100', 1),
                                                                                              ('C001', '대구', '01', '대출업무', '100,100', '200,200', 2),
                                                                                              ('C001', '대구', '02', '상담업무', '200,200', '300,300', 3),
                                                                                              ('C001', '대구', '03', '기타업무', '300,300', '400,400', 4);
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
