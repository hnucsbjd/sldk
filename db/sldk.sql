-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2018-02-06 12:41:09
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

-- --------------------------------------------------------

--
-- 表的结构 `dk_state`
--

CREATE TABLE `dk_state` (
  `current_num` int(11) NOT NULL,
  `max_num` int(11) NOT NULL DEFAULT '20',
  `ghao` int(11) NOT NULL,
  `openid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `dk_state`
--

INSERT INTO `dk_state` (`current_num`, `max_num`, `ghao`, `openid`) VALUES
(20, 20, 13001405, 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA');

-- --------------------------------------------------------

--
-- 表的结构 `yg_info`
--

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
(1, 13001405, '研发中心', '李德', 'ocXF65ZsF2bOcSL1dJ7qDaRU77IA');

-- --------------------------------------------------------

--
-- 表的结构 `yg_md`
--

CREATE TABLE `yg_md` (
  `ygid` bigint(11) NOT NULL,
  `ghao` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phonenumberDh` varchar(11) DEFAULT NULL COMMENT '手机号,短号',
  `phonenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `department` varchar(255) DEFAULT NULL COMMENT '部门'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `yg_md`
--
--
-- Indexes for dumped tables
--

--
-- Indexes for table `dk_state`
--
ALTER TABLE `dk_state`
  ADD PRIMARY KEY (`openid`);

--
-- Indexes for table `yg_info`
--
ALTER TABLE `yg_info`
  ADD PRIMARY KEY (`ygid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `yg_info`
--
ALTER TABLE `yg_info`
  MODIFY `ygid` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
