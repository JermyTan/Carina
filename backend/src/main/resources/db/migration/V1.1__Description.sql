CREATE TABLE `carpark_availability_monday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_tuesday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_wednesday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_thursday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_friday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_saturday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `carpark_availability_sunday` (
  `carpark_id` varchar(255) NOT NULL,
  `area` varchar(255) NOT NULL,
  `development` varchar(255) NOT NULL,
  `latitude` double(16,8) NOT NULL,
  `longitude` double(16,8) NOT NULL,
  `available_lots` int(8) unsigned NOT NULL,
  `lot_type` varchar(255) NOT NULL,
  `agency` varchar(255) NOT NULL,
  `timestamp` bigint(16) unsigned NOT NULL,
  PRIMARY KEY (`carpark_id`),
  UNIQUE KEY `carparkId_UNIQUE` (`carpark_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
