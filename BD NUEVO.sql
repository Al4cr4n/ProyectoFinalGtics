CREATE DATABASE  IF NOT EXISTS `db_grupo2` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_grupo2`;
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

--
-- Table structure for table `codigodespachador`
--

DROP TABLE IF EXISTS `codigodespachador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigodespachador` (
  `idcodigoDespachador` int NOT NULL AUTO_INCREMENT,
  `codigoDespachador` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`idcodigoDespachador`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigodespachador`
--

LOCK TABLES `codigodespachador` WRITE;
/*!40000 ALTER TABLE `codigodespachador` DISABLE KEYS */;
INSERT INTO `codigodespachador` VALUES (1,'DESP001','Activo'),
(2,'DESP002','Inactivo'),(3,'DESP003','Activo'),
(4,'DESP004','Pendiente'),(5,'DESP005','Activo'),
(6,'DESP006','Inactivo'),(7,'DESP007','Activo'),
(8,'DESP008','Inactivo'),(9,'DESP009','Pendiente'),
(10,'DESP010','Activo'),(11,'DESP011','Activo'),
(12,'DESP012','Inactivo'),(13,'DESP013','Pendiente'),
(14,'DESP014','Activo'),(15,'DESP015','Inactivo'),
(16,'DESP016','Activo'),(17,'DESP017','Pendiente'),
(18,'DESP018','Activo'),(19,'DESP019','Inactivo'),
(20,'DESP020','Pendiente'),(21,'DESP021','Activo'),
(22,'DESP022','Pendiente'),(23,'DESP023','Inactivo'),(24,'DESP024','Activo'),(25,'DESP025','Pendiente'),(26,'DESP026','Inactivo'),(27,'DESP027','Activo'),(28,'DESP028','Inactivo'),(29,'DESP029','Pendiente'),(30,'DESP030','Activo'),(31,'DESP031','Activo'),(32,'DESP032','Inactivo'),(33,'DESP033','Pendiente'),(34,'DESP034','Activo'),(35,'DESP035','Pendiente'),(36,'DESP036','Inactivo'),(37,'DESP037','Activo'),(38,'DESP038','Pendiente'),(39,'DESP039','Inactivo'),(40,'DESP040','Activo');
/*!40000 ALTER TABLE `codigodespachador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosjurisdiccion`
--

DROP TABLE IF EXISTS `codigosjurisdiccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosjurisdiccion` (
  `idcodigos` int NOT NULL AUTO_INCREMENT,
  `codigoJurisdiccion` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`idcodigos`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosjurisdiccion`
--

LOCK TABLES `codigosjurisdiccion` WRITE;
/*!40000 ALTER TABLE `codigosjurisdiccion` DISABLE KEYS */;
INSERT INTO `codigosjurisdiccion` VALUES (36,'JUR001','Activo'),(37,'JUR002','Inactivo'),(38,'JUR003','Activo'),(39,'JUR004','Pendiente'),(40,'JUR005','Activo'),(41,'JUR006','Inactivo'),(42,'JUR007','Activo');
/*!40000 ALTER TABLE `codigosjurisdiccion` ENABLE KEYS */;
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
INSERT INTO `distritos` VALUES 
-- NORTE --
(1,'Ancon',1),(2,'Santa Rosa',1),(3,'Carabayllo',1),(4,'Puente Piedra',1),(5, 'Comas', 1),
(6, 'Los Olivos', 1),(7, 'San Martín de Porres', 1),(8, 'Independencia', 1),
-- SUR --
(9, 'San Juan de Miraflores', 2),(10, 'Villa María del Triunfo', 2),
(11, 'Villa el Salvador', 2),(12, 'Pachacamac', 2),
(13, 'Lurín', 2),(14, 'Punta Hermosa', 2),
(15, 'Punta Negra', 2),(16, 'San Bartolo', 2),
(17, 'Santa María del Mar', 2),(18, 'Pucusana', 2),
-- ESTE --
(19, 'San Juan de Lurigancho', 3),(20, 'Lurigancho/Chosica', 3),
(21, 'Ate', 3),(22, 'El Agustino', 3),
(23, 'Santa Anita', 3),(24, 'La Molina', 3),
(25, 'Cieneguilla', 3),
-- OESTE --
(26, 'Rimac', 4),(27, 'Cercado de Lima', 4),(28, 'Breña', 4),
(29, 'Pueblo Libre', 4),(30, 'Magdalena', 4),(31, 'Jesus Maria', 4),
(32, 'La Victoria', 4),(33, 'Lince', 4),(34, 'San Isidro', 4),
(35, 'San Miguel', 4),(36, 'Surquillo', 4),(37, 'San Borja', 4),
(38, 'Santiago de Surco', 4),(39, 'Barranco', 4),(40, 'Chorrillos', 4),
(41, 'San Luis', 4),(42, 'Miraflores', 4);
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
  `foto1` blob NOT NULL,
  `productos_idproductos` int NOT NULL,
  PRIMARY KEY (`idfotos`),
  KEY `fk_fotos_productos1_idx` (`productos_idproductos`),
  CONSTRAINT `fk_fotos_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotos`
--

LOCK TABLES `fotos` WRITE;
/*!40000 ALTER TABLE `fotos` DISABLE KEYS */;
/*!40000 ALTER TABLE `fotos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `ordenes` (
  `idordenes` int NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` varchar(45) NOT NULL,
  `fechaArribo` date NOT NULL,
  `usuario_idusuario` int NOT NULL,
  `mesCreacion` varchar(20) NOT NULL,  
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  KEY `fk_ordenes_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_ordenes_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Insertando datos en `ordenes`
--

LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;

INSERT INTO `ordenes` (idordenes, estadoOrdenes, fechaArribo, usuario_idusuario, mesCreacion) VALUES 
(1, 'CREADO', '2024-09-22', 1, 'Enero'),
(2, 'CREADO', '2024-09-23', 2, 'Febrero'),
(3, 'CREADO', '2024-09-24', 3, 'Marzo'),
(4, 'CREADO', '2024-09-25', 4, 'Abril'),

(5, 'EN VALIDACIÓN', '2024-09-26', 5, 'Mayo'),
(6, 'EN VALIDACIÓN', '2024-09-27', 6, 'Junio'),
(7, 'EN VALIDACIÓN', '2024-09-28', 7, 'Julio'),
(8, 'EN VALIDACIÓN', '2024-09-29', 8, 'Agosto'),

(9, 'EN PROCESO', '2024-09-30', 9, 'Septiembre'),
(10, 'EN PROCESO', '2024-10-01', 10, 'Octubre'),
(11, 'EN PROCESO', '2024-10-02', 11, 'Noviembre'),
(12, 'EN PROCESO', '2024-10-03', 12, 'Diciembre'),

(13, 'ARRIBO AL PAÍS', '2024-10-04', 13, 'Enero'),
(14, 'ARRIBO AL PAÍS', '2024-10-05', 14, 'Febrero'),
(15, 'ARRIBO AL PAÍS', '2024-10-06', 15, 'Marzo'),
(16, 'ARRIBO AL PAÍS', '2024-10-07', 16, 'Abril'),

(17, 'EN ADUANAS', '2024-10-08', 17, 'Mayo'),
(18, 'EN ADUANAS', '2024-10-09', 18, 'Junio'),
(19, 'EN ADUANAS', '2024-10-10', 19, 'Julio'),
(20, 'EN ADUANAS', '2024-10-11', 20, 'Agosto'),

(21, 'EN RUTA', '2024-10-12', 21, 'Septiembre'),
(22, 'EN RUTA', '2024-10-13', 22, 'Octubre'),
(23, 'EN RUTA', '2024-10-14', 23, 'Noviembre'),
(24, 'EN RUTA', '2024-10-15', 24, 'Diciembre'),

(25, 'RECIBIDO', '2024-10-16', 25, 'Enero'),
(26, 'RECIBIDO', '2024-10-17', 26, 'Febrero'),
(27, 'RECIBIDO', '2024-10-18', 27, 'Marzo'),
(28, 'RECIBIDO', '2024-10-19', 28, 'Abril');

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
  `numeroTarjeta` date NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
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
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Laptop Lenovo','Electrónica',15,'Laptop Lenovo con 16GB RAM, 512GB SSD, pantalla de 15.6 pulgadas.',1200.5,25,1,70),(2,'iPhone 13','Electrónica',20,'iPhone 13 con 128GB, pantalla OLED de 6.1 pulgadas.',999.99,20,2,100),(3,'Samsung Galaxy S21','Electrónica',10,'Samsung Galaxy S21 con 128GB, pantalla de 6.2 pulgadas.',850.75,18,3,200),(4,'Silla Gamer','Muebles',30,'Silla gamer ergonómica con soporte lumbar ajustable.',199.99,15,300,300),(5,'Teclado Mecánico','Accesorios',50,'Teclado mecánico RGB con switches azules.',75,10,200,500),(6,'Monitor 4K','Electrónica',12,'Monitor 4K UHD de 27 pulgadas con HDR.',350,22,80,200),(7,'Cámara Canon EOS','Fotografía',8,'Cámara Canon EOS Rebel T7i con lente de 18-55mm.',700.99,30,50,100),(8,'Auriculares Bluetooth','Accesorios',25,'Auriculares inalámbricos Bluetooth con cancelación de ruido.',150,8,100,400),(9,'Mouse Inalámbrico','Accesorios',60,'Mouse inalámbrico con sensor óptico de alta precisión.',35.99,5,300,700),(10,'Impresora HP LaserJet','Oficina',5,'Impresora HP LaserJet con impresión a doble cara automática.',250.5,12,40,400);
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
INSERT INTO `producto_has_proveedor` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(5,2),(3,3),(4,3);
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
INSERT INTO `productos_has_usuario` VALUES (1,1),(4,1),(1,2),(2,2),(3,2),(3,3),(5,3),(1,4),(2,4);
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
INSERT INTO `proveedor` VALUES (1,'Tienda Tech','Carlos Pérez','12345678','20123456781','carlos.perez@tiendatech.com',1,NULL,'Activo'),(2,'Electrónica Lima','María García','87654321','20234567892','maria.garcia@electroniclima.com',2,NULL,'Baneado'),(3,'Muebles del Sur','Jorge López','11223344','20345678903','jorge.lopez@mueblesdelsur.com',2,NULL,'Activo'),(4,'Fashion Store','Ana Torres','22334455','20456789014','ana.torres@fashionstore.com',3,NULL,'Baneado'),(5,'Supermercado Central','Luis Ramos','33445566','20567890125','luis.ramos@supercentral.com',3,NULL,'Activo'),(6,'Tech World','Sofía Mendoza','44556677','20678901236','sofia.mendoza@techworld.com',1,NULL,'Baneado'),(7,'Electro Perú','Fernando Salas','55667788','20789012347','fernando.salas@electroperu.com',4,NULL,'Activo'),(8,'Construcción Total','Raúl Vega','66778899','20890123458','raul.vega@construcciontotal.com',4,NULL,'Baneado'),(9,'Librería Escolar','Claudia Díaz','77889900','20901234569','claudia.diaz@libreriaescolar.com',2,NULL,'Activo'),(10,'Alimentos Frescos','Ricardo Núñez','88990011','21012345670','ricardo.nunez@alimentosfrescos.com',3,NULL,'Baneado');
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
  `cantidad` int NOT NULL,
  `fechaSolicitud` date NOT NULL,
  `productos_idproductos` int NOT NULL,
  PRIMARY KEY (`idreposicionProductos`),
  KEY `fk_reposicionProductos_productos1_idx` (`productos_idproductos`),
  CONSTRAINT `fk_reposicionProductos_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reposicionproductos`
--

LOCK TABLES `reposicionproductos` WRITE;
/*!40000 ALTER TABLE `reposicionproductos` DISABLE KEYS */;
INSERT INTO `reposicionproductos` VALUES (36,50,'2024-09-01',1),(37,30,'2024-09-05',2),(38,20,'2024-09-10',3),(39,100,'2024-09-12',1),(40,15,'2024-09-15',4),(41,45,'2024-09-20',2),(42,70,'2024-09-22',3);
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
  `usuario_idusuario` int NULL,
  `tipoPublicacion` varchar(45) NOT NULL,
  PRIMARY KEY (`idresenias`),
  KEY `fk_resenias_productos1_idx` (`productos_idproductos`),
  KEY `fk_resenias_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_resenias_productos1` FOREIGN KEY (`productos_idproductos`) REFERENCES `producto` (`idproducto`),
  CONSTRAINT `fk_resenias_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resenias`
--

LOCK TABLES `resenias` WRITE;
/*!40000 ALTER TABLE `resenias` DISABLE KEYS */;
INSERT INTO `resenias` VALUES (26,'Excelente producto, lo recomendaría a todos.','Alta','Rápida',5,NULL,'Gracias por tu comentario!',1,'Recomendación de Producto A','Me encantó el Producto A, superó mis expectativas.',29,'Foro'),
(27,'Buen rendimiento, aunque podría mejorar la calidad.','Media','Moderada',4,NULL,'Agradecemos tu feedback.',2,'Opinión sobre Producto B','El Producto B es bueno, pero creo que necesita ajustes.',30,'Foro'),
(28,'No estoy satisfecho con el servicio.','Baja','Lenta',2,NULL,'Lamentamos que hayas tenido esta experiencia.',3,'Crítica del Producto C','El Producto C no cumplió con mis expectativas.',31,'Reseña'),
(29,'Gran relación calidad-precio.','Alta','Rápida',5,NULL,'Nos alegra que lo pienses así.',1,'Producto D recomendado','El Producto D es muy bueno y económico.',32,'Foro'),
(30,'El producto llegó dañado.','Baja','Lenta',1,NULL,'Lamentamos lo ocurrido, te ayudaremos.',4,'Problema con el Producto E','No estoy contento con la entrega, llegó en mal estado.',33,'Reseña');
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
  `apellido` varchar(45) NULL,
  `dni` varchar(45) NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  `direccion` varchar(256) NULL,
  `ruc` varchar(45) NULL,
  `notificaciones` varchar(1024) DEFAULT NULL,
  `correo` varchar(45) NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `estadoUsuario` varchar(45) DEFAULT NULL,
  `codigoJurisdiccion` varchar(45) DEFAULT NULL,
  `codigoDespachador` varchar(45) DEFAULT NULL,
  `razonSocial` varchar(100) DEFAULT NULL,
  `solicitudAgente` tinyint DEFAULT NULL,
  `isBan` tinyint NOT NULL DEFAULT 0,
  `idroles` int NOT NULL,
  `distritos_iddistritos` int DEFAULT NULL,
  `idzona` int NOT NULL,
  `fechanacim` date DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `idproveedor` int NULL,
  `idcodigodespachador` int DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  UNIQUE KEY `correo_UNIQUE` (`correo`),
  UNIQUE KEY `ruc_UNIQUE` (`ruc`),
  KEY `fk_usuario_roles1_idx` (`idroles`),
  KEY `fk_usuario_distritos1_idx` (`distritos_iddistritos`),
  KEY `fk_usuario_zona1_idx` (`idzona`),
  KEY `fk_usuario_proveedor1_idx` (`idproveedor`),
  KEY `fk_usuario_codigodespachador1_idx` (`idcodigodespachador`),
  CONSTRAINT `fk_usuario_codigodespachador1` FOREIGN KEY (`idcodigodespachador`) REFERENCES `codigodespachador` (`idcodigoDespachador`),
  CONSTRAINT `fk_usuario_distritos1` FOREIGN KEY (`distritos_iddistritos`) REFERENCES `distritos` (`iddistritos`),
  CONSTRAINT `fk_usuario_proveedor1` FOREIGN KEY (`idproveedor`) REFERENCES `proveedor` (`idproveedor`),
  CONSTRAINT `fk_usuario_roles1` FOREIGN KEY (`idroles`) REFERENCES `roles` (`idRoles`),
  CONSTRAINT `fk_usuario_zona1` FOREIGN KEY (`idzona`) REFERENCES `zona` (`idzona`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Juan','Pérez','87654301','pass111','Calle Luna 100','20123456701','Notificaciones Activas','juan.perez@example.com','987654321','Activo','JUR101','DES401','Pérez SAC',0,0,1,1,1,NULL,'activo',1,NULL),
(2,'Maria','López','87654302','pass222','Av. Sol 200','20123456702','Notificaciones Inactivas','maria.lopez@example.com','912345678','Activo','JUR102','DES402','López EIRL',0,0,3,11,2,'1993-01-17','activo',1,2),
(3,'Carlos','García','87654303','pass333','Calle Estrella 300','20123456703','Notificaciones Activas','carlos.garcia@example.com','923456789','Inactivo','JUR103','DES403','García SAC',0,0,3,7,1,'1994-08-24','activo',10,1),
(4,'Laura','Ramírez','87654304','pass444','Av. Luna 400','20123456704','Notificaciones Inactivas','laura.ramirez@example.com','934567890','Activo','JUR104','DES404','Ramírez EIRL',0,0,2,3,1,'1987-11-15','activo',2,NULL),
(5,'Pedro','Fernández','87654305','pass555','Calle Mar 500','20123456705','Notificaciones Activas','pedro.fernandez@example.com','945678901','Inactivo','JUR105','DES405','Fernández SAC',0,0,2,5,1,'1990-02-22','activo',3,NULL),
(6,'Elena','Castro','87654306','pass666','Av. Roca 600','20123456706','Notificaciones Activas','elena.castro@example.com','956789012','Activo','JUR106','DES406','Castro EIRL',0,0,3,12,2,'1987-11-11','activo',2,3),
(7,'Raúl','Torres','87654307','pass777','Calle Nube 700','20123456707','Notificaciones Inactivas','raul.torres@example.com','967890123','Activo','JUR107','DES407','Torres SAC',1,0,4,3,1,'1994-06-07','baneado',7,8),
(8,'Sofía','Vargas','87654308','pass888','Av. Arena 800','20123456708','Notificaciones Activas','sofia.vargas@example.com','978901234','Inactivo','JUR108','DES408','Vargas EIRL',0,0,4,13,2,'1994-01-12','inactivo',2,13),
(9,'Jorge','Díaz','87654309','pass999','Calle Sol 900','20123456709','Notificaciones Inactivas','jorge.diaz@example.com','989012345','Activo','JUR109','DES409','Díaz SAC',1,0,4,23,3,'1993-11-09','activo',7,18),
(10,'Lucía','Mendoza','87654310','pass1010','Av. Viento 1000','20123456710','Notificaciones Activas','lucia.mendoza@example.com','990123883','Activo','JUR110','DES410','Mendoza EIRL',1,0,4,4,1,'1993-09-20','activo',8,9),
(11,'Ana','Rojas','87654311','pass1111','Calle Fuego 1100','20123456711','Notificaciones Inactivas','ana.rojas@example.com','991234567','Activo','JUR111','DES411','Rojas SAC',1,0,4,5,1,'1989-12-27','activo',9,10),
(12,'Luis','Ortiz','87654312','pass1212','Av. Brisa 1200','20123456712','Notificaciones Activas','luis.ortiz@example.com','992345678','Inactivo','JUR112','DES412','Ortiz EIRL',1,0,4,24,3,'1988-12-21','inactivo',8,19),
(13,'Gloria','Paredes','87654313','pass1313','Calle Trueno 1300','20123456713','Notificaciones Activas','gloria.paredes@example.com','993456789','Activo','JUR113','DES413','Paredes SAC',0,0,4,14,2,'1995-12-02','inactivo',3,14),
(14,'Andrés','Guzmán','87654314','pass1414','Av. Relámpago 1400','20123456714','Notificaciones Inactivas','andres.guzman@example.com','994567890','Inactivo','JUR114','DES414','Guzmán EIRL',0,0,4,6,1,'1995-05-04','activo',10,11),
(15,'Patricia','Montes','87654315','pass1515','Calle Lluvia 1500','20123456715','Notificaciones Activas','patricia.montes@example.com','995678901','Activo','JUR115','DES415','Montes SAC',1,0,4,15,2,'1989-05-15','activo',4,15),
(16,'Gabriel','Salazar','87654316','pass1616','Av. Viento 1600','20123456716','Notificaciones Inactivas','gabriel.salazar@example.com','996789012','Activo','JUR116','DES416','Salazar EIRL',1,0,4,23,3,'1987-01-28','inactivo',9,20),
(17,'Daniela','Cruz','87654317','pass1717','Calle Rayo 1700','20123456717','Notificaciones Activas','daniela.cruz@example.com','997890123','Inactivo','JUR117','DES417','Cruz SAC',0,0,4,16,2,'1987-03-11','inactivo',5,16),
(18,'Hugo','Mejía','87654318','pass1818','Av. Truenos 1800','20123456718','Notificaciones Inactivas','hugo.mejia@example.com','998901234','Activo','JUR118','DES418','Mejía EIRL',1,0,4,7,1,'1988-07-30','activo',1,12),
(19,'Camila','Vega','87654319','pass1919','Calle Ríos 1900','20123456719','Notificaciones Activas','camila.vega@example.com','999012345','Activo','JUR119','DES419','Vega SAC',1,0,4,17,2,'1992-10-17','activo',6,17),
(20,'Alonso','Valdez','87654320','pass2020','Av. Roca 2000','20123456720','Notificaciones Inactivas','alonso.valdez@example.com','990123456','Inactivo','JUR120','DES420','Valdez EIRL',0,0,4,22,3,'1989-08-03','inactivo',10,21),
(21,'Diego','Navarro','87654321','pass2121','Calle Verde 2100','20123456721','Notificaciones Activas','diego.navarro@example.com','991112233','Activo','JUR121','DES421','Navarro SAC',0,0,4,29,4,'1991-09-12','activo',1,22),
(22,'Mónica','Zambrano','87654322','pass2222','Av. Sol 2200','20123456722','Notificaciones Inactivas','monica.zambrano@example.com','992223344','Inactivo','JUR122','DES422','Zambrano EIRL',1,0,4,30,4,NULL,'inactivo',2,23),
(23,'Javier','Cárdenas','87654323','pass2323','Calle Luna 2300','20123456723','Notificaciones Activas','javier.cardenas@example.com','993334455','Activo','JUR123','DES423','Cárdenas SAC',0,0,4,31,4,NULL,'activo',3,24),
(24,'Carolina','Sánchez','87654324','pass2424','Av. Estrella 2400','20123456724','Notificaciones Inactivas','carolina.sanchez@example.com','994445566','Activo','JUR124','DES424','Sánchez EIRL',1,0,4,32,4,NULL,'activo',4,25),
(26,'Rodrigo','Flores','87654326','pass2626','Av. Pino 2600','20123456726','Notificaciones Activas','rodrigo.flores@example.com','996112233','Activo','JUR126','DES426','Flores SAC',0,0,2,9,2,'1993-03-18','activo',4,NULL),
(27,'Valeria','Huerta','87654327','pass2727','Calle Sauce 2700','20123456727','Notificaciones Inactivas','valeria.huerta@example.com','997223344','Inactivo','JUR127','DES427','Huerta EIRL',1,0,2,10,2,'1991-05-09','activo',5,NULL),
(28,'Francisco','Pinto','87654328','pass2828','Av. Cedro 2800','20123456728','Notificaciones Activas','francisco.pinto@example.com','998334455','Activo','JUR128','DES428','Pinto SAC',0,0,2,19,3,'1992-07-22','activo',6,NULL),
(29,'Gabriela','Montalvo','87654329','pass2929','Calle Abeto 2900','20123456729','Notificaciones Inactivas','gabriela.montalvo@example.com','999445566','Activo','JUR129','DES429','Montalvo EIRL',1,0,2,20,3,'1988-10-30','activo',7,NULL),
(30,'Sergio','Villalobos','87654330','pass3030','Av. Olivo 3000','20123456730','Notificaciones Activas','sergio.villalobos@example.com','990556677','Inactivo','JUR130','DES430','Villalobos SAC',0,0,2,25,4,'1989-12-14','activo',8,NULL),
(31,'Elisa','Cabrera','87654331','pass3131','Calle Roble 3100','20123456731','Notificaciones Activas','elisa.cabrera@example.com','991667788','Activo','JUR131','DES431','Cabrera EIRL',1,0,2,26,4,'1995-06-19','activo',9,NULL),
(32,'Miguel','Ortega','87654332','pass3232','Av. Pino 3200','20123456732','Notificaciones Inactivas','miguel.ortega@example.com','992778899','Activo','JUR132','DES432','Ortega SAC',0,0,3,21,3,'1990-03-29','activo',3,4),
(33,'Luciana','Rivera','87654333','pass3333','Calle Sauce 3300','20123456733','Notificaciones Activas','luciana.rivera@example.com','993889900','Activo','JUR133','DES433','Rivera EIRL',1,0,3,22,3,'1988-05-06','activo',4,5),
(34,'Julio','Escobar','87654334','pass3434','Av. Cedro 3400','20123456734','Notificaciones Inactivas','julio.escobar@example.com','994990011','Inactivo','JUR134','DES434','Escobar SAC',0,0,3,27,4,'1991-04-25','activo',5,6),
(35,'Natalia','Castillo','87654335','pass3535','Calle Abeto 3500','20123456735','Notificaciones Activas','natalia.castillo@example.com','995001122','Activo','JUR135','DES435','Castillo EIRL',1,0,3,28,4,'1992-08-14','activo',6,7);
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
ALTER TABLE resenias DROP FOREIGN KEY fk_resenias_usuario1;

ALTER TABLE resenias
ADD CONSTRAINT fk_resenias_usuario1
FOREIGN KEY (usuario_idusuario) REFERENCES usuario(idusuario)
ON DELETE CASCADE;
-- Dump completed on 2024-09-27  3:22:32
