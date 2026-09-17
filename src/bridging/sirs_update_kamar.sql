-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 09, 2025 at 05:42 PM
-- Server version: 10.3.32-MariaDB-0ubuntu0.20.04.1
-- PHP Version: 7.3.33-8+ubuntu20.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rsthb`
--

-- --------------------------------------------------------

--
-- Table structure for table `sirs_update_kamar`
--

CREATE TABLE `sirs_update_kamar` (
  `kd_bangsal` char(50) NOT NULL,
  `message` varchar(100) DEFAULT NULL,
  `updated_at` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sirs_update_kamar`
--

INSERT INTO `sirs_update_kamar` (`kd_bangsal`, `message`, `updated_at`) VALUES
('KL-1A', 'Data tempat tidur dengan id_tt 3 di ruangan Cendana 1 A telah diupdate', '2025-06-09 16:55:58');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sirs_update_kamar`
--
ALTER TABLE `sirs_update_kamar`
  ADD PRIMARY KEY (`kd_bangsal`) USING BTREE;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sirs_update_kamar`
--
ALTER TABLE `sirs_update_kamar`
  ADD CONSTRAINT `update_kamar_ibfk_1` FOREIGN KEY (`kd_bangsal`) REFERENCES `sirs_mapping_kamar` (`kd_bangsal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
