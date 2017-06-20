CREATE TABLE `credit` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `account_id` int(30) NOT NULL,
  `currency_id` int(30) NOT NULL,
  `balance` int(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_currency_qu` (`account_id`,`currency_id`),
  KEY `fk_credit_2_idx` (`currency_id`),
  CONSTRAINT `fk_credit_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_credit_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1