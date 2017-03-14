-- MySQL dump 10.13  Distrib 5.6.21, for debian-linux-gnu (x86_64)
--
-- Host: 10.0.10.132    Database: hlj_risk
-- ------------------------------------------------------
-- Server version	5.6.21-1~dotdeb.1-log

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
-- Table structure for table `action_script`
--

DROP TABLE IF EXISTS `action_script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_script` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `script_content` mediumtext NOT NULL,
  `remark` varchar(100) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `action_script_name_uk` (`name`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `base_column`
--

DROP TABLE IF EXISTS `base_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(50) NOT NULL COMMENT '显示名',
  `column_name` varchar(50) NOT NULL COMMENT '字段名',
  `column_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '字段类型',
  `event_type` varchar(20) NOT NULL DEFAULT '' COMMENT '事件类型',
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `black_list`
--

DROP TABLE IF EXISTS `black_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `black_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `gmt_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(2) NOT NULL COMMENT '状态 0新增 ，1 同步API数据库黑名单失败，2 同步API数据库黑名单成功',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  KEY `idx_gmt_created` (`gmt_created`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=20251 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collect_param_config`
--

DROP TABLE IF EXISTS `collect_param_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collect_param_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uri` varchar(50) NOT NULL COMMENT 'uri',
  `application_name` varchar(50) NOT NULL,
  `event_type` varchar(10) NOT NULL,
  `switch_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT ' 0 1 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collect_param_detail`
--

DROP TABLE IF EXISTS `collect_param_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collect_param_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(50) NOT NULL,
  `target` varchar(50) NOT NULL,
  `type` varchar(10) NOT NULL,
  `collect_param_config_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence_id` varchar(100) NOT NULL DEFAULT '' COMMENT '登陆账户',
  `account_login` varchar(50) NOT NULL DEFAULT '' COMMENT '登陆账户',
  `account_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '登陆手机',
  `ip` varchar(50) NOT NULL COMMENT '来源ip',
  `state` varchar(10) NOT NULL DEFAULT '' COMMENT '事件结果 0 失败,1 成功',
  `device_id` varchar(100) NOT NULL DEFAULT '' COMMENT '设备唯一id',
  `beacon_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'beacon_id',
  `event_type` varchar(10) NOT NULL DEFAULT '' COMMENT '事件类型',
  `event_detail` mediumtext COMMENT '事件内容',
  `score` int(10) DEFAULT '0' COMMENT '裁决得分',
  `decision` tinyint(1) DEFAULT '0' COMMENT '裁决结果,0 无,1 通过,2 待审核,3 拒绝',
  `decision_detail` mediumtext COMMENT '裁决结果详情',
  `decision_party` tinyint(1) DEFAULT '0' COMMENT '决策方 1同盾 2 河狸家',
  `user_agent` varchar(500) NOT NULL DEFAULT '',
  `device_type` tinyint(1) DEFAULT '0' COMMENT '设备类型 0 android,1 ios,2 web,3 无',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `event_data_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发送状态，0 未发送同盾,1 发送同盾失败,2 发送同盾成功',
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_account_login` (`account_login`),
  KEY `idx_account_mobile` (`account_mobile`),
  KEY `idx_sequence_id` (`sequence_id`),
  KEY `idx_ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=1418725 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_data`
--

DROP TABLE IF EXISTS `event_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account_login` varchar(50) DEFAULT NULL COMMENT '登陆账户',
  `type` varchar(50) NOT NULL COMMENT '事件类型',
  `message` mediumtext COMMENT '事件内容',
  `score` int(10) DEFAULT '0' COMMENT '评估得分',
  `decision` int(10) DEFAULT '0' COMMENT '评估结果',
  `decision_detail` mediumtext COMMENT '评估结果详情',
  `decision_party` tinyint(2) DEFAULT '0' COMMENT '决策方 1同盾 2 河狸家',
  `user_agent` varchar(500) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT NULL,
  `sys_platform` varchar(20) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_account_login` (`account_login`),
  KEY `idx_gmt_created` (`gmt_created`)
) ENGINE=InnoDB AUTO_INCREMENT=1195011 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT 'code',
  `name` varchar(50) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forensic_data`
--

DROP TABLE IF EXISTS `forensic_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forensic_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(20) NOT NULL COMMENT '内容',
  `type` tinyint(2) NOT NULL COMMENT '1 手机号，2 ip',
  `gmt_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `content` (`content`),
  KEY `idx_gmt_created` (`gmt_created`)
) ENGINE=InnoDB AUTO_INCREMENT=1137 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grab_data`
--

DROP TABLE IF EXISTS `grab_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grab_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(20) NOT NULL COMMENT '手机号',
  `status` tinyint(2) NOT NULL COMMENT '0 未抓取1 已抓取',
  `type` varchar(10) DEFAULT NULL COMMENT '1 手机号 2 ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=628296 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hit_rule`
--

DROP TABLE IF EXISTS `hit_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hit_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_seq_id` varchar(100) NOT NULL COMMENT '事件seq',
  `rule_id` int(11) NOT NULL COMMENT '规则id',
  `rule_name` varchar(50) NOT NULL COMMENT '规则名称',
  `score` int(11) NOT NULL COMMENT '规则评分',
  PRIMARY KEY (`id`),
  KEY `idx_event_seq_id` (`event_seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='事件命中规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip`
--

DROP TABLE IF EXISTS `ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_start` varchar(45) DEFAULT NULL COMMENT 'ip,',
  `ip_end` varchar(45) DEFAULT NULL COMMENT 'ip,ip,',
  `ip_start_num` bigint(20) DEFAULT NULL COMMENT 'ip,',
  `ip_end_num` bigint(20) DEFAULT NULL COMMENT 'ip,ip,',
  `continent` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `isp` varchar(45) DEFAULT NULL,
  `area_code` varchar(45) DEFAULT NULL,
  `country_english` varchar(45) DEFAULT NULL,
  `country_code` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ip_start_num` (`ip_start_num`),
  KEY `idx_ip_end_num` (`ip_end_num`),
  KEY `idx_ip_start` (`ip_start`),
  KEY `idx_ip_end` (`ip_end`)
) ENGINE=InnoDB AUTO_INCREMENT=196606 DEFAULT CHARSET=utf8mb4 COMMENT='ip';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `list_data`
--

DROP TABLE IF EXISTS `list_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_type` varchar(50) NOT NULL,
  `data_value` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `data_type` (`data_type`,`data_value`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `list_type`
--

DROP TABLE IF EXISTS `list_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `name_list`
--

DROP TABLE IF EXISTS `name_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `name_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) NOT NULL COMMENT '名单内容',
  `type` tinyint(1) NOT NULL COMMENT '名单类型, 1 手机号 2 ip 3 设备id 4 beacon_id',
  `grade` tinyint(1) NOT NULL COMMENT '名单等级, 1 黑名单 2 白名单 3 灰名单',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0新增 ，1 同步API数据库黑名单失败，2 同步API数据库黑名单成功,3 已删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `content` (`content`),
  KEY `idx_gmt_created` (`gmt_created`)
) ENGINE=InnoDB AUTO_INCREMENT=832923 DEFAULT CHARSET=utf8mb4 COMMENT='名单库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pref` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL COMMENT ' ',
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `isp` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `zip` varchar(45) DEFAULT NULL,
  `types` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_pref` (`pref`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=393211 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy`
--

DROP TABLE IF EXISTS `policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `event_type` varchar(50) NOT NULL,
  `event_id` varchar(50) NOT NULL,
  `device_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT ' 0 ,1 ios,2 ,3 unknow',
  `min_risk` int(11) NOT NULL,
  `max_risk` int(11) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `action_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `policy_set`
--

DROP TABLE IF EXISTS `policy_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `policy_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `policy_set_name` varchar(50) NOT NULL COMMENT '策略集名称',
  `event_type` varchar(50) NOT NULL COMMENT '事件类型',
  `event_id` varchar(50) NOT NULL COMMENT '事件标示',
  `app_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '应用类型 0 安卓,1 ios,2 网页',
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_info`
--

DROP TABLE IF EXISTS `risk_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risk_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `risk_type` varchar(50) NOT NULL,
  `fraud_risk_type` varchar(50) NOT NULL,
  `message` mediumtext,
  `fraud_response` mediumtext,
  `user_agent` varchar(500) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT NULL,
  `sys_platform` varchar(20) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1304620 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_term`
--

DROP TABLE IF EXISTS `risk_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risk_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(20) NOT NULL,
  `whole_word` tinyint(1) NOT NULL COMMENT ',, 1  0 ',
  `allow_skip` tinyint(1) NOT NULL COMMENT ', 1  0 ',
  `weight` int(11) NOT NULL DEFAULT '5',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '  ',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `word_type` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `word` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `policy_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `risk_weight` int(11) NOT NULL,
  `expression` varchar(1000) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint(1) NOT NULL DEFAULT '1',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `action_name` varchar(100) DEFAULT NULL,
  `decision_config` varchar(1000) DEFAULT NULL COMMENT 'decision cofnig',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secret_key`
--

DROP TABLE IF EXISTS `secret_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secret_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_version_begin` varchar(10) NOT NULL DEFAULT '' COMMENT '客户端开始版本号，格式x.x.x，如3.1.0',
  `app_version_end` varchar(10) NOT NULL DEFAULT '' COMMENT '客户端结束版本号，格式x.x.x',
  `device_type` tinyint(1) DEFAULT '0' COMMENT '设备类型 0 android,1 ios',
  `public_key` varchar(1000) NOT NULL DEFAULT '' COMMENT '公钥',
  `private_key` varchar(1000) NOT NULL DEFAULT '' COMMENT '私钥',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效 0 无效,1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_device_info`
--

DROP TABLE IF EXISTS `user_device_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(20) NOT NULL DEFAULT '' COMMENT '用户账号',
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户id',
  `user_type` tinyint(1) DEFAULT '0' COMMENT '用户类型 0 普通用户,1 手艺人',
  `device_type` tinyint(1) DEFAULT '0' COMMENT '设备OS类型 0 android,1 ios',
  `sign_ver` varchar(10) NOT NULL DEFAULT '' COMMENT '签名库版本',
  `device_id` varchar(100) NOT NULL DEFAULT '' COMMENT '设备唯一号',
  `cpu_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'cpu序列号',
  `cpu_mode` varchar(100) NOT NULL DEFAULT '' COMMENT 'cpu模式',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效 0 无效,1 有效',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_device` (`user_account`,`user_type`,`device_id`),
  KEY `idx_user_account` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=929031 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_pay_relation`
--

DROP TABLE IF EXISTS `user_pay_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_pay_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(40) NOT NULL COMMENT '用户id',
  `user_mobile` varchar(20) NOT NULL COMMENT '用户手机号',
  `pay_id` varchar(100) NOT NULL COMMENT '支付唯一id',
  `pay_type` tinyint(2) DEFAULT '1' COMMENT '1 支付宝 2 微信 3银联',
  `gmt_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`pay_id`),
  KEY `idx_gmt_created` (`gmt_created`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pay_id` (`pay_id`),
  KEY `idx_user_mobile` (`user_mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户支付唯一id映射';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-09 11:25:58
