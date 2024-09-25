-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema DB_GRUPO2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema DB_GRUPO2
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `DB_GRUPO2`;
CREATE SCHEMA IF NOT EXISTS `DB_GRUPO2` DEFAULT CHARACTER SET utf8mb3 ;
USE `DB_GRUPO2` ;

-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`codigodespachador`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`codigodespachador` (
  `idcodigoDespachador` INT NOT NULL AUTO_INCREMENT,
  `codigoDespachador` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcodigoDespachador`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`codigoDespachador` 
(`codigoDespachador`, `estado`) VALUES
('DESP001', 'Activo'),
('DESP002', 'Inactivo'),
('DESP003', 'Activo'),
('DESP004', 'Pendiente'),
('DESP005', 'Activo'),
('DESP006', 'Inactivo'),
('DESP007', 'Activo');


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`codigosjurisdiccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`codigosjurisdiccion` (
  `idcodigos` INT NOT NULL AUTO_INCREMENT,
  `codigoJurisdiccion` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcodigos`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`codigosJurisdiccion` 
(`codigoJurisdiccion`, `estado`) VALUES
('JUR001', 'Activo'),
('JUR002', 'Inactivo'),
('JUR003', 'Activo'),
('JUR004', 'Pendiente'),
('JUR005', 'Activo'),
('JUR006', 'Inactivo'),
('JUR007', 'Activo');


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`faq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`faq` (
  `idfaq` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(200) NOT NULL,
  `pregunta` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`idfaq`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`roles` (
  `idRoles` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idRoles`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `roles` (`idRoles`, `rol`)
VALUES
(1, 'Superadmin'),
(2, 'Coordinador'),
(3, 'Agente'),
(4, 'Usuario');


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`zona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`zona` (
  `idzona` INT NOT NULL,
  `nombreZona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB;
INSERT INTO `zona` (`idzona`, `nombreZona`)
VALUES
(1, 'Norte'),
(2, 'Sur'),
(3, 'Este'),
(4, 'Oeste');


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`distritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`distritos` (
  `iddistritos` INT NOT NULL,
  `nombreDistrito` VARCHAR(45) NOT NULL,
  `zona_idzona` INT NOT NULL,
  PRIMARY KEY (`iddistritos`),
  INDEX `fk_distritos_zona1_idx` (`zona_idzona` ASC) VISIBLE,
  CONSTRAINT `fk_distritos_zona1`
    FOREIGN KEY (`zona_idzona`)
    REFERENCES `DB_GRUPO2`.`zona` (`idzona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `DB_GRUPO2`.`distritos` (`iddistritos`, `nombreDistrito`, `zona_idzona`)
VALUES
-- Zona Norte (1)
(1, 'Comas', 1),
(2, 'Carabayllo', 1),
(3, 'San Martín de Porres', 1),
(4, 'Los Olivos', 1),
(5, 'Independencia', 1),
(6, 'Puente Piedra', 1),
(7, 'Ancón', 1),
(8, 'Santa Rosa', 1),

-- Zona Sur (2)
(9, 'Santiago de Surco', 2),
(10, 'Chorrillos', 2),
(11, 'Villa El Salvador', 2),
(12, 'Pachacámac', 2),
(13, 'Lurín', 2),
(14, 'Punta Hermosa', 2),
(15, 'Punta Negra', 2),
(16, 'San Bartolo', 2),
(17, 'Santa María del Mar', 2),
(18, 'Villa María del Triunfo', 2),

-- Zona Este (3)
(19, 'Ate', 3),
(20, 'La Molina', 3),
(21, 'Cieneguilla', 3),
(22, 'Santa Anita', 3),
(23, 'San Juan de Lurigancho', 3),
(24, 'El Agustino', 3),

-- Zona Oeste (4)
(25, 'Miraflores', 4),
(26, 'San Isidro', 4),
(27, 'Magdalena del Mar', 4),
(28, 'San Miguel', 4),
(29, 'Pueblo Libre', 4),
(30, 'Breña', 4),
(31, 'Jesús María', 4),
(32, 'Lince', 4),
(33, 'La Victoria', 4);



-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(256) NOT NULL,
  `ruc` VARCHAR(45) NOT NULL,
  `notificaciones` VARCHAR(1024) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NULL DEFAULT NULL,
  `estadoUsuario` VARCHAR(45) NULL DEFAULT NULL,
  `codigoJurisdiccion` VARCHAR(45) NULL DEFAULT NULL,
  `codigoDespachador` VARCHAR(45) NULL DEFAULT NULL,
  `razonSocial` VARCHAR(100) NULL DEFAULT NULL,
  `solicitudAgente` TINYINT NULL DEFAULT NULL,
  `isBan` TINYINT NOT NULL,
  `roles_idRoles` INT NOT NULL,
  `distritos_iddistritos` INT NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`roles_idRoles` ASC) VISIBLE,
  INDEX `fk_usuario_distritos1_idx` (`distritos_iddistritos` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`roles_idRoles`)
    REFERENCES `DB_GRUPO2`.`roles` (`idRoles`),
  CONSTRAINT `fk_usuario_distritos1`
    FOREIGN KEY (`distritos_iddistritos`)
    REFERENCES `DB_GRUPO2`.`distritos` (`iddistritos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `usuario` (`idusuario`, `nombre`, `apellido`, `dni`, `contrasena`, `direccion`, `ruc`, `notificaciones`, `correo`, `telefono`, `estadoUsuario`, `codigoJurisdiccion`, `codigoDespachador`, `razonSocial`, `solicitudAgente`, `isBan`, `roles_idRoles`,`distritos_iddistritos`)
VALUES
-- --Un superadmin--
(1, 'Juan', 'Pérez', '87654301', 'pass111', 'Calle Luna 100', '20123456701',  'Notificaciones Activas', 'juan.perez@example.com', '987654321', 'Activo', 'JUR101', 'DES401', 'Pérez SAC',0, 0, 1,1),
-- --8 coordinadores zonales como máximo -- --
-- -- -- 2 coordis por zona -- -- --
-- -- -- norte -- -- --
(4, 'Laura', 'Ramírez', '87654304', 'pass444', 'Av. Luna 400', '20123456704', 'Notificaciones Inactivas', 'laura.ramirez@example.com', '934567890', 'Activo', 'JUR104', 'DES404', 'Ramírez EIRL', 0, 0, 2,3),
(5, 'Pedro', 'Fernández', '87654305', 'pass555', 'Calle Mar 500', '20123456705',  'Notificaciones Activas', 'pedro.fernandez@example.com', '945678901', 'Inactivo', 'JUR105', 'DES405', 'Fernández SAC', 0, 0, 2,5),
-- -- -- sur -- -- --
(26, 'Rodrigo', 'Flores', '87654326', 'pass2626', 'Av. Pino 2600', '20123456726',  'Notificaciones Activas', 'rodrigo.flores@example.com', '996112233', 'Activo', 'JUR126', 'DES426', 'Flores SAC', 0, 0, 2,9),
(27, 'Valeria', 'Huerta', '87654327', 'pass2727', 'Calle Sauce 2700', '20123456727', 'Notificaciones Inactivas', 'valeria.huerta@example.com', '997223344', 'Inactivo', 'JUR127', 'DES427', 'Huerta EIRL', 1, 0, 2,10),
-- -- -- este -- -- --
(28, 'Francisco', 'Pinto', '87654328', 'pass2828', 'Av. Cedro 2800', '20123456728',  'Notificaciones Activas', 'francisco.pinto@example.com', '998334455', 'Activo', 'JUR128', 'DES428', 'Pinto SAC', 0, 0, 2,19),
(29, 'Gabriela', 'Montalvo', '87654329', 'pass2929', 'Calle Abeto 2900', '20123456729',  'Notificaciones Inactivas', 'gabriela.montalvo@example.com', '999445566', 'Activo', 'JUR129', 'DES429', 'Montalvo EIRL', 1, 0, 2,20),
-- -- -- oeste -- -- --
(30, 'Sergio', 'Villalobos', '87654330', 'pass3030', 'Av. Olivo 3000', '20123456730', 'Notificaciones Activas', 'sergio.villalobos@example.com', '990556677', 'Inactivo', 'JUR130', 'DES430', 'Villalobos SAC', 0, 0, 2,25),
(31, 'Elisa', 'Cabrera', '87654331', 'pass3131', 'Calle Roble 3100', '20123456731', 'Notificaciones Activas', 'elisa.cabrera@example.com', '991667788', 'Activo', 'JUR131', 'DES431', 'Cabrera EIRL', 1,0, 2,26),
-- --24 agentes de compra como máximo -- --
-- -- -- 6 agentes por zona -- -- --
-- -- -- norte -- -- --
(3, 'Carlos', 'García', '87654303', 'pass333', 'Calle Estrella 300', '20123456703', 'Notificaciones Activas', 'carlos.garcia@example.com', '923456789', 'Inactivo', 'JUR103', 'DES403', 'García SAC', 0,0, 3,7),
-- -- -- sur -- -- --
(2, 'Maria', 'López', '87654302', 'pass222', 'Av. Sol 200', '20123456702',  'Notificaciones Inactivas', 'maria.lopez@example.com', '912345678', 'Activo', 'JUR102', 'DES402', 'López EIRL', 0, 0, 3,11),
(6, 'Elena', 'Castro', '87654306', 'pass666', 'Av. Roca 600', '20123456706',  'Notificaciones Activas', 'elena.castro@example.com', '956789012', 'Activo', 'JUR106', 'DES406', 'Castro EIRL', 0,0, 3,12),
-- -- -- este -- -- --
(32, 'Miguel', 'Ortega', '87654332', 'pass3232', 'Av. Pino 3200', '20123456732',  'Notificaciones Inactivas', 'miguel.ortega@example.com', '992778899', 'Activo', 'JUR132', 'DES432', 'Ortega SAC', 0, 0, 3,21),
(33, 'Luciana', 'Rivera', '87654333', 'pass3333', 'Calle Sauce 3300', '20123456733',  'Notificaciones Activas', 'luciana.rivera@example.com', '993889900', 'Activo', 'JUR133', 'DES433', 'Rivera EIRL', 1, 0, 3,22),
-- -- -- oeste -- -- --
(34, 'Julio', 'Escobar', '87654334', 'pass3434', 'Av. Cedro 3400', '20123456734', 'Notificaciones Inactivas', 'julio.escobar@example.com', '994990011', 'Inactivo', 'JUR134', 'DES434', 'Escobar SAC', 0,0, 3,27),
(35, 'Natalia', 'Castillo', '87654335', 'pass3535', 'Calle Abeto 3500', '20123456735',  'Notificaciones Activas', 'natalia.castillo@example.com', '995001122', 'Activo', 'JUR135', 'DES435', 'Castillo EIRL', 1,0, 3,28),
-- --2400 usuarios finales como máximo -- --
-- -- -- 600 usuarios por zona -- -- --
-- -- -- norte -- -- --
(7, 'Raúl', 'Torres', '87654307', 'pass777', 'Calle Nube 700', '20123456707',  'Notificaciones Inactivas', 'raul.torres@example.com', '967890123', 'Activo', 'JUR107', 'DES407', 'Torres SAC', 1, 0, 4,3),
(10, 'Lucía', 'Mendoza', '87654310', 'pass1010', 'Av. Viento 1000', '20123456710',  'Notificaciones Activas', 'lucia.mendoza@example.com', '990123456', 'Activo', 'JUR110', 'DES410', 'Mendoza EIRL', 1,0, 4,4),
(11, 'Ana', 'Rojas', '87654311', 'pass1111', 'Calle Fuego 1100', '20123456711',  'Notificaciones Inactivas', 'ana.rojas@example.com', '991234567', 'Activo', 'JUR111', 'DES411', 'Rojas SAC', 1, 0, 4,5),
(14, 'Andrés', 'Guzmán', '87654314', 'pass1414', 'Av. Relámpago 1400', '20123456714',  'Notificaciones Inactivas', 'andres.guzman@example.com', '994567890', 'Inactivo', 'JUR114', 'DES414', 'Guzmán EIRL', 0, 0, 4,6),
(18, 'Hugo', 'Mejía', '87654318', 'pass1818', 'Av. Truenos 1800', '20123456718',  'Notificaciones Inactivas', 'hugo.mejia@example.com', '998901234', 'Activo', 'JUR118', 'DES418', 'Mejía EIRL', 1, 0, 4,7),
-- -- -- sur -- -- --
(8, 'Sofía', 'Vargas', '87654308', 'pass888', 'Av. Arena 800', '20123456708',  'Notificaciones Activas', 'sofia.vargas@example.com', '978901234', 'Inactivo', 'JUR108', 'DES408', 'Vargas EIRL', 0, 0, 4,13),
(13, 'Gloria', 'Paredes', '87654313', 'pass1313', 'Calle Trueno 1300', '20123456713','Notificaciones Activas', 'gloria.paredes@example.com', '993456789', 'Activo', 'JUR113', 'DES413', 'Paredes SAC', 0, 0, 4,14),
(15, 'Patricia', 'Montes', '87654315', 'pass1515', 'Calle Lluvia 1500', '20123456715',  'Notificaciones Activas', 'patricia.montes@example.com', '995678901', 'Activo', 'JUR115', 'DES415', 'Montes SAC', 1, 0, 4,15),
(17, 'Daniela', 'Cruz', '87654317', 'pass1717', 'Calle Rayo 1700', '20123456717',  'Notificaciones Activas', 'daniela.cruz@example.com', '997890123', 'Inactivo', 'JUR117', 'DES417', 'Cruz SAC', 0,0, 4,16),
(19, 'Camila', 'Vega', '87654319', 'pass1919', 'Calle Ríos 1900', '20123456719',  'Notificaciones Activas', 'camila.vega@example.com', '999012345', 'Activo', 'JUR119', 'DES419', 'Vega SAC', 1, 0, 4,17),
-- -- -- este -- -- --
(9, 'Jorge', 'Díaz', '87654309', 'pass999', 'Calle Sol 900', '20123456709',  'Notificaciones Inactivas', 'jorge.diaz@example.com', '989012345', 'Activo', 'JUR109', 'DES409', 'Díaz SAC', 1, 0, 4,23),
(12, 'Luis', 'Ortiz', '87654312', 'pass1212', 'Av. Brisa 1200', '20123456712',  'Notificaciones Activas', 'luis.ortiz@example.com', '992345678', 'Inactivo', 'JUR112', 'DES412', 'Ortiz EIRL', 1, 0, 4,24),
(16, 'Gabriel', 'Salazar', '87654316', 'pass1616', 'Av. Viento 1600', '20123456716',  'Notificaciones Inactivas', 'gabriel.salazar@example.com', '996789012', 'Activo', 'JUR116', 'DES416', 'Salazar EIRL', 1,0, 4,23),
(20, 'Alonso', 'Valdez', '87654320', 'pass2020', 'Av. Roca 2000', '20123456720',  'Notificaciones Inactivas', 'alonso.valdez@example.com', '990123456', 'Inactivo', 'JUR120', 'DES420', 'Valdez EIRL', 0, 0, 4,22),
-- -- -- oeste -- -- --
(21, 'Diego', 'Navarro', '87654321', 'pass2121', 'Calle Verde 2100', '20123456721',  'Notificaciones Activas', 'diego.navarro@example.com', '991112233', 'Activo', 'JUR121', 'DES421', 'Navarro SAC', 0, 0, 4,29),
(22, 'Mónica', 'Zambrano', '87654322', 'pass2222', 'Av. Sol 2200', '20123456722',  'Notificaciones Inactivas', 'monica.zambrano@example.com', '992223344', 'Inactivo', 'JUR122', 'DES422', 'Zambrano EIRL', 1,0, 4,30),
(23, 'Javier', 'Cárdenas', '87654323', 'pass2323', 'Calle Luna 2300', '20123456723',  'Notificaciones Activas', 'javier.cardenas@example.com', '993334455', 'Activo', 'JUR123', 'DES423', 'Cárdenas SAC', 0,0, 4,31),
(24, 'Carolina', 'Sánchez', '87654324', 'pass2424', 'Av. Estrella 2400', '20123456724',  'Notificaciones Inactivas', 'carolina.sanchez@example.com', '994445566', 'Activo', 'JUR124', 'DES424', 'Sánchez EIRL', 1, 0, 4,32);


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`ordenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`ordenes` (
  `idordenes` INT NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` VARCHAR(45) NOT NULL,
  `fechaArribo` DATE NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  INDEX `fk_ordenes_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `DB_GRUPO2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`ordenes` (`idordenes`, `estadoOrdenes`, `fechaArribo`, `usuario_idusuario`)
VALUES
(1, 'Pendiente', '2024-09-22', 1),
(2, 'En Proceso', '2024-09-23', 2),
(3, 'Completada', '2024-09-24', 3),
(4, 'Cancelada', '2024-09-25', 4),
(5, 'Pendiente', '2024-09-26', 5),
(6, 'Completada', '2024-09-27', 6),
(7, 'En Proceso', '2024-09-28', 7),
(8, 'Cancelada', '2024-09-29', 8),
(9, 'Pendiente', '2024-09-30', 9),
(10, 'Completada', '2024-10-01', 10);



-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`producto` (
  `idproducto` INT NOT NULL AUTO_INCREMENT,
  `nombreProducto` VARCHAR(100) NOT NULL,
  `categoria` VARCHAR(45) NOT NULL,
  `cantidadDisponible` INT NOT NULL,
  `descripcion` VARCHAR(1000) NOT NULL,
  `precio` DOUBLE NOT NULL,
  `costoEnvio` DOUBLE NOT NULL,
  `cantidadTotal` INT NULL DEFAULT NULL,
  `ordenes_idordenes` INT NOT NULL,
  PRIMARY KEY (`idproducto`),
  INDEX `fk_productos_ordenes1_idx` (`ordenes_idordenes` ASC) VISIBLE,
  CONSTRAINT `fk_productos_ordenes1`
    FOREIGN KEY (`ordenes_idordenes`)
    REFERENCES `DB_GRUPO2`.`ordenes` (`idordenes`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`producto` (`idproducto`, `nombreProducto`, `categoria`, `cantidadDisponible`, `descripcion`, `precio`, `costoEnvio`, `cantidadTotal`, `ordenes_idordenes`)
VALUES
(1, 'Laptop Lenovo', 'Electrónica', 15, 'Laptop Lenovo con 16GB RAM, 512GB SSD, pantalla de 15.6 pulgadas.', 1200.50, 25.00, 100, 1),
(2, 'iPhone 13', 'Electrónica', 20, 'iPhone 13 con 128GB, pantalla OLED de 6.1 pulgadas.', 999.99, 20.00, 150, 2),
(3, 'Samsung Galaxy S21', 'Electrónica', 10, 'Samsung Galaxy S21 con 128GB, pantalla de 6.2 pulgadas.', 850.75, 18.00, 120, 3),
(4, 'Silla Gamer', 'Muebles', 30, 'Silla gamer ergonómica con soporte lumbar ajustable.', 199.99, 15.00, 300, 4),
(5, 'Teclado Mecánico', 'Accesorios', 50, 'Teclado mecánico RGB con switches azules.', 75.00, 10.00, 200, 5),
(6, 'Monitor 4K', 'Electrónica', 12, 'Monitor 4K UHD de 27 pulgadas con HDR.', 350.00, 22.00, 80, 6),
(7, 'Cámara Canon EOS', 'Fotografía', 8, 'Cámara Canon EOS Rebel T7i con lente de 18-55mm.', 700.99, 30.00, 50, 7),
(8, 'Auriculares Bluetooth', 'Accesorios', 25, 'Auriculares inalámbricos Bluetooth con cancelación de ruido.', 150.00, 8.00, 100, 8),
(9, 'Mouse Inalámbrico', 'Accesorios', 60, 'Mouse inalámbrico con sensor óptico de alta precisión.', 35.99, 5.00, 300, 9),
(10, 'Impresora HP LaserJet', 'Oficina', 5, 'Impresora HP LaserJet con impresión a doble cara automática.', 250.50, 12.00, 40, 10);


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`fotos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`fotos` (
  `idfotos` INT NOT NULL AUTO_INCREMENT,
  `foto1` BLOB NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idfotos`),
  INDEX `fk_fotos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_fotos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `DB_GRUPO2`.`producto` (`idproducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`pagos` (
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
    REFERENCES `DB_GRUPO2`.`ordenes` (`idordenes`),
  CONSTRAINT `fk_pagos_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `DB_GRUPO2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`proveedor` (
  `idproveedor` INT NOT NULL AUTO_INCREMENT,
  `nombreTienda` VARCHAR(45) NOT NULL,
  `nombreProveedor` VARCHAR(45) NOT NULL,
  `dni` VARCHAR(45) NOT NULL,
  `ruc` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `zona_idzona1` INT NOT NULL,
  `fotoProveedor` BLOB NULL DEFAULT NULL,
  
  PRIMARY KEY (`idproveedor`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_proveedor_zona2_idx` (`zona_idzona1` ASC) VISIBLE,
  CONSTRAINT `fk_proveedor_zona2`
    FOREIGN KEY (`zona_idzona1`)
    REFERENCES `DB_GRUPO2`.`zona` (`idzona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`proveedor` (`idproveedor`, `nombreTienda`, `nombreProveedor`, `dni`, `ruc`, `correo`, `zona_idzona1`, `fotoProveedor`)
VALUES
(1, 'Tienda Tech', 'Carlos Pérez', '12345678', '20123456781', 'carlos.perez@tiendatech.com', 1, LOAD_FILE('path/to/photo1.jpg')),
(2, 'Electrónica Lima', 'María García', '87654321', '20234567892', 'maria.garcia@electroniclima.com', 2, LOAD_FILE('path/to/photo2.jpg')),
(3, 'Muebles del Sur', 'Jorge López', '11223344', '20345678903', 'jorge.lopez@mueblesdelsur.com', 2, LOAD_FILE('path/to/photo3.jpg')),
(4, 'Fashion Store', 'Ana Torres', '22334455', '20456789014', 'ana.torres@fashionstore.com', 3, LOAD_FILE('path/to/photo4.jpg')),
(5, 'Supermercado Central', 'Luis Ramos', '33445566', '20567890125', 'luis.ramos@supercentral.com', 3, LOAD_FILE('path/to/photo5.jpg')),
(6, 'Tech World', 'Sofía Mendoza', '44556677', '20678901236', 'sofia.mendoza@techworld.com', 1, LOAD_FILE('path/to/photo6.jpg')),
(7, 'Electro Perú', 'Fernando Salas', '55667788', '20789012347', 'fernando.salas@electroperu.com', 4, LOAD_FILE('path/to/photo7.jpg')),
(8, 'Construcción Total', 'Raúl Vega', '66778899', '20890123458', 'raul.vega@construcciontotal.com', 4, LOAD_FILE('path/to/photo8.jpg')),
(9, 'Librería Escolar', 'Claudia Díaz', '77889900', '20901234569', 'claudia.diaz@libreriaescolar.com', 2, LOAD_FILE('path/to/photo9.jpg')),
(10, 'Alimentos Frescos', 'Ricardo Núñez', '88990011', '21012345670', 'ricardo.nunez@alimentosfrescos.com', 3, LOAD_FILE('path/to/photo10.jpg'));


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`producto_has_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`producto_has_proveedor` (
  `producto_idproducto` INT NOT NULL,
  `proveedor_idproveedor` INT NOT NULL,
  PRIMARY KEY (`producto_idproducto`, `proveedor_idproveedor`),
  INDEX `fk_producto_has_proveedor_proveedor1_idx` (`proveedor_idproveedor` ASC) VISIBLE,
  INDEX `fk_producto_has_proveedor_producto1_idx` (`producto_idproducto` ASC) VISIBLE,
  CONSTRAINT `fk_producto_has_proveedor_producto1`
    FOREIGN KEY (`producto_idproducto`)
    REFERENCES `DB_GRUPO2`.`producto` (`idproducto`),
  CONSTRAINT `fk_producto_has_proveedor_proveedor1`
    FOREIGN KEY (`proveedor_idproveedor`)
    REFERENCES `DB_GRUPO2`.`proveedor` (`idproveedor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`producto_has_proveedor` 
(`producto_idproducto`, `proveedor_idproveedor`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 1),
(4, 3),
(5, 2),
(2, 1),
(3, 3);


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`productos_has_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`productos_has_usuario` (
  `productos_idproductos` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`productos_idproductos`, `usuario_idusuario`),
  INDEX `fk_productos_has_usuario_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  INDEX `fk_productos_has_usuario_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_productos_has_usuario_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `DB_GRUPO2`.`producto` (`idproducto`),
  CONSTRAINT `fk_productos_has_usuario_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `DB_GRUPO2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`productos_has_usuario` 
(`productos_idproductos`, `usuario_idusuario`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(2, 4),
(4, 1),
(5, 3),
(1, 4),
(3, 2);


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`reposicionproductos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`reposicionproductos` (
  `idreposicionProductos` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `fechaSolicitud` DATE NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idreposicionProductos`),
  INDEX `fk_reposicionProductos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_reposicionProductos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `DB_GRUPO2`.`producto` (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`reposicionProductos` 
(`cantidad`, `fechaSolicitud`, `productos_idproductos`) VALUES
(50, '2024-09-01', 1),
(30, '2024-09-05', 2),
(20, '2024-09-10', 3),
(100, '2024-09-12', 1),
(15, '2024-09-15', 4),
(45, '2024-09-20', 2),
(70, '2024-09-22', 3);


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`resenias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`resenias` (
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
  `usuario_idusuario` INT NOT NULL,
  `tipoPublicacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idresenias`),
  INDEX `fk_resenias_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  INDEX `fk_resenias_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_resenias_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `DB_GRUPO2`.`producto` (`idproducto`),
  CONSTRAINT `fk_resenias_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `DB_GRUPO2`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;
INSERT INTO `DB_GRUPO2`.`resenias` (`descripcion`, `calidad`, `rapidez`, `puntuacion`, `foto`, `respuesta`, `productos_idproductos`, `tituloForo`, `descripcionForo`,`usuario_idusuario`,`tipoPublicacion`) VALUES
('Excelente producto, lo recomendaría a todos.', 'Alta', 'Rápida', 5, NULL, 'Gracias por tu comentario!', 1, 'Recomendación de Producto A', 'Me encantó el Producto A, superó mis expectativas.',29,'Foro'),
('Buen rendimiento, aunque podría mejorar la calidad.', 'Media', 'Moderada', 4, NULL, 'Agradecemos tu feedback.', 2, 'Opinión sobre Producto B', 'El Producto B es bueno, pero creo que necesita ajustes.',30,'Foro'),
('No estoy satisfecho con el servicio.', 'Baja', 'Lenta', 2, NULL, 'Lamentamos que hayas tenido esta experiencia.', 3, 'Crítica del Producto C', 'El Producto C no cumplió con mis expectativas.',31,'Reseña'),
('Gran relación calidad-precio.', 'Alta', 'Rápida', 5, NULL, 'Nos alegra que lo pienses así.', 1, 'Producto D recomendado', 'El Producto D es muy bueno y económico.',32,'Foro'),
('El producto llegó dañado.', 'Baja', 'Lenta', 1, NULL, 'Lamentamos lo ocurrido, te ayudaremos.', 4, 'Problema con el Producto E', 'No estoy contento con la entrega, llegó en mal estado.',33,'Reseña');


-- -----------------------------------------------------
-- Table `DB_GRUPO2`.`respuestas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_GRUPO2`.`respuestas` (
  `resenias_idresenias` INT NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  `respuestas` VARCHAR(45) NULL,
  PRIMARY KEY (`resenias_idresenias`, `usuario_idusuario`),
  INDEX `fk_resenias_has_usuario_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  INDEX `fk_resenias_has_usuario_resenias1_idx` (`resenias_idresenias` ASC) VISIBLE,
  CONSTRAINT `fk_resenias_has_usuario_resenias1`
    FOREIGN KEY (`resenias_idresenias`)
    REFERENCES `DB_GRUPO2`.`resenias` (`idresenias`),
  CONSTRAINT `fk_resenias_has_usuario_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `DB_GRUPO2`.`usuario` (`idusuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
