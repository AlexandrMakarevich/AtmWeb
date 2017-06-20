CREATE TABLE `debit` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `account_id` int(30) NOT NULL,
  `currency_id` int(30) NOT NULL,
  `balance` int(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_currency_uq` (`account_id`,`currency_id`),
  KEY `fk_debit_2` (`currency_id`),
  CONSTRAINT `fk_debit_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_debit_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1