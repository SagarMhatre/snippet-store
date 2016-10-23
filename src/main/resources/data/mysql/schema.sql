DROP TABLE IF EXISTS `Greeting`;

CREATE TABLE Greeting ( 
	`id` bigint(20) unsigned NOT NULL auto_increment,  	
	`text` varchar(100) NOT NULL,	
  	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;