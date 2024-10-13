-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: db_grupo2
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- -----------------------------------------------------
-- Schema db_grupo2
-- -----------------------------------------------------
UNLOCK TABLES;
COMMIT;
DROP SCHEMA IF EXISTS `db_grupo2` ;
-- -----------------------------------------------------
-- Schema db_grupo2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_grupo2` DEFAULT CHARACTER SET utf8mb3 ;
USE `db_grupo2` ;

-- -----------------------------------------------------
-- Table `db_grupo2`.`despachador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `despachador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `despachador` (
  `iddespachador` int NOT NULL,
  `despachador` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`iddespachador`),
  UNIQUE KEY `despachador_UNIQUE` (`despachador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `despachador`
--

LOCK TABLES `despachador` WRITE;
/*!40000 ALTER TABLE `despachador` DISABLE KEYS */;
INSERT INTO `despachador` VALUES 
(43,'DESP001','Habilitado'),
(44,'DESP002','Habilitado'),
(45,'DESP003','Habilitado'),
(46,'DESP004','Habilitado'),
(47,'DESP005','Habilitado'),
(48,'DESP006','Habilitado'),
(49,'DESP007','Habilitado'),
(50,'DESP008','Habilitado'),
(51,'DESP009','Habilitado'),
(52,'DESP010','Habilitado'),
(53,'DESP011','Habilitado'),
(54,'DESP012','Habilitado'),
(55,'DESP013','Habilitado'),
(56,'DESP014','Habilitado'),
(57,'DESP015','Habilitado'),
(58,'DESP016','Habilitado'),
(59,'DESP017','Supendido'),
(60,'DESP018','Multado'),
(61,'DESP019','Anulado'),
(62,'DESP020','Multado'),
(63,'DESP021','Anulado');
/*!40000 ALTER TABLE `despachador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distritos`
--

DROP TABLE IF EXISTS `distritos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `distritos` (
  `iddistritos` int NOT NULL,
  `nombreDistrito` varchar(45) NOT NULL,
  `idzona` int NOT NULL,
  PRIMARY KEY (`iddistritos`),
  KEY `fk_distritos_zona1_idx` (`idzona`),
  CONSTRAINT `fk_distritos_zona1` FOREIGN KEY (`idzona`) REFERENCES `zona` (`idzona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distritos`
--

LOCK TABLES `distritos` WRITE;
/*!40000 ALTER TABLE `distritos` DISABLE KEYS */;
INSERT INTO `distritos` VALUES (1,'Ancon',1),(2,'Santa Rosa',1),(3,'Carabayllo',1),(4,'Puente Piedra',1),(5,'Comas',1),(6,'Los Olivos',1),(7,'San Martín de Porres',1),(8,'Independencia',1),(9,'San Juan de Miraflores',2),(10,'Villa María del Triunfo',2),(11,'Villa el Salvador',2),(12,'Pachacamac',2),(13,'Lurín',2),(14,'Punta Hermosa',2),(15,'Punta Negra',2),(16,'San Bartolo',2),(17,'Santa María del Mar',2),(18,'Pucusana',2),(19,'San Juan de Lurigancho',3),(20,'Lurigancho/Chosica',3),(21,'Ate',3),(22,'El Agustino',3),(23,'Santa Anita',3),(24,'La Molina',3),(25,'Cieneguilla',3),(26,'Rimac',4),(27,'Cercado de Lima',4),(28,'Breña',4),(29,'Pueblo Libre',4),(30,'Magdalena',4),(31,'Jesus Maria',4),(32,'La Victoria',4),(33,'Lince',4),(34,'San Isidro',4),(35,'San Miguel',4),(36,'Surquillo',4),(37,'San Borja',4),(38,'Santiago de Surco',4),(39,'Barranco',4),(40,'Chorrillos',4),(41,'San Luis',4),(42,'Miraflores',4);
/*!40000 ALTER TABLE `distritos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
  `idfaq` int NOT NULL AUTO_INCREMENT,
  `respuesta` varchar(200) NOT NULL,
  `pregunta` varchar(200) NOT NULL,
  PRIMARY KEY (`idfaq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotos`
--

DROP TABLE IF EXISTS `fotos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotos` (
  `idfotos` int NOT NULL AUTO_INCREMENT,
  `foto1` varchar(255) NOT NULL,
  `productos_idproductos` int NOT NULL,
  PRIMARY KEY (`idfotos`),
  KEY `fk_fotos_productos1_idx` (`productos_idproductos`),
  CONSTRAINT `fk_fotos_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotos`
--

LOCK TABLES `fotos` WRITE;
/*!40000 ALTER TABLE `fotos` DISABLE KEYS */;
INSERT INTO `fotos` VALUES (1,'/imagenes/producto1-1.jpg',1),(2,'/imagenes/producto1-2.jpg',1),(3,'/imagenes/producto1-3.jpg',1),(4,'/imagenes/producto1-4.jpg',1),(5,'/imagenes/producto2-1.jpg',2),(6,'/imagenes/producto2-2.jpg',2),(7,'/imagenes/producto2-3.jpg',2),(8,'/imagenes/producto2-4.jpg',2),(9,'/imagenes/producto3-1.jpg',3),(10,'/imagenes/producto3-2.jpg',3),(11,'/imagenes/producto3-3.jpg',3),(12,'/imagenes/producto3-4.jpg',3),(13,'/imagenes/producto4-1.jpg',4),(14,'/imagenes/producto4-2.jpg',4),(15,'/imagenes/producto4-3.jpg',4),(16,'/imagenes/producto4-4.jpg',4),(17,'/imagenes/producto5-1.jpg',5),(18,'/imagenes/producto5-2.jpg',5),(19,'/imagenes/producto5-3.jpg',5),(20,'/imagenes/producto5-4.jpg',5),(21,'/imagenes/producto6-1.jpg',6),(22,'/imagenes/producto6-2.jpg',6),(23,'/imagenes/producto6-3.jpg',6),(24,'/imagenes/producto6-4.jpg',6),(25,'/imagenes/producto7-1.jpg',7),(26,'/imagenes/producto7-2.jpg',7),(27,'/imagenes/producto7-3.jpg',7),(28,'/imagenes/producto7-4.jpg',7),(29,'/imagenes/producto8-1.jpg',8),(30,'/imagenes/producto8-2.jpg',8),(31,'/imagenes/producto8-3.jpg',8),(32,'/imagenes/producto8-4.jpg',8),(33,'/imagenes/producto9-1.jpg',9),(34,'/imagenes/producto9-2.jpg',9),(35,'/imagenes/producto9-3.jpg',9),(36,'/imagenes/producto9-4.jpg',9),(37,'/imagenes/producto10-1.jpg',10),(38,'/imagenes/producto10-2.jpg',10),(39,'/imagenes/producto10-3.jpg',10),(40,'/imagenes/producto10-4.jpg',10);
/*!40000 ALTER TABLE `fotos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jurisdiccion`
--

DROP TABLE IF EXISTS `jurisdiccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jurisdiccion` (
  `idjurisdiccion` int NOT NULL,
  `jurisdiccion` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`idjurisdiccion`),
  UNIQUE KEY `jurisdiccion_UNIQUE` (`jurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jurisdiccion`
--

LOCK TABLES `jurisdiccion` WRITE;
/*!40000 ALTER TABLE `jurisdiccion` DISABLE KEYS */;
INSERT INTO `jurisdiccion` VALUES 
(1,'JURIS001','Habilitado'),
(2,'JURIS002','Habilitado'),
(3,'JURIS003','Habilitado'),
(4,'JURIS004','Habilitado'),
(5,'JURIS005','Habilitado'),
(6,'JURIS006','Habilitado'),
(7,'JURIS007','Habilitado'),
(8,'JURIS008','Habilitado'),
(9,'JURIS009','Habilitado'),
(10,'JURIS010','Habilitado'),
(11,'JURIS011','Habilitado'),
(12,'JURIS012','Habilitado'),
(13,'JURIS013','Habilitado'),
(14,'JURIS014','Habilitado'),
(15,'JURIS015','Habilitado'),
(16,'JURIS016','Habilitado'),
(17,'JURIS017','Habilitado'),
(18,'JURIS018','Suspendido'),
(19,'JURIS019','Anulado'),
(20,'JURIS020','Multado'),
(21,'JURIS021','Multado');
/*!40000 ALTER TABLE `jurisdiccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes` (
  `idordenes` int NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` varchar(45) DEFAULT NULL,
  `fechaArribo` date NULL,
  `usuario_idusuario` int NOT NULL,
  `mesCreacion` varchar(20) NULL,
  `fechaCreacion` date NULL,
  PRIMARY KEY (`idordenes`,`usuario_idusuario`),
  KEY `fk_ordenes_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_ordenes_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=1022 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenes`
--

LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;
INSERT INTO `ordenes` VALUES (1001,'RECIBIDO','2024-02-13',18,'Enero','2024-01-10'),
(1002,'RECIBIDO','2024-02-14',19,'Enero','2024-01-08'),
(1003,'RECIBIDO','2024-03-15',20,'Febrero','2024-02-07'),
(1004,'ARRIBO AL PAÍS','2024-04-16',21,'Febrero','2024-02-20'),
(1005,'ARRIBO AL PAÍS','2024-10-14',22,'Abril','2024-04-28'),
(1006,'ARRIBO AL PAÍS','2024-11-15',23,'Mayo','2024-05-29'),
(1007,'EN ADUANAS','2024-12-16',24,'Junio','2024-06-30'),
(1008,'EN ADUANAS','2024-11-12',25,'Julio','2024-07-28'),
(1009,'EN ADUANAS','2024-12-10',26,'Agosto','2024-08-14'),
(1010,'EN RUTA','2025-01-02',27,'Setiembre','2024-09-09'),
(1011,'EN RUTA','2024-12-15',18,'Abril','2024-04-05'),
(1012,'EN RUTA','2024-12-16',19,'Mayo','2024-05-17'),
(1013,'EN VALIDACIÓN','2024-12-17',20,'Junio','2024-06-19'),
(1014,'EN VALIDACIÓN','2025-01-10',21,'Setiembre','2024-09-18'),
(1015,'EN VALIDACIÓN','2025-02-07',22,'Agosto','2024-08-14'),
(1016,'CREADO','2025-01-23',23,'Octubre','2024-10-01'),
(1017,'CREADO','2025-02-28',24,'Octubre','2024-10-02'),
(1018,'EN PROCESO','2025-01-19',25,'Octubre','2024-10-02'),
(1019,'EN PROCESO','2025-02-01',26,'Octubre','2024-10-03'),
(1020,'Pendiente','2025-02-02',27,'Octubre','2024-10-04'),
(1021,'Pendiente','2025-02-03',28,'Octubre','2024-10-05');
/*!40000 ALTER TABLE `ordenes` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `idpagos` int NOT NULL AUTO_INCREMENT,
  `monto` double NOT NULL,
  `numeroTarjeta` varchar(45) NOT NULL,
  `cvv` int NOT NULL,
  `fechaPago` datetime NOT NULL,
  `metodoPago` varchar(45) NOT NULL,
  `estadoPago` varchar(45) NOT NULL,
  `ordenes_idordenes` int NOT NULL,
  `usuario_idusuario` int NOT NULL,
  PRIMARY KEY (`idpagos`,`ordenes_idordenes`),
  KEY `fk_pagos_ordenes1_idx` (`ordenes_idordenes`),
  KEY `fk_pagos_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_pagos_ordenes1` FOREIGN KEY (`ordenes_idordenes`) REFERENCES `ordenes` (`idordenes`),
  CONSTRAINT `fk_pagos_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (1,1300,'11111111',123,'2024-01-10 10:30:00','Tarjeta de Crédito','Completado',1001,18),(2,1200,'22222222',456,'2024-01-08 14:45:00','PayPal','Completado',1002,19),(3,1000,'33333333',789,'2024-02-07 09:15:00','Tarjeta de Débito','Completado',1003,20),(4,199.99,'44444444',234,'2024-02-20 16:20:00','Transferencia Bancaria','Completado',1004,21),(5,100.5,'55555555',567,'2024-04-28 11:00:00','Tarjeta de Crédito','Completado',1005,22),(6,400,'66666666',890,'2024-05-29 13:30:00','PayPal','Completado',1006,23),(7,1000.7,'77777777',345,'2024-06-30 15:45:00','Tarjeta de Débito','Completado',1007,24),(8,500.5,'88888888',678,'2024-07-28 10:00:00','Transferencia Bancaria','Completado',1008,25),(9,100,'99999999',901,'2024-08-14 12:15:00','Tarjeta de Crédito','Completado',1009,26),(10,500.5,'AAAAAAAA',234,'2024-09-09 17:30:00','PayPal','Completado',1010,27),(11,1300,'11111111',123,'2024-04-05 10:30:00','Tarjeta de Crédito','Completado',1011,18),(12,1250,'22222222',456,'2024-06-20 14:45:00','PayPal','Completado',1012,19),(13,900,'33333333',789,'2024-07-10 09:15:00','Tarjeta de Débito','Completado',1013,20),(14,200,'44444444',234,'2024-08-05 16:20:00','Transferencia Bancaria','Completado',1014,21),(15,100.5,'55555555',567,'2024-09-12 11:00:00','Tarjeta de Crédito','Completado',1015,22),(16,500,'66666666',890,'2024-10-18 13:30:00','PayPal','Completado',1016,23),(17,900.5,'77777777',345,'2024-11-22 15:45:00','Tarjeta de Débito','Completado',1017,24);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idproducto` int NOT NULL AUTO_INCREMENT,
  `nombreProducto` varchar(100) NOT NULL,
  `categoria` varchar(45) NOT NULL,
  `cantidadDisponible` int NOT NULL,
  `descripcion` varchar(1000) NOT NULL,
  `precio` double NOT NULL,
  `costoEnvio` double NOT NULL,
  `cantidadTotal` int DEFAULT NULL,
  `cantidadComprada` int DEFAULT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `image` BLOB DEFAULT NULL,
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Laptop Lenovo','Electrónica',15,'Laptop Lenovo con 16GB RAM, 512GB SSD, pantalla de 15.6 pulgadas.',1200.5,25,1,70,NULL,NULL,null),
(2,'iPhone 13','Electrónica',20,'iPhone 13 con 128GB, pantalla OLED de 6.1 pulgadas.',999.99,20,2,100,NULL,NULL,null),
(3,'Samsung Galaxy S21','Electrónica',10,'Samsung Galaxy S21 con 128GB, pantalla de 6.2 pulgadas.',850.75,18,3,200,NULL,NULL,null),
(4,'Silla Gamer','Muebles',30,'Silla gamer ergonómica con soporte lumbar ajustable.',199.99,15,300,300,NULL,NULL,null),
(5,'Teclado Mecánico','Accesorios',50,'Teclado mecánico RGB con switches azules.',75,10,200,500,NULL,NULL,null),
(6,'Monitor 4K','Electrónica',12,'Monitor 4K UHD de 27 pulgadas con HDR.',350,22,80,200,NULL,NULL,null),
(7,'Cámara Canon EOS','Fotografía',8,'Cámara Canon EOS Rebel T7i con lente de 18-55mm.',700.99,30,50,100,NULL,NULL,null),
(8,'Auriculares Bluetooth','Accesorios',25,'Auriculares inalámbricos Bluetooth con cancelación de ruido.',150,8,100,400,NULL,NULL,null),
(9,'Mouse Inalámbrico','Accesorios',60,'Mouse inalámbrico con sensor óptico de alta precisión.',35.99,5,300,700,NULL,NULL,null),
(10,'Impresora HP LaserJet','Oficina',5,'Impresora HP LaserJet con impresión a doble cara automática.',250.5,12,40,400,NULL,NULL,null);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_has_ordenes`
--

DROP TABLE IF EXISTS `producto_has_ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_has_ordenes` (
  `producto_idproducto` int NOT NULL,
  `ordenes_idordenes` int NOT NULL,
  `cantidadxproducto` int DEFAULT NULL,
  PRIMARY KEY (`producto_idproducto`,`ordenes_idordenes`),
  KEY `fk_ordenes` (`ordenes_idordenes`),
  CONSTRAINT `fk_ordenes` FOREIGN KEY (`ordenes_idordenes`) REFERENCES `ordenes` (`idordenes`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_producto` FOREIGN KEY (`producto_idproducto`) REFERENCES `producto` (`idproducto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_has_ordenes`
--

LOCK TABLES `producto_has_ordenes` WRITE;
/*!40000 ALTER TABLE `producto_has_ordenes` DISABLE KEYS */;
INSERT INTO `producto_has_ordenes` VALUES (1,1001,1),(1,1006,6),(1,1011,2),(1,1016,2),(2,1002,2),(2,1007,7),(2,1012,1),(2,1017,3),(3,1003,3),(3,1008,8),(3,1013,3),(3,1018,3),(4,1004,4),(4,1009,9),(4,1014,5),(4,1019,2),(5,1005,5),(5,1010,1),(5,1015,1),(5,1020,1),(5,1021,1);
/*!40000 ALTER TABLE `producto_has_ordenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_has_proveedor`
--

DROP TABLE IF EXISTS `producto_has_proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_has_proveedor` (
  `producto_idproducto` int NOT NULL,
  `proveedor_idproveedor` int NOT NULL,
  PRIMARY KEY (`producto_idproducto`,`proveedor_idproveedor`),
  KEY `fk_producto_has_proveedor_proveedor1_idx` (`proveedor_idproveedor`),
  KEY `fk_producto_has_proveedor_producto1_idx` (`producto_idproducto`),
  CONSTRAINT `fk_producto_has_proveedor_producto1` FOREIGN KEY (`producto_idproducto`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `fk_producto_has_proveedor_proveedor1` FOREIGN KEY (`proveedor_idproveedor`) REFERENCES `proveedor` (`idproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_has_proveedor`
--

LOCK TABLES `producto_has_proveedor` WRITE;
/*!40000 ALTER TABLE `producto_has_proveedor` DISABLE KEYS */;
INSERT INTO `producto_has_proveedor` VALUES (1,1),(2,1),(5,1),(8,1),(1,2),(3,2),(6,2),(9,2),(2,3),(4,3),(7,3),(9,3),(3,4),(5,4),(7,4),(10,4),(4,5),(6,5),(8,5),(10,5);
/*!40000 ALTER TABLE `producto_has_proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos_has_usuario`
--

DROP TABLE IF EXISTS `productos_has_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos_has_usuario` (
  `productos_idproductos` int NOT NULL,
  `usuario_idusuario` int NOT NULL,
  PRIMARY KEY (`productos_idproductos`,`usuario_idusuario`),
  KEY `fk_productos_has_usuario_usuario1_idx` (`usuario_idusuario`),
  KEY `fk_productos_has_usuario_productos1_idx` (`productos_idproductos`),
  CONSTRAINT `fk_productos_has_usuario_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `fk_productos_has_usuario_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos_has_usuario`
--

LOCK TABLES `productos_has_usuario` WRITE;
/*!40000 ALTER TABLE `productos_has_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `productos_has_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `idproveedor` int NOT NULL AUTO_INCREMENT,
  `nombreTienda` varchar(45) NOT NULL,
  `nombreProveedor` varchar(45) NOT NULL,
  `dni` varchar(45) NOT NULL,
  `ruc` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `idzona` int NOT NULL,
  `fotoProveedor` blob,
  `estadoProveedor` varchar(20) DEFAULT NULL,
  `rating` decimal(2,1) DEFAULT NULL,
  PRIMARY KEY (`idproveedor`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  UNIQUE KEY `ruc_UNIQUE` (`ruc`),
  KEY `fk_proveedor_zona2_idx` (`idzona`),
  CONSTRAINT `fk_proveedor_zona2` FOREIGN KEY (`idzona`) REFERENCES `zona` (`idzona`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES 
(1,'Tienda Tech','Carlos Pérez','12345678','20123456781','carlos.perez@tiendatech.com',1,NULL,'Baneado',4.3),
(2,'Electrónica Lima','María García','87654321','20234567892','maria.garcia@electroniclima.com',2,NULL,'Activo',3.8),
(3,'Muebles del Sur','Jorge López','11223344','20345678903','jorge.lopez@mueblesdelsur.com',2,NULL,'Baneado',2.7),
(4,'Fashion Store','Ana Torres','22334455','20456789014','ana.torres@fashionstore.com',3,NULL,'Baneado',4.9),
(5,'Supermercado Central','Luis Ramos','33445566','20567890125','luis.ramos@supercentral.com',3,NULL,'Activo',1.5),
(6,'Tech World','Sofía Mendoza','44556677','20678901236','sofia.mendoza@techworld.com',1,NULL,'Activo',4.1),
(7,'Electro Perú','Fernando Salas','55667788','20789012347','fernando.salas@electroperu.com',4,NULL,'Activo',3.2),
(8,'Construcción Total','Raúl Vega','66778899','20890123458','raul.vega@construcciontotal.com',4,NULL,'Activo',2.9),
(9,'Librería Escolar','Claudia Díaz','77889900','20901234569','claudia.diaz@libreriaescolar.com',2,NULL,'Baneado',4.0),
(10,'Alimentos Frescos','Ricardo Núñez','88990011','21012345670','ricardo.nunez@alimentosfrescos.com',3,NULL,'Activo',3.6);
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `reposicionproductos`
--

DROP TABLE IF EXISTS `reposicionproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reposicionproductos` (
  `idreposicionProductos` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL DEFAULT '24',
  `fechaSolicitud` date NOT NULL,
  `productos_idproductos` int NOT NULL,
  `usuario_idusuario` int NOT NULL,
  PRIMARY KEY (`idreposicionProductos`),
  KEY `fk_reposicionProductos_productos1_idx` (`productos_idproductos`),
  KEY `fk_reposicionproductos_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_reposicionProductos_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `fk_reposicionproductos_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reposicionproductos`
--

LOCK TABLES `reposicionproductos` WRITE;
/*!40000 ALTER TABLE `reposicionproductos` DISABLE KEYS */;
/*!40000 ALTER TABLE `reposicionproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resenias`
--

DROP TABLE IF EXISTS `resenias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resenias` (
  `idresenias` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(500) NOT NULL,
  `calidad` varchar(45) NOT NULL,
  `rapidez` varchar(45) NOT NULL,
  `puntuacion` int NOT NULL,
  `foto` blob,
  `respuesta` varchar(45) DEFAULT NULL,
  `productos_idproductos` int NOT NULL,
  `tituloForo` varchar(200) DEFAULT NULL,
  `descripcionForo` varchar(200) DEFAULT NULL,
  `usuario_idusuario` int DEFAULT NULL,
  `tipoPublicacion` varchar(45) NOT NULL,
  PRIMARY KEY (`idresenias`),
  KEY `fk_resenias_productos1_idx` (`productos_idproductos`),
  KEY `fk_resenias_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_resenias_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `fk_resenias_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resenias`
--

LOCK TABLES `resenias` WRITE;
/*!40000 ALTER TABLE `resenias` DISABLE KEYS */;
/*!40000 ALTER TABLE `resenias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respuestas`
--

DROP TABLE IF EXISTS `respuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `respuestas` (
  `resenias_idresenias` int NOT NULL,
  `usuario_idusuario` int NOT NULL,
  `respuestas` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`resenias_idresenias`,`usuario_idusuario`),
  KEY `fk_resenias_has_usuario_usuario1_idx` (`usuario_idusuario`),
  KEY `fk_resenias_has_usuario_resenias1_idx` (`resenias_idresenias`),
  CONSTRAINT `fk_resenias_has_usuario_resenias1` FOREIGN KEY (`resenias_idresenias`) REFERENCES `resenias` (`idresenias`),
  CONSTRAINT `fk_resenias_has_usuario_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respuestas`
--

LOCK TABLES `respuestas` WRITE;
/*!40000 ALTER TABLE `respuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `respuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `idRoles` int NOT NULL AUTO_INCREMENT,
  `rol` varchar(50) NOT NULL,
  PRIMARY KEY (`idRoles`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Superadmin'),(2,'Coordinador'),(3,'Agente'),(4,'Usuario');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `dni` varchar(45) NOT NULL,
  `contrasena` varchar(60) NOT NULL,
  `direccion` varchar(256) DEFAULT NULL,
  `ruc` varchar(45) DEFAULT NULL,
  `notificaciones` varchar(1024) DEFAULT NULL,
  `correo` varchar(45) NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `estadoUsuario` varchar(45) DEFAULT NULL,
  `razonSocial` varchar(100) DEFAULT NULL,
  `solicitudAgente` tinyint DEFAULT NULL,
  `idroles` int NOT NULL,
  `distritos_iddistritos` int DEFAULT NULL,
  `idzona` int NOT NULL,
  `fechanacim` date DEFAULT NULL,
  `idproveedor` int DEFAULT NULL,
  `motivo` varchar(45) DEFAULT NULL,
  `idsuperior` int DEFAULT NULL,
  `cantidadcompras` int DEFAULT NULL,
  `fotos_idfotos` int DEFAULT NULL,
  `isban` tinyint DEFAULT NULL,
  `calificacion` int DEFAULT NULL,
  `despachador` varchar(50) DEFAULT NULL,
  `jurisdiccion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  UNIQUE KEY `correo_UNIQUE` (`correo`),
  UNIQUE KEY `ruc_UNIQUE` (`ruc`),
  KEY `fk_usuario_roles1_idx` (`idroles`),
  KEY `fk_usuario_distritos1_idx` (`distritos_iddistritos`),
  KEY `fk_usuario_zona1_idx` (`idzona`),
  KEY `fk_usuario_proveedor1_idx` (`idproveedor`),
  KEY `fk_usuario_usuario1_idx` (`idsuperior`),
  KEY `fk_usuario_fotos1_idx` (`fotos_idfotos`),
  KEY `fk_usuario_despachador1_idx` (`despachador`),
  KEY `fk_usuario_jurisdiccion1_idx` (`jurisdiccion`),
  CONSTRAINT `fk_usuario_distritos1` FOREIGN KEY (`distritos_iddistritos`) REFERENCES `distritos` (`iddistritos`),
  CONSTRAINT `fk_usuario_fotos1` FOREIGN KEY (`fotos_idfotos`) REFERENCES `fotos` (`idfotos`),
  CONSTRAINT `fk_usuario_proveedor1` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`),
  CONSTRAINT `fk_usuario_roles1` FOREIGN KEY (`idroles`) REFERENCES `roles` (`idRoles`),
  CONSTRAINT `fk_usuario_usuario1` FOREIGN KEY (`idsuperior`) REFERENCES `usuario` (`idusuario`),
  CONSTRAINT `fk_usuario_zona1` FOREIGN KEY (`idzona`) REFERENCES `zona` (`idzona`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES 
(1,'Juan','Pérez','87654301','$2a$10$m.uauzBWuQVoR7qJge1O3ukcYO2vnIp2wJVTLy6UdDHpkeSAm/WkK','Calle Luna 100','20123456701','Notificaciones Activas','juan.perez@example.com','987654321','Activo','Pérez SAC',0,1,1,1,'1985-05-15',NULL,NULL,NULL,45,NULL,0,NULL,NULL,NULL),
(2,'Maria','López','87654302','$2a$10$gaAmMNaWXUrmMHMHaO1FRuwdPwBb1aF1EQSJBZeAaAF.wqZAhSMDa','Av. Sol 200','20123456702','Notificaciones Inactivas','maria.lopez@example.com','912345678','Activo','López EIRL',0,2,1,1,'1990-01-17',NULL,NULL,NULL,0,NULL,0,NULL,NULL,NULL),
(3,'Carlos','García','87654303','$2a$10$jEnQpKDadz9NIr3TULSqE.ABdrwqlEsFyCqQ6gJMCGvNYsAM.iUii','Calle Estrella 300','20123456703','Notificaciones Activas','carlos.garcia@example.com','923456789','Activo','García SAC',0,3,2,1,'1988-08-24',NULL,NULL,NULL,0,NULL,0,3,'DESP001','JURIS001'),
(4,'Laura','Ramírez','87654304','$2a$10$ACFCClib67XkFnKyOQ/z4.O/sHOchI1cE7x7PBQDiezmA/ZzCq1J.','Av. Luna 400','20123456704','Notificaciones Inactivas','laura.ramirez@example.com','934567890','Activo','Ramírez EIRL',0,3,3,1,'1992-11-15',NULL,NULL,2,100,NULL,0,2,'DESP002','JURIS002'),
(5,'Pedro','Fernández','87654355','$2a$10$R8NzT3unrbtdi0aJ90gQ1.rA12OhVJfdQrth87MVo5OyY2eKJGYwS','Calle Mar 500','20123456755','Notificaciones Activas','pedro.fernandez@example.com','945678901','Activo','Fernández SAC',0,3,4,1,'1989-02-22',NULL,NULL,2,30,NULL,0,1,'DESP003','JURIS003'),
(6,'Elena','Castro','87654306','$2a$10$24Jx5FRaYqR7rQ8gWKFRN.TzHVIt6SyH2y8KJ7fnysqlQwt5VbIae','Av. Roca 600','20123456706','Notificaciones Activas','elena.castro@example.com','956789012','Activo','Castro EIRL',0,2,9,2,'1993-11-11',NULL,'Violación de términos',3,10,NULL,0,NULL,NULL,NULL),
(7,'Raúl','Torres','87654307','$2a$10$4vkMwb9I94wS9ZQV/FyjY.IFEmUcm3vRlgfw2K7PjXkFkPHzfKwry','Calle Nube 700','20123456707','Notificaciones Inactivas','raul.torres@example.com','967890123','Activo','Torres SAC',0,3,10,2,'1991-06-07',NULL,NULL,3,5,NULL,0,1,'DESP004','JURIS004'),
(8,'Sofía','Vargas','87654308','$2a$10$lAIIRSR9rENen8g/cgMVwe7zG1UXmpQ2jyl9fKc.Xbt2dVVSuoxZ2','Av. Arena 800','20123456708','Notificaciones Activas','sofia.vargas@example.com','978901234','Activo','Vargas EIRL',0,3,11,2,'1987-01-12',NULL,NULL,NULL,6,NULL,0,2,'DESP005','JURIS005'),
(9,'Jorge','Díaz','87654309','$2a$10$nnBbYLxc7dtzvmJmQ.i7YuGo759bVAAsHQvZAl8LUuxQMQDiEnYpa','Calle Sol 900','20123456709','Notificaciones Inactivas','jorge.diaz@example.com','989012345','Activo','Díaz SAC',0,3,12,2,'1994-11-09',NULL,NULL,NULL,7,NULL,0,3,'DESP006','JURIS006'),
(10,'Lucía','Mendoza','87654310','$2a$10$fPitFhE8dzg0KlZKSSw5UOOll0lvjL9xdyyAKOnQixNOexFPU8DfK','Av. Viento 1000','20123456710','Notificaciones Activas','lucia.mendoza@example.com','990123883','Activo','Mendoza EIRL',0,2,19,3,'1986-09-20',NULL,NULL,NULL,8,NULL,10,NULL,NULL,NULL),
(11,'Ana','Rojas','87654311','$2a$10$24N6LMS/LhWP5cCAZVld7.gSGY0g4tn.B5zsbNOCdZufN3xrZN3KO','Calle Fuego 1100','20123456711','Notificaciones Inactivas','ana.rojas@example.com','991234567','Activo','Rojas SAC',0,3,20,3,'1990-12-27',NULL,NULL,9,9,NULL,0,5,'DESP007','JURIS007'),
(12,'Luis','Ortiz','87654312','$2a$10$FQY3FTi7cXogEn8bhXsq7.dDV2oVNRlqOUa92lnKG/xNev3lHxeS.','Av. Brisa 1200','20123456712','Notificaciones Activas','luis.ortiz@example.com','992345678','Activo','Ortiz EIRL',0,3,21,3,'1988-12-21',NULL,NULL,10,14,NULL,0,4,'DESP008','JURIS008'),
(13,'Gloria','Paredes','87654313','$2a$10$cFmSxOdY7R998zA/XC30PuKLKKEdatBUy6W/zMM5wvM9./CWynd3u','Calle Trueno 1300','20123456713','Notificaciones Activas','gloria.paredes@example.com','993456789','Activo','Paredes SAC',0,3,24,3,'1992-12-02',NULL,NULL,10,23,NULL,0,4,'DESP009','JURIS009'),
(14,'Andrés','Guzmán','87654314','$2a$10$vBPcQ2qFgjB25Jw50ux.puNmnloDUC1J0ljs8AUjsxzgpGkk/j3UO','Av. Relámpago 1400','20123456714','Notificaciones Inactivas','andres.guzman@example.com','994567890','Activo','Guzmán EIRL',0,2,26,4,'1989-05-04',NULL,NULL,NULL,33,NULL,0,NULL,NULL,NULL),
(15,'Patricia','Montes','87654315','$2a$10$N79rHHVeZeXY6JEm6sRi4eqKIIbZLp8mrHEK9w2KD2fxcQgx91F.G','Calle Lluvia 1500','20123456715','Notificaciones Activas','patricia.montes@example.com','995678901','Activo','Montes SAC',0,3,42,4,'1993-05-15',NULL,NULL,NULL,54,NULL,0,2,'DESP010','JURIS010'),
(16,'Gabriel','Salazar','87654316','$2a$10$WJFzk68ozO9u1Z6DIdgq3epJLdnTMQNmjGzOD/kh/Flnivdqb9Tii','Av. Viento 1600','20123456716','Notificaciones Inactivas','gabriel.salazar@example.com','996789012','Activo','Salazar EIRL',0,3,28,4,'1991-01-28',NULL,NULL,NULL,21,NULL,0,3,'DESP011','JURIS011'),
(17,'Daniela','Cruz','87654317','$2a$10$XymLh0gUqZyHnMLXTjxc3uOEW4OdEEQ3MUKw6WXW3gbcb5rYnhSzu','Calle Rayo 1700','20123456717','Notificaciones Activas','daniela.cruz@example.com','997890123','Activo','Cruz SAC',0,3,29,4,'1987-03-11',NULL,NULL,NULL,20,NULL,0,4,'DESP012','JURIS012'),
(18,'Hugo','Mejía','87654318','$2a$10$ypIEXVggJUyz/isJq9iB0.XVz8.x4ApXrNO6wWDxyR6fBDlMNFb1W','Av. Truenos 1800','20123456718','Notificaciones Inactivas','hugo.mejia@example.com','998901234','Activo','Mejía EIRL',1,4,1,1,'1990-07-30',NULL,NULL,NULL,15,NULL,0,NULL,'DESP013','JURIS013'),
(19,'Camila','Vega','87654319','$2a$10$D.7eKKMrvBkYgJlyg370NOzp8rSQNhdy181F7IbXmySbGdYmAvjbq','Calle Ríos 1900','20123456719','Notificaciones Activas','camila.vega@example.com','999012345','Baneado','Vega SAC',0,4,2,1,'1994-08-10',NULL,'Actividad sospechosa',NULL,13,NULL,1,NULL,'DESP014','JURIS014'),
(20,'Alonso','Valdez','87654320','$2a$10$vJ0yCddrmhOV5BM8N6AH.OaJcm55CYjJZggwm7jdxfU9RU4elU/Du','Av. Roca 2000','20123456720','Notificaciones Inactivas','alonso.valdez@example.com','990123456','Inactivo','Valdez EIRL',0,4,15,2,'1986-08-03',NULL,NULL,NULL,17,NULL,0,NULL,'DESP015','JURIS015'),
(21,'Laura','Martínez','87654377','$2a$10$UzUUkQiMK8EmrFOWQ/TLUeZPrrgV7fhqAg/XjrEGYd9xN/m8l3R9S','Calle Sol 400','20123456777','Notificaciones Activas','laura.martinez@example.com','934567890','Activo','Martínez SRL',0,4,16,2,'1995-02-28',NULL,NULL,NULL,20,NULL,0,NULL,'DESP016','JURIS016'),
(22,'Javier','Rodríguez','87654305','$2a$10$kLDp7wBEA/Ygl6RU2mEiguIeWRACD62NVgLBSjIZaiWovzp43V.EW','Av. Estrella 500','20123456705','Notificaciones Inactivas','javier.rodriguez@example.com','945678901','Activo','Rodríguez EIRL',0,4,23,3,'1987-03-15',NULL,NULL,NULL,15,NULL,0,NULL,'DESP017','JURIS017'),
(23,'Ana','González','87654366','$2a$10$EPKAuguC7.urpJ4itWnCleNit06o6DVoLUuFgpEkSznoQa1t7ETQG','Calle Mar 600','20123456766','Notificaciones Activas','ana.gonzalez@example.com','956789012','Activo','González SAC',1,4,22,3,'1992-11-11',NULL,NULL,NULL,10,NULL,0,NULL,'DESP018','JURIS018'),
(24,'Luis','Hernández','87654387','$2a$10$YLp2nze36xy.97TKSnqnFu2vLQYeUG49m52X906d2ytxcCf.3F0ia','Calle Tierra 700','20123456787','Notificaciones Activas','luis.hernandez@example.com','967890123','Activo','Hernández Ltda.',1,4,35,4,'1985-06-30',NULL,NULL,NULL,5,NULL,0,NULL,'DESP019','JURIS019'),
(25,'Sofía','Morales','87654388','$2a$10$sKgjOMiYCJK8jXSE7xOKNeMYYf4ob8BdmW7PfuslONajMfu7YJ1AC','Av. Agua 800','20123456788','Notificaciones Inactivas','sofia.morales@example.com','978901234','Activo','Morales EIRL',0,4,36,4,'1990-09-09',NULL,NULL,NULL,25,NULL,0,NULL,'DESP020','JURIS020'),
(26,'Fernando','Vásquez','87654399','$2a$10$ufLoBCdkPxw/pButrHjP1OO96kAvb0vopgar4joCuFKGBSae9WajW','Calle Fuego 900','20123456799','Notificaciones Activas','fernando.vasquez@example.com','989012345','Activo','Vásquez SAC',1,4,37,4,'1983-04-21',NULL,NULL,NULL,30,NULL,0,NULL,'DESP021','JURIS021'),
(27,'Claudia','Cruz','87654400','$2a$10$7tgKsn1sjQhOL5fXrxZuH.aDsDVXmZcmC9IxK4riKSFIlFXQEVoB6','Av. Viento 1000','20123456700','Notificaciones Inactivas','claudia.cruz@example.com','990123456','Baneado','Cruz Ltda.',1,4,38,4,'1996-12-05',NULL,'Comportamiento inapropiado',NULL,12,NULL,1,NULL,'DESP022','JURIS022');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zona`
--

DROP TABLE IF EXISTS `zona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zona` (
  `idzona` int NOT NULL,
  `nombreZona` varchar(45) NOT NULL,
  PRIMARY KEY (`idzona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zona`
--

LOCK TABLES `zona` WRITE;
/*!40000 ALTER TABLE `zona` DISABLE KEYS */;
INSERT INTO `zona` VALUES (1,'Norte'),(2,'Sur'),(3,'Este'),(4,'Oeste');
/*!40000 ALTER TABLE `zona` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-11 15:41:49
