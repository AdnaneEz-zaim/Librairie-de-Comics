-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 23 jan. 2023 à 22:47
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `comicunivers`
--

-- --------------------------------------------------------

--
-- Structure de la table `authorpref`
--

DROP TABLE IF EXISTS `authorpref`;
CREATE TABLE IF NOT EXISTS `authorpref` (
  `userid` int NOT NULL,
  `comicid` varchar(45) NOT NULL,
  `authorName` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`,`authorName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `authorpref`
--

INSERT INTO `authorpref` (`userid`, `comicid`, `authorName`) VALUES
(6, '667', 'Dick Sprang'),
(6, '667', 'Charles Paris'),
(6, '667', 'Bob Kane'),
(6, '667', 'Bill Finger'),
(4, '965560', 'Chris Duffy'),
(4, '965560', 'Brendan Mallory'),
(4, '965560', 'Fred Harper'),
(4, '965560', 'Jon Proctor'),
(4, '965560', 'Lee Loughridge'),
(4, '965560', 'Rob Steen'),
(4, '965560', 'Stuart Moore'),
(4, '952009', 'Yoann Guillo'),
(4, '952009', 'Vincent Brugeas'),
(4, '952009', 'Ronan Toulhoat'),
(5, '965766', 'Clem Robins'),
(5, '965766', 'Dave Stewart'),
(5, '965766', 'Guy Davis'),
(5, '965766', 'Jenny Blenk'),
(5, '965766', 'John Arcudi'),
(5, '965766', 'Katii O\'Brien'),
(5, '965766', 'Mike Mignola'),
(5, '965766', 'Misha Gehr');

-- --------------------------------------------------------

--
-- Structure de la table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
  `userid` int NOT NULL,
  `idComic` varchar(1000) NOT NULL,
  `commentContent` varchar(1000) DEFAULT NULL,
  `publish_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `userid` (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `comments`
--

INSERT INTO `comments` (`userid`, `idComic`, `commentContent`, `publish_date`) VALUES
(1, '963819', 'Super comics', '2023-01-19 11:12:09'),
(1, '963611', 'hi', '2023-01-19 11:12:09'),
(1, '963819', 'hi', '2023-01-19 11:12:48'),
(4, '965560', 'test', '2023-01-21 14:02:19'),
(4, '961495', 'hi', '2023-01-23 00:47:18'),
(4, '485465', 'best comic', '2023-01-23 20:40:14'),
(4, '963611', 'test', '2023-01-23 22:10:55');

-- --------------------------------------------------------

--
-- Structure de la table `conceptpref`
--

DROP TABLE IF EXISTS `conceptpref`;
CREATE TABLE IF NOT EXISTS `conceptpref` (
  `userid` int NOT NULL,
  `comicid` varchar(45) NOT NULL,
  `concept` varchar(50) NOT NULL,
  PRIMARY KEY (`userid`,`concept`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `conceptpref`
--

INSERT INTO `conceptpref` (`userid`, `comicid`, `concept`) VALUES
(6, '667', 'Deathtrap'),
(5, '965766', 'Demons'),
(5, '965766', 'Dream Sequence'),
(5, '965766', 'Flashback '),
(5, '965766', 'From the World of Hellboy'),
(5, '965766', 'Homunculus'),
(5, '965766', 'Indigenous People'),
(5, '965766', 'Monster Hunter'),
(5, '965766', 'Occult'),
(5, '965766', 'Possession'),
(5, '965766', 'Torture'),
(5, '965766', 'Wendigo Curse'),
(5, '965766', 'Werecreatures'),
(6, '667', 'Giant props'),
(4, '952009', 'Translated Comic');

-- --------------------------------------------------------

--
-- Structure de la table `library`
--

DROP TABLE IF EXISTS `library`;
CREATE TABLE IF NOT EXISTS `library` (
  `idUser` int NOT NULL,
  `idComic` varchar(255) NOT NULL,
  `imageComic` varchar(255) NOT NULL,
  `nameComic` varchar(255) NOT NULL,
  `comicState` varchar(255) NOT NULL DEFAULT 'To read',
  PRIMARY KEY (`idUser`,`idComic`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `library`
--

INSERT INTO `library` (`idUser`, `idComic`, `imageComic`, `nameComic`, `comicState`) VALUES
(4, '477737', 'https://comicvine.gamespot.com/a/uploads/scale_avatar/11120/111202620/4353208-solo12damionscott_01g.jpg', 'Brendan McCarthy', 'To read'),
(5, '965766', 'https://comicvine.gamespot.com/a/uploads/scale_avatar/11156/111567728/8793497-b.p.r.d.omnibusv3%282022%29.jpg', 'Volume 3', 'current'),
(4, '965560', 'https://comicvine.gamespot.com/a/uploads/scale_avatar/11122/111221019/8791931-7996343651-HIGHB.jpg', 'Revolutions Per Second - Part Two; Duffeeee; There\'s a Mosquito in My Spacesuit', 'finished'),
(6, '667', 'https://comicvine.gamespot.com/a/uploads/scale_avatar/0/4/623-796-667-1-batman.jpg', 'Batman of the Mounties', 'To read'),
(4, '952009', 'https://comicvine.gamespot.com/a/uploads/scale_avatar/11156/111567728/8693278-cossacks%231%282022%29.jpg', 'Volume 1: The Winged Hussar', 'To read');

-- --------------------------------------------------------

--
-- Structure de la table `rating`
--

DROP TABLE IF EXISTS `rating`;
CREATE TABLE IF NOT EXISTS `rating` (
  `idUser` int NOT NULL,
  `idComic` varchar(45) NOT NULL,
  `rating` double NOT NULL,
  PRIMARY KEY (`idUser`,`idComic`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `rating`
--

INSERT INTO `rating` (`idUser`, `idComic`, `rating`) VALUES
(1, '963819', 3.4943181818181817),
(1, '963614', 2.7897726405750625),
(1, '963316', 2.153409177606756),
(2, '667', 3.2897728139703926),
(4, '667', 4.494318095120517),
(4, '961495', 4.448863809758967),
(4, '965560', 4.426136450334029),
(5, '965766', 3.335227099331943),
(4, '485465', 2.31249982660467),
(4, '959081', 2.403409264304421),
(4, '477737', 4.289772727272727),
(4, '963611', 2.2897729006680576),
(6, '667', 2.2897729006680576);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`userid`, `email`, `password`, `username`, `firstname`, `lastname`) VALUES
(1, 'lardon.adrian@gmail.com', '123456', 'Adrian', 'Adrian', 'Lardon'),
(2, 'emma@gmail.com', 'medrare', 'emma123', 'emma', 'emma'),
(3, 'emma@gmail.com', '123123', 'emma123', 'emma', 'emma'),
(4, 'test@gmail.com', '123123', 'test123', 'test', 'test'),
(6, 'oumaima@gmail.com', '123123', 'oumaima', 'oumaima', 'medrare'),
(5, 'emma@gmail.com', '123123', 'emma@123', 'medrare', 'oumaima');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
