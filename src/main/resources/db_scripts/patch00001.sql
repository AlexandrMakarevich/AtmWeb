CREATE TABLE `account` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_name_UNIQUE` (`account_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1