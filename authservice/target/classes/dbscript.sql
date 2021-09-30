CREATE SCHEMA IF NOT EXISTS `ip_treatment` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `ip_treatment` ;

DROP TABLE IF EXISTS `ip_treatment`.`role` ;

CREATE  TABLE IF NOT EXISTS `ip_treatment`.`role` (
  `ro_id` INT NOT NULL AUTO_INCREMENT ,
  `ro_name` VARCHAR(45) NULL ,
  PRIMARY KEY (`ro_id`) )
ENGINE = InnoDB;



DROP TABLE IF EXISTS `ip_treatment`.`user` ;

CREATE  TABLE IF NOT EXISTS `ip_treatment`.`user` (
  `us_id` INT NOT NULL AUTO_INCREMENT ,
  `us_name` VARCHAR(60) NULL ,
  `us_password` VARCHAR(100) NULL ,
  `us_first_name` VARCHAR(45) NULL ,
  `us_last_name` VARCHAR(45) NULL ,
  PRIMARY KEY (`us_id`) )
ENGINE = InnoDB;



DROP TABLE IF EXISTS `ip_treatment`.`user_role` ;

CREATE  TABLE IF NOT EXISTS `ip_treatment`.`user_role` (
  `ur_id` INT NOT NULL AUTO_INCREMENT ,
  `ur_us_id` INT NULL ,
  `ur_ro_id` INT NULL ,
  INDEX `fk_user_has_role_role1` (`ur_ro_id` ASC) ,
  INDEX `fk_user_has_role_user1` (`ur_us_id` ASC) ,
  PRIMARY KEY (`ur_id`) ,
  CONSTRAINT `fk_user_has_role_user1`
    FOREIGN KEY (`ur_us_id` )
    REFERENCES `ip_treatment`.`user` (`us_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`ur_ro_id` )
    REFERENCES `ip_treatment`.`role` (`ro_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


INSERT INTO `ip_treatment`.`role` (`ro_id`, `ro_name`) VALUES (1, 'USER');
INSERT INTO `ip_treatment`.`role` (`ro_id`, `ro_name`) VALUES (2, 'ADMIN');
INSERT INTO `ip_treatment`.`role` (`ro_id`, `ro_name`) VALUES (3, 'PATIENT');


INSERT INTO `ip_treatment`.`user` (`us_id`, `us_name`, `us_password`) VALUES (1, 'user', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
INSERT INTO `ip_treatment`.`user` (`us_id`, `us_name`, `us_password`) VALUES (2, 'admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');


INSERT INTO `ip_treatment`.`user_role` (`ur_id`, `ur_us_id`, `ur_ro_id`) VALUES (1, 1, 1);
INSERT INTO `ip_treatment`.`user_role` (`ur_id`, `ur_us_id`, `ur_ro_id`) VALUES (2, 2, 2);


CREATE TABLE `treatment_package` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`ailment_id` bigint NOT NULL ,
`name` varchar(50) NOT NULL ,
`test_details` varchar(50) NOT NULL ,
`cost` int NOT NULL ,
`duration_weeks` int NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `patient` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`name` varchar(50) NOT NULL ,
`age` int NOT NULL ,
PRIMARY KEY (
`id`
)
);


CREATE TABLE `specialist` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`name` varchar(50) NOT NULL ,
`ailment_id_expertise` bigint NOT NULL ,
`experience_in_years` int NOT NULL ,
`contact_number` bigint NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `treatment_plan` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`patient_id` bigint NOT NULL ,
`package_id` bigint NOT NULL ,
`commencement_date` date NOT NULL ,
`specialist_id` bigint NOT NULL ,
`end_date` date NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `insurer` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`name` varchar(50) NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `claim` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`patient_id` bigint NOT NULL ,
`package_id` bigint NOT NULL ,
`insurer_id` bigint NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `ailment` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`ailment_category` varchar(50) NOT NULL ,
PRIMARY KEY (
`id`
)
);

CREATE TABLE `package_insurer` (
`id` bigint NOT NULL AUTO_INCREMENT ,
`insurer_id` bigint NOT NULL ,
`package_id` bigint NOT NULL ,
`amount_limit` int NOT NULL ,
`disbursement_duration` int NOT NULL ,
PRIMARY KEY (
`id`
)
);

ALTER TABLE `treatment_package` ADD CONSTRAINT `fk_treatment_package_ailment_id` FOREIGN KEY(`ailment_id`)
REFERENCES `ailment` (`id`);

ALTER TABLE `specialist` ADD CONSTRAINT `fk_specialist_ailment_id_expertise` FOREIGN KEY(`ailment_id_expertise`)
REFERENCES `ailment` (`id`);

ALTER TABLE `treatment_plan` ADD CONSTRAINT `fk_treatment_plan_patient_id` FOREIGN KEY(`patient_id`)
REFERENCES `patient` (`id`);

ALTER TABLE `treatment_plan` ADD CONSTRAINT `fk_treatment_plan_treatment_package_id` FOREIGN KEY(`package_id`)
REFERENCES `treatment_package` (`id`);

ALTER TABLE `treatment_plan` ADD CONSTRAINT `fk_treatment_plan_specialist_id` FOREIGN KEY(`specialist_id`)
REFERENCES `specialist` (`id`);

ALTER TABLE `claim` ADD CONSTRAINT `fk_claim_patient_id` FOREIGN KEY(`patient_id`)
REFERENCES `patient` (`id`);

ALTER TABLE `claim` ADD CONSTRAINT `fk_claim_treatment_package_id` FOREIGN KEY(`package_id`)
REFERENCES `treatment_package` (`id`);

ALTER TABLE `claim` ADD CONSTRAINT `fk_claim_insurer_id` FOREIGN KEY(`insurer_id`)
REFERENCES `insurer` (`id`);

ALTER TABLE `package_insurer` ADD CONSTRAINT `fk_package_insurer_insurer_id` FOREIGN KEY(`insurer_id`)
REFERENCES `insurer` (`id`);

ALTER TABLE `package_insurer` ADD CONSTRAINT `fk_package_insurer_treatment_package_id` FOREIGN KEY(`package_id`)
REFERENCES `treatment_package` (`id`);

ALTER TABLE `package_insurer` ADD CONSTRAINT `uk_package_insurer_insurer_id_treatment_package_id` UNIQUE(`insurer_id`, `package_id`);
ALTER TABLE `treatment_plan` ADD CONSTRAINT `uk_treatment_plan_patient_id_treatment_package_id` UNIQUE(`patient_id`, `package_id`, `commencement_date`);

INSERT INTO `ailment` VALUES (1,'Hematology'),(2,'Cardiology'),(3,'Orthopedics'),(4,'Urology'),(6,'Gastroenterology'),(7,'  Vascular Medicine');
INSERT INTO `patient` VALUES (1,'Younes Belcaid',20),(2,'Ali Hamza',30),(3,'John Kennedy',40),(4,'Odrey Smith',50);
INSERT INTO `insurer` VALUES (1,'John White'),(2,'Claire Thomson'),(3,'Anna Guetta');
INSERT INTO `treatment_package` VALUES (1,1,'Package1','cde1, cde3',1000,3),(2,3,'Package2','cde2',2000,4),(3,4,'Package1','cde1, cde3',1000,5);
INSERT INTO `package_insurer` VALUES (1,1,1,5000,24),(3,2,2,1000,24),(2,3,1,1500,12);
INSERT INTO `claim` VALUES (1,1,3,1),(2,3,3,3),(3,2,1,2);
INSERT INTO `specialist` VALUES ('1', 'Raoult', '2', '5', '555555555');