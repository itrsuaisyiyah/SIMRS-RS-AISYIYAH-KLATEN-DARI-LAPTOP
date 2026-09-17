-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 09, 2025 at 05:43 PM
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
-- Table structure for table `sirs_mapping_kamar`
--

CREATE TABLE `sirs_mapping_kamar` (
  `kd_bangsal` varchar(5) NOT NULL,
  `id_tt` varchar(15) DEFAULT NULL,
  `tt_kelas` varchar(40) DEFAULT NULL,
  `nm_ruang` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sirs_mapping_kamar`
--

INSERT INTO `sirs_mapping_kamar` (`kd_bangsal`, `id_tt`, `tt_kelas`, `nm_ruang`) VALUES
('KL-1A', '3', 'Kelas I', 'Cendana 1 A'),
('KL-1B', '4', 'Kelas I', 'Cendana 1 B');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sirs_mapping_kamar`
--
ALTER TABLE `sirs_mapping_kamar`
  ADD PRIMARY KEY (`kd_bangsal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
