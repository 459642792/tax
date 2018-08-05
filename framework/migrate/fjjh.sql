-- MySQL dump 10.13  Distrib 5.6.27, for osx10.10 (x86_64)
--
-- Host: 192.168.0.111    Database: fjjh
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `aa`
--

DROP TABLE IF EXISTS `aa`;
/*!50001 DROP VIEW IF EXISTS `aa`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `aa` AS SELECT 
 1 AS `Name`,
 1 AS `Code`,
 1 AS `ParentCode`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `advertiseinfo`
--

DROP TABLE IF EXISTS `advertiseinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertiseinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `TypeCode` varchar(125) DEFAULT NULL,
  `Name` varchar(125) DEFAULT NULL,
  `SortNumber` int(11) DEFAULT NULL,
  `Img` varchar(125) DEFAULT NULL,
  `Url` text,
  `CreateBy` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `EnableFlag` varchar(1) NOT NULL,
  `ForeignKey` varchar(50) DEFAULT NULL,
  `CityCode` varchar(125) DEFAULT NULL,
  `BrandId` int(11) DEFAULT NULL,
  `ClickCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `advertisetypeinfo`
--

DROP TABLE IF EXISTS `advertisetypeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisetypeinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SortNumber` int(11) DEFAULT NULL,
  `Name` varchar(30) NOT NULL,
  `Code` varchar(125) DEFAULT NULL,
  `ParentCode` varchar(125) DEFAULT NULL,
  `CreateBy` varchar(50) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `UpdateBy` varchar(50) NOT NULL,
  `UpdateDate` varchar(50) NOT NULL DEFAULT 'getdate()',
  `EnableFlag` varchar(1) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apkset`
--

DROP TABLE IF EXISTS `apkset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apkset` (
  `apk_id` int(11) NOT NULL AUTO_INCREMENT,
  `apk_name` varchar(50) DEFAULT NULL,
  `apk_des` varchar(200) DEFAULT NULL,
  `apk_url` varchar(200) DEFAULT NULL,
  `apk_version` varchar(20) DEFAULT NULL,
  `createby` varchar(50) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`apk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billinginfo`
--

DROP TABLE IF EXISTS `billinginfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billinginfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `BillingInfoType` int(11) NOT NULL,
  `VendorInfoId` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `IdentityCard` varchar(18) NOT NULL,
  `AlipayCardBank` varchar(50) NOT NULL,
  `BankName` varchar(50) DEFAULT NULL,
  `AccountOpeningCity` varchar(50) DEFAULT NULL,
  `AccountOpeningBranch` varchar(100) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `carriersinfo`
--

DROP TABLE IF EXISTS `carriersinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carriersinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `CityCode` varchar(50) NOT NULL,
  `Addr` varchar(150) DEFAULT NULL,
  `ManagementArea` text NOT NULL,
  `Lon` decimal(27,18) DEFAULT NULL,
  `Lat` decimal(27,18) DEFAULT NULL,
  `Phone` varchar(120) DEFAULT NULL,
  `Status` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `CreateDate` datetime NOT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cityinfo`
--

DROP TABLE IF EXISTS `cityinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cityinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `PinYin` varchar(50) DEFAULT NULL,
  `ParentCode` varchar(50) DEFAULT NULL,
  `Code` varchar(50) DEFAULT NULL,
  `EnableFlag` varchar(1) DEFAULT NULL,
  `CreateDate` datetime NOT NULL,
  `CreateBy` varchar(50) NOT NULL,
  `UpdateDate` datetime NOT NULL,
  `UpdateBy` varchar(50) NOT NULL,
  `IsExistVendor` varchar(1) DEFAULT NULL,
  `FullName` varchar(50) DEFAULT NULL,
  `ProvinceCode` varchar(50) DEFAULT NULL,
  `ProvinceName` varchar(50) DEFAULT NULL,
  `CityCode` varchar(50) DEFAULT NULL,
  `CityName` varchar(50) DEFAULT NULL,
  `DistrictCode` varchar(50) DEFAULT NULL,
  `DistrictName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7456 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consumerinfo`
--

DROP TABLE IF EXISTS `consumerinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumerinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNo` varchar(50) NOT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Remark` varchar(200) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  `Time` datetime DEFAULT NULL,
  `Price` decimal(20,2) NOT NULL,
  `Count` int(11) NOT NULL,
  `TotalMoney` decimal(20,2) NOT NULL,
  `PayWay` varchar(20) NOT NULL,
  `OrderStatus` int(11) NOT NULL,
  `CityCode` varchar(50) NOT NULL,
  `DiscountAmount` decimal(20,2) NOT NULL,
  `CreateBy` varchar(50) NOT NULL,
  `UpdateBy` varchar(50) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `UpdateDate` datetime NOT NULL,
  `CouponId` int(11) DEFAULT NULL,
  `Addr` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `continuation`
--

DROP TABLE IF EXISTS `continuation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `continuation` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ForeignKey` int(11) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `ExpandText1` varchar(200) DEFAULT NULL,
  `ExpandText2` varchar(200) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `couponinfo`
--

DROP TABLE IF EXISTS `couponinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `couponinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(50) DEFAULT NULL COMMENT '类型  平台 商家 运营商',
  `Money` decimal(20,2) DEFAULT NULL COMMENT '优惠券面额 元',
  `BeginTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `Count` int(11) DEFAULT NULL COMMENT '多少张',
  `Detail` longtext COMMENT '详情',
  `Condition` longtext COMMENT '是否无条件使用',
  `CostLimitMoney` decimal(20,2) DEFAULT NULL COMMENT '满多少减money',
  `CityCode` varchar(255) DEFAULT NULL COMMENT '城市',
  `ForeignKey` int(11) DEFAULT NULL COMMENT '商家id 平台0',
  `CreateBy` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL COMMENT '优惠券说明',
  `UserId` int(11) DEFAULT NULL COMMENT '用户id',
  `CostStatus` varchar(1) DEFAULT NULL COMMENT '是否使用 Y 使用 N 没有使用',
  `ExpandId` int(11) DEFAULT NULL COMMENT '关联的优惠券id',
  `Status` int(11) DEFAULT '0',
  `Addr` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8 COMMENT='优惠券信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `couponrecord`
--

DROP TABLE IF EXISTS `couponrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `couponrecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(200) DEFAULT NULL,
  `Remark` varchar(200) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  `CouponId` int(11) DEFAULT NULL,
  `Money` decimal(20,2) DEFAULT NULL,
  `TotalMoney` decimal(20,2) DEFAULT NULL,
  `OrderNo` varchar(50) DEFAULT NULL,
  `VendorInfoCouponId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency_record`
--

DROP TABLE IF EXISTS `currency_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `vendorinfo_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `userinfo_id` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `diamond_stones`
--

DROP TABLE IF EXISTS `diamond_stones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diamond_stones` (
  `id` varchar(255) NOT NULL,
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `content` longtext NOT NULL,
  `description` longtext,
  `valid` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_diamond_datagroup` (`data_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discover`
--

DROP TABLE IF EXISTS `discover`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discover` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(200) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Label` varchar(200) DEFAULT NULL,
  `IsUser` varchar(50) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Visits` int(11) DEFAULT '0',
  `IsShow` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Detail` longtext,
  `HandLine` varchar(1) DEFAULT NULL,
  `Reason` varchar(200) DEFAULT NULL,
  `VendorType` varchar(50) DEFAULT NULL,
  `Groom` varchar(1) DEFAULT NULL,
  KEY `Id` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_cash_record`
--

DROP TABLE IF EXISTS `goods_cash_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods_cash_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `traded_goods_id` int(11) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `goods_name` varchar(100) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `cash_price` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `express_company` varchar(50) DEFAULT NULL,
  `express_numbers` varchar(50) DEFAULT NULL,
  `vendorinfo_id` int(11) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `express_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_info`
--

DROP TABLE IF EXISTS `goods_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendor_id` int(11) NOT NULL,
  `model_goods_id` int(11) NOT NULL,
  `goods_status` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `create_by` varchar(20) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_table_image`
--

DROP TABLE IF EXISTS `goods_table_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods_table_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `traded_goods_id` int(11) NOT NULL,
  `traded_goods_image_id` int(11) NOT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods_traded_image_info`
--

DROP TABLE IF EXISTS `goods_traded_image_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods_traded_image_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `traded_goods_image_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `create_by` varchar(10) NOT NULL,
  `update_date` datetime NOT NULL,
  `update_by` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imageinfo`
--

DROP TABLE IF EXISTS `imageinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imageinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Image` varchar(200) NOT NULL,
  `Url` varchar(200) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Detail` varchar(200) DEFAULT NULL,
  `ForeignKey` varchar(50) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `SortNumber` int(11) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `ExtendId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `TargetMethod` text,
  `MethodDescription` text,
  `RequestName` varchar(100) DEFAULT NULL,
  `RequestIP` varchar(30) DEFAULT NULL,
  `ExceptionCode` text,
  `ExceptionDetail` longtext,
  `CreateBy` text,
  `CreateDate` varchar(10) DEFAULT NULL,
  `RequestUrl` text,
  `RequestParamter` longtext,
  `RequestToken` varchar(200) DEFAULT NULL,
  `RequestUserAgent` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchantinfo`
--

DROP TABLE IF EXISTS `merchantinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchantinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `MerchantName` varchar(100) DEFAULT NULL,
  `LeftMoney` decimal(20,2) DEFAULT NULL,
  `BlockedMoney` decimal(20,2) DEFAULT NULL,
  `Rate` decimal(6,1) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchantrecordinfo`
--

DROP TABLE IF EXISTS `merchantrecordinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchantrecordinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNo` varchar(50) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Remark` varchar(200) DEFAULT NULL,
  `VendorId` int(11) DEFAULT NULL,
  `PurchaseUserId` int(11) DEFAULT NULL,
  `PayTime` datetime DEFAULT NULL,
  `PayWay` varchar(50) DEFAULT NULL,
  `Price` decimal(20,2) DEFAULT NULL,
  `Count` int(11) DEFAULT NULL,
  `TotalMoney` decimal(20,2) DEFAULT NULL,
  `Rate` decimal(6,1) DEFAULT NULL,
  `Poundage` decimal(7,2) DEFAULT NULL,
  `RealMoney` decimal(7,2) DEFAULT NULL,
  `DiscountAmount` decimal(20,0) DEFAULT NULL,
  `OrderStatus` int(11) DEFAULT NULL,
  `CityCode` varchar(20) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TypeCode` varchar(50) NOT NULL,
  `DataKey` varchar(50) DEFAULT NULL,
  `Title` varchar(200) NOT NULL,
  `Content` text NOT NULL,
  `Recipients` int(11) NOT NULL DEFAULT '0',
  `VendorID` int(11) NOT NULL,
  `CarriersID` int(11) NOT NULL,
  `ReceivingUserTypes` int(11) NOT NULL DEFAULT '0',
  `SendingTime` datetime NOT NULL,
  `DataSource` varchar(50) NOT NULL,
  `SoftApp` varchar(50) NOT NULL,
  `Platform` varchar(50) NOT NULL,
  `PushServerVersion` varchar(20) NOT NULL,
  `CreateBy` int(11) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `LastUpdateBy` int(11) NOT NULL,
  `LastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messageinfo`
--

DROP TABLE IF EXISTS `messageinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Telephone` varchar(11) DEFAULT NULL,
  `ForeignKey` varchar(200) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Detail` text,
  `Status` int(11) DEFAULT NULL,
  `CreateDate` datetime NOT NULL,
  `CreateBy` varchar(50) NOT NULL,
  `UpdateDate` datetime NOT NULL,
  `UpdateBy` varchar(50) NOT NULL,
  `VendorId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messagereading`
--

DROP TABLE IF EXISTS `messagereading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagereading` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MessageID` int(11) NOT NULL,
  `Recipients` int(11) NOT NULL,
  `ReadingStatus` int(11) NOT NULL DEFAULT '0',
  `ReadingTime` datetime NOT NULL,
  `CreateBy` int(11) NOT NULL,
  `CreateDate` datetime NOT NULL,
  `LastUpdateBy` int(11) NOT NULL,
  `LastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_test`
--

DROP TABLE IF EXISTS `order_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_test` (
  `id` bigint(18) NOT NULL COMMENT '订单id 唯一标识',
  `name` varchar(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (id DIV 1000000)
(PARTITION p1 VALUES LESS THAN (180101000000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (180401000000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (180701000000) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (181001000000) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orderinfo`
--

DROP TABLE IF EXISTS `orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNo` varchar(50) DEFAULT NULL,
  `VendorId` int(11) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  `BookTime` datetime DEFAULT NULL,
  `Remark` varchar(200) DEFAULT NULL,
  `ProductId` int(11) DEFAULT NULL,
  `Price` decimal(20,2) DEFAULT NULL,
  `Count` int(11) DEFAULT NULL,
  `TotalMoney` decimal(20,2) DEFAULT NULL,
  `PayWay` varchar(20) DEFAULT NULL,
  `PayTime` datetime DEFAULT NULL,
  `PayCode` varchar(20) DEFAULT NULL,
  `OrderStatus` int(11) DEFAULT NULL,
  `FinishTime` datetime DEFAULT NULL,
  `RefundTime` datetime DEFAULT NULL,
  `CouponId` int(11) DEFAULT NULL,
  `RefundMoney` decimal(20,2) DEFAULT NULL,
  `DiscountAmount` decimal(20,2) DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(255) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `TradingArea` varchar(100) DEFAULT NULL,
  `settlement_money` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='订单信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pinpaiinfo`
--

DROP TABLE IF EXISTS `pinpaiinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pinpaiinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `Desc` varchar(200) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Image` varchar(200) DEFAULT NULL,
  `SortNumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proc`
--

DROP TABLE IF EXISTS `proc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proc` (
  `db` char(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `name` char(64) NOT NULL DEFAULT '',
  `type` enum('FUNCTION','PROCEDURE') NOT NULL,
  `specific_name` char(64) NOT NULL DEFAULT '',
  `language` enum('SQL') NOT NULL DEFAULT 'SQL',
  `sql_data_access` enum('CONTAINS_SQL','NO_SQL','READS_SQL_DATA','MODIFIES_SQL_DATA') NOT NULL DEFAULT 'CONTAINS_SQL',
  `is_deterministic` enum('YES','NO') NOT NULL DEFAULT 'NO',
  `security_type` enum('INVOKER','DEFINER') NOT NULL DEFAULT 'DEFINER',
  `param_list` blob NOT NULL,
  `returns` longblob NOT NULL,
  `body` longblob NOT NULL,
  `definer` char(93) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sql_mode` set('REAL_AS_FLOAT','PIPES_AS_CONCAT','ANSI_QUOTES','IGNORE_SPACE','NOT_USED','ONLY_FULL_GROUP_BY','NO_UNSIGNED_SUBTRACTION','NO_DIR_IN_CREATE','POSTGRESQL','ORACLE','MSSQL','DB2','MAXDB','NO_KEY_OPTIONS','NO_TABLE_OPTIONS','NO_FIELD_OPTIONS','MYSQL323','MYSQL40','ANSI','NO_AUTO_VALUE_ON_ZERO','NO_BACKSLASH_ESCAPES','STRICT_TRANS_TABLES','STRICT_ALL_TABLES','NO_ZERO_IN_DATE','NO_ZERO_DATE','INVALID_DATES','ERROR_FOR_DIVISION_BY_ZERO','TRADITIONAL','NO_AUTO_CREATE_USER','HIGH_NOT_PRECEDENCE','NO_ENGINE_SUBSTITUTION','PAD_CHAR_TO_FULL_LENGTH') NOT NULL DEFAULT '',
  `comment` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `character_set_client` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `collation_connection` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `db_collation` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `body_utf8` longblob,
  PRIMARY KEY (`db`,`name`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Stored Procedures';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `productinfo`
--

DROP TABLE IF EXISTS `productinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `VendorId` int(11) NOT NULL,
  `Img` varchar(200) DEFAULT NULL,
  `OriginalPrice` decimal(20,2) DEFAULT NULL,
  `Price` decimal(20,2) DEFAULT NULL,
  `SoldCount` int(11) DEFAULT NULL,
  `LeftCount` int(11) DEFAULT NULL,
  `Desc` varchar(200) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `IsRecommend` int(11) DEFAULT NULL,
  `PinpaiId` int(11) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promotion_catagory`
--

DROP TABLE IF EXISTS `promotion_catagory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promotion_catagory` (
  `promotion_catagory_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动分类ID',
  `name` varchar(10) DEFAULT NULL COMMENT '活动名称',
  `model` tinyint(4) DEFAULT NULL COMMENT '位置模式 0-小 1-大',
  `logo` varchar(200) DEFAULT NULL COMMENT '活动logo图片',
  `banner` varchar(200) DEFAULT NULL COMMENT '首页banner图片',
  `summary` varchar(32) DEFAULT NULL COMMENT '活动简介',
  `update_staff_id` int(11) DEFAULT NULL COMMENT '操作管理员ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`promotion_catagory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='首页6大活动分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promotion_info`
--

DROP TABLE IF EXISTS `promotion_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promotion_info` (
  `promotion_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动信息ID',
  `promotion_catagory_id` int(11) DEFAULT NULL COMMENT '所属活动分类ID',
  `vendor_id` int(11) DEFAULT NULL COMMENT '参与店铺ID',
  `vendor_name` varchar(100) DEFAULT NULL COMMENT '参与店铺名称',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '参与店铺商品ID',
  `goods_name` varchar(128) DEFAULT NULL COMMENT '参与店铺商品名称',
  `city_code` varchar(50) DEFAULT NULL COMMENT '店铺所属城市代码',
  `start_time` datetime DEFAULT NULL COMMENT '促销开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '促销结束时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态 0-过期 1-正常',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `visits` bigint(20) DEFAULT '0' COMMENT '访问次数',
  `sales_count` bigint(20) DEFAULT '0' COMMENT '销量',
  `update_staff_id` int(11) DEFAULT NULL COMMENT '操作管理员ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`promotion_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='首页6大活动信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `receiverecord`
--

DROP TABLE IF EXISTS `receiverecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `receiverecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CouponId` int(11) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `CreateDate` datetime NOT NULL,
  `CreateBy` varchar(50) NOT NULL,
  `UpdateDate` datetime NOT NULL,
  `UpdateBy` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `revendor`
--

DROP TABLE IF EXISTS `revendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revendor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `AreaAddr` varchar(50) DEFAULT NULL,
  `VendorName` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `OrderField` int(11) DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `ClickCount` int(11) DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `EabledFlag` varchar(1) DEFAULT NULL,
  `TradingArea` varchar(100) DEFAULT NULL,
  `vendorId` int(11) DEFAULT NULL,
  `CarriersName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scoreinfo`
--

DROP TABLE IF EXISTS `scoreinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scoreinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ProductId` int(11) DEFAULT NULL,
  `Detail` varchar(200) DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `VendorId` int(11) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  `OrderNo` varchar(50) DEFAULT NULL,
  `Score` decimal(20,2) DEFAULT NULL,
  `PointPraise` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `settlementrecords`
--

DROP TABLE IF EXISTS `settlementrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settlementrecords` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `VendorInfoId` int(11) DEFAULT NULL,
  `Amounts` decimal(20,2) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `settlement_start` datetime DEFAULT NULL,
  `settlement_end` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `smallroutinecomment`
--

DROP TABLE IF EXISTS `smallroutinecomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smallroutinecomment` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UpVote` int(11) DEFAULT '0',
  `UserId` int(11) DEFAULT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `UserImage` varchar(200) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Content` longtext,
  `Type` varchar(50) DEFAULT NULL,
  `ForeignKey` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_g_attr`
--

DROP TABLE IF EXISTS `td_g_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_g_attr` (
  `ATTR_CODE` char(3) DEFAULT NULL COMMENT '属性编码',
  `PARENT_ATTR_CODE` char(3) DEFAULT NULL COMMENT '父属性编码, 999代表酒水类商品根属性',
  `ATTR_NAME` varchar(32) DEFAULT NULL COMMENT '属性名称',
  `ATTR_TYPE` tinyint(1) DEFAULT NULL COMMENT '属性值类型：0-select选择 1-input自定义输入',
  `ATTR_GOODS_TYPE` int(4) DEFAULT NULL COMMENT '属性商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID',
  `NECESSARY_TAG` tinyint(1) DEFAULT NULL COMMENT '属性是否必选，0-非必选 1-必选',
  `ADMIN_SHOW_TAG` tinyint(1) DEFAULT NULL COMMENT '后台管理属性展示标记：0-不展示 1-展示',
  `ADMIN_SHOW_ORDER` smallint(6) DEFAULT NULL COMMENT '后台管理属性展示顺序，值越小展示优先级越高',
  `OPERATE_TAG` tinyint(1) DEFAULT NULL COMMENT '属性操作类型 0-不可添加 1-可添加',
  `FIRST_SHOW_TAG` tinyint(1) DEFAULT NULL COMMENT '第一套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示',
  `FIRST_SHOW_ORDER` smallint(6) DEFAULT NULL COMMENT '第一套商品属性应用端展示顺序，值越小展示优先级越高',
  `SECOND_SHOW_TAG` tinyint(1) DEFAULT NULL COMMENT '第二套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示',
  `SECOND_SHOW_ORDER` smallint(6) DEFAULT NULL COMMENT '第二套商品属性应用端展示顺序，值越小展示优先级越高',
  `THIRD_SHOW_TAG` tinyint(1) DEFAULT NULL COMMENT '第三套商品属性应用端展示标记：0-不显示 1-列表页详情页都显示 2-仅列表页展示 3-仅详情页展示',
  `THIRD_SHOW_ORDER` smallint(6) DEFAULT NULL COMMENT '第三套商品属性应用端展示顺序，值越小展示优先级越高',
  UNIQUE KEY `ATTR_CODE` (`ATTR_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_g_attr_val`
--

DROP TABLE IF EXISTS `td_g_attr_val`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_g_attr_val` (
  `ATTR_CODE` char(3) DEFAULT NULL COMMENT '属性编码',
  `ATTR_VALUE_CODE` char(7) DEFAULT NULL COMMENT '属性值编码：属性编码拼接VALUE_INDEX字段，保证唯一性',
  `VALUE_INDEX` int(4) DEFAULT NULL COMMENT '属性值编码后四位(要求>1000)，用于拼接生成新增属性值编码，每次新增属性值，获取当前属性下的所有属性值编码后四位VALUE_INDEX，取最大值加一作为当前属性值编码后四位',
  `ATTR_VALUE_NAME` varchar(32) DEFAULT NULL COMMENT '属性值',
  `OPERATE_TAG` tinyint(1) DEFAULT NULL COMMENT '操作类型：0-不可编辑 1-可修改可删除 2-只可修改 3-只可删除',
  `ATTR_VALUE_SHOW_ORDER` smallint(6) DEFAULT NULL COMMENT '属性值展示顺序，值越小展示优先级越高',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  UNIQUE KEY `ATTR_VALUE_CODE` (`ATTR_VALUE_CODE`),
  UNIQUE KEY `UK_CODE_NAME` (`ATTR_CODE`,`ATTR_VALUE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性值参数表(自定义属性值属性不需要关联该表)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_g_brand_main`
--

DROP TABLE IF EXISTS `td_g_brand_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_g_brand_main` (
  `MAIN_BRAND_ID` int(4) NOT NULL AUTO_INCREMENT COMMENT '主品牌编码',
  `MAIN_BRAND_NAME` varchar(32) DEFAULT NULL COMMENT '主品牌名称',
  `MAIN_BRAND_PHOTO` varchar(128) DEFAULT NULL COMMENT '主品牌LOG图片',
  `MAIN_BRAND_DESC` longtext COMMENT '主品牌介绍',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MAIN_BRAND_ID`),
  UNIQUE KEY `MAIN_BRAND_NAME` (`MAIN_BRAND_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=1040 DEFAULT CHARSET=utf8 COMMENT='主品牌信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_g_brand_sub`
--

DROP TABLE IF EXISTS `td_g_brand_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_g_brand_sub` (
  `BRAND_ID` int(5) NOT NULL AUTO_INCREMENT COMMENT '子品牌编码',
  `MAIN_BRAND_ID` int(4) DEFAULT NULL COMMENT '主品牌编码',
  `BRAND_NAME` varchar(32) NOT NULL,
  `STATE_TAG` tinyint(1) DEFAULT NULL COMMENT '品牌状态：0-无效 1-有效',
  `BRAND_GOODS_TYPE` int(4) DEFAULT NULL COMMENT '品牌商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID',
  `BRAND_PHOTO` varchar(128) DEFAULT NULL COMMENT '品牌照片',
  `BRAND_DESC` longtext COMMENT '品牌描述',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`BRAND_ID`),
  UNIQUE KEY `SUB_BRAND_NAME` (`BRAND_NAME`),
  UNIQUE KEY `BRAND_NAME` (`BRAND_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=10069 DEFAULT CHARSET=utf8 COMMENT='子品牌信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `td_g_goods_type`
--

DROP TABLE IF EXISTS `td_g_goods_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `td_g_goods_type` (
  `GOODS_TYPE_ID` int(4) NOT NULL COMMENT '商品类型id:四位数字,将商品分成三个层级level1(1位表示，区分实体、虚拟等商品)-level2（1位表示，区分商品大类，当前只有酒水类）-level3（2位表示，最小粒度商品类别：当前有白酒，红酒等等）',
  `PARENT_TYPE_ID` int(4) DEFAULT NULL COMMENT '上级商品类别id：9999表示root类别',
  `GOODS_TYPE_NAME` varchar(32) DEFAULT NULL COMMENT '商品类型名称',
  `SHWO_ORDER` smallint(6) DEFAULT NULL COMMENT '展示顺序',
  PRIMARY KEY (`GOODS_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_a_vendor_brand`
--

DROP TABLE IF EXISTS `tf_a_vendor_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_a_vendor_brand` (
  `VENDOR_ID` int(11) DEFAULT NULL COMMENT '商家id',
  `BRAND_ID` int(5) DEFAULT NULL COMMENT '子品牌id',
  `AUTHORITY_TAG` int(11) DEFAULT NULL COMMENT '权限标志：0-无效 1-有效 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家品牌权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_goods_verify`
--

DROP TABLE IF EXISTS `tf_b_goods_verify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_goods_verify` (
  `VERIFY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '审核编码',
  `VERIFY_BAR_CODE` varchar(32) DEFAULT NULL COMMENT '审核条形码',
  `VERIFY_GOODS_NAME` varchar(128) DEFAULT NULL COMMENT '审核商品名称',
  `VERIFY_GOODS_STATE` tinyint(1) DEFAULT NULL COMMENT '审核商品状态：0-审核通过不上架 1-审核通过上架',
  `VERIFY_GOODS_PHOTO` varchar(512) DEFAULT NULL COMMENT '审核商品照片',
  `VENDOR_ID` int(11) DEFAULT NULL COMMENT '发起商品审核的商家id',
  `VERIFY_SALE_PRICE` int(11) DEFAULT NULL COMMENT '商家销售价格',
  `VERIFY_STATE` tinyint(4) DEFAULT NULL COMMENT '审核状态：0-未审核 1-审核不通过 2-审核通过',
  `VERIFY_REFUSE_CODE` tinyint(4) DEFAULT NULL COMMENT '审核失败编码：0-条形码输入错误 1-图片违规 2-名称不合法 3-其他',
  `VERIFY_REFUSE_REASON` varchar(128) DEFAULT NULL COMMENT '审核失败详情',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`VERIFY_ID`),
  UNIQUE KEY `UK_BARCODE_VENDOR` (`VERIFY_BAR_CODE`,`VENDOR_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='商品审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order`
--

DROP TABLE IF EXISTS `tf_b_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id 唯一标识',
  `order_no` varchar(20) NOT NULL COMMENT '订单编号，用于平台对用户展示',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `vendor_id` int(11) NOT NULL COMMENT '商家id',
  `order_state` tinyint(4) NOT NULL COMMENT '订单状态，关联字典值表tf_d_state',
  `complete_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单完成状态 0-未完成  1-已完成  2-已取消',
  `refund_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退款状态 关联字典值表 tf_d_state',
  `comment_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '评价状态  0-未评价  1-已评价',
  `validity_state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用于C端是否显示： 1 是有效代表未删除 0代表无效已删除',
  `order_source` tinyint(1) NOT NULL DEFAULT '1' COMMENT '定单的来源 1代表普通订单有商品的 2带面面对面付款',
  `order_channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web',
  `pay_channel` tinyint(255) DEFAULT NULL COMMENT '支付渠道：1-微信小程序 2-支付宝小程序 3-app 4-web',
  `pay_type_id` tinyint(4) NOT NULL COMMENT '支付类型id  关联支付类型表',
  `pay_type_name` varchar(255) NOT NULL COMMENT '支付类型名称',
  `pay_way_id` tinyint(4) DEFAULT NULL COMMENT '支付方式id  关联支付方式表',
  `pay_way_name` varchar(255) DEFAULT NULL COMMENT '支付类型名称',
  `original_price` bigint(20) NOT NULL COMMENT '订单原价(单位-分)',
  `pay_price` bigint(20) NOT NULL COMMENT '支付金额(单位-分)',
  `remark` varchar(30) DEFAULT NULL COMMENT '留言',
  `coupon_amount` bigint(20) NOT NULL COMMENT '优惠金额(单位-分)',
  `delivery_type` tinyint(4) NOT NULL COMMENT '收货方式  1-配送  2-自提',
  `delivery_fee` bigint(20) NOT NULL COMMENT '配送费(单位-分)',
  `delivery_time` varchar(22) NOT NULL COMMENT '订单配送时间或者自提时间(YYYYMMdd-hh:mm-hh:mm)',
  `delivery_address` varchar(128) NOT NULL COMMENT '收货地址（配送:配送地址,自提:自提地址）',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  `promise_time` datetime DEFAULT NULL COMMENT '商家接单时间',
  `update_time` datetime NOT NULL COMMENT '订单更新时间',
  `update_staff_id` int(11) DEFAULT NULL COMMENT '更新员工id',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
  `comment_score` int(11) DEFAULT '0',
  `complete_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `version` int(11) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_attr`
--

DROP TABLE IF EXISTS `tf_b_order_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_attr` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `goods_id` bigint(12) NOT NULL COMMENT '商品id',
  `attr_code` char(3) NOT NULL COMMENT '属性id',
  `parent_attr_code` char(3) DEFAULT NULL COMMENT '父属性id',
  `attr_name` varchar(32) NOT NULL COMMENT '属性名',
  `attr_type` tinyint(4) NOT NULL COMMENT '属性类型',
  `attr_goods_type` int(4) NOT NULL COMMENT '属性商品类型',
  `necessary_tag` tinyint(4) NOT NULL COMMENT '属性必选标志  0-非必选 1-必选',
  `attr_value_code` char(7) DEFAULT NULL COMMENT '属性值编码',
  `attr_value_name` varchar(32) NOT NULL COMMENT '属性值名',
  `attr_value_show_name` varchar(32) NOT NULL COMMENT '属性值展示名',
  `show_tag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '属性展示标记 0-不展示 1-列表和详情都展示  2-仅列表  3-仅详情',
  `show_order` smallint(6) DEFAULT NULL COMMENT '属性展示顺序，值越小展示优先级越高'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品属性表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_comment`
--

DROP TABLE IF EXISTS `tf_b_order_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '订单评分，1-5星',
  `comment_content` varchar(255) DEFAULT NULL COMMENT '评价内容',
  `comment_picture` varchar(2000) DEFAULT NULL COMMENT '评价附图',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标示，0正常，1删除',
  `user_name` varchar(255) DEFAULT NULL,
  `vendor_id` int(11) NOT NULL,
  `goods_name` varchar(1000) DEFAULT NULL COMMENT '商品名字，多个商品以^分隔',
  `head_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_complain`
--

DROP TABLE IF EXISTS `tf_b_order_complain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_complain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `order_id` bigint(20) NOT NULL COMMENT '投诉订单id',
  `complain_reason` varchar(255) NOT NULL COMMENT '投诉原因',
  `complain_picture` text COMMENT '投诉图片,多张以^分隔',
  `update_staff_id` int(11) DEFAULT NULL COMMENT '处理员工id',
  `dispose_result` varchar(255) DEFAULT NULL COMMENT '处理结果',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '处理状态：0未处理，1处理中，2已处理',
  `vendor_Id` int(11) NOT NULL COMMENT '商家id',
  `order_no` varchar(20) NOT NULL COMMENT '订单编号',
  `reply` varchar(255) DEFAULT NULL COMMENT '客服回复',
  `pay_price` int(11) NOT NULL COMMENT '订单支付金额(单位:分)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_dispatch`
--

DROP TABLE IF EXISTS `tf_b_order_dispatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_dispatch` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dispatch_name` varchar(32) DEFAULT NULL COMMENT '配送名称',
  `dispatch_type` tinyint(4) DEFAULT NULL COMMENT '配送类型：0-商家配型 1-平台配送 2-第三方配送',
  `dispatch_provider` varchar(32) DEFAULT NULL COMMENT '配送提供方',
  `dispatch_fee` bigint(20) DEFAULT NULL COMMENT '配送费用(单位-分)',
  `dispatch_detail` varchar(64) DEFAULT NULL COMMENT '配送详情',
  `contact_name` varchar(32) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(16) DEFAULT NULL COMMENT '联系人手机',
  `address_name` varchar(32) DEFAULT NULL COMMENT '地址名称',
  `address_desc` varchar(64) DEFAULT NULL COMMENT '地址详情',
  `input_address` varchar(64) DEFAULT NULL COMMENT '用户输入地址',
  `structured_address` varchar(128) DEFAULT NULL COMMENT '结构化地址',
  `longitude` varchar(32) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) DEFAULT NULL COMMENT '纬度',
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市编码',
  `ad_code` varchar(8) DEFAULT NULL COMMENT '行政区编码',
  `town_code` varchar(16) DEFAULT NULL COMMENT '街区编码',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_staff_id` int(11) DEFAULT NULL COMMENT '更新员工id',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单配送信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_fee`
--

DROP TABLE IF EXISTS `tf_b_order_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_fee` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `fee_type_code` tinyint(4) NOT NULL COMMENT '费用类型编码',
  `fee_type_name` varchar(32) NOT NULL COMMENT '费用类型名称',
  `receivable_fee` bigint(20) NOT NULL COMMENT '应收费用(单位-分)',
  `real_fee` bigint(20) NOT NULL COMMENT '实际费用(单位-分)',
  `counpon_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '优惠金额(单位-分)',
  `fee_belong` varchar(64) DEFAULT NULL COMMENT '费用归属'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单费用明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_goods_item`
--

DROP TABLE IF EXISTS `tf_b_order_goods_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_goods_item` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `goods_id` bigint(12) NOT NULL COMMENT '商品id',
  `goods_name` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `bar_code` varchar(64) NOT NULL COMMENT '条形码',
  `goods_num` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `goods_type_name` varchar(32) DEFAULT NULL COMMENT '商品类型名',
  `main_brand_name` varchar(32) DEFAULT NULL COMMENT '主品牌名称',
  `brand_name` varchar(32) DEFAULT NULL COMMENT '品牌名称',
  `goods_photos` varchar(2000) DEFAULT NULL COMMENT '商品图片 多张以^分隔',
  `original_price` bigint(20) NOT NULL COMMENT '商品原价(单位-分)',
  `pay_price` bigint(20) NOT NULL COMMENT '实际支付金额(单位-分)',
  `coupon_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '优惠金额(单位-分)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_b_order_settlement`
--

DROP TABLE IF EXISTS `tf_b_order_settlement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_b_order_settlement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region` varchar(100) DEFAULT NULL COMMENT '所在地(地区)',
  `vendor_id` int(11) DEFAULT NULL COMMENT '商家ID',
  `vendor_name` varchar(100) DEFAULT NULL COMMENT '商家名称',
  `all_balanced_amounts` decimal(20,2) DEFAULT '0.00' COMMENT '本次结算后的的已结算总额',
  `balanced_amounts` decimal(20,2) DEFAULT NULL COMMENT '本次结算的结算金额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结算日期',
  `create_username` varchar(50) DEFAULT NULL COMMENT '结算创建者',
  `create_user_id` int(11) DEFAULT NULL COMMENT '结算创建者ID',
  `start_time` datetime DEFAULT NULL COMMENT '结算开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结算截止时间',
  `count_type` int(1) DEFAULT NULL COMMENT '结算方式  1微信   2 银行卡  3 支付宝 ',
  `count_no` varchar(40) DEFAULT NULL COMMENT '账户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='商家订单结算';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_d_state`
--

DROP TABLE IF EXISTS `tf_d_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_d_state` (
  `state_id` tinyint(4) NOT NULL COMMENT '状态id',
  `state_type` tinyint(4) NOT NULL COMMENT '状态类型 0-订单状态 1-退款状态',
  `state_name` varchar(32) NOT NULL COMMENT '状态名称',
  `state_business_type` tinyint(4) NOT NULL COMMENT '状态业务类型，针对订单状态为订单完成类型：0-已取消 1-已完成；针对退款状态待定默认0',
  `state_order` varchar(32) NOT NULL COMMENT '状态逻辑顺序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='状态字典值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_f_user_address`
--

DROP TABLE IF EXISTS `tf_f_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_f_user_address` (
  `user_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `contact_name` varchar(32) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(16) NOT NULL COMMENT '联系人手机号',
  `address_name` varchar(32) NOT NULL COMMENT '地址名称',
  `address_desc` varchar(64) NOT NULL COMMENT '地址详情',
  `input_address` varchar(64) DEFAULT NULL COMMENT '用户输入地址',
  `formatted_address` varchar(128) NOT NULL COMMENT '结构化地址',
  `longitude` varchar(32) NOT NULL COMMENT '经度',
  `latitude` varchar(32) NOT NULL COMMENT '纬度',
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市编码',
  `ad_code` varchar(8) DEFAULT NULL COMMENT '行政区编码',
  `town_code` varchar(16) DEFAULT NULL COMMENT '街区编码',
  `state_tag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态标记：0-无效 1-有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_address_id`),
  KEY `user_id` (`user_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='用户收货地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_f_vendor_dispatch`
--

DROP TABLE IF EXISTS `tf_f_vendor_dispatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_f_vendor_dispatch` (
  `vendor_id` int(11) NOT NULL COMMENT '商家id',
  `dispatch_id` tinyint(4) NOT NULL COMMENT '配送id',
  `state_tag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态标记：0-无效 1-有效',
  `show_order` tinyint(4) DEFAULT NULL COMMENT '展示顺序(降序)',
  PRIMARY KEY (`vendor_id`,`dispatch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家配送信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_f_vendor_pay_type`
--

DROP TABLE IF EXISTS `tf_f_vendor_pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_f_vendor_pay_type` (
  `vendor_id` int(11) NOT NULL COMMENT '商家id',
  `channel_pay_type_id` tinyint(4) NOT NULL COMMENT '渠道支付类型id',
  PRIMARY KEY (`vendor_id`,`channel_pay_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家支付类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_f_vendor_pay_way`
--

DROP TABLE IF EXISTS `tf_f_vendor_pay_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_f_vendor_pay_way` (
  `vendor_id` int(11) NOT NULL COMMENT '商家id',
  `channel_pay_way_id` int(11) NOT NULL COMMENT '渠道支付方式id',
  PRIMARY KEY (`vendor_id`,`channel_pay_way_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家支付方式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_goods`
--

DROP TABLE IF EXISTS `tf_g_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_goods` (
  `GOODS_ID` bigint(12) NOT NULL COMMENT '商品id：前八位为uuid,后两位为商品类型',
  `GOODS_NAME` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `GOODS_LABEL` varchar(128) DEFAULT NULL COMMENT '商品标签',
  `GOODS_TYPE` int(4) DEFAULT NULL COMMENT '商品类型：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID',
  `BAR_CODE` varchar(64) DEFAULT NULL COMMENT '条形码',
  `MAIN_BRAND_ID` int(4) DEFAULT NULL COMMENT '主品牌id',
  `BRAND_ID` int(5) DEFAULT NULL COMMENT '子品牌id',
  `SUGGEST_PRICE` bigint(20) DEFAULT NULL COMMENT '建议零售价，单位：分',
  `GOODS_STATE` tinyint(1) DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `OPERATE_TAG` tinyint(1) DEFAULT NULL COMMENT '商品操作标志：0-不可编辑 1-可编辑',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`GOODS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表'
/*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
PARTITIONS 10 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_goods_attr`
--

DROP TABLE IF EXISTS `tf_g_goods_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_goods_attr` (
  `GOODS_ID` bigint(12) DEFAULT NULL COMMENT '商品id',
  `ATTR_CODE` char(3) DEFAULT NULL COMMENT '属性编码',
  `ATTR_VALUE_CODE` char(7) DEFAULT NULL COMMENT '属性值编码:属性值类型为input框自定义输入时，该字段不落值',
  `ATTR_VALUE_NAME` varchar(64) DEFAULT NULL COMMENT '属性值',
  `ATTR_VALUE_SHOW_NAME` varchar(64) DEFAULT NULL COMMENT '属性值应用端展示',
  UNIQUE KEY `UK_ID_CODE_NAME` (`GOODS_ID`,`ATTR_CODE`,`ATTR_VALUE_NAME`),
  KEY `INDEX_GOODS_ATTR` (`GOODS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性表'
/*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
PARTITIONS 10 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_goods_detail`
--

DROP TABLE IF EXISTS `tf_g_goods_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_goods_detail` (
  `GOODS_ID` bigint(12) NOT NULL COMMENT '商品ID',
  `GOODS_DESC` longtext COMMENT '商品描述',
  PRIMARY KEY (`GOODS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表'
/*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
PARTITIONS 10 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_goods_photo`
--

DROP TABLE IF EXISTS `tf_g_goods_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_goods_photo` (
  `GOODS_ID` bigint(12) DEFAULT NULL COMMENT '商品ID',
  `GOODS_PHOTO` varchar(128) DEFAULT NULL COMMENT '商品照片',
  `PHOTO_STATE` tinyint(1) DEFAULT NULL COMMENT '图片状态:0-无效 1-有效',
  `PHOTO_TONE` varchar(20) DEFAULT NULL COMMENT '图片色调'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片表'
/*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
PARTITIONS 10 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_goods_vendor`
--

DROP TABLE IF EXISTS `tf_g_goods_vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_goods_vendor` (
  `GOODS_ID` bigint(12) DEFAULT NULL COMMENT '商品id',
  `VENDOR_ID` int(11) DEFAULT NULL COMMENT '商家id',
  `SALE_PRICE` int(11) DEFAULT NULL COMMENT '商家销售价格',
  `VENDOR_GOODS_STATE` tinyint(1) DEFAULT NULL COMMENT '商家商品状态：0-下架 1-上架',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  UNIQUE KEY `UK_GOODS_VENDOR` (`GOODS_ID`,`VENDOR_ID`),
  KEY `INDEX_GOODS_VENDOR` (`GOODS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家商品信息表'
/*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
PARTITIONS 10 */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_g_vendor_brand`
--

DROP TABLE IF EXISTS `tf_g_vendor_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_g_vendor_brand` (
  `VENDOR_ID` int(11) DEFAULT NULL COMMENT '商家id',
  `BRAND_ID` int(5) DEFAULT NULL COMMENT '子品牌id',
  `BRAND_GOODS_AMOUNT` int(11) DEFAULT NULL COMMENT '品牌上架商品数',
  `AUTHORITY_TAG` int(11) DEFAULT NULL COMMENT '权限标志：0-无效 1-有效 ',
  `CREATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '创建员工ID',
  `UPDATE_STAFF_ID` int(11) DEFAULT NULL COMMENT '最近一次更新的员工ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `IS_MAJOR` int(11) DEFAULT '0',
  UNIQUE KEY `UK_VENDOR_BRAND` (`VENDOR_ID`,`BRAND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家品牌信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_apply_refund_record`
--

DROP TABLE IF EXISTS `tf_m_apply_refund_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_apply_refund_record` (
  `apply_refund_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款申请id',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `refund_fee` bigint(20) NOT NULL COMMENT '退款金额  分',
  `refund_reason_code` tinyint(4) DEFAULT NULL COMMENT '退款理由编码 0-商品与描述不符 1-不想买了 2-店家收取配送费 3 店家不愿配送 4 换成其它商品 9-其他',
  `refund_reason_desc` varchar(64) DEFAULT NULL COMMENT '退款理由描述',
  `refund_remark` varchar(100) DEFAULT NULL COMMENT '退款备注',
  `process_status` tinyint(4) DEFAULT NULL COMMENT '处理状态 0-未处理 1-同意退款 2-拒绝退款 3-已取消',
  `process_by` int(11) DEFAULT NULL COMMENT '处理人ID ',
  `process_time` datetime DEFAULT NULL COMMENT '结果时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`apply_refund_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='订单退款申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_channel`
--

DROP TABLE IF EXISTS `tf_m_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_channel` (
  `channel_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '渠道id',
  `channel_name` varchar(32) NOT NULL COMMENT '渠道名称',
  `channel_detail` varchar(64) DEFAULT NULL COMMENT '渠道详情',
  `channel_address` varchar(64) DEFAULT NULL COMMENT '渠道地址',
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='渠道信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_channel_pay_type`
--

DROP TABLE IF EXISTS `tf_m_channel_pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_channel_pay_type` (
  `channel_pay_type_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '渠道支付类型id',
  `channel_id` tinyint(4) NOT NULL COMMENT '渠道id',
  `pay_type_id` tinyint(4) NOT NULL COMMENT '支付类型id',
  `state_tag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态标志：0-无效 1-有效',
  `show_order` tinyint(4) DEFAULT NULL COMMENT '展示顺序(升序)',
  PRIMARY KEY (`channel_pay_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='渠道支付类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_channel_pay_way`
--

DROP TABLE IF EXISTS `tf_m_channel_pay_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_channel_pay_way` (
  `channel_pay_way_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '渠道支付方式id',
  `channel_pay_type_id` tinyint(4) NOT NULL COMMENT '渠道支付类型id',
  `pay_way_id` tinyint(4) DEFAULT NULL COMMENT '支付方式id',
  `state_tag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态标志：0-无效 1-有效',
  `show_order` tinyint(4) DEFAULT NULL COMMENT '展示顺序(升序)',
  PRIMARY KEY (`channel_pay_way_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='渠道支付方式表（渠道支付类型表的从表）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_dispatch`
--

DROP TABLE IF EXISTS `tf_m_dispatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_dispatch` (
  `dispatch_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '配送id',
  `dispatch_name` varchar(32) NOT NULL COMMENT '配送名称',
  `dispatch_type` tinyint(4) NOT NULL COMMENT '配送类型 0-商家配送 1-平台配送 2-第三方配送',
  `dispatch_provider` varchar(32) DEFAULT NULL COMMENT '配送提供方',
  `dispatch_fee` bigint(20) NOT NULL DEFAULT '0' COMMENT '配送费用(单位-分)',
  `dispatch_detail` varchar(64) DEFAULT NULL COMMENT '配送详情',
  PRIMARY KEY (`dispatch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='配送信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_message_content`
--

DROP TABLE IF EXISTS `tf_m_message_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_message_content` (
  `message_content_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `message_subject_id` int(11) NOT NULL COMMENT '主题id',
  `message_content` varchar(8000) NOT NULL COMMENT '详细内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`message_content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息详细内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_message_subject`
--

DROP TABLE IF EXISTS `tf_m_message_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_message_subject` (
  `message_subject_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id 针对分组用户的提醒0',
  `message_source` int(11) NOT NULL COMMENT '消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它',
  `detailed_id` bigint(20) NOT NULL COMMENT '详细内容id 根据需求关联表',
  `subject_genre` varchar(20) NOT NULL COMMENT '主题分类',
  `subject_type` int(11) NOT NULL COMMENT '主题类型',
  `subject_state` int(11) NOT NULL COMMENT '主题状态 0 未读 1已读',
  `subject_title` varchar(20) NOT NULL COMMENT '主题标题',
  `subject_content` varchar(50) NOT NULL COMMENT '主题内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `update_by` varchar(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`message_subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='消息内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_message_template`
--

DROP TABLE IF EXISTS `tf_m_message_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_message_template` (
  `message_template_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `template_genre` varchar(20) NOT NULL COMMENT '消息分类',
  `template_type` int(11) NOT NULL COMMENT '消息类型(1 通知 2 提醒 3消息)',
  `template_title` varchar(10) DEFAULT NULL COMMENT '消息头 如 订单通知',
  `template_argument` varchar(20) NOT NULL COMMENT '消息参数',
  `template_subject` varchar(50) NOT NULL COMMENT '消息内容 必须包含template_argument参数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `update_by` varchar(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`message_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='消息模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_pay_type`
--

DROP TABLE IF EXISTS `tf_m_pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_pay_type` (
  `pay_type_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '支付类型id',
  `pay_type_name` varchar(32) NOT NULL COMMENT '支付类型名,如:在线支付,货到付款',
  `pay_type_detail` varchar(64) DEFAULT NULL COMMENT '支付类型详情',
  PRIMARY KEY (`pay_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='支付类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_pay_way`
--

DROP TABLE IF EXISTS `tf_m_pay_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_pay_way` (
  `pay_way_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '支付方式id',
  `pay_type_id` tinyint(4) NOT NULL COMMENT '支付类型id',
  `pay_way_name` varchar(32) NOT NULL COMMENT '支付类型名称,如:微信支付,支付宝等',
  `pay_way_detail` varchar(64) DEFAULT NULL COMMENT '支付类型详情',
  PRIMARY KEY (`pay_way_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='支付方式表（支付类型表的从表）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_prepaid_record`
--

DROP TABLE IF EXISTS `tf_m_prepaid_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_prepaid_record` (
  `prepaid_record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `time_stamp` varchar(30) NOT NULL COMMENT '时间戳',
  `nonce_str` varchar(50) NOT NULL COMMENT '随机串',
  `prepaid_package` varchar(200) NOT NULL COMMENT '数据包',
  `sign_type` varchar(50) NOT NULL COMMENT '签名类型',
  `pay_sign` varchar(50) NOT NULL COMMENT '签名',
  `order_no` varchar(50) NOT NULL COMMENT '订单no',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`prepaid_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预支付记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_refund_result`
--

DROP TABLE IF EXISTS `tf_m_refund_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_refund_result` (
  `refund_result_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款结果id',
  `result_state` tinyint(4) NOT NULL COMMENT '0-退款失败 1-退款成功',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `refund_type` tinyint(4) NOT NULL COMMENT '退款类型:1-系统退款 2-人工退款',
  `refund_fee` bigint(20) NOT NULL COMMENT '退款金额(单位:分)',
  `refund_channel` tinyint(4) NOT NULL COMMENT '退款渠道: 1-微信 2-支付宝 3-银行卡转账',
  `receive_id` varchar(30) DEFAULT NULL COMMENT '人工退款接收方唯一id:微信号/支付宝账号/银行卡账号',
  `create_time` datetime NOT NULL COMMENT '创建时间:退款成功即退款时间',
  `staff_id` int(11) DEFAULT NULL COMMENT '操作员工id',
  PRIMARY KEY (`refund_result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='退款结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tf_m_trade_record`
--

DROP TABLE IF EXISTS `tf_m_trade_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tf_m_trade_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `refund_id` bigint(20) DEFAULT NULL COMMENT '如果是退款 退款id',
  `result_code` varchar(16) DEFAULT NULL COMMENT '业务结果',
  `trade_type` int(2) NOT NULL COMMENT '交易类型 1 支付 2 退款',
  `trade_start_record` varchar(4000) DEFAULT NULL COMMENT '发起参数',
  `trade_end_record` varchar(4000) DEFAULT NULL COMMENT '结果详情',
  `trade_start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `trade_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `third_party_user_info`
--

DROP TABLE IF EXISTS `third_party_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `third_party_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `third_party_id` varchar(200) DEFAULT NULL,
  `third_party_nick_name` varchar(50) DEFAULT NULL,
  `third_party_head_image` varchar(200) DEFAULT NULL,
  `third_party_city` varchar(50) DEFAULT NULL,
  `user_type` int(11) NOT NULL,
  `third_party_type` int(11) NOT NULL,
  `third_party_status` int(11) NOT NULL,
  `create_by` varchar(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(20) NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `traded_goods`
--

DROP TABLE IF EXISTS `traded_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traded_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(100) DEFAULT NULL,
  `brand_name` varchar(100) DEFAULT NULL,
  `goods_price` int(11) NOT NULL,
  `goods_status` int(11) DEFAULT NULL,
  `goods_genre` int(11) DEFAULT NULL,
  `goods_details` text,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `traded_goods_image`
--

DROP TABLE IF EXISTS `traded_goods_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traded_goods_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `upvoterecord`
--

DROP TABLE IF EXISTS `upvoterecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upvoterecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) DEFAULT NULL,
  `ForeignKey` int(11) DEFAULT NULL,
  `UserImage` varchar(200) DEFAULT NULL,
  `UserId` int(11) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_middle_third_party`
--

DROP TABLE IF EXISTS `user_middle_third_party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_middle_third_party` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_info_id` int(11) NOT NULL,
  `third_party_id` int(11) NOT NULL,
  `middle_status` int(1) NOT NULL COMMENT '0 解绑 1 绑定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) DEFAULT NULL,
  `NickName` varchar(50) DEFAULT NULL,
  `UserTypes` int(11) DEFAULT NULL,
  `AuditStatus` int(11) DEFAULT NULL,
  `IsLock` int(11) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Telephone` varchar(11) DEFAULT NULL,
  `RegisterIp` varchar(20) DEFAULT NULL,
  `LoginTime` datetime DEFAULT NULL,
  `Remark` varchar(20) DEFAULT NULL,
  `DataSource` varchar(50) DEFAULT NULL,
  `HeadImage` varchar(200) DEFAULT NULL,
  `CityCode` varchar(50) DEFAULT NULL,
  `WxOpenId` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Addr` varchar(100) DEFAULT NULL,
  `UserPwd` varchar(50) DEFAULT NULL,
  `PasswordSalt` varchar(50) DEFAULT NULL,
  `device_tokens` varchar(100) DEFAULT NULL,
  `vendorWxOpenId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendorinfo`
--

DROP TABLE IF EXISTS `vendorinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendorinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) DEFAULT NULL COMMENT '店铺名称',
  `CityCode` varchar(50) DEFAULT NULL,
  `Addr` varchar(200) DEFAULT NULL COMMENT '地址',
  `Opentime` varchar(50) DEFAULT NULL COMMENT '营业时间',
  `Telephone` varchar(11) DEFAULT NULL COMMENT '手机，登录唯一',
  `Phone` varchar(20) DEFAULT NULL COMMENT '座机',
  `VisitCount` int(11) DEFAULT '0',
  `Status` int(11) DEFAULT NULL,
  `Detail` text,
  `RecommendProduct` varchar(255) DEFAULT NULL,
  `AuditStatus` varchar(1) DEFAULT NULL,
  `ContactPerson` varchar(100) DEFAULT NULL COMMENT '联系人',
  `Rate` decimal(20,2) DEFAULT NULL,
  `Image` varchar(200) DEFAULT NULL COMMENT '店招',
  `IMAGE_TONE` varchar(30) DEFAULT NULL COMMENT '主色调',
  `UserId` int(11) DEFAULT NULL,
  `Label` varchar(200) DEFAULT NULL COMMENT '公告',
  `Longitude` varchar(20) DEFAULT NULL,
  `Latitude` varchar(20) DEFAULT NULL,
  `PinpaiIds` varchar(200) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL COMMENT '创建时间',
  `CreateBy` varchar(50) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  `UpdateBy` varchar(50) DEFAULT NULL,
  `Advantage` varchar(200) DEFAULT NULL,
  `LegalPerson` varchar(20) DEFAULT NULL COMMENT '法人',
  `LegalPersonIdCard` varchar(20) DEFAULT NULL COMMENT '法人身份证',
  `BusinessLicenseImg` varchar(150) DEFAULT NULL COMMENT '营业执照图片',
  `LicenseFileImg` varchar(150) DEFAULT NULL,
  `IdInHandImage` varchar(150) DEFAULT NULL COMMENT '手持身份证照',
  `AuthenticationAuditor` int(11) DEFAULT NULL,
  `AuthenticationStatus` int(11) DEFAULT NULL COMMENT '审核状态，10（待审核）,20（审核通过）,30（未通过）',
  `AuthenticatioDate` datetime DEFAULT NULL,
  `Modify` text,
  `AuthenticationReason` text,
  `TradingArea` varchar(100) DEFAULT NULL COMMENT '取回地址',
  `company_name` varchar(255) DEFAULT NULL COMMENT '企业名称',
  `credit_code` varchar(255) DEFAULT NULL COMMENT '统一社会信用代码',
  `province_code` varchar(50) DEFAULT NULL COMMENT '省级代码',
  `province_name` varchar(50) DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(50) DEFAULT NULL COMMENT '市编码',
  `city_name` varchar(50) DEFAULT NULL COMMENT '市名称',
  `district_code` varchar(50) DEFAULT NULL COMMENT '区编码',
  `district_name` varchar(50) DEFAULT NULL COMMENT '区名称',
  `is_open` int(11) DEFAULT '0' COMMENT '是否已开店，默认否0',
  `volume` int(11) DEFAULT '0' COMMENT '总成交量',
  `page_views` int(11) DEFAULT '0' COMMENT '总浏览量',
  `sales_amount` int(11) DEFAULT '0' COMMENT '总销售额',
  `send_qr_code_status` int(11) DEFAULT '0' COMMENT '二维码寄送状态（0未申请，1已申请，2已寄送(扩展备用））',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `visittrackinfo`
--

DROP TABLE IF EXISTS `visittrackinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visittrackinfo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ForeignId` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `VisitType` int(11) DEFAULT NULL,
  `VisitTime` datetime NOT NULL,
  `UserAgent` text,
  `ClientAddress` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=951 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weixinuser`
--

DROP TABLE IF EXISTS `weixinuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weixinuser` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `subscribe` int(11) NOT NULL,
  `openId` varchar(50) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `sex` varchar(5) NOT NULL,
  `language` varchar(50) NOT NULL,
  `city` varchar(20) NOT NULL,
  `province` varchar(20) NOT NULL,
  `country` varchar(20) NOT NULL,
  `headImgUrl` varchar(200) NOT NULL,
  `subscribeTime` bigint(20) DEFAULT NULL,
  `unionId` varchar(50) DEFAULT NULL,
  `sexId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `westcache_flusher`
--

DROP TABLE IF EXISTS `westcache_flusher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `westcache_flusher` (
  `CACHE_KEY` varchar(1000) NOT NULL COMMENT 'cache key',
  `KEY_MATCH` varchar(20) NOT NULL DEFAULT 'full' COMMENT 'full:full match,prefix:prefix match',
  `MATCH_PRI` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'priority to match in descending way',
  `VALUE_VERSION` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'version of cache, increment it to update cache',
  `CACHE_STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'direct json value for the cache',
  `VALUE_TYPE` varchar(20) NOT NULL DEFAULT 'none' COMMENT 'value access type, direct: use direct json in DIRECT_VALUE field',
  `SPECS` varchar(1000) DEFAULT NULL COMMENT 'specs for extension',
  `DIRECT_VALUE` text,
  `CACHE_REMARK` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`CACHE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'fjjh'
--

--
-- Final view structure for view `aa`
--

/*!50001 DROP VIEW IF EXISTS `aa`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`lshd123`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `aa` AS (select `cityinfo`.`Name` AS `Name`,`cityinfo`.`Code` AS `Code`,`cityinfo`.`ParentCode` AS `ParentCode` from `cityinfo` where (`cityinfo`.`EnableFlag` = 'Y')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-07 14:35:24
