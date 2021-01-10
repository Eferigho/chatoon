DROP USER IF EXISTS 'petuser'@'localhost';
CREATE USER  'petuser'@'localhost' IDENTIFIED BY 'petuser123';
GRANT ALL PRIVILEGES ON chatondb.* TO 'petuser'@'localhost';


DROP DATABASE IF EXISTS chatondb;

CREATE SCHEMA IF NOT EXISTS `chatondb` DEFAULT CHARACTER SET latin1 ;

-- -----------------------------------------------------
-- Table `chatondb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`address` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`address` (
  `id` INT(11) NOT NULL,
  `house_number` VARCHAR(20) NULL DEFAULT NULL,
  `street_name` VARCHAR(100) NULL DEFAULT NULL,
  `city` VARCHAR(100) NULL DEFAULT NULL,
  `state` VARCHAR(100) NULL DEFAULT NULL,
  `country` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `chatondb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`user` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`user` (
  `id` INT(11) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(400) NOT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `profile_image` VARCHAR(100) NULL DEFAULT NULL,
  `user_billboard` VARCHAR(100) NULL DEFAULT NULL,
  `address_id` VARCHAR(10) NOT NULL,
  `date_of_birth` DATE,
  `date_join` TIMESTAMP,
  `is_active` VARCHAR(10) NULL DEFAULT NULL,
  `all_videos_id` VARCHAR(10) NULL DEFAULT NULL,
  `all_images_id` VARCHAR(10) NULL DEFAULT NULL,
  `posts_id` VARCHAR(10) NULL DEFAULT NULL,
  `comments_id` VARCHAR(10) NULL DEFAULT NULL,
  `likes_id` VARCHAR(10) NULL DEFAULT NULL,
  `all_friends_id` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`address_id`)
     REFERENCES `chatondb`.`address` (`id`),
  FOREIGN KEY (`all_videos_id`)
     REFERENCES `chatondb`.`video` (`id`),
  FOREIGN KEY (`all_images_id`)
       REFERENCES `chatondb`.`image` (`id`),
  FOREIGN KEY (`posts_id`)
       REFERENCES `chatondb`.`post` (`id`),
  FOREIGN KEY (`comments_id`)
       REFERENCES `chatondb`.`comment` (`id`),
  FOREIGN KEY (`likes_id`)
       REFERENCES `chatondb`.`user_like` (`id`),
  FOREIGN KEY (`all_friends_id`)
       REFERENCES `chatondb`.`friend` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`friend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`friend` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`friend` (
  `id` INT(11) NOT NULL,
  `friended_id` VARCHAR(10) NOT NULL,
  `friendee_id` VARCHAR(10) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_friend`
  FOREIGN KEY (`friended_id`)
        REFERENCES `chatondb`.`user` (`id`),
  FOREIGN KEY(`friendee_id`)
        REFERENCES `chatondb`.`user`(`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `chatondb`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`post` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`post` (
  `id` INT(11) NOT NULL,
  `post_body` VARCHAR(1000) NOT NULL,
  `post_image_id` VARCHAR(10) NULL DEFAULT NULL,
  `post_video_id` VARCHAR(10) NULL DEFAULT NULL,
  `all_comment` VARCHAR(10) NULL DEFAULT NULL,
  `likes` VARCHAR(10) NULL DEFAULT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `date_posted` TIMESTAMP,
  `date_updated` TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
        FOREIGN KEY (`post_image_id`)
    REFERENCES `chatondb`.`image` (`id`),
        FOREIGN KEY (`post_video_id`)
    REFERENCES `chatondb`.`video` (`id`),
    FOREIGN KEY (`all_comment`)
      REFERENCES `chatondb`.`comment` (`id`),
    FOREIGN KEY (`likes`)
      REFERENCES `chatondb`.`user_like` (`id`),
    FOREIGN KEY (`author_id`)
            REFERENCES `chatondb`.`user` (`id`),
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `chatondb`.`user_like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`user_like` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`user_like` (
  `id` INT(11) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `post_id` VARCHAR(10) NOT NULL,
  `like_value` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
    FOREIGN KEY (`author_id`)
        REFERENCES `chatondb`.`user` (`id`),
    FOREIGN KEY (`post_id`)
        REFERENCES `chatondb`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`comment` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`comment` (
  `id` INT(11) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `post_id` VARCHAR(10) NOT NULL,
  `comment_body` VARCHAR(128) NOT NULL,
  `comment_date` TIMESTAMP NOT NULL,
  `date_updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
        FOREIGN KEY (`author_id`)
    REFERENCES `chatondb`.`user` (`id`),
    FOREIGN KEY (`post_id`)
        REFERENCES `chatondb`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `chatondb`.`video`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`video` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`video` (
  `id` INT(11) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `video_url` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
    FOREIGN KEY (`author_id`)
        REFERENCES `chatondb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `chatondb`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chatondb`.`image` ;

CREATE TABLE IF NOT EXISTS `chatondb`.`image` (
  `id` INT(11) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  `image_url` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_author` (`author_id` ASC),
  CONSTRAINT `fk_author`
    FOREIGN KEY (`author_id`)
        REFERENCES `chatondb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
