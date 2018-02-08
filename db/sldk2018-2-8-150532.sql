-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2018-02-08 07:05:14
-- 服务器版本： 5.7.21
-- PHP Version: 7.0.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sldk`
--
CREATE DATABASE IF NOT EXISTS `sldk` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sldk`;

-- --------------------------------------------------------

--
-- 表的结构 `collect_state`
--

DROP TABLE IF EXISTS `collect_state`;
CREATE TABLE `collect_state` (
  `current_num` int(11) NOT NULL,
  `max_num` int(11) NOT NULL DEFAULT '20',
  `ghao` int(11) NOT NULL,
  `openid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `collect_state`
--

INSERT INTO `collect_state` (`current_num`, `max_num`, `ghao`, `openid`) VALUES
(20, 20, 13001405, 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA');

-- --------------------------------------------------------

--
-- 表的结构 `dk_record`
--

DROP TABLE IF EXISTS `dk_record`;
CREATE TABLE `dk_record` (
  `id` bigint(11) NOT NULL,
  `dk_time` varchar(255) NOT NULL,
  `ghao` int(11) DEFAULT NULL,
  `openid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `dk_record`
--

INSERT INTO `dk_record` (`id`, `dk_time`, `ghao`, `openid`) VALUES
(1, '2018-02-08 14:55:11', NULL, 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA');

-- --------------------------------------------------------

--
-- 表的结构 `pz_record`
--

DROP TABLE IF EXISTS `pz_record`;
CREATE TABLE `pz_record` (
  `id` bigint(20) NOT NULL,
  `ghao` int(11) DEFAULT NULL,
  `openid` varchar(255) NOT NULL,
  `sendpic_time` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `pz_record`
--

INSERT INTO `pz_record` (`id`, `ghao`, `openid`, `sendpic_time`, `latitude`, `longitude`) VALUES
(1, NULL, 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA', '2018-02-08 14:44:18', 23.129163, 113),
(2, NULL, 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA', '2018-02-08 14:54:52', 23.129163, 113);

-- --------------------------------------------------------

--
-- 表的结构 `yg_info`
--

DROP TABLE IF EXISTS `yg_info`;
CREATE TABLE `yg_info` (
  `ygid` bigint(11) NOT NULL,
  `ghao` int(11) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL COMMENT '部门',
  `name` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `yg_info`
--

INSERT INTO `yg_info` (`ygid`, `ghao`, `department`, `name`, `openid`) VALUES
(1, 13001405, '研发中心', '李德', 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA'),
(4, 13000837, '研发中心', '周卫星', 'ocXF65RkkEuL1cdkYE9qldaI2JR4'),
(5, 13000962, '研发中心', '张恺', 'ocXF65euK4HibxwqtOdVl03mbY8E');

-- --------------------------------------------------------

--
-- 表的结构 `yg_md`
--

DROP TABLE IF EXISTS `yg_md`;
CREATE TABLE `yg_md` (
  `ygid` bigint(11) NOT NULL,
  `ghao` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phonenumberDh` varchar(11) DEFAULT NULL COMMENT '手机号,短号',
  `phonenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `department` varchar(255) DEFAULT NULL COMMENT '部门'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `collect_state`
--
ALTER TABLE `collect_state`
  ADD PRIMARY KEY (`openid`);

--
-- Indexes for table `dk_record`
--
ALTER TABLE `dk_record`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pz_record`
--
ALTER TABLE `pz_record`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `yg_info`
--
ALTER TABLE `yg_info`
  ADD PRIMARY KEY (`ygid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `dk_record`
--
ALTER TABLE `dk_record`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `pz_record`
--
ALTER TABLE `pz_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `yg_info`
--
ALTER TABLE `yg_info`
  MODIFY `ygid` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
