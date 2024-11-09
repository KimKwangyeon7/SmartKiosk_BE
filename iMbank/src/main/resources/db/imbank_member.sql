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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `user_id` int NOT NULL COMMENT '행번',
  `dept_nm` varchar(255) NOT NULL COMMENT '소속 코드명',
  `email` varchar(255) NOT NULL COMMENT '이메일',
  `name` varchar(40) DEFAULT NULL COMMENT '이름',
  `password` varchar(80) DEFAULT NULL COMMENT '비밀번호',
  `profile_image` varchar(255) DEFAULT NULL COMMENT '프로필 이미지 URL',
  `role` enum('BRANCH','HEADQUARTER') NOT NULL COMMENT '권한',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO Member (user_id, email, password, name, profile_image, dept_nm, role) VALUES
    (100, 'hd100@naver.com', '$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6', '김광연', NULL, '대구', 'HEADQUARTER');
INSERT INTO Member (user_id, email, password, name, profile_image, dept_nm, role) VALUES
    (200, 'hd200@naver.com', '$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6', '김광연', NULL, '대구', 'HEADQUARTER');
INSERT INTO `member` VALUES (1,'강남','user1@example.com','Alice','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(2,'강남','user2@example.com','Bob','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(3,'강남','user3@example.com','James','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(4,'홍대','user4@example.com','Bronny','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(5,'홍대','user5@example.com','Durant','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(6,'부산','user6@example.com','Westbrook','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(7,'대전','user7@example.com','Davis','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(8,'대전','user8@example.com','Beal','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(9,'인천','user9@example.com','Oscar','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH'),(10,'인천','user10@example.com','Jordan','$2a$10$lXHWXkbDeYu8KdAeNBwQKeAbs3SctSy1sn5S2vzsQy8ttCl5xuez6',NULL,'BRANCH');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
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
