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
CREATE TABLE IF NOT EXISTS `db_grupo2`.`despachador` (
  `iddespachador` INT NOT NULL AUTO_INCREMENT,
  `despachador` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iddespachador`),
  UNIQUE INDEX `codigo_UNIQUE` (`despachador` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 43
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `despachador` WRITE;
/*!40000 ALTER TABLE `despachador` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`despachador` 
(`despachador`, `estado`)
VALUES
('DESP001', 'Habilitado'),
('DESP002', 'Habilitado'),
('DESP003', 'Habilitado'),
('DESP004', 'Habilitado'),
('DESP005', 'Habilitado'),
('DESP006', 'Habilitado'),
('DESP007', 'Habilitado'),
('DESP008', 'Habilitado'),
('DESP009', 'Habilitado'),
('DESP010', 'Habilitado'),
('DESP011', 'Habilitado'),
('DESP012', 'Habilitado'),
('DESP013', 'Habilitado'),
('DESP014', 'Habilitado'),
('DESP015', 'Habilitado'),
('DESP016', 'Habilitado'),
('DESP017', 'Supendido'),
('DESP018', 'Multado'),
('DESP019', 'Anulado'),
('DESP020', 'Multado'),
('DESP021', 'Anulado');
/*!40000 ALTER TABLE `despachador` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `db_grupo2`.`zona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`zona` (
  `idzona` INT NOT NULL,
  `nombreZona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `zona` WRITE;
/*!40000 ALTER TABLE `zona` DISABLE KEYS */;
INSERT INTO `zona` VALUES (1,'Norte'),(2,'Sur'),(3,'Este'),(4,'Oeste');
/*!40000 ALTER TABLE `zona` ENABLE KEYS */;
UNLOCK TABLES;


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
  `modelo` VARCHAR(45) NULL DEFAULT NULL,
  `color` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES 
(1,'Laptop Lenovo','Electrónica',15,'Laptop Lenovo con 16GB RAM, 512GB SSD, pantalla de 15.6 pulgadas.',1200.5,25,1,70,NULL,NULL),
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
  `foto1` VARCHAR(255) NOT NULL,  -- Almacena la ruta de la imagen
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idfotos`),
  INDEX `fk_fotos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_fotos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `fotos` WRITE;
/*!40000 ALTER TABLE `fotos` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`fotos` VALUES 
(1,'/imagenes/producto1-1.jpg', 1),(2,'/imagenes/producto1-2.jpg', 1),(3,'/imagenes/producto1-3.jpg', 1),(4,'/imagenes/producto1-4.jpg', 1),
(5,'/imagenes/producto2-1.jpg', 2),(6,'/imagenes/producto2-2.jpg', 2),(7,'/imagenes/producto2-3.jpg', 2),(8,'/imagenes/producto2-4.jpg', 2),
(9,'/imagenes/producto3-1.jpg', 3),(10,'/imagenes/producto3-2.jpg', 3),(11,'/imagenes/producto3-3.jpg', 3),(12,'/imagenes/producto3-4.jpg', 3),
(13,'/imagenes/producto4-1.jpg', 4),(14,'/imagenes/producto4-2.jpg', 4),(15,'/imagenes/producto4-3.jpg', 4),(16,'/imagenes/producto4-4.jpg', 4),
(17,'/imagenes/producto5-1.jpg', 5),(18,'/imagenes/producto5-2.jpg', 5),(19,'/imagenes/producto5-3.jpg', 5),(20,'/imagenes/producto5-4.jpg', 5),
(21,'/imagenes/producto6-1.jpg', 6),(22,'/imagenes/producto6-2.jpg', 6),(23,'/imagenes/producto6-3.jpg', 6),(24,'/imagenes/producto6-4.jpg', 6),
(25,'/imagenes/producto7-1.jpg', 7),(26,'/imagenes/producto7-2.jpg', 7),(27,'/imagenes/producto7-3.jpg', 7),(28,'/imagenes/producto7-4.jpg', 7),
(29,'/imagenes/producto8-1.jpg', 8),(30,'/imagenes/producto8-2.jpg', 8),(31,'/imagenes/producto8-3.jpg', 8),(32,'/imagenes/producto8-4.jpg', 8),
(33,'/imagenes/producto9-1.jpg', 9),(34,'/imagenes/producto9-2.jpg', 9),(35,'/imagenes/producto9-3.jpg', 9),(36,'/imagenes/producto9-4.jpg', 9),
(37,'/imagenes/producto10-1.jpg', 10),(38,'/imagenes/producto10-2.jpg', 10),(39,'/imagenes/producto10-3.jpg', 10),(40,'/imagenes/producto10-4.jpg', 10);

/*!40000 ALTER TABLE `fotos` ENABLE KEYS */;
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
LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES 
(1,'Tienda Tech','Carlos Pérez','12345678','20123456781','carlos.perez@tiendatech.com',1,NULL,'Activo'),
(2,'Electrónica Lima','María García','87654321','20234567892','maria.garcia@electroniclima.com',2,NULL,'Activo'),
(3,'Muebles del Sur','Jorge López','11223344','20345678903','jorge.lopez@mueblesdelsur.com',2,NULL,'Activo'),
(4,'Fashion Store','Ana Torres','22334455','20456789014','ana.torres@fashionstore.com',3,NULL,'Activo'),
(5,'Supermercado Central','Luis Ramos','33445566','20567890125','luis.ramos@supercentral.com',3,NULL,'Activo'),
(6,'Tech World','Sofía Mendoza','44556677','20678901236','sofia.mendoza@techworld.com',1,NULL,'Activo'),
(7,'Electro Perú','Fernando Salas','55667788','20789012347','fernando.salas@electroperu.com',4,NULL,'Activo'),
(8,'Construcción Total','Raúl Vega','66778899','20890123458','raul.vega@construcciontotal.com',4,NULL,'Activo'),
(9,'Librería Escolar','Claudia Díaz','77889900','20901234569','claudia.diaz@libreriaescolar.com',2,NULL,'Activo'),
(10,'Alimentos Frescos','Ricardo Núñez','88990011','21012345670','ricardo.nunez@alimentosfrescos.com',3,NULL,'Activo');
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
LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Superadmin'),(2,'Coordinador'),(3,'Agente'),(4,'Usuario');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table `db_grupo2`.`jurisdiccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`jurisdiccion` (
  `idjurisdiccion` INT NOT NULL,
  `jurisdiccion` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idjurisdiccion`),
  UNIQUE INDEX `jurisdiccion_UNIQUE` (`jurisdiccion` ASC) VISIBLE)
ENGINE = InnoDB;
LOCK TABLES `jurisdiccion` WRITE;
/*!40000 ALTER TABLE `jurisdiccion` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`jurisdiccion` 
(`idjurisdiccion`,`jurisdiccion`, `estado`)
VALUES
(1,'JURIS001', 'Habilitado'),
(2,'JURIS002', 'Habilitado'),
(3,'JURIS003', 'Habilitado'),
(4,'JURIS004', 'Habilitado'),
(5,'JURIS005', 'Habilitado'),
(6,'JURIS006', 'Habilitado'),
(7,'JURIS007', 'Habilitado'),
(8,'JURIS008', 'Habilitado'),
(9,'JURIS009', 'Habilitado'),
(10,'JURIS010', 'Habilitado'),
(11,'JURIS011', 'Habilitado'),
(12,'JURIS012', 'Habilitado'),
(13,'JURIS013', 'Habilitado'),
(14,'JURIS014', 'Habilitado'),
(15,'JURIS015', 'Habilitado'),
(16,'JURIS016', 'Habilitado'),
(17,'JURIS017', 'Habilitado'),
(18,'JURIS018', 'Suspendido'),
(19,'JURIS019', 'Anulado'),
(20,'JURIS020', 'Multado'),
(21,'JURIS021', 'Multado');
/*!40000 ALTER TABLE `jurisdiccion` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `db_grupo2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NULL DEFAULT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(60) NOT NULL,
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
  `motivo` VARCHAR(45) NULL DEFAULT NULL,
  `idsuperior` INT NULL,
  `cantidadcompras` INT NULL DEFAULT NULL,
  `fotos_idfotos` INT NULL DEFAULT NULL,
  `isban` TINYINT NULL DEFAULT NULL,
  `calificacion` INT NULL DEFAULT NULL,
  `despachador_iddespachador` INT NULL,
  `jurisdiccion_idjurisdiccion` INT NULL,
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
  INDEX `fk_usuario_despachador1_idx` (`despachador_iddespachador` ASC) VISIBLE,
  INDEX `fk_usuario_jurisdiccion1_idx` (`jurisdiccion_idjurisdiccion` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_distritos1`
    FOREIGN KEY (`distritos_iddistritos`)
    REFERENCES `db_grupo2`.`distritos` (`iddistritos`),
  CONSTRAINT `fk_usuario_fotos1`
    FOREIGN KEY (`fotos_idfotos`)
    REFERENCES `db_grupo2`.`fotos` (`idfotos`),
  CONSTRAINT `fk_usuario_proveedor1`
    FOREIGN KEY (`idproveedor`)
    REFERENCES `db_grupo2`.`proveedor` (`idproveedor`),
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`idroles`)
    REFERENCES `db_grupo2`.`roles` (`idRoles`),
  CONSTRAINT `fk_usuario_usuario1`
    FOREIGN KEY (`idsuperior`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`),
  CONSTRAINT `fk_usuario_zona1`
    FOREIGN KEY (`idzona`)
    REFERENCES `db_grupo2`.`zona` (`idzona`),
  CONSTRAINT `fk_usuario_despachador1`
    FOREIGN KEY (`despachador_iddespachador`)
    REFERENCES `db_grupo2`.`despachador` (`iddespachador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_jurisdiccion1`
    FOREIGN KEY (`jurisdiccion_idjurisdiccion`)
    REFERENCES `db_grupo2`.`jurisdiccion` (`idjurisdiccion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 110
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idusuario`, `nombre`, `apellido`, `dni`, `contrasena`, `direccion`, `ruc`, `notificaciones`, `correo`, `telefono`, `estadoUsuario`, `razonSocial`, `solicitudAgente`, `idroles`, `distritos_iddistritos`, `idzona`, `fechanacim`, `idproveedor`, `motivo`, `idsuperior`, `cantidadcompras`, `fotos_idfotos`,`isban`,`calificacion`,`despachador_iddespachador`,`jurisdiccion_idjurisdiccion` ) VALUES 
-- 1 superadmin--
(1, 'Juan', 'Pérez', '87654301', '$2a$10$m.uauzBWuQVoR7qJge1O3ukcYO2vnIp2wJVTLy6UdDHpkeSAm/WkK', 'Calle Luna 100', '20123456701', 'Notificaciones Activas', 'juan.perez@example.com', '987654321', 'Activo', 'Pérez SAC', 0, 1, 1, 1, '1985-05-15', NULL, NULL, NULL, 45, NULL,0,NULL,NULL,NULL),
-- norte--
-- -- 2 coordinadores --
(2, 'Maria', 'López', '87654302', '$2a$10$gaAmMNaWXUrmMHMHaO1FRuwdPwBb1aF1EQSJBZeAaAF.wqZAhSMDa', 'Av. Sol 200', '20123456702', 'Notificaciones Inactivas', 'maria.lopez@example.com', '912345678', 'Activo', 'López EIRL', 0, 2, 1, 1, '1990-01-17', NULL, NULL, NULL, 0, NULL,0,NULL,NULL,NULL),
-- -- 6 agentes --
(3, 'Carlos', 'García', '87654303', '$2a$10$jEnQpKDadz9NIr3TULSqE.ABdrwqlEsFyCqQ6gJMCGvNYsAM.iUii', 'Calle Estrella 300', '20123456703', 'Notificaciones Activas', 'carlos.garcia@example.com', '923456789', 'Activo', 'García SAC', 0, 3, 2, 1, '1988-08-24', NULL, NULL, NULL, 0, NULL,0,3,1,1),
(4, 'Laura', 'Ramírez', '87654304', '$2a$10$ACFCClib67XkFnKyOQ/z4.O/sHOchI1cE7x7PBQDiezmA/ZzCq1J.', 'Av. Luna 400', '20123456704', 'Notificaciones Inactivas', 'laura.ramirez@example.com', '934567890', 'Activo', 'Ramírez EIRL', 0, 3, 3, 1, '1992-11-15', NULL, NULL, 2, 100, NULL,0,2,2,2),
(5, 'Pedro', 'Fernández', '87654355', '$2a$10$R8NzT3unrbtdi0aJ90gQ1.rA12OhVJfdQrth87MVo5OyY2eKJGYwS', 'Calle Mar 500', '20123456755', 'Notificaciones Activas', 'pedro.fernandez@example.com', '945678901', 'Activo', 'Fernández SAC', 0, 3, 4, 1, '1989-02-22', NULL, NULL, 2, 30, NULL,0,1,3,3),
-- sur --
-- -- 2 coordinadores --
(6, 'Elena', 'Castro', '87654306', '$2a$10$24Jx5FRaYqR7rQ8gWKFRN.TzHVIt6SyH2y8KJ7fnysqlQwt5VbIae', 'Av. Roca 600', '20123456706', 'Notificaciones Activas', 'elena.castro@example.com', '956789012', 'Activo', 'Castro EIRL', 0, 2, 9, 2, '1993-11-11', NULL, 'Violación de términos', 3, 10, NULL,0,NULL,NULL,NULL),
-- -- 6 agentes --
(7, 'Raúl', 'Torres', '87654307', '$2a$10$4vkMwb9I94wS9ZQV/FyjY.IFEmUcm3vRlgfw2K7PjXkFkPHzfKwry', 'Calle Nube 700', '20123456707', 'Notificaciones Inactivas', 'raul.torres@example.com', '967890123', 'Activo', 'Torres SAC', 0, 3, 10, 2, '1991-06-07', NULL, NULL, 3, 5, NULL,0,1,4,4),
(8, 'Sofía', 'Vargas', '87654308', '$2a$10$lAIIRSR9rENen8g/cgMVwe7zG1UXmpQ2jyl9fKc.Xbt2dVVSuoxZ2', 'Av. Arena 800', '20123456708', 'Notificaciones Activas', 'sofia.vargas@example.com', '978901234', 'Activo', 'Vargas EIRL', 0, 3, 11, 2, '1987-01-12', NULL, NULL, NULL, 6, NULL,0,2,4,4),
(9, 'Jorge', 'Díaz', '87654309', '$2a$10$nnBbYLxc7dtzvmJmQ.i7YuGo759bVAAsHQvZAl8LUuxQMQDiEnYpa', 'Calle Sol 900', '20123456709', 'Notificaciones Inactivas', 'jorge.diaz@example.com', '989012345', 'Activo', 'Díaz SAC', 0, 3, 12, 2, '1994-11-09', NULL, NULL, NULL, 7, NULL,0,3,5,5),
-- este --
-- -- 2 coordinadores --
(10, 'Lucía', 'Mendoza', '87654310', '$2a$10$fPitFhE8dzg0KlZKSSw5UOOll0lvjL9xdyyAKOnQixNOexFPU8DfK', 'Av. Viento 1000', '20123456710', 'Notificaciones Activas', 'lucia.mendoza@example.com', '990123883', 'Activo', 'Mendoza EIRL', 0, 2, 19, 3, '1986-09-20', NULL, NULL, NULL,8, NULL,10,NULL,NULL,NULL),
-- -- 6 agentes --
(11, 'Ana', 'Rojas', '87654311', '$2a$10$24N6LMS/LhWP5cCAZVld7.gSGY0g4tn.B5zsbNOCdZufN3xrZN3KO', 'Calle Fuego 1100', '20123456711', 'Notificaciones Inactivas', 'ana.rojas@example.com', '991234567', 'Activo', 'Rojas SAC', 0, 3, 20, 3, '1990-12-27', NULL, NULL, 9, 9, NULL,0,5,6,6),
(12, 'Luis', 'Ortiz', '87654312', '$2a$10$FQY3FTi7cXogEn8bhXsq7.dDV2oVNRlqOUa92lnKG/xNev3lHxeS.', 'Av. Brisa 1200', '20123456712', 'Notificaciones Activas', 'luis.ortiz@example.com', '992345678', 'Activo', 'Ortiz EIRL', 0, 3, 21, 4, '1988-12-21', NULL, NULL, 10, 14, NULL,0,4,7,7),
(13, 'Gloria', 'Paredes', '87654313', '$2a$10$cFmSxOdY7R998zA/XC30PuKLKKEdatBUy6W/zMM5wvM9./CWynd3u', 'Calle Trueno 1300', '20123456713', 'Notificaciones Activas', 'gloria.paredes@example.com', '993456789', 'Activo', 'Paredes SAC', 0, 3, 22, 4, '1992-12-02', NULL, NULL, 10, 23, NULL,0,4,8,8),
-- oeste --
-- -- 2 coordinadores --
(14, 'Andrés', 'Guzmán', '87654314', '$2a$10$vBPcQ2qFgjB25Jw50ux.puNmnloDUC1J0ljs8AUjsxzgpGkk/j3UO', 'Av. Relámpago 1400', '20123456714', 'Notificaciones Inactivas', 'andres.guzman@example.com', '994567890', 'Activo', 'Guzmán EIRL', 0, 2, 26, 4, '1989-05-04', NULL, NULL, NULL, 33, NULL,0,NULL,NULL,NULL),
-- -- 6 agentes --
(15, 'Patricia', 'Montes', '87654315', '$2a$10$N79rHHVeZeXY6JEm6sRi4eqKIIbZLp8mrHEK9w2KD2fxcQgx91F.G', 'Calle Lluvia 1500', '20123456715', 'Notificaciones Activas', 'patricia.montes@example.com', '995678901', 'Activo', 'Montes SAC', 0, 3, 27, 4, '1993-05-15', NULL, NULL, NULL, 54, NULL,0,2,9,9),
(16, 'Gabriel', 'Salazar', '87654316', '$2a$10$WJFzk68ozO9u1Z6DIdgq3epJLdnTMQNmjGzOD/kh/Flnivdqb9Tii', 'Av. Viento 1600', '20123456716', 'Notificaciones Inactivas', 'gabriel.salazar@example.com', '996789012', 'Activo', 'Salazar EIRL', 0, 3, 28, 4, '1991-01-28', NULL, NULL, NULL, 21, NULL,0,3,10,10),
(17, 'Daniela', 'Cruz', '87654317', '$2a$10$XymLh0gUqZyHnMLXTjxc3uOEW4OdEEQ3MUKw6WXW3gbcb5rYnhSzu', 'Calle Rayo 1700', '20123456717', 'Notificaciones Activas', 'daniela.cruz@example.com', '997890123', 'Activo', 'Cruz SAC', 0, 3, 29, 4, '1987-03-11', NULL, NULL, NULL, 20, NULL,0,4,11,11),
-- usuarios --
(18, 'Hugo', 'Mejía', '87654318', '$2a$10$ypIEXVggJUyz/isJq9iB0.XVz8.x4ApXrNO6wWDxyR6fBDlMNFb1W', 'Av. Truenos 1800', '20123456718', 'Notificaciones Inactivas', 'hugo.mejia@example.com', '998901234', 'Activo', 'Mejía EIRL', 1, 4, 1, 1, '1990-07-30', NULL, NULL, NULL, 15, NULL,0,NULL,12,12),
(19, 'Camila', 'Vega', '87654319', '$2a$10$D.7eKKMrvBkYgJlyg370NOzp8rSQNhdy181F7IbXmySbGdYmAvjbq', 'Calle Ríos 1900', '20123456719', 'Notificaciones Activas', 'camila.vega@example.com', '999012345', 'Baneado', 'Vega SAC', 0, 4, 2, 1, '1994-08-10', NULL, 'Actividad sospechosa', NULL, 13, NULL,1,NULL,13,13),
(20, 'Alonso', 'Valdez', '87654320', '$2a$10$vJ0yCddrmhOV5BM8N6AH.OaJcm55CYjJZggwm7jdxfU9RU4elU/Du', 'Av. Roca 2000', '20123456720', 'Notificaciones Inactivas', 'alonso.valdez@example.com', '990123456', 'Inactivo', 'Valdez EIRL', 0, 4, 15, 2, '1986-08-03', NULL, NULL, NULL, 17, NULL,0,NULL,14,14),
(21, 'Laura', 'Martínez', '87654377', '$2a$10$UzUUkQiMK8EmrFOWQ/TLUeZPrrgV7fhqAg/XjrEGYd9xN/m8l3R9S', 'Calle Sol 400', '20123456777', 'Notificaciones Activas', 'laura.martinez@example.com', '934567890', 'Activo', 'Martínez SRL', 0, 4, 16, 2, '1995-02-28', NULL, NULL, NULL, 20, NULL, 0,NULL,15,15),
(22, 'Javier', 'Rodríguez', '87654305', '$2a$10$kLDp7wBEA/Ygl6RU2mEiguIeWRACD62NVgLBSjIZaiWovzp43V.EW', 'Av. Estrella 500', '20123456705', 'Notificaciones Inactivas', 'javier.rodriguez@example.com', '945678901', 'Activo', 'Rodríguez EIRL', 0, 4, 23, 3, '1987-03-15', NULL, NULL, NULL, 15, NULL, 0, NULL,16,16),
(23, 'Ana', 'González', '87654366', '$2a$10$EPKAuguC7.urpJ4itWnCleNit06o6DVoLUuFgpEkSznoQa1t7ETQG', 'Calle Mar 600', '20123456766', 'Notificaciones Activas', 'ana.gonzalez@example.com', '956789012', 'Activo', 'González SAC', 1, 4, 24, 3, '1992-11-11', NULL, NULL, NULL, 10, NULL, 0,NULL,17,17),
(24, 'Luis', 'Hernández', '87654387', '$2a$10$YLp2nze36xy.97TKSnqnFu2vLQYeUG49m52X906d2ytxcCf.3F0ia', 'Calle Tierra 700', '20123456787', 'Notificaciones Activas', 'luis.hernandez@example.com', '967890123', 'Activo', 'Hernández Ltda.', 1, 4, 35, 4, '1985-06-30', NULL, NULL, NULL, 5, NULL,  0, NULL,18,18),
(25, 'Sofía', 'Morales', '87654388', '$2a$10$sKgjOMiYCJK8jXSE7xOKNeMYYf4ob8BdmW7PfuslONajMfu7YJ1AC', 'Av. Agua 800', '20123456788', 'Notificaciones Inactivas', 'sofia.morales@example.com', '978901234', 'Activo', 'Morales EIRL', 0, 4, 36, 4, '1990-09-09', NULL, NULL, NULL, 25, NULL,  0, NULL,19,19),
(26, 'Fernando', 'Vásquez', '87654399', '$2a$10$ufLoBCdkPxw/pButrHjP1OO96kAvb0vopgar4joCuFKGBSae9WajW', 'Calle Fuego 900', '20123456799', 'Notificaciones Activas', 'fernando.vasquez@example.com', '989012345', 'Activo', 'Vásquez SAC', 1, 4, 37,4 , '1983-04-21', NULL, NULL, NULL, 30, NULL,  0, NULL,20,20),
(27, 'Claudia', 'Cruz', '87654400', '$2a$10$7tgKsn1sjQhOL5fXrxZuH.aDsDVXmZcmC9IxK4riKSFIlFXQEVoB6', 'Av. Viento 1000', '20123456700', 'Notificaciones Inactivas', 'claudia.cruz@example.com', '990123456', 'Baneado', 'Cruz Ltda.', 1, 4, 38, 4, '1996-12-05', NULL, 'Comportamiento inapropiado', NULL, 12, NULL,  1, NULL,21,21);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `db_grupo2`.`ordenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`ordenes` (
  `idordenes` INT NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` VARCHAR(45) NULL DEFAULT NULL,
  `estadoOrdenesUser` VARCHAR(45) NOT NULL,
  `fechaArribo` DATE NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  `mesCreacion` VARCHAR(20) NOT NULL,
  `fechaCreacion` DATE NOT NULL,
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  INDEX `fk_ordenes_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 1022
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `ordenes` WRITE;
/*!40000 ALTER TABLE `ordenes` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`ordenes` (`idordenes`, `estadoOrdenes`, `estadoOrdenesUser`, `fechaArribo`, `usuario_idusuario`, `mesCreacion`, `fechaCreacion`) VALUES

(1001,'Resuelta','Resuelta', '2024-02-13', 18, 'Enero', '2024-01-10'),
(1002,'Resuelta','Resuelta', '2024-02-14', 19, 'Enero', '2024-01-08'),
(1003,'Resuelta','Resuelta', '2024-03-15', 20, 'Febrero', '2024-02-07'),
(1004,'Resuelta','Resuelta', '2024-04-16', 21, 'Febrero', '2024-02-20'),

(1005, 'Progreso', 'Arribo','2024-10-14',22, 'Abril', '2024-04-28'),
(1006, 'Progreso', 'Arribo','2024-11-15',23, 'Mayo', '2024-05-29'),
(1007, 'Progreso', 'Arribo','2024-12-16',24, 'Junio', '2024-06-30'),

(1008, 'Progreso', 'Aduanas','2024-11-12',25, 'Julio', '2024-07-28'),
(1009, 'Progreso', 'Aduanas','2024-12-10',26, 'Agosto', '2024-08-14'),
(1010, 'Progreso', 'Aduanas','2025-01-02',27, 'Setiembre', '2024-09-09'),

(1011, 'Progreso', 'En ruta','2024-12-15',18, 'Abril', '2024-04-05'),
(1012, 'Progreso', 'En ruta','2024-12-16',19, 'Mayo', '2024-05-17'),
(1013, 'Progreso', 'En ruta','2024-12-17',20, 'Junio', '2024-06-19'),

(1014, 'Sin asignar','Validacion',  '2025-01-10', 21, 'Setiembre', '2024-09-18'),
(1015, 'Sin asignar','Validacion',  '2025-02-07', 22, 'Agosto', '2024-08-14'),
(1016, 'Sin asignar','Validacion',  '2025-01-23', 23, 'Octubre', '2024-10-01'),
(1017, 'Sin asignar','Validacion',  '2025-02-28', 24, 'Octubre', '2024-10-02'),

(1018,NULL,'Pendiente', '2025-01-19', 25, 'Octubre', '2024-10-02'),
(1019,NULL,'Pendiente', '2025-02-01', 26, 'Octubre', '2024-10-03'),
(1020,NULL,'Pendiente', '2025-02-02', 27, 'Octubre', '2024-10-04'),
(1021,NULL,'Pendiente', '2025-02-03', 27, 'Octubre', '2024-10-05');
/*!40000 ALTER TABLE `ordenes` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table `db_grupo2`.`pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`pagos` (
  `idpagos` INT NOT NULL AUTO_INCREMENT,
  `monto` DOUBLE NOT NULL,
  `numeroTarjeta` VARCHAR(45) NOT NULL,
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
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb3;
LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`pagos` 
(`idpagos`,`monto`, `numeroTarjeta`, `cvv`, `fechaPago`, `metodoPago`, `estadoPago`, `ordenes_idordenes`, `usuario_idusuario`)
VALUES
(1,1300.00, '11111111', 123, '2024-01-10 10:30:00', 'Tarjeta de Crédito', 'Completado', 1001, 18),
(2,1200.00, '22222222', 456, '2024-01-08 14:45:00', 'PayPal', 'Completado', 1002, 19),
(3,1000.00, '33333333', 789, '2024-02-07 09:15:00', 'Tarjeta de Débito', 'Completado', 1003, 20),
(4,199.99, '44444444', 234, '2024-02-20 16:20:00', 'Transferencia Bancaria', 'Completado', 1004, 21),

(5,100.50, '55555555', 567, '2024-04-28 11:00:00', 'Tarjeta de Crédito', 'Completado', 1005, 22),
(6,400.00, '66666666', 890, '2024-05-29 13:30:00', 'PayPal', 'Completado', 1006, 23),
(7,1000.70, '77777777', 345, '2024-06-30 15:45:00', 'Tarjeta de Débito', 'Completado', 1007, 24),

(8,500.50, '88888888', 678, '2024-07-28 10:00:00', 'Transferencia Bancaria', 'Completado', 1008, 25),
(9,100.00, '99999999', 901, '2024-08-14 12:15:00', 'Tarjeta de Crédito', 'Completado', 1009, 26),
(10,500.50, 'AAAAAAAA', 234, '2024-09-09 17:30:00', 'PayPal', 'Completado', 1010, 27),

(11,1300.00, '11111111', 123, '2024-04-05 10:30:00', 'Tarjeta de Crédito', 'Completado', 1011, 18),
(12,1250.00, '22222222', 456, '2024-06-20 14:45:00', 'PayPal', 'Completado', 1012, 19),
(13,900.00, '33333333', 789, '2024-07-10 09:15:00', 'Tarjeta de Débito', 'Completado', 1013, 20),

(14,200.00, '44444444', 234, '2024-08-05 16:20:00', 'Transferencia Bancaria', 'Completado', 1014, 21),
(15,100.50, '55555555', 567, '2024-09-12 11:00:00', 'Tarjeta de Crédito', 'Completado', 1015, 22),
(16,500.00, '66666666', 890, '2024-10-18 13:30:00', 'PayPal', 'Completado', 1016, 23),
(17,900.50, '77777777', 345, '2024-11-22 15:45:00', 'Tarjeta de Débito', 'Completado', 1017, 24);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

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
LOCK TABLES `producto_has_ordenes` WRITE;
/*!40000 ALTER TABLE `producto_has_ordenes` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`producto_has_ordenes` 
(`producto_idproducto`, `ordenes_idordenes`)
VALUES
(1, 1001),
(2, 1002),
(3, 1003),
(4, 1004),
(5, 1005),
(1, 1006),
(2, 1007),
(3, 1008),
(4, 1009),
(5, 1010),
(1, 1011),
(2, 1012),
(3, 1013),
(4, 1014),
(5, 1015),
(1, 1016),
(2, 1017),
(3, 1018),
(4, 1019),
(5, 1020),
(5, 1021);
/*!40000 ALTER TABLE `producto_has_ordenes` ENABLE KEYS */;
UNLOCK TABLES;

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
LOCK TABLES `producto_has_proveedor` WRITE;
/*!40000 ALTER TABLE `producto_has_proveedor` DISABLE KEYS */;
INSERT INTO `db_grupo2`.`producto_has_proveedor` 
(`producto_idproducto`, `proveedor_idproveedor`)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 2),
(3, 4),
(4, 3),
(4, 5),
(5, 4),
(5, 1),
(6, 2),
(6, 5),
(7, 3),
(7, 4),
(8, 1),
(8, 5),
(9, 2),
(9, 3),
(10, 4),
(10, 5);
/*!40000 ALTER TABLE `producto_has_proveedor` ENABLE KEYS */;
UNLOCK TABLES;


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
  `cantidad` INT NOT NULL DEFAULT '24',
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
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
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
