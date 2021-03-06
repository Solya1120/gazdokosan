-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2020. Sze 13. 20:38
-- Kiszolgáló verziója: 10.4.14-MariaDB
-- PHP verzió: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `gazdokosan`
--

DELIMITER $$
--
-- Eljárások
--
DROP PROCEDURE IF EXISTS `addNewPlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewPlayer` (IN `userIdIN` INT(8), IN `colorIN` VARCHAR(25), IN `balanceIN` INT(8), IN `sumBalanceIN` INT(8))  NO SQL
INSERT INTO `current_game`(`id`, `user_id`, `color`, `balance`, `sum_balance`) VALUES (NULL,userIdIN,colorIN,balanceIN,sumBalanceIN)$$

DROP PROCEDURE IF EXISTS `addNewScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewScore` (IN `scoreIN` INT(8), IN `rankIN` INT(8))  NO SQL
INSERT INTO `score`(`id`, `score`, `rank`) VALUES (NULL,scoreIN, rankIN)$$

DROP PROCEDURE IF EXISTS `addNewStatistics`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewStatistics` (IN `userIN` INT(8), IN `totalScoreIN` INT(7))  NO SQL
INSERT INTO `statistics`(`id`, `user`, `total_score`) VALUES (NULL,userIN, totalScoreIN)$$

DROP PROCEDURE IF EXISTS `addNewUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewUser` (IN `usernameIN` VARCHAR(50), IN `passwordIN` VARCHAR(100))  NO SQL
INSERT INTO `user`(`username`, `password`) VALUES (usernameIN,passwordIN)$$

DROP PROCEDURE IF EXISTS `addNewWare`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewWare` (IN `objectIN` VARCHAR(100), IN `priceIN` INT(6))  NO SQL
INSERT INTO `wares`(`id`, `object`, `price`) VALUES (NULL,objectIN,priceIN)$$

DROP PROCEDURE IF EXISTS `checkPlayerByUsername`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkPlayerByUsername` (IN `usernameIN` VARCHAR(50))  NO SQL
SELECT `user`.`username`, `current_game`.`color`,`current_game`.`balance`
FROM `user`
RIGHT JOIN `current_game`
ON `user`.`id`=`current_game`.`user_id`
WHERE `user`.`username`= usernameIN$$

DROP PROCEDURE IF EXISTS `deleteAllPlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllPlayer` ()  NO SQL
DELETE FROM `current_game`$$

DROP PROCEDURE IF EXISTS `deleteAllScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllScore` ()  NO SQL
DELETE FROM `score`$$

DROP PROCEDURE IF EXISTS `deleteAllStatistics`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllStatistics` ()  NO SQL
DELETE FROM `statistics`$$

DROP PROCEDURE IF EXISTS `deleteAllUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllUser` ()  NO SQL
DELETE FROM `user`$$

DROP PROCEDURE IF EXISTS `deleteAllWares`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllWares` ()  NO SQL
DELETE FROM `wares`$$

DROP PROCEDURE IF EXISTS `deleteOnePlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOnePlayer` (IN `idIN` INT(8))  NO SQL
DELETE FROM `current_game` WHERE `current_game`.`id`=idIN$$

DROP PROCEDURE IF EXISTS `deleteOneScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOneScore` (IN `idIN` INT(8))  NO SQL
DELETE FROM `score` WHERE `score`.`id` = idIN$$

DROP PROCEDURE IF EXISTS `deleteOneStatistic`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOneStatistic` (IN `idIN` INT(8))  NO SQL
DELETE FROM `statistics` WHERE `statistics`.`id` = idIN$$

DROP PROCEDURE IF EXISTS `deleteOneUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOneUser` (IN `idIN` INT(8))  NO SQL
DELETE FROM `user` WHERE `user`.`id` = idIN$$

DROP PROCEDURE IF EXISTS `deleteOneWare`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOneWare` (IN `idIN` INT(8))  NO SQL
DELETE FROM `wares` WHERE `wares`.`id`=idIN$$

DROP PROCEDURE IF EXISTS `joinTop3Statistics`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `joinTop3Statistics` ()  NO SQL
SELECT `user`.`username`, `statistics`.`id`, `statistics`.`total_score`
FROM `user`
INNER JOIN `statistics`
ON `user`.`id` = `statistics`.`user`
ORDER BY `statistics`.`total_score` DESC
LIMIT 3$$

DROP PROCEDURE IF EXISTS `selectAllPlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllPlayer` ()  NO SQL
SELECT * FROM `current_game`$$

DROP PROCEDURE IF EXISTS `selectAllPlayerDESC`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllPlayerDESC` ()  NO SQL
SELECT *
FROM `current_game`
ORDER By current_game.balance DESC$$

DROP PROCEDURE IF EXISTS `selectAllScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllScore` ()  NO SQL
SELECT * FROM `score`$$

DROP PROCEDURE IF EXISTS `selectAllStatistics`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllStatistics` ()  NO SQL
SELECT * FROM `statistics`$$

DROP PROCEDURE IF EXISTS `selectAllStatisticsDESC`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllStatisticsDESC` ()  NO SQL
SELECT * 
FROM `statistics`
ORDER BY `statistics`.`total_score` DESC$$

DROP PROCEDURE IF EXISTS `selectAllUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllUser` ()  NO SQL
SELECT * FROM `user`$$

DROP PROCEDURE IF EXISTS `selectAllWares`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllWares` ()  NO SQL
SELECT * FROM `wares`$$

DROP PROCEDURE IF EXISTS `selectOnePlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectOnePlayer` (IN `user_idIN` INT(8))  NO SQL
SELECT * FROM `current_game` WHERE `current_game`.`user_id`=user_idIN$$

DROP PROCEDURE IF EXISTS `selectOneScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectOneScore` (IN `idIN` INT(8))  NO SQL
SELECT * FROM `score` WHERE `score`.`id`=idIN$$

DROP PROCEDURE IF EXISTS `selectOneStatistics`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectOneStatistics` (IN `userIN` INT(8))  NO SQL
SELECT * FROM `statistics` WHERE `statistics`.`user`=userIN$$

DROP PROCEDURE IF EXISTS `selectOneUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectOneUser` (IN `idIN` INT(8))  NO SQL
SELECT * FROM `user` WHERE `user`.`id`=idIN$$

DROP PROCEDURE IF EXISTS `selectOneWare`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectOneWare` (IN `idIN` INT(8))  NO SQL
SELECT * FROM `wares` WHERE `wares`.`id`=idIN$$

DROP PROCEDURE IF EXISTS `selectTop3Wares`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectTop3Wares` ()  NO SQL
SELECT *
FROM `wares`
ORDER BY `wares`.`price` DESC
LIMIT 3$$

DROP PROCEDURE IF EXISTS `selectUserNotPlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectUserNotPlayer` ()  NO SQL
SELECT `user`.`username`
FROM `user`
LEFT JOIN `current_game`
ON `user`.`id` = `current_game`.`user_id`
WHERE `current_game`.`user_id` IS NULL$$

DROP PROCEDURE IF EXISTS `updateOnePlayer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOnePlayer` (IN `userIdIN` INT(8), IN `colorIN` VARCHAR(25), IN `balanceIN` INT(8), IN `sumBalanceIN` INT(8), IN `rankIN` INT(8), IN `idIN` INT(8))  NO SQL
UPDATE `current_game` SET `user_id`=userIdIN,`color`=colorIN,`balance`=balanceIN,`sum_balance`=sumBalanceIN,`rank`=rankIN WHERE `id`=idIN$$

DROP PROCEDURE IF EXISTS `updateOneScore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOneScore` (IN `scoreIN` INT(8), IN `idIN` INT(8))  NO SQL
UPDATE `score` SET `score`=scoreIN,`rank`=rankIN WHERE `id`=idIN$$

DROP PROCEDURE IF EXISTS `updateOneStatistic`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOneStatistic` (IN `rankIN` INT(8), IN `userIN` INT(8), IN `totalScoreIN` INT(7), IN `idIN` INT(8))  NO SQL
UPDATE `statistics` SET `rank`=rankIN,`user`=userIN,`total_score`=totalScoreIN WHERE `id`= idIN$$

DROP PROCEDURE IF EXISTS `updateOneUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOneUser` (IN `usernameIN` VARCHAR(50), IN `passwordIN` INT(100), IN `idIN` INT(8))  NO SQL
UPDATE `user` SET `username`=usernameIN,`password`=passwordIN WHERE `id`=idIN$$

DROP PROCEDURE IF EXISTS `updateOneWare`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOneWare` (IN `idIN` INT(8), IN `objectIN` VARCHAR(100), IN `priceIN` INT(6))  NO SQL
UPDATE `wares` SET `object`=objectIN,`price`=priceIN WHERE `id`=idIN$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `current_game`
--

DROP TABLE IF EXISTS `current_game`;
CREATE TABLE `current_game` (
  `id` int(8) NOT NULL,
  `user_id` int(8) NOT NULL,
  `color` varchar(25) NOT NULL,
  `balance` int(8) DEFAULT NULL,
  `sum_balance` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `current_game`
--

INSERT INTO `current_game` (`id`, `user_id`, `color`, `balance`, `sum_balance`) VALUES
(1, 1, 'kakaó', 45700, 45700),
(2, 6, 'homok', 87000, 87000),
(3, 4, 'sárga', 74500, 74500),
(4, 9, 'kék', 89500, 89500),
(5, 5, 'zöld', 125000, 125000),
(8, 2, 'piros', 135000, 135000),
(9, 6, 'barna', 75000, 200);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `score`
--

DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(8) NOT NULL,
  `score` int(8) NOT NULL,
  `rank` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `score`
--

INSERT INTO `score` (`id`, `score`, `rank`) VALUES
(1, 100, 1),
(2, 90, 2),
(3, 80, 3),
(4, 70, 4),
(5, 60, 5),
(6, 50, 6),
(7, 40, 7),
(8, 30, 8),
(9, 20, 9),
(10, 10, 10),
(12, 8, 11),
(13, 6, 12),
(14, 0, 12);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `statistics`
--

DROP TABLE IF EXISTS `statistics`;
CREATE TABLE `statistics` (
  `id` int(8) NOT NULL,
  `user` int(8) NOT NULL,
  `total_score` int(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `statistics`
--

INSERT INTO `statistics` (`id`, `user`, `total_score`) VALUES
(1, 1, 12345),
(3, 1, 130),
(4, 2, 120),
(5, 3, 110),
(6, 4, 100),
(7, 5, 90),
(8, 6, 80),
(9, 7, 70),
(10, 8, 60),
(11, 9, 50),
(12, 10, 40),
(13, 11, 30),
(14, 3, 100);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(8) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `reg_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `reg_time`) VALUES
(1, 'anya77', 'anya77jelszo', '2020-08-25 08:58:25'),
(2, 'apu71', 'apu71jelszo', '2020-08-25 08:58:25'),
(3, 'k.peti', 'k.petijelszo', '2020-08-25 08:58:25'),
(4, 'sziszi2002', 'sziszi2002jelszo', '2020-08-25 08:58:25'),
(5, 'Sanyaöcsi', 'Sanyaöcsijelszo', '2020-08-25 08:58:25'),
(6, 'l.tibike', 'l.tibikejelszo', '2020-08-25 08:58:25'),
(7, 'd.henrietta65', 'd.henrietta65jelszo', '2020-08-25 08:58:25'),
(8, 'hugocska', 'hugocskajelszo', '2020-08-25 08:58:25'),
(9, 'nővérke12', 'nővérke12jelszo', '2020-08-25 08:58:25'),
(10, 'bátyusLaci', 'bátyusLacijelszo', '2020-08-25 08:58:25'),
(11, 'tinatina', 'tinatinajelszo', '2020-08-27 08:48:29'),
(13, 'newnew', 'passwordnewnew', '2020-09-09 08:21:05'),
(15, 'jozsi33', 'jozsi33jelszo', '2020-09-13 18:08:13');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `wares`
--

DROP TABLE IF EXISTS `wares`;
CREATE TABLE `wares` (
  `id` int(8) NOT NULL,
  `object` varchar(100) NOT NULL,
  `price` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `wares`
--

INSERT INTO `wares` (`id`, `object`, `price`) VALUES
(2, 'mosógép', 90000),
(3, 'hütőszekrény', 80000),
(4, 'tűzhely', 70000),
(5, 'szobabútor', 900000),
(6, 'televízió', 70000),
(7, 'lakás', 9500000),
(8, 'autó', 7000000),
(9, 'konyhabútor', 300000),
(11, 'telefon', 15000);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `current_game`
--
ALTER TABLE `current_game`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- A tábla indexei `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `wares`
--
ALTER TABLE `wares`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `current_game`
--
ALTER TABLE `current_game`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `score`
--
ALTER TABLE `score`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `statistics`
--
ALTER TABLE `statistics`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `wares`
--
ALTER TABLE `wares`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `current_game`
--
ALTER TABLE `current_game`
  ADD CONSTRAINT `current_game_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `statistics`
--
ALTER TABLE `statistics`
  ADD CONSTRAINT `statistics_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
