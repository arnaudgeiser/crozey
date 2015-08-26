-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`UTILISATEURS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`UTILISATEURS` (
  `ID` INT NOT NULL,
  `NOM_PRENOM` VARCHAR(45) NOT NULL,
  `UTILISATEUR` VARCHAR(45) NOT NULL,
  `MOT_DE_PASSE` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`RESERVATIONS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`RESERVATIONS` (
  `ID` INT NOT NULL,
  `IDUTILISATEUR` INT NOT NULL,
  `TITRE` VARCHAR(45) NOT NULL,
  `DATE_DEBUT` DATE NOT NULL,
  `DATE_FIN` DATE NOT NULL,
  `PRIVE` TINYINT(1) NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_RESERVATIONS_UTILISATEURS_idx` (`IDUTILISATEUR` ASC),
  CONSTRAINT `FK_RESERVATIONS_UTILISATEURS`
    FOREIGN KEY (`IDUTILISATEUR`)
    REFERENCES `mydb`.`UTILISATEURS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CHALETS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CHALETS` (
  `ID` INT NOT NULL,
  `NOM` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NOM_UNIQUE` (`NOM` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
