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

-- -----------------------------------------------------
-- Schema db_grupo2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_grupo2` DEFAULT CHARACTER SET utf8mb3 ;
USE `db_grupo2` ;

-- -----------------------------------------------------
-- Table `db_grupo2`.`despachador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`despachador` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`despachador` (
  `iddespachador` INT NOT NULL,
  `despachador` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iddespachador`),
  UNIQUE INDEX `despachador_UNIQUE` (`despachador` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`zona`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`zona` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`zona` (
  `idzona` INT NOT NULL,
  `nombreZona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`distritos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`distritos` ;

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


-- -----------------------------------------------------
-- Table `db_grupo2`.`faq`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`faq` ;

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
DROP TABLE IF EXISTS `db_grupo2`.`producto` ;

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
  `image` BLOB NULL DEFAULT NULL,
  `fechaArribo` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`fotos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`fotos` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`fotos` (
  `idfotos` INT NOT NULL AUTO_INCREMENT,
  `foto1` VARCHAR(255) NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idfotos`),
  INDEX `fk_fotos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_fotos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`jurisdiccion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`jurisdiccion` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`jurisdiccion` (
  `idjurisdiccion` INT NOT NULL,
  `jurisdiccion` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idjurisdiccion`),
  UNIQUE INDEX `jurisdiccion_UNIQUE` (`jurisdiccion` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`proveedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`proveedor` ;

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
  `rating` DECIMAL(2,1) NULL DEFAULT NULL,
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


-- -----------------------------------------------------
-- Table `db_grupo2`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`roles` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`roles` (
  `idRoles` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idRoles`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`usuario` ;

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
  `idsuperior` INT NULL DEFAULT NULL,
  `cantidadcompras` INT NULL DEFAULT NULL,
  `fotos_idfotos` INT NULL DEFAULT NULL,
  `isban` TINYINT NULL DEFAULT NULL,
  `calificacion` INT NULL DEFAULT NULL,
  `despachador` VARCHAR(50) NULL DEFAULT NULL,
  `jurisdiccion` VARCHAR(50) NULL DEFAULT NULL,
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
  INDEX `fk_usuario_despachador1_idx` (`despachador` ASC) VISIBLE,
  INDEX `fk_usuario_jurisdiccion1_idx` (`jurisdiccion` ASC) VISIBLE,
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
    REFERENCES `db_grupo2`.`zona` (`idzona`))
ENGINE = InnoDB
AUTO_INCREMENT = 110
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`ordenes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`ordenes` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`ordenes` (
  `idordenes` INT NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` VARCHAR(45) NULL DEFAULT NULL,
  `fechaArribo` DATE NULL DEFAULT NULL,
  `usuario_idusuario` INT NOT NULL,
  `mesCreacion` VARCHAR(20) NULL DEFAULT NULL,
  `fechaCreacion` DATE NULL DEFAULT NULL,
  `agentexorden` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  INDEX `fk_ordenes_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 1022
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`pagos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`pagos` ;

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


-- -----------------------------------------------------
-- Table `db_grupo2`.`producto_has_ordenes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`producto_has_ordenes` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`producto_has_ordenes` (
  `producto_idproducto` INT NOT NULL,
  `ordenes_idordenes` INT NOT NULL,
  `cantidadxproducto` INT NULL DEFAULT NULL,
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
DROP TABLE IF EXISTS `db_grupo2`.`producto_has_proveedor` ;

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
DROP TABLE IF EXISTS `db_grupo2`.`productos_has_usuario` ;

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
DROP TABLE IF EXISTS `db_grupo2`.`reposicionproductos` ;

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
DROP TABLE IF EXISTS `db_grupo2`.`resenias` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`resenias` (
  `idresenias` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(500) NOT NULL,
  `calidad` VARCHAR(45) NULL DEFAULT NULL,
  `rapidez` VARCHAR(45) NULL DEFAULT NULL,
  `puntuacion` INT NULL DEFAULT NULL,
  `foto` BLOB NULL DEFAULT NULL,
  `productos_idproductos` INT NULL DEFAULT NULL,
  `tituloresena` VARCHAR(200) NULL DEFAULT NULL,
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
DROP TABLE IF EXISTS `db_grupo2`.`respuestas` ;

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


-- -----------------------------------------------------
-- Table `db_grupo2`.`spring_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`spring_session` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`spring_session` (
  `PRIMARY_ID` CHAR(36) NOT NULL,
  `SESSION_ID` CHAR(36) NOT NULL,
  `CREATION_TIME` BIGINT NOT NULL,
  `LAST_ACCESS_TIME` BIGINT NOT NULL,
  `MAX_INACTIVE_INTERVAL` INT NOT NULL,
  `EXPIRY_TIME` BIGINT NOT NULL,
  `PRINCIPAL_NAME` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE INDEX `SPRING_SESSION_IX1` (`SESSION_ID` ASC) VISIBLE,
  INDEX `SPRING_SESSION_IX2` (`EXPIRY_TIME` ASC) VISIBLE,
  INDEX `SPRING_SESSION_IX3` (`PRINCIPAL_NAME` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3
ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `db_grupo2`.`spring_session_attributes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_grupo2`.`spring_session_attributes` ;

CREATE TABLE IF NOT EXISTS `db_grupo2`.`spring_session_attributes` (
  `SESSION_PRIMARY_ID` CHAR(36) NOT NULL,
  `ATTRIBUTE_NAME` VARCHAR(200) NOT NULL,
  `ATTRIBUTE_BYTES` BLOB NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK`
    FOREIGN KEY (`SESSION_PRIMARY_ID`)
    REFERENCES `db_grupo2`.`spring_session` (`PRIMARY_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3
ROW_FORMAT = DYNAMIC;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
