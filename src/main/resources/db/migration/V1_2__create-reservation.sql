CREATE TABLE IF NOT EXISTS `reservation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_NO` varchar(10) NOT NULL,
  `RSV_DATE` date NOT NULL,
  `TITLE` varchar(50) NOT NULL,
  `START_TIME` char(4) NOT NULL,
  `END_TIME` char(4) NOT NULL,
  `DETAIL` varchar(255) NOT NULL,
  `MEMO` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

