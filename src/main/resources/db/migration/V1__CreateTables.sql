
-- -----------------------------------------------------
-- Table `book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `stock_quantity` INT NULL DEFAULT '0',
  `price` DECIMAL(10,0) NULL DEFAULT '0',
  `in_stock` BIT(1) NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  INDEX `IDX_book` (`category` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `total_price` DECIMAL(10,0) NULL DEFAULT '0',
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `cart_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cart_book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cart_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `quantity` INT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `IDX_book` (`book_id` ASC) VISIBLE,
  INDEX `IDX_cart` (`cart_id` ASC) VISIBLE,
  CONSTRAINT `FK_cartbook_cart`
    FOREIGN KEY (`cart_id`)
    REFERENCES `cart` (`id`),
  CONSTRAINT `FK_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `book` (`id`));


-- -----------------------------------------------------
-- Table `payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `payment_date` DATETIME NULL DEFAULT NULL,
  `cart_id` INT NOT NULL,
  `paid_price` DECIMAL(10,0) NULL DEFAULT NULL,
  `payment_mode` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `IDX_cart` (`cart_id` ASC) VISIBLE,
  CONSTRAINT `FK_payment_cart`
    FOREIGN KEY (`cart_id`)
    REFERENCES `cart` (`id`));


-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  INDEX `IDX_role` (`role_id` ASC) VISIBLE,
  INDEX `IDX_user` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`),
  CONSTRAINT `FK_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`));