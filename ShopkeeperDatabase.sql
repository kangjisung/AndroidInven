-- phpMyAdmin SQL Dump
-- version 4.3.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- 생성 시간: 16-12-03 09:05
-- 서버 버전: 5.5.39-MariaDB
-- PHP 버전: 5.5.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `ShopkeeperDatabase`
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
  `이름` varchar(100) DEFAULT NULL,
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
  `공지 시작 날짜` date DEFAULT NULL,
  `공지 마감 날짜` date DEFAULT NULL,
  `작성시간` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `삭제` tinyint(1) DEFAULT NULL,
  `공지이미지저장경로` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `매장쿠폰 사용현황`
--

CREATE TABLE IF NOT EXISTS `매장쿠폰 사용현황` (
  `고유회원등록번호` int(11) NOT NULL,
  `사용여부` tinyint(1) DEFAULT '0',
  `쿠폰고유번호` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `제품정보`
--

CREATE TABLE IF NOT EXISTS `제품정보` (
  `매장번호` int(11) NOT NULL,
  `제품코드` int(11) DEFAULT NULL,
  `이름` varchar(45) DEFAULT NULL,
  `원가` int(11) DEFAULT NULL,
  `판매가` int(11) DEFAULT NULL,
  `잔존가` int(11) DEFAULT NULL,
  `등록일` date DEFAULT NULL,
  `사용여부` tinyint(1) DEFAULT NULL,
  `제품이미지저장경로` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `제품판매량`
--

CREATE TABLE IF NOT EXISTS `제품판매량` (
  `제품코드` int(11) NOT NULL,
  `판매량` int(11) DEFAULT NULL,
  `날짜` date DEFAULT NULL,
  `예상판매량` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `최적재고량`
--

CREATE TABLE IF NOT EXISTS `최적재고량` (
  `제품코드` int(11) NOT NULL,
  `최적재고량` int(11) DEFAULT NULL,
  `날짜` date DEFAULT NULL
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
  `고유회원등록번호` int(11) NOT NULL,
  `포인트` int(11) DEFAULT '0',
  `포인트갱신날짜` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `회원매장등록정보`
--

CREATE TABLE IF NOT EXISTS `회원매장등록정보` (
  `고유등록번호` int(11) NOT NULL,
  `회원번호` int(11) DEFAULT NULL,
  `매장번호` int(11) DEFAULT NULL,
  `탈퇴여부` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `회원정보`
--

CREATE TABLE IF NOT EXISTS `회원정보` (
  `고유회원등록번호` int(11) NOT NULL,
  `이름` varchar(45) DEFAULT NULL,
  `전화번호` varchar(15) DEFAULT NULL,
  `생년월일` date DEFAULT NULL,
  `이메일` varchar(50) DEFAULT NULL,
  `삭제` tinyint(1) DEFAULT NULL
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
-- 테이블의 인덱스 `매장쿠폰 사용현황`
--
ALTER TABLE `매장쿠폰 사용현황`
  ADD PRIMARY KEY (`고유회원등록번호`,`쿠폰고유번호`), ADD KEY `fk_매장쿠폰 사용현황_매장쿠폰 등록현황1_idx` (`쿠폰고유번호`);

--
-- 테이블의 인덱스 `제품정보`
--
ALTER TABLE `제품정보`
  ADD PRIMARY KEY (`매장번호`);

--
-- 테이블의 인덱스 `제품판매량`
--
ALTER TABLE `제품판매량`
  ADD PRIMARY KEY (`제품코드`);

--
-- 테이블의 인덱스 `최적재고량`
--
ALTER TABLE `최적재고량`
  ADD PRIMARY KEY (`제품코드`);

--
-- 테이블의 인덱스 `쿠폰모양정보`
--
ALTER TABLE `쿠폰모양정보`
  ADD PRIMARY KEY (`모양코드`);

--
-- 테이블의 인덱스 `포인트`
--
ALTER TABLE `포인트`
  ADD PRIMARY KEY (`고유회원등록번호`);

--
-- 테이블의 인덱스 `회원매장등록정보`
--
ALTER TABLE `회원매장등록정보`
  ADD PRIMARY KEY (`고유등록번호`);

--
-- 테이블의 인덱스 `회원정보`
--
ALTER TABLE `회원정보`
  ADD PRIMARY KEY (`고유회원등록번호`);

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `매장공지`
--
ALTER TABLE `매장공지`
ADD CONSTRAINT `fk_매장공지_매장1` FOREIGN KEY (`매장번호`) REFERENCES `매장` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `매장쿠폰 사용현황`
--
ALTER TABLE `매장쿠폰 사용현황`
ADD CONSTRAINT `fk_매장쿠폰 사용현황_매장쿠폰 등록현황1` FOREIGN KEY (`쿠폰고유번호`) REFERENCES `매장쿠폰 등록현황` (`쿠폰고유번호`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_매장쿠폰 사용현황_회원정보1` FOREIGN KEY (`고유회원등록번호`) REFERENCES `회원정보` (`고유회원등록번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `제품정보`
--
ALTER TABLE `제품정보`
ADD CONSTRAINT `fk_제품정보_매장1` FOREIGN KEY (`매장번호`) REFERENCES `매장` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `제품판매량`
--
ALTER TABLE `제품판매량`
ADD CONSTRAINT `fk_제품판매량_제품정보1` FOREIGN KEY (`제품코드`) REFERENCES `제품정보` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `최적재고량`
--
ALTER TABLE `최적재고량`
ADD CONSTRAINT `fk_최적재고량_제품정보1` FOREIGN KEY (`제품코드`) REFERENCES `제품정보` (`매장번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `쿠폰모양정보`
--
ALTER TABLE `쿠폰모양정보`
ADD CONSTRAINT `fk_쿠폰모양정보_매장쿠폰 등록현황` FOREIGN KEY (`모양코드`) REFERENCES `매장쿠폰 등록현황` (`모양코드`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 테이블의 제약사항 `포인트`
--
ALTER TABLE `포인트`
ADD CONSTRAINT `fk_포인트_회원정보1` FOREIGN KEY (`고유회원등록번호`) REFERENCES `회원정보` (`고유회원등록번호`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
