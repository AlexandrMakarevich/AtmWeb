CREATE TABLE `currency` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `currency_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `currency_name_UNIQUE` (`currency_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1