-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_grupo2
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `db_grupo2` ;

-- -----------------------------------------------------
-- Schema db_grupo2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_grupo2` DEFAULT CHARACTER SET utf8mb3 ;
USE `db_grupo2` ;

-- -----------------------------------------------------
-- Table `db_grupo2`.`zona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`zona` (
  `idzona` INT NOT NULL,
  `nombreZona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `zona`
--

LOCK TABLES `zona` WRITE;
/*!40000 ALTER TABLE `zona` DISABLE KEYS */;
INSERT INTO `zona` VALUES (1,'Norte'),(2,'Sur'),(3,'Este'),(4,'Oeste');
/*!40000 ALTER TABLE `zona` ENABLE KEYS */;


-- -----------------------------------------------------
-- Table `db_grupo2`.`distritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`distritos` (
  `iddistritos` INT NOT NULL,
  `nombreDistrito` VARCHAR(45) NOT NULL,
  `idzona` INT NOT NULL,
  PRIMARY KEY (`iddistritos`),
  INDEX `fk_distritos_zona1_idx` (`idzona` ASC) VISIBLE,
  CONSTRAINT `fk_distritos_zona1`
    FOREIGN KEY (`idzona`)
    REFERENCES `db_grupo2`.`zona` (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

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

-- -----------------------------------------------------
-- Table `db_grupo2`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`proveedor` (
  `idproveedor` INT NOT NULL AUTO_INCREMENT,
  `nombreTienda` VARCHAR(45) NOT NULL,
  `nombreProveedor` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `ruc` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `idzona` INT NOT NULL,
  `fotoProveedor` BLOB NULL DEFAULT NULL,
  `estadoProveedor` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`idproveedor`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_proveedor_zona2_idx` (`idzona` ASC) VISIBLE,
  CONSTRAINT `fk_proveedor_zona2`
    FOREIGN KEY (`idzona`)
    REFERENCES `db_grupo2`.`zona` (`idzona`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Tienda Tech','Carlos Pérez','12345678','20123456781','carlos.perez@tiendatech.com',1,NULL,'Activo'),
(2,'Electrónica Lima','María García','87654321','20234567892','maria.garcia@electroniclima.com',2,NULL,'Activo'),
(3,'Muebles del Sur','Jorge López','11223344','20345678903','jorge.lopez@mueblesdelsur.com',2,NULL,'Activo'),
(4,'Fashion Store','Ana Torres','22334455','20456789014','ana.torres@fashionstore.com',3,NULL,'Activo'),
(5,'Supermercado Central','Luis Ramos','33445566','20567890125','luis.ramos@supercentral.com',3,NULL,'Activo'),
(6,'Tech World','Sofía Mendoza','44556677','20678901236','sofia.mendoza@techworld.com',1,NULL,'Activo'),
(7,'Electro Perú','Fernando Salas','55667788','20789012347','fernando.salas@electroperu.com',4,NULL,'Activo'),
(8,'Construcción Total','Raúl Vega','66778899','20890123458','raul.vega@construcciontotal.com',4,NULL,'Baneado'),
(9,'Librería Escolar','Claudia Díaz','77889900','20901234569','claudia.diaz@libreriaescolar.com',2,NULL,'Activo'),
(10,'Alimentos Frescos','Ricardo Núñez','88990011','21012345670','ricardo.nunez@alimentosfrescos.com',3,NULL,'Baneado');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `db_grupo2`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`roles` (
  `idRoles` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idRoles`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Superadmin'),(2,'Coordinador'),(3,'Agente'),(4,'Usuario');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `db_grupo2`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`producto` (
  `idproducto` INT NOT NULL AUTO_INCREMENT,
  `nombreProducto` VARCHAR(100) NOT NULL,
  `categoria` VARCHAR(45) NOT NULL,
  `cantidadDisponible` INT NOT NULL,
  `descripcion` VARCHAR(1000) NOT NULL,
  `precio` DOUBLE NOT NULL,
  `costoEnvio` DOUBLE NOT NULL,
  `cantidadTotal` INT NULL DEFAULT NULL,
  `cantidadComprada` INT NULL DEFAULT NULL,
  `modelo` VARCHAR(45) NULL,
  `color` VARCHAR(45) NULL,
  PRIMARY KEY (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Laptop Lenovo','Electrónica',15,'Laptop Lenovo con 16GB RAM, 512GB SSD, pantalla de 15.6 pulgadas.',1200.5,25,1,70,NULL,NULL),
(2,'iPhone 13','Electrónica',20,'iPhone 13 con 128GB, pantalla OLED de 6.1 pulgadas.',999.99,20,2,100,NULL,NULL),
(3,'Samsung Galaxy S21','Electrónica',10,'Samsung Galaxy S21 con 128GB, pantalla de 6.2 pulgadas.',850.75,18,3,200,NULL,NULL),
(4,'Silla Gamer','Muebles',30,'Silla gamer ergonómica con soporte lumbar ajustable.',199.99,15,300,300,NULL,NULL),
(5,'Teclado Mecánico','Accesorios',50,'Teclado mecánico RGB con switches azules.',75,10,200,500,NULL,NULL),
(6,'Monitor 4K','Electrónica',12,'Monitor 4K UHD de 27 pulgadas con HDR.',350,22,80,200,NULL,NULL),
(7,'Cámara Canon EOS','Fotografía',8,'Cámara Canon EOS Rebel T7i con lente de 18-55mm.',700.99,30,50,100,NULL,NULL),
(8,'Auriculares Bluetooth','Accesorios',25,'Auriculares inalámbricos Bluetooth con cancelación de ruido.',150,8,100,400,NULL,NULL),
(9,'Mouse Inalámbrico','Accesorios',60,'Mouse inalámbrico con sensor óptico de alta precisión.',35.99,5,300,700,NULL,NULL),
(10,'Impresora HP LaserJet','Oficina',5,'Impresora HP LaserJet con impresión a doble cara automática.',250.5,12,40,400,NULL,NULL);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `db_grupo2`.`fotos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`fotos` (
  `idfotos` INT NOT NULL AUTO_INCREMENT,
  `foto1` BLOB NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idfotos`),
  INDEX `fk_fotos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_fotos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`usuario` (
  `idusuario` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NULL DEFAULT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(256) NULL DEFAULT NULL,
  `ruc` VARCHAR(45) NULL DEFAULT NULL,
  `notificaciones` VARCHAR(1024) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NULL DEFAULT NULL,
  `estadoUsuario` VARCHAR(45) NULL DEFAULT NULL,
  `razonSocial` VARCHAR(100) NULL DEFAULT NULL,
  `solicitudAgente` TINYINT NULL DEFAULT NULL,
  `idroles` INT NOT NULL,
  `distritos_iddistritos` INT NULL DEFAULT NULL,
  `idzona` INT NOT NULL,
  `fechanacim` DATE NULL DEFAULT NULL,
  `idproveedor` INT NULL DEFAULT NULL,
  `motivobaneado` VARCHAR(45) NULL,
  `idsuperior` INT NULL AUTO_INCREMENT,
  `cantidadcompras` INT NULL,
  `fotos_idfotos` INT NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`idroles` ASC) VISIBLE,
  INDEX `fk_usuario_distritos1_idx` (`distritos_iddistritos` ASC) VISIBLE,
  INDEX `fk_usuario_zona1_idx` (`idzona` ASC) VISIBLE,
  INDEX `fk_usuario_proveedor1_idx` (`idproveedor` ASC) VISIBLE,
  INDEX `fk_usuario_usuario1_idx` (`idsuperior` ASC) VISIBLE,
  INDEX `fk_usuario_fotos1_idx` (`fotos_idfotos` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_distritos1`
    FOREIGN KEY (`distritos_iddistritos`)
    REFERENCES `db_grupo2`.`distritos` (`iddistritos`),
  CONSTRAINT `fk_usuario_proveedor1`
    FOREIGN KEY (`idproveedor`)
    REFERENCES `db_grupo2`.`proveedor` (`idproveedor`),
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`idroles`)
    REFERENCES `db_grupo2`.`roles` (`idRoles`),
  CONSTRAINT `fk_usuario_zona1`
    FOREIGN KEY (`idzona`)
    REFERENCES `db_grupo2`.`zona` (`idzona`),
  CONSTRAINT `fk_usuario_usuario1`
    FOREIGN KEY (`idsuperior`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_fotos1`
    FOREIGN KEY (`fotos_idfotos`)
    REFERENCES `db_grupo2`.`fotos` (`idfotos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Juan','Pérez','87654301','pass111','Calle Luna 100','20123456701','Notificaciones Activas','juan.perez@example.com','987654321','Activo','Pérez SAC',0,1,1,1,NULL,NULL,NULL,NULL,45,1),
(2,'Maria','López','87654302','pass222','Av. Sol 200','20123456702','Notificaciones Inactivas','maria.lopez@example.com','912345678','Activo','López EIRL',0,3,2,1,'1993-01-17',2,NULL,NULL,0,45),
(3,'Carlos','García','87654303','pass333','Calle Estrella 300','20123456703','Notificaciones Activas','carlos.garcia@example.com','923456789','Inactivo','García SAC',0,3,3,1,'1994-08-24',	3,NULL,'activo',0,24,2),
-- ------------------ 
(4,'Laura','Ramírez','87654304','pass444','Av. Luna 400','20123456704','Notificaciones Inactivas','laura.ramirez@example.com','934567890','Activo','Ramírez EIRL',0,0,2,3,1,'1987-11-15','activo',2,NULL),
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

-- -----------------------------------------------------
-- Table `db_grupo2`.`codigos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`codigos` (
  `idcodigos` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idcodigos`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC) VISIBLE,
  INDEX `fk_codigos_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_codigos_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb3;

--
-- Dumping data for table `codigodespachador`
--

LOCK TABLES `codigos` WRITE;
/*!40000 ALTER TABLE `codigos` DISABLE KEYS */;
INSERT INTO `codigos` VALUES (1,'DESP001','Activo',2),(2,'DESP002','Inactivo',8),(3,'DESP003','Activo',3),
(4,'DESP004','Pendiente',7),(5,'DESP005','Activo',9),(6,'DESP006','Inactivo',10),(7,'DESP007','Activo',6),
(8,'DESP008','Inactivo',11),(9,'DESP009','Pendiente',12),(10,'DESP010','Activo',32),(11,'DESP011','Activo',33),
(12,'DESP012','Inactivo',13),(13,'DESP013','Pendiente',14),(14,'DESP014','Activo',34),(15,'DESP015','Inactivo',15),
(16,'DESP016','Activo',35),(17,'DESP017','Pendiente',16),(18,'DESP018','Activo',17),(19,'DESP019','Inactivo',18),
(20,'DESP020','Pendiente',19),(21,'DESP021','Activo',20),(22,'DESP022','Pendiente',21),(23,'DESP023','Inactivo',22),
(24,'DESP024','Activo',23),(25,'DESP025','Pendiente',24),(26,'DESP026','Inactivo',25),(27,'DESP027','Activo',35),
(28,'DESP028','Inactivo',26),(29,'DESP029','Pendiente',27),(30,'DESP030','Activo',34),(31,'DESP031','Activo',33),
(32,'DESP032','Inactivo',28),(33,'DESP033','Pendiente',29),(34,'DESP034','Activo',32),(35,'DESP035','Pendiente',30),
(36,'DESP036','Inactivo',31),(37,'DESP037','Activo',6),(38,'DESP038','Pendiente',9),(39,'DESP039','Inactivo',10),
(40,'DESP040','Activo',3),(41,'JUR006','Inactivo',11),(42,'JUR007','Activo',2);
/*!40000 ALTER TABLE `codigodespachador` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `db_grupo2`.`faq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`faq` (
  `idfaq` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(200) NOT NULL,
  `pregunta` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`idfaq`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`ordenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`ordenes` (
  `idordenes` INT NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` VARCHAR(45) NOT NULL,
  `fechaArribo` DATE NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  `mesCreacion` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  INDEX `fk_ordenes_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`pagos` (
  `idpagos` INT NOT NULL AUTO_INCREMENT,
  `monto` DOUBLE NOT NULL,
  `numeroTarjeta` DATE NOT NULL,
  `cvv` INT NOT NULL,
  `fechaPago` DATETIME NOT NULL,
  `metodoPago` VARCHAR(45) NOT NULL,
  `estadoPago` VARCHAR(45) NOT NULL,
  `ordenes_idordenes` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idpagos`, `ordenes_idordenes`),
  INDEX `fk_pagos_ordenes1_idx` (`ordenes_idordenes` ASC) VISIBLE,
  INDEX `fk_pagos_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_pagos_ordenes1`
    FOREIGN KEY (`ordenes_idordenes`)
    REFERENCES `db_grupo2`.`ordenes` (`idordenes`),
  CONSTRAINT `fk_pagos_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`producto_has_ordenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`producto_has_ordenes` (
  `producto_idproducto` INT NOT NULL,
  `ordenes_idordenes` INT NOT NULL,
  PRIMARY KEY (`producto_idproducto`, `ordenes_idordenes`),
  INDEX `fk_ordenes` (`ordenes_idordenes` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes`
    FOREIGN KEY (`ordenes_idordenes`)
    REFERENCES `db_grupo2`.`ordenes` (`idordenes`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_producto`
    FOREIGN KEY (`producto_idproducto`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`producto_has_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`producto_has_proveedor` (
  `producto_idproducto` INT NOT NULL,
  `proveedor_idproveedor` INT NOT NULL,
  PRIMARY KEY (`producto_idproducto`, `proveedor_idproveedor`),
  INDEX `fk_producto_has_proveedor_proveedor1_idx` (`proveedor_idproveedor` ASC) VISIBLE,
  INDEX `fk_producto_has_proveedor_producto1_idx` (`producto_idproducto` ASC) VISIBLE,
  CONSTRAINT `fk_producto_has_proveedor_producto1`
    FOREIGN KEY (`producto_idproducto`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`),
  CONSTRAINT `fk_producto_has_proveedor_proveedor1`
    FOREIGN KEY (`proveedor_idproveedor`)
    REFERENCES `db_grupo2`.`proveedor` (`idproveedor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`productos_has_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`productos_has_usuario` (
  `productos_idproductos` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`productos_idproductos`, `usuario_idusuario`),
  INDEX `fk_productos_has_usuario_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  INDEX `fk_productos_has_usuario_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_productos_has_usuario_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`),
  CONSTRAINT `fk_productos_has_usuario_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`reposicionproductos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`reposicionproductos` (
  `idreposicionProductos` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL DEFAULT 24,
  `fechaSolicitud` DATE NOT NULL,
  `productos_idproductos` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idreposicionProductos`),
  INDEX `fk_reposicionProductos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  INDEX `fk_reposicionproductos_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_reposicionProductos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`),
  CONSTRAINT `fk_reposicionproductos_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 43
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`resenias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`resenias` (
  `idresenias` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(500) NOT NULL,
  `calidad` VARCHAR(45) NOT NULL,
  `rapidez` VARCHAR(45) NOT NULL,
  `puntuacion` INT NOT NULL,
  `foto` BLOB NULL DEFAULT NULL,
  `respuesta` VARCHAR(45) NULL DEFAULT NULL,
  `productos_idproductos` INT NOT NULL,
  `tituloForo` VARCHAR(200) NULL DEFAULT NULL,
  `descripcionForo` VARCHAR(200) NULL DEFAULT NULL,
  `usuario_idusuario` INT NULL DEFAULT NULL,
  `tipoPublicacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idresenias`),
  INDEX `fk_resenias_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  INDEX `fk_resenias_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_resenias_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`),
  CONSTRAINT `fk_resenias_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`respuestas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`respuestas` (
  `resenias_idresenias` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  `respuestas` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`resenias_idresenias`, `usuario_idusuario`),
  INDEX `fk_resenias_has_usuario_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  INDEX `fk_resenias_has_usuario_resenias1_idx` (`resenias_idresenias` ASC) VISIBLE,
  CONSTRAINT `fk_resenias_has_usuario_resenias1`
    FOREIGN KEY (`resenias_idresenias`)
    REFERENCES `db_grupo2`.`resenias` (`idresenias`),
  CONSTRAINT `fk_resenias_has_usuario_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
