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
-- Table `db_grupo2`.`codigodespachador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`codigodespachador` (
  `idcodigoDespachador` INT NOT NULL AUTO_INCREMENT,
  `codigoDespachador` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcodigoDespachador`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`codigosjurisdiccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`codigosjurisdiccion` (
  `idcodigos` INT NOT NULL AUTO_INCREMENT,
  `codigoJurisdiccion` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcodigos`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`zona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`zona` (
  `idzona` INT NOT NULL,
  `nombreZona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idzona`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


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
  PRIMARY KEY (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


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
  PRIMARY KEY (`idproveedor`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_proveedor_zona2_idx` (`idzona` ASC) VISIBLE,
  CONSTRAINT `fk_proveedor_zona2`
    FOREIGN KEY (`idzona`)
    REFERENCES `db_grupo2`.`zona` (`idzona`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb3;


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


-- -----------------------------------------------------
-- Table `db_grupo2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`usuario` (
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
  `idroles` INT NOT NULL,
  `distritos_iddistritos` INT NULL DEFAULT NULL,
  `idzona` INT NOT NULL,
  `fechanacim` DATE NULL DEFAULT NULL,
  `estado` VARCHAR(45) NULL DEFAULT NULL,
  `idproveedor` INT NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) VISIBLE,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) VISIBLE,
  UNIQUE INDEX `ruc_UNIQUE` (`ruc` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`idroles` ASC) VISIBLE,
  INDEX `fk_usuario_distritos1_idx` (`distritos_iddistritos` ASC) VISIBLE,
  INDEX `fk_usuario_zona1_idx` (`idzona` ASC) VISIBLE,
  INDEX `fk_usuario_proveedor1_idx` (`idproveedor` ASC) VISIBLE,
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
    REFERENCES `db_grupo2`.`zona` (`idzona`))
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_grupo2`.`ordenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_grupo2`.`ordenes` (
  `idordenes` INT NOT NULL AUTO_INCREMENT,
  `estadoOrdenes` VARCHAR(45) NOT NULL,
  `fechaArribo` DATE NOT NULL,
  `usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idordenes`, `usuario_idusuario`),
  INDEX `fk_ordenes_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_ordenes_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
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
  `cantidad` INT NOT NULL,
  `fechaSolicitud` DATE NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`idreposicionProductos`),
  INDEX `fk_reposicionProductos_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_reposicionProductos_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
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
  `usuario_idusuario` INT NOT NULL,
  `tipoPublicacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idresenias`),
  INDEX `fk_resenias_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  INDEX `fk_resenias_usuario1_idx` (`usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_resenias_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `db_grupo2`.`producto` (`idproducto`),
  CONSTRAINT `fk_resenias_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `db_grupo2`.`usuario` (`idusuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
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
