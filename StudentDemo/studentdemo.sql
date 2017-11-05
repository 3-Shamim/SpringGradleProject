-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 05, 2017 at 12:50 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `studentdemo`
--

-- --------------------------------------------------------

--
-- Table structure for table `course_details`
--

CREATE TABLE IF NOT EXISTS `course_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_code` varchar(255) NOT NULL,
  `course_credit` int(11) DEFAULT NULL,
  `course_title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `course_details`
--

INSERT INTO `course_details` (`id`, `course_code`, `course_credit`, `course_title`) VALUES
(1, 'Cse2015', 3, 'Java'),
(2, 'Cse2016', 1, 'Java Lab'),
(3, 'Cse3011', 3, 'Database'),
(4, 'Cse3012', 1, 'Database Lab'),
(5, 'Cse4047', 3, 'Advance Java'),
(6, 'Cse4048', 1, 'Advance Java Lab'),
(7, 'Cse3035', 3, 'Information System Design And Software Engineering'),
(8, 'Cse3036', 1, 'Information System Design And Software Engineering Lab'),
(9, 'Cse4012', 1, 'Networking Lab');

-- --------------------------------------------------------

--
-- Table structure for table `depertment_details`
--

CREATE TABLE IF NOT EXISTS `depertment_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `depertment_details`
--

INSERT INTO `depertment_details` (`id`, `dept_name`) VALUES
(7, 'CSE'),
(8, 'BBA'),
(10, 'EEE'),
(11, 'English');

-- --------------------------------------------------------

--
-- Table structure for table `student_details`
--

CREATE TABLE IF NOT EXISTS `student_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `student_id` varchar(13) NOT NULL,
  `depertment_details_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnlhriu7w0c574ycf0n5pnrpay` (`depertment_details_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `student_details`
--

INSERT INTO `student_details` (`id`, `email`, `name`, `student_id`, `depertment_details_id`) VALUES
(1, 'kamrul@gmail.com', 'Kamrul', '2015000000183', 8),
(15, 'faisal@gmail.com', 'Faisal', '2015000000199', 7),
(16, 'shamim@gmail.com', 'Shamim', '2015000000003', 7),
(18, 'kamrul@gmail.com', 'Kamrul', '2015000000183', 7);

-- --------------------------------------------------------

--
-- Table structure for table `student_details_course_details`
--

CREATE TABLE IF NOT EXISTS `student_details_course_details` (
  `student_details_id` int(11) NOT NULL,
  `course_details_id` int(11) NOT NULL,
  KEY `FKchjcfx7mib5nr6vi801x6gwy4` (`course_details_id`),
  KEY `FK1wcq4s6r7j0u66kmi211vx168` (`student_details_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_details_course_details`
--

INSERT INTO `student_details_course_details` (`student_details_id`, `course_details_id`) VALUES
(15, 1),
(15, 2),
(15, 3),
(15, 4),
(15, 5),
(15, 6),
(15, 7);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student_details`
--
ALTER TABLE `student_details`
  ADD CONSTRAINT `FKnlhriu7w0c574ycf0n5pnrpay` FOREIGN KEY (`depertment_details_id`) REFERENCES `depertment_details` (`id`);

--
-- Constraints for table `student_details_course_details`
--
ALTER TABLE `student_details_course_details`
  ADD CONSTRAINT `FK1wcq4s6r7j0u66kmi211vx168` FOREIGN KEY (`student_details_id`) REFERENCES `student_details` (`id`),
  ADD CONSTRAINT `FKchjcfx7mib5nr6vi801x6gwy4` FOREIGN KEY (`course_details_id`) REFERENCES `course_details` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
