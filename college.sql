CREATE DATABASE  IF NOT EXISTS `college` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `college`;
-- MySQL dump 10.13  Distrib 8.0.14, for Win64 (x86_64)
--
-- Host: localhost    Database: college
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `course` (
  `courseId` int(11) DEFAULT NULL,
  `courseName` char(20) DEFAULT NULL,
  `majorId` int(11) DEFAULT NULL,
  `teacherId` int(11) DEFAULT NULL,
  KEY `course_major_majorId_fk` (`majorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'c',1,2),(2,'现代控制原理',2,2),(3,'模拟电路基础',3,3),(4,'数据原理',1,4),(5,'高等数学',2,5),(6,'高等数学',3,5),(7,'高等数学',4,5),(8,'高等数学',5,5),(9,'高等数学',6,5),(10,'高等数学',7,5),(11,'高等数学',8,5),(12,'高等数学',9,5),(13,'大学物理',4,6),(14,'大学物理',5,6),(15,'大学物理',6,6);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institute`
--

DROP TABLE IF EXISTS `institute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `institute` (
  `instituteId` int(11) NOT NULL,
  `instituteName` char(30) DEFAULT NULL,
  `numberOfMajor` int(10) DEFAULT NULL,
  PRIMARY KEY (`instituteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institute`
--

LOCK TABLES `institute` WRITE;
/*!40000 ALTER TABLE `institute` DISABLE KEYS */;
INSERT INTO `institute` VALUES (1,'信息科学与工程学院',3),(2,'机电工程学院',3),(3,'粉末冶金研究院',3);
/*!40000 ALTER TABLE `institute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `major` (
  `majorId` int(11) NOT NULL,
  `majorName` char(30) DEFAULT NULL,
  `instituteId` int(11) DEFAULT NULL,
  PRIMARY KEY (`majorId`),
  KEY `major_institute_instituteId_fk` (`instituteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'计算机科学',1),(2,'自动化',1),(3,'电气工程',1),(4,'机械设计制造及其自动化',2),(5,'车辆工程',2),(6,'微电子科学与工程',2),(7,'材料化学',3),(8,'粉体材料科学与工程',3),(9,'高分子材料材料与工程',3);
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `studentId` int(11) NOT NULL,
  `studentName` char(20) DEFAULT NULL,
  `studentSex` char(4) DEFAULT NULL,
  `majorId` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'ccc','男',1),(2,'弘凡','男',2),(3,'贺创','男',3),(4,'空益','男',4),(5,'松沛','男',5),(6,'唐民','男',6),(7,'宋真','女',7),(8,'吴彩','女',8),(9,'邓喜','女',9),(10,'师惠','女',1),(11,'何凡','女',2),(12,'cwdq','cc',3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teacher` (
  `teacherId` int(11) NOT NULL,
  `teacherName` char(20) DEFAULT NULL,
  `teacherSex` char(4) DEFAULT NULL,
  `instituteId` int(11) DEFAULT NULL,
  PRIMARY KEY (`teacherId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'李尔白','男',1),(2,'仰之山','男',2),(3,'余知睿','男',3),(4,'幸书双','女',1),(5,'卢宜人','男',2),(6,'宛昊东','男',2);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-24 17:02:56
