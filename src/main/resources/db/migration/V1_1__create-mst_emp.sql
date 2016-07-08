CREATE TABLE IF NOT EXISTS `emp_mst` (
  `EMP_NO` varchar(10) NOT NULL,
  `PASS` varchar(255) NOT NULL,
  `NAME` varchar(40) NOT NULL,
  `MAIL` varchar(255) NOT NULL,
  PRIMARY KEY (`EMP_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT IGNORE INTO `emp_mst` (`EMP_NO`, `PASS`, `NAME`, `MAIL`) VALUES
	('5007', 'ff52d55640a6b8513d57f5c0aff1d737ced37c01cdd6faba338aef1f2bf3c531', '川崎達也', 'kawasaki-tatsuya@ixui.co.jp');
