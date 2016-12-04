-- phpMyAdmin SQL Dump
-- version 4.3.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- 생성 시간: 16-12-03 06:55
-- 서버 버전: 5.5.39-MariaDB
-- PHP 버전: 5.5.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `CustomerDatabase`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `매장`
--

CREATE TABLE IF NOT EXISTS `매장` (
  `매장번호` int(11) NOT NULL,
  `주소` varchar(200) DEFAULT NULL,
  `위도` double DEFAULT NULL,
  `경도` double DEFAULT NULL,
  `이름` varchar(45) DEFAULT NULL,
  `전화번호` varchar(15) DEFAULT NULL,
  `삭제` tinyint(1) DEFAULT NULL,
  `매장이미지저장경로` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `매장공지`
--

CREATE TABLE IF NOT EXISTS `매장공지` (
  `매장번호` int(11) NOT NULL,
  `제목` varchar(2000) DEFAULT NULL,
  `내용` varchar(2000) DEFAULT NULL,
  `공지시작날짜` date DEFAULT NULL,
  `공지마감날짜` date DEFAULT NULL,
  `삭제` tinyint(1) DEFAULT NULL,
  `매장공지이미지저장경로` varchar(500) DEFAULT NULL,
  `읽음여부` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `쿠폰등록현황`
--

CREATE TABLE IF NOT EXISTS `쿠폰등록현황` (
  `매장번호` int(11) NOT NULL,
  `제목` varchar(2000) DEFAULT NULL,
  `내용` varchar(2000) DEFAULT NULL,
  `모양코드` int(11) NOT NULL,
  `삭제` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `쿠폰모양정보`
--

CREATE TABLE IF NOT EXISTS `쿠폰모양정보` (
  `모양코드` int(11) NOT NULL,
  `모양` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `포인트`
--

CREATE TABLE IF NOT EXISTS `포인트` (
  `회원번호` int(11) NOT NULL,
  `매장번호` int(11) NOT NULL,
  `포인트` int(11) DEFAULT NULL,
  `포인트갱신날짜` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `회원정보`
--

CREATE TABLE IF NOT EXISTS `회원정보` (
  `회원번호` int(11) NOT NULL,
  `이름` varchar(45) DEFAULT NULL,
  `전화번호` varchar(15) DEFAULT NULL,
  `이메일` varchar(50) DEFAULT NULL,
  `생년월일` date DEFAULT NULL,
  `삭제` tinyint(1) DEFAULT NULL,
  `회원이미지저장경로` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `매장`
--
ALTER TABLE `매장`
  ADD PRIMARY KEY (`매장번호`);

--
-- 테이블의 인덱스 `매장공지`
--
ALTER TABLE `매장공지`
  ADD PRIMARY KEY (`매장번호`);

--
-- 테이블의 인덱스 `쿠폰등록현황`
--
ALTER TABLE `쿠폰등록현황`
  ADD PRIMARY KEY (`매장번호`,`모양코드`);

--
-- 테이블의 인덱스 `쿠폰모양정보`
--
ALTER TABLE `쿠폰모양정보`
  ADD PRIMARY KEY (`모양코드`);

--
-- 테이블의 인덱스 `포인트`
--
ALTER TABLE `포인트`
  ADD PRIMARY KEY (`회원번호`,`매장번호`), ADD KEY `fk_포인트_매장1_idx` (`매장번호`);

--
-- 테이블의 인덱스 `회원정보`
--
ALTER TABLE `회원정보`
  ADD PRIMARY KEY (`회원번호`);

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `매장공지`
--
ALTER TABLE `매장공지`
ADD CONSTRAINT `fk_매장공지_매장1` FOREIGN KEY (`매장번호`) REFERENCES `매장` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `쿠폰등록현황`
--
ALTER TABLE `쿠폰등록현황`
ADD CONSTRAINT `fk_쿠폰등록현황_매장1` FOREIGN KEY (`매장번호`) REFERENCES `매장` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `쿠폰모양정보`
--
ALTER TABLE `쿠폰모양정보`
ADD CONSTRAINT `fk_쿠폰모양정보_쿠폰등록현황` FOREIGN KEY (`모양코드`) REFERENCES `쿠폰등록현황` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `포인트`
--
ALTER TABLE `포인트`
ADD CONSTRAINT `fk_포인트_매장1` FOREIGN KEY (`매장번호`) REFERENCES `매장` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_포인트_회원정보1` FOREIGN KEY (`회원번호`) REFERENCES `회원정보` (`회원번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
