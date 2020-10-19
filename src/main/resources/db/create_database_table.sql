DROP USER IF EXISTS 'petuser'@'localhost';
CREATE USER  'petuser'@'localhost' IDENTIFIED BY 'petuser123';
GRANT ALL PRIVILEGES ON chatondb.* TO 'petuser'@'localhost';


DROP DATABASE IF EXISTS chatondb;

CREATE SCHEMA IF NOT EXISTS `chatondb` DEFAULT CHARACTER SET latin1 ;
USE `chatondb` ;

-- -----------------------------------------------------
-- Table `chatondb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`address`;

CREATE TABLE IF NOT EXISTS `chatondb`.`address` (
  `id` INT(11) NOT NULL,
  `house_number` VARCHAR(20) NULL DEFAULT NULL,
  `street_name` VARCHAR(100) NULL DEFAULT NULL,
  `town_name` VARCHAR(100) NULL DEFAULT NULL,
  `local_gov_area` VARCHAR(100) NULL DEFAULT NULL,
  `state` VARCHAR(200) NULL DEFAULT NULL,
  `country` VARCHAR(100) NULL DEFAULT NULL
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `chatondb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`user` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`user` (
  `id` INT(11) NOT NULL,
  `username` VARCHAR(5) NOT NULL,
  `password` VARCHAR(400) NOT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(200) NOT NULL,
  `phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `user_photo` VARCHAR(100) NULL DEFAULT NULL,
  `user_billboard` VARCHAR(100) NULL DEFAULT NULL,
  `address_id` INT(10) NULL DEFAULT NULL,
  `date_join` TIMESTAMP,
  `role` VARCHAR(128) NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
      FOREIGN KEY (`address_id`)
      REFERENCES `chatondb`.`address` (`id`)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`post` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`post` (
  `id` INT(11) NOT NULL,
  `post_title` VARCHAR(50) NULL DEFAULT NULL,
  `post_body` VARCHAR(1000) NULL DEFAULT NULL,
  `post_file` VARCHAR(100) NULL DEFAULT NULL,
  `date_posted` TIMESTAMP,
  `date_updated` TIMESTAMP,
  `author_id` VARCHAR(10) NOT NULL,
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
  PRIMARY KEY (`id`),
      FOREIGN KEY (`author_id`)
      REFERENCES `chatondb`.`user` (`id`)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`comment` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`comment` (
  `id` INT(10) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `post_id` VARCHAR(10) NOT NULL,
  `comment_body` VARCHAR(500) NULL DEFAULT NULL,
  `comment_date` TIMESTAMP,
  `date_updated` TIMESTAMP,
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
  PRIMARY KEY (`id`),
      FOREIGN KEY (`author_id`)
      REFERENCES `chatondb`.`user` (`id`),
      FOREIGN KEY (`post_id`)
      REFERENCES `chatondb`.`post` (`id`)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`like` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`like` (
  `id` INT(10) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `post_id` VARCHAR(10) NOT NULL,
  `like_value` VARCHAR(20) NOT NULL,
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
  PRIMARY KEY (`id`),
      FOREIGN KEY (`author_id`)
      REFERENCES `chatondb`.`user` (`id`),
      FOREIGN KEY (`post_id`)
      REFERENCES `chatondb`.`post` (`id`)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;