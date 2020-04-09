/*
Navicat MySQL Data Transfer

Source Server         : project
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : wms

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-04-09 18:56:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bus_customer
-- ----------------------------
DROP TABLE IF EXISTS `bus_customer`;
CREATE TABLE `bus_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_customer
-- ----------------------------
INSERT INTO `bus_customer` VALUES ('1', '小张超市', '111', '武汉', '027-9123131', '张大明', '13812312312321312', '中国银行', '654431331343413', '213123@sina.com', '430000', '1');
INSERT INTO `bus_customer` VALUES ('2', '小明超市', '222', '深圳', '0755-9123131', '张小明', '13812312312321312', '中国银行', '654431331343413', '213123@sina.com', '430000', '1');
INSERT INTO `bus_customer` VALUES ('3', '快七超市', '430000', '武汉', '027-11011011', '雷生', '13434134131', '招商银行', '6543123341334133', '6666@66.com', '545341', '1');

-- ----------------------------
-- Table structure for bus_goods
-- ----------------------------
DROP TABLE IF EXISTS `bus_goods`;
CREATE TABLE `bus_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) DEFAULT NULL,
  `produceplace` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `goodspackage` varchar(255) DEFAULT NULL,
  `productcode` varchar(255) DEFAULT NULL,
  `promitcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `dangernum` int(11) DEFAULT NULL,
  `goodsimg` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_sq0btr2v2lq8gt8b4gb8tlk0i` (`providerid`) USING BTREE,
  CONSTRAINT `bus_goods_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_goods
-- ----------------------------
INSERT INTO `bus_goods` VALUES ('1', '娃哈哈', '武汉', '120ML', '瓶', 'PH12345', 'PZ1234', '小孩子都爱的', '2', '488', '10', '2018-12-25/userface1.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('2', '旺旺雪饼[小包]', '仙桃', '包', '袋', 'PH12312312', 'PZ12312', '好吃不上火', '4', '1100', '10', '2018-12-25/userface2.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('3', '旺旺大礼包', '仙桃', '盒', '盒', '11', '11', '111', '28', '1001', '100', '2018-12-25/userface3.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('4', '娃哈哈', '武汉', '200ML', '瓶', '11', '111', '12321', '3', '1100', '10', '2018-12-25/userface4.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('5', '娃哈哈', '武汉', '300ML', '瓶', '1234', '12321', '22221111', '3', '1000', '100', '2018-12-25/userface5.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('6', '212', '212', '212', '212', '212', '212', '212', '212', '121221', '212', '2020-02-29/C48FCE41197C41DFB9CADE30520E16C4.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('7', '312', '312', '312', '312', '312', '3123', '321', '312', '344', '312', '2020-02-29/C0402CB5F71D4BAFA5CD0CDA0B91EB5F.jpg', '1', '2');
INSERT INTO `bus_goods` VALUES ('8', 'eqw3', '3123', '23112', '3123', '312', '312', '3123', '312', '310', '312', '2020-02-29/87CBCB868B634506AA9FEF4D20C8976D.jpg', '1', '3');

-- ----------------------------
-- Table structure for bus_inport
-- ----------------------------
DROP TABLE IF EXISTS `bus_inport`;
CREATE TABLE `bus_inport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype` varchar(255) DEFAULT NULL,
  `inporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `inportprice` double DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_1o4bujsyd2kl4iqw48fie224v` (`providerid`) USING BTREE,
  KEY `FK_ho29poeu4o2dxu4rg1ammeaql` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`),
  CONSTRAINT `bus_inport_ibfk_2` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_inport
-- ----------------------------
INSERT INTO `bus_inport` VALUES ('1', '微信', '2018-05-07 00:00:00', '张三', '100', '备注', '3.5', '1', '1');
INSERT INTO `bus_inport` VALUES ('2', '支付宝', '2018-05-07 00:00:00', '张三', '1000', '无', '2.5', '3', '3');
INSERT INTO `bus_inport` VALUES ('3', '银联', '2018-05-07 00:00:00', '张三', '100', '1231', '111', '3', '3');
INSERT INTO `bus_inport` VALUES ('4', '银联', '2018-05-07 00:00:00', '张三', '1000', '无', '2', '3', '1');
INSERT INTO `bus_inport` VALUES ('5', '银联', '2018-05-07 00:00:00', '张三', '100', '无', '1', '3', '1');
INSERT INTO `bus_inport` VALUES ('6', '银联', '2018-05-07 00:00:00', '张三', '100', '1231', '2.5', '1', '2');
INSERT INTO `bus_inport` VALUES ('10', '支付宝', '2018-08-07 17:17:57', '超级管理员', '100', 'sadfasfdsa', '1.5', '3', '1');
INSERT INTO `bus_inport` VALUES ('11', '支付宝', '2018-09-17 16:12:25', '超级管理员', '21', '21', '21', '1', '3');
INSERT INTO `bus_inport` VALUES ('12', '微信', '2018-12-25 16:48:24', '超级管理员', '100', '123213213', '12321321', '3', '1');
INSERT INTO `bus_inport` VALUES ('14', '支付宝', '2020-02-29 13:48:27', '超级管理员', '32', '423423', '324', '2', '7');

-- ----------------------------
-- Table structure for bus_outport
-- ----------------------------
DROP TABLE IF EXISTS `bus_outport`;
CREATE TABLE `bus_outport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `outputtime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `outportprice` double(10,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_outport
-- ----------------------------
INSERT INTO `bus_outport` VALUES ('1', '3', '微信', '2019-08-16 08:19:50', '超级管理员', '12321321.00', '1', '', '1');
INSERT INTO `bus_outport` VALUES ('2', '3', '微信', '2019-08-16 08:26:54', '超级管理员', '12321321.00', '11', '11', '1');
INSERT INTO `bus_outport` VALUES ('3', '3', '银联', '2020-03-01 05:07:55', '超级管理员', '332.00', '3', '321312', '8');
INSERT INTO `bus_outport` VALUES ('4', '3', '银联', '2020-03-01 05:13:12', '超级管理员', '332.00', '3', '32321', '8');
INSERT INTO `bus_outport` VALUES ('5', '3', '微信', '2020-03-01 05:17:37', '超级管理员', '332.00', '1', '213', '8');

-- ----------------------------
-- Table structure for bus_provider
-- ----------------------------
DROP TABLE IF EXISTS `bus_provider`;
CREATE TABLE `bus_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_provider
-- ----------------------------
INSERT INTO `bus_provider` VALUES ('1', '旺旺食品', '434000', '仙桃', '0728-4124312', '小明', '13413441141', '中国农业银行', '654124345131', '12312321@sina.com', '5123123', '1');
INSERT INTO `bus_provider` VALUES ('2', '达利园食品', '430000', '汉川', '0728-4124312', '大明', '13413441141', '中国农业银行', '654124345131', '12312321@sina.com', '5123123', '1');
INSERT INTO `bus_provider` VALUES ('3', '娃哈哈集团', '513131', '杭州', '21312', '小晨', '12312', '建设银行', '512314141541', '123131', '312312', '1');

-- ----------------------------
-- Table structure for bus_sales
-- ----------------------------
DROP TABLE IF EXISTS `bus_sales`;
CREATE TABLE `bus_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salestime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `saleprice` decimal(10,2) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_32424syd2kl4iqw48fie224v` (`customerid`) USING BTREE,
  KEY `FK_h4324dsu4o2dxu4rg1ammeaql` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_3` FOREIGN KEY (`customerid`) REFERENCES `bus_customer` (`id`),
  CONSTRAINT `bus_inport_ibfk_4` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_sales
-- ----------------------------
INSERT INTO `bus_sales` VALUES ('2', '2', '支付宝', '2020-03-01 15:59:06', '张三', '100', '2132', '4.00', '2');
INSERT INTO `bus_sales` VALUES ('3', '2', '支付宝', '2020-03-01 09:31:03', '超级管理员', '21', '312321', '2312.00', '3');
INSERT INTO `bus_sales` VALUES ('4', '2', '支付宝', '2020-03-01 09:31:03', '超级管理员', '20', '312321', '2312.00', '3');

-- ----------------------------
-- Table structure for bus_salesback
-- ----------------------------
DROP TABLE IF EXISTS `bus_salesback`;
CREATE TABLE `bus_salesback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salesbacktime` datetime DEFAULT NULL,
  `salebackprice` double(10,2) DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_salesback
-- ----------------------------
INSERT INTO `bus_salesback` VALUES ('1', '1', '微信', '2020-03-01 10:30:52', '2.00', '超级管理员', '100', '232', '1');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码【为了调事显示顺序】',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '总经办', '1', '大BOSS', '深圳', '1', '1', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('2', '1', '销售部', '0', '程序员屌丝', '武汉', '1', '2', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('3', '1', '运营部', '0', '无', '武汉', '1', '3', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('4', '1', '生产部', '0', '无', '武汉', '1', '4', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('5', '2', '销售一部', '0', '销售一部', '武汉', '1', '5', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('6', '2', '销售二部', '0', '销售二部', '武汉', '1', '6', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('7', '3', '运营一部', '0', '运营一部', '武汉', '1', '7', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('8', '2', '销售三部', '0', '销售三部', '11', '1', '8', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('9', '2', '销售四部', '0', '销售四部', '222', '1', '9', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('10', '2', '销售五部', '0', '销售五部', '33', '1', '10', '2019-04-10 14:06:32');
INSERT INTO `sys_dept` VALUES ('18', '4', '生产一部', '0', '生产食品', '武汉', '1', '11', '2019-04-13 09:49:38');

-- ----------------------------
-- Table structure for sys_loginfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_loginfo`;
CREATE TABLE `sys_loginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `loginip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_loginfo
-- ----------------------------
INSERT INTO `sys_loginfo` VALUES ('2', '超级管理员-system', '127.0.0.1', '2018-12-21 16:54:52');
INSERT INTO `sys_loginfo` VALUES ('3', '超级管理员-system', '127.0.0.1', '2018-12-21 16:55:15');
INSERT INTO `sys_loginfo` VALUES ('4', '超级管理员-system', '127.0.0.1', '2018-12-21 17:29:22');
INSERT INTO `sys_loginfo` VALUES ('5', '超级管理员-system', '127.0.0.1', '2018-12-22 09:05:22');
INSERT INTO `sys_loginfo` VALUES ('6', '超级管理员-system', '127.0.0.1', '2018-12-22 09:20:43');
INSERT INTO `sys_loginfo` VALUES ('7', '超级管理员-system', '127.0.0.1', '2018-12-22 09:25:40');
INSERT INTO `sys_loginfo` VALUES ('8', '超级管理员-system', '127.0.0.1', '2018-12-22 09:27:11');
INSERT INTO `sys_loginfo` VALUES ('9', '超级管理员-system', '127.0.0.1', '2018-12-22 09:29:58');
INSERT INTO `sys_loginfo` VALUES ('10', '超级管理员-system', '127.0.0.1', '2018-12-22 09:36:49');
INSERT INTO `sys_loginfo` VALUES ('11', '超级管理员-system', '127.0.0.1', '2018-12-22 09:46:56');
INSERT INTO `sys_loginfo` VALUES ('12', '超级管理员-system', '127.0.0.1', '2018-12-22 09:48:29');
INSERT INTO `sys_loginfo` VALUES ('13', '超级管理员-system', '127.0.0.1', '2018-12-22 09:49:13');
INSERT INTO `sys_loginfo` VALUES ('14', '超级管理员-system', '127.0.0.1', '2018-12-22 09:49:57');
INSERT INTO `sys_loginfo` VALUES ('15', '超级管理员-system', '127.0.0.1', '2018-12-22 09:57:46');
INSERT INTO `sys_loginfo` VALUES ('16', '超级管理员-system', '127.0.0.1', '2018-12-22 10:21:53');
INSERT INTO `sys_loginfo` VALUES ('17', '超级管理员-system', '127.0.0.1', '2018-12-22 10:38:25');
INSERT INTO `sys_loginfo` VALUES ('18', '超级管理员-system', '127.0.0.1', '2018-12-22 10:49:21');
INSERT INTO `sys_loginfo` VALUES ('19', '超级管理员-system', '127.0.0.1', '2018-12-22 14:01:42');
INSERT INTO `sys_loginfo` VALUES ('20', '超级管理员-system', '127.0.0.1', '2018-12-22 14:19:48');
INSERT INTO `sys_loginfo` VALUES ('21', '超级管理员-system', '127.0.0.1', '2018-12-22 14:45:29');
INSERT INTO `sys_loginfo` VALUES ('22', '超级管理员-system', '127.0.0.1', '2018-12-22 15:58:05');
INSERT INTO `sys_loginfo` VALUES ('23', '超级管理员-system', '127.0.0.1', '2018-12-22 16:40:53');
INSERT INTO `sys_loginfo` VALUES ('24', '超级管理员-system', '127.0.0.1', '2018-12-22 17:12:01');
INSERT INTO `sys_loginfo` VALUES ('25', '超级管理员-system', '127.0.0.1', '2018-12-24 09:19:15');
INSERT INTO `sys_loginfo` VALUES ('26', '超级管理员-system', '127.0.0.1', '2018-12-24 09:37:28');
INSERT INTO `sys_loginfo` VALUES ('27', '超级管理员-system', '127.0.0.1', '2018-12-24 09:46:51');
INSERT INTO `sys_loginfo` VALUES ('28', '超级管理员-system', '127.0.0.1', '2018-12-24 09:50:40');
INSERT INTO `sys_loginfo` VALUES ('29', '超级管理员-system', '127.0.0.1', '2018-12-24 09:52:52');
INSERT INTO `sys_loginfo` VALUES ('30', '超级管理员-system', '127.0.0.1', '2018-12-24 10:00:26');
INSERT INTO `sys_loginfo` VALUES ('31', '超级管理员-system', '127.0.0.1', '2018-12-24 10:10:58');
INSERT INTO `sys_loginfo` VALUES ('32', '超级管理员-system', '127.0.0.1', '2018-12-24 10:21:28');
INSERT INTO `sys_loginfo` VALUES ('33', '超级管理员-system', '127.0.0.1', '2018-12-24 11:22:27');
INSERT INTO `sys_loginfo` VALUES ('34', '超级管理员-system', '127.0.0.1', '2018-12-24 11:35:28');
INSERT INTO `sys_loginfo` VALUES ('35', '超级管理员-system', '127.0.0.1', '2018-12-24 14:05:28');
INSERT INTO `sys_loginfo` VALUES ('36', '超级管理员-system', '127.0.0.1', '2018-12-24 15:16:29');
INSERT INTO `sys_loginfo` VALUES ('37', '李四-ls', '127.0.0.1', '2018-12-24 15:16:50');
INSERT INTO `sys_loginfo` VALUES ('38', '王五-ww', '127.0.0.1', '2018-12-24 15:17:36');
INSERT INTO `sys_loginfo` VALUES ('39', '赵六-zl', '127.0.0.1', '2018-12-24 15:17:47');
INSERT INTO `sys_loginfo` VALUES ('40', '孙七-sq', '127.0.0.1', '2018-12-24 15:17:58');
INSERT INTO `sys_loginfo` VALUES ('41', '刘八-lb', '127.0.0.1', '2018-12-24 15:18:09');
INSERT INTO `sys_loginfo` VALUES ('42', '超级管理员-system', '127.0.0.1', '2018-12-24 15:34:59');
INSERT INTO `sys_loginfo` VALUES ('43', '超级管理员-system', '127.0.0.1', '2018-12-24 15:35:09');
INSERT INTO `sys_loginfo` VALUES ('44', '刘八-lb', '127.0.0.1', '2018-12-24 15:36:09');
INSERT INTO `sys_loginfo` VALUES ('45', '刘八-lb', '127.0.0.1', '2018-12-24 15:42:58');
INSERT INTO `sys_loginfo` VALUES ('46', '刘八-lb', '127.0.0.1', '2018-12-24 15:43:04');
INSERT INTO `sys_loginfo` VALUES ('47', '超级管理员-system', '127.0.0.1', '2018-12-24 15:46:01');
INSERT INTO `sys_loginfo` VALUES ('48', '超级管理员-system', '127.0.0.1', '2018-12-24 17:03:54');
INSERT INTO `sys_loginfo` VALUES ('49', '超级管理员-system', '127.0.0.1', '2018-12-24 17:26:32');
INSERT INTO `sys_loginfo` VALUES ('50', '超级管理员-system', '127.0.0.1', '2018-12-25 09:07:42');
INSERT INTO `sys_loginfo` VALUES ('51', '超级管理员-system', '127.0.0.1', '2018-12-25 09:16:27');
INSERT INTO `sys_loginfo` VALUES ('52', '超级管理员-system', '127.0.0.1', '2018-12-25 09:59:03');
INSERT INTO `sys_loginfo` VALUES ('53', '超级管理员-system', '127.0.0.1', '2018-12-25 10:05:13');
INSERT INTO `sys_loginfo` VALUES ('54', '超级管理员-system', '127.0.0.1', '2018-12-25 10:05:47');
INSERT INTO `sys_loginfo` VALUES ('55', '超级管理员-system', '127.0.0.1', '2018-12-25 10:11:17');
INSERT INTO `sys_loginfo` VALUES ('56', '超级管理员-system', '127.0.0.1', '2018-12-25 10:14:06');
INSERT INTO `sys_loginfo` VALUES ('57', '超级管理员-system', '127.0.0.1', '2018-12-25 10:36:16');
INSERT INTO `sys_loginfo` VALUES ('58', '超级管理员-system', '127.0.0.1', '2018-12-25 14:17:21');
INSERT INTO `sys_loginfo` VALUES ('59', '超级管理员-system', '127.0.0.1', '2018-12-25 14:20:00');
INSERT INTO `sys_loginfo` VALUES ('60', '超级管理员-system', '127.0.0.1', '2018-12-25 14:34:22');
INSERT INTO `sys_loginfo` VALUES ('61', '超级管理员-system', '127.0.0.1', '2018-12-25 14:34:27');
INSERT INTO `sys_loginfo` VALUES ('62', '超级管理员-system', '127.0.0.1', '2018-12-25 14:38:37');
INSERT INTO `sys_loginfo` VALUES ('63', '超级管理员-system', '127.0.0.1', '2018-12-25 14:50:37');
INSERT INTO `sys_loginfo` VALUES ('64', '超级管理员-system', '127.0.0.1', '2018-12-25 16:01:35');
INSERT INTO `sys_loginfo` VALUES ('65', '超级管理员-system', '127.0.0.1', '2018-12-25 16:25:28');
INSERT INTO `sys_loginfo` VALUES ('66', '超级管理员-system', '127.0.0.1', '2018-12-25 16:27:37');
INSERT INTO `sys_loginfo` VALUES ('67', '超级管理员-system', '127.0.0.1', '2018-12-25 16:28:28');
INSERT INTO `sys_loginfo` VALUES ('68', '超级管理员-system', '127.0.0.1', '2018-12-25 16:44:02');
INSERT INTO `sys_loginfo` VALUES ('69', '超级管理员-system', '127.0.0.1', '2018-12-25 16:47:55');
INSERT INTO `sys_loginfo` VALUES ('70', '超级管理员-system', '127.0.0.1', '2018-12-28 15:59:34');
INSERT INTO `sys_loginfo` VALUES ('71', '超级管理员-system', '127.0.0.1', '2018-12-28 17:27:16');
INSERT INTO `sys_loginfo` VALUES ('72', '超级管理员-system', '127.0.0.1', '2018-12-28 17:29:40');
INSERT INTO `sys_loginfo` VALUES ('73', '超级管理员-system', '127.0.0.1', '2018-12-28 17:51:10');
INSERT INTO `sys_loginfo` VALUES ('74', '超级管理员-system', '127.0.0.1', '2019-04-15 17:05:02');
INSERT INTO `sys_loginfo` VALUES ('75', '超级管理员-system', '127.0.0.1', '2019-04-15 17:05:12');
INSERT INTO `sys_loginfo` VALUES ('76', '超级管理员-system', '127.0.0.1', '2019-04-15 17:10:22');
INSERT INTO `sys_loginfo` VALUES ('77', '刘八-lb', '127.0.0.1', '2019-04-15 17:11:45');
INSERT INTO `sys_loginfo` VALUES ('78', '刘八-lb', '127.0.0.1', '2019-04-15 17:28:50');
INSERT INTO `sys_loginfo` VALUES ('79', '超级管理员-system', '127.0.0.1', '2019-04-15 17:29:13');
INSERT INTO `sys_loginfo` VALUES ('80', '刘八-lb', '127.0.0.1', '2019-04-15 17:30:59');
INSERT INTO `sys_loginfo` VALUES ('81', '刘八-lb', '127.0.0.1', '2019-04-15 17:32:42');
INSERT INTO `sys_loginfo` VALUES ('82', '刘八-lb', '127.0.0.1', '2019-04-15 17:33:48');
INSERT INTO `sys_loginfo` VALUES ('83', '刘八-lb', '127.0.0.1', '2019-04-15 17:34:17');
INSERT INTO `sys_loginfo` VALUES ('84', '超级管理员-system', '127.0.0.1', '2019-04-15 17:36:40');
INSERT INTO `sys_loginfo` VALUES ('85', '超级管理员-system', '127.0.0.1', '2019-04-15 17:47:21');
INSERT INTO `sys_loginfo` VALUES ('86', '超级管理员-system', '127.0.0.1', '2019-04-15 17:54:10');
INSERT INTO `sys_loginfo` VALUES ('87', '超级管理员-system', '127.0.0.1', '2019-04-15 17:55:52');
INSERT INTO `sys_loginfo` VALUES ('88', '超级管理员-system', '127.0.0.1', '2019-04-16 09:26:01');
INSERT INTO `sys_loginfo` VALUES ('89', '超级管理员-system', '127.0.0.1', '2019-04-16 09:26:25');
INSERT INTO `sys_loginfo` VALUES ('90', '超级管理员-system', '127.0.0.1', '2019-04-16 09:46:54');
INSERT INTO `sys_loginfo` VALUES ('91', '超级管理员-system', '127.0.0.1', '2019-04-16 10:07:48');
INSERT INTO `sys_loginfo` VALUES ('92', '超级管理员-system', '127.0.0.1', '2019-04-16 10:10:30');
INSERT INTO `sys_loginfo` VALUES ('93', '超级管理员-system', '127.0.0.1', '2019-04-16 10:14:29');
INSERT INTO `sys_loginfo` VALUES ('94', '超级管理员-system', '127.0.0.1', '2019-04-16 10:15:04');
INSERT INTO `sys_loginfo` VALUES ('95', '超级管理员-system', '127.0.0.1', '2019-04-16 10:15:58');
INSERT INTO `sys_loginfo` VALUES ('96', '超级管理员-system', '127.0.0.1', '2019-04-16 10:28:14');
INSERT INTO `sys_loginfo` VALUES ('97', '超级管理员-system', '127.0.0.1', '2019-04-16 10:32:42');
INSERT INTO `sys_loginfo` VALUES ('98', '超级管理员-system', '127.0.0.1', '2019-04-16 10:33:03');
INSERT INTO `sys_loginfo` VALUES ('99', '超级管理员-system', '127.0.0.1', '2019-04-16 11:02:01');
INSERT INTO `sys_loginfo` VALUES ('100', '超级管理员-system', '127.0.0.1', '2019-04-16 11:03:09');
INSERT INTO `sys_loginfo` VALUES ('101', '超级管理员-system', '127.0.0.1', '2019-04-16 11:13:42');
INSERT INTO `sys_loginfo` VALUES ('102', '超级管理员-system', '127.0.0.1', '2019-04-16 11:24:55');
INSERT INTO `sys_loginfo` VALUES ('104', '超级管理员-system', '127.0.0.1', '2019-08-14 01:11:34');
INSERT INTO `sys_loginfo` VALUES ('105', '超级管理员-system', '127.0.0.1', '2019-08-14 01:24:03');
INSERT INTO `sys_loginfo` VALUES ('106', '李四-ls', '127.0.0.1', '2019-08-14 01:25:47');
INSERT INTO `sys_loginfo` VALUES ('107', '李四-ls', '127.0.0.1', '2019-08-14 01:26:41');
INSERT INTO `sys_loginfo` VALUES ('108', '孙七-sq', '127.0.0.1', '2019-08-14 01:28:22');
INSERT INTO `sys_loginfo` VALUES ('109', '刘八-lb', '127.0.0.1', '2019-08-14 01:28:32');
INSERT INTO `sys_loginfo` VALUES ('110', '超级管理员-system', '127.0.0.1', '2019-08-14 01:46:18');
INSERT INTO `sys_loginfo` VALUES ('111', '超级管理员-system', '127.0.0.1', '2019-08-14 02:18:44');
INSERT INTO `sys_loginfo` VALUES ('112', '超级管理员-system', '127.0.0.1', '2019-08-14 02:32:06');
INSERT INTO `sys_loginfo` VALUES ('113', '李四-ls', '127.0.0.1', '2019-08-14 03:00:19');
INSERT INTO `sys_loginfo` VALUES ('114', '李四-ls', '127.0.0.1', '2019-08-14 03:00:56');
INSERT INTO `sys_loginfo` VALUES ('115', '李四-ls', '127.0.0.1', '2019-08-14 03:01:31');
INSERT INTO `sys_loginfo` VALUES ('116', '李四-ls', '127.0.0.1', '2019-08-14 03:01:39');
INSERT INTO `sys_loginfo` VALUES ('117', '李四-ls', '127.0.0.1', '2019-08-14 03:02:25');
INSERT INTO `sys_loginfo` VALUES ('118', '李四-ls', '127.0.0.1', '2019-08-14 03:04:57');
INSERT INTO `sys_loginfo` VALUES ('119', '李四-ls', '127.0.0.1', '2019-08-14 03:07:19');
INSERT INTO `sys_loginfo` VALUES ('120', '李四-ls', '127.0.0.1', '2019-08-14 03:07:54');
INSERT INTO `sys_loginfo` VALUES ('121', '超级管理员-system', '127.0.0.1', '2019-08-14 03:13:06');
INSERT INTO `sys_loginfo` VALUES ('122', '李四-ls', '127.0.0.1', '2019-08-14 03:14:46');
INSERT INTO `sys_loginfo` VALUES ('123', '超级管理员-system', '127.0.0.1', '2019-08-14 06:03:47');
INSERT INTO `sys_loginfo` VALUES ('124', '超级管理员-system', '127.0.0.1', '2019-08-14 07:20:12');
INSERT INTO `sys_loginfo` VALUES ('125', '超级管理员-system', '127.0.0.1', '2019-08-14 07:23:05');
INSERT INTO `sys_loginfo` VALUES ('126', '超级管理员-system', '127.0.0.1', '2019-08-14 07:25:30');
INSERT INTO `sys_loginfo` VALUES ('127', '超级管理员-system', '127.0.0.1', '2019-08-14 07:26:34');
INSERT INTO `sys_loginfo` VALUES ('128', '超级管理员-system', '127.0.0.1', '2019-08-14 07:27:22');
INSERT INTO `sys_loginfo` VALUES ('129', '超级管理员-system', '127.0.0.1', '2019-08-14 07:27:51');
INSERT INTO `sys_loginfo` VALUES ('130', '超级管理员-system', '127.0.0.1', '2019-08-14 08:22:05');
INSERT INTO `sys_loginfo` VALUES ('131', '超级管理员-system', '127.0.0.1', '2019-08-14 08:43:53');
INSERT INTO `sys_loginfo` VALUES ('132', '超级管理员-system', '127.0.0.1', '2019-08-14 08:45:55');
INSERT INTO `sys_loginfo` VALUES ('133', '超级管理员-system', '127.0.0.1', '2019-08-14 08:47:13');
INSERT INTO `sys_loginfo` VALUES ('134', '超级管理员-system', '127.0.0.1', '2019-08-14 09:35:20');
INSERT INTO `sys_loginfo` VALUES ('135', '超级管理员-system', '127.0.0.1', '2019-08-14 09:41:02');
INSERT INTO `sys_loginfo` VALUES ('136', '超级管理员-system', '127.0.0.1', '2019-08-14 09:44:04');
INSERT INTO `sys_loginfo` VALUES ('137', '超级管理员-system', '127.0.0.1', '2019-08-14 09:50:27');
INSERT INTO `sys_loginfo` VALUES ('138', '超级管理员-system', '127.0.0.1', '2019-08-14 09:56:57');
INSERT INTO `sys_loginfo` VALUES ('139', '超级管理员-system', '127.0.0.1', '2019-08-14 09:59:02');
INSERT INTO `sys_loginfo` VALUES ('140', '超级管理员-system', '127.0.0.1', '2019-08-16 01:05:37');
INSERT INTO `sys_loginfo` VALUES ('141', '超级管理员-system', '127.0.0.1', '2019-08-16 02:01:44');
INSERT INTO `sys_loginfo` VALUES ('142', '超级管理员-system', '127.0.0.1', '2019-08-16 02:05:57');
INSERT INTO `sys_loginfo` VALUES ('143', '超级管理员-system', '127.0.0.1', '2019-08-16 02:07:04');
INSERT INTO `sys_loginfo` VALUES ('144', '超级管理员-system', '127.0.0.1', '2019-08-16 02:20:02');
INSERT INTO `sys_loginfo` VALUES ('145', '超级管理员-system', '127.0.0.1', '2019-08-16 02:20:20');
INSERT INTO `sys_loginfo` VALUES ('146', '超级管理员-system', '127.0.0.1', '2019-08-16 02:21:42');
INSERT INTO `sys_loginfo` VALUES ('147', '超级管理员-system', '127.0.0.1', '2019-08-16 03:37:37');
INSERT INTO `sys_loginfo` VALUES ('148', '超级管理员-system', '127.0.0.1', '2019-08-16 03:52:40');
INSERT INTO `sys_loginfo` VALUES ('149', '超级管理员-system', '127.0.0.1', '2019-08-16 03:59:37');
INSERT INTO `sys_loginfo` VALUES ('150', '超级管理员-system', '127.0.0.1', '2019-08-16 06:11:45');
INSERT INTO `sys_loginfo` VALUES ('151', '超级管理员-system', '127.0.0.1', '2019-08-16 06:23:27');
INSERT INTO `sys_loginfo` VALUES ('152', '超级管理员-system', '127.0.0.1', '2019-08-16 06:50:31');
INSERT INTO `sys_loginfo` VALUES ('153', '超级管理员-system', '127.0.0.1', '2019-08-16 06:54:49');
INSERT INTO `sys_loginfo` VALUES ('154', '超级管理员-system', '127.0.0.1', '2019-08-16 07:00:48');
INSERT INTO `sys_loginfo` VALUES ('155', '超级管理员-system', '127.0.0.1', '2019-08-16 07:01:18');
INSERT INTO `sys_loginfo` VALUES ('156', '超级管理员-system', '127.0.0.1', '2019-08-16 07:03:35');
INSERT INTO `sys_loginfo` VALUES ('157', '超级管理员-system', '127.0.0.1', '2019-08-16 07:09:55');
INSERT INTO `sys_loginfo` VALUES ('158', '超级管理员-system', '127.0.0.1', '2019-08-16 07:46:09');
INSERT INTO `sys_loginfo` VALUES ('159', '超级管理员-system', '127.0.0.1', '2019-08-16 08:17:59');
INSERT INTO `sys_loginfo` VALUES ('160', '超级管理员-system', '127.0.0.1', '2019-08-16 08:22:29');
INSERT INTO `sys_loginfo` VALUES ('161', '超级管理员-system', '127.0.0.1', '2019-08-16 08:23:32');
INSERT INTO `sys_loginfo` VALUES ('162', '超级管理员-system', '127.0.0.1', '2019-08-16 08:26:48');
INSERT INTO `sys_loginfo` VALUES ('163', '超级管理员-system', '127.0.0.1', '2019-08-16 08:31:19');
INSERT INTO `sys_loginfo` VALUES ('164', '超级管理员-system', '127.0.0.1', '2019-08-16 08:55:36');
INSERT INTO `sys_loginfo` VALUES ('165', '超级管理员-system', '127.0.0.1', '2019-08-16 08:55:59');
INSERT INTO `sys_loginfo` VALUES ('166', '超级管理员-system', '127.0.0.1', '2019-08-16 08:56:53');
INSERT INTO `sys_loginfo` VALUES ('167', '超级管理员-system', '127.0.0.1', '2019-08-16 08:57:42');
INSERT INTO `sys_loginfo` VALUES ('168', '超级管理员-system', '127.0.0.1', '2019-08-16 09:00:47');
INSERT INTO `sys_loginfo` VALUES ('169', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-01-28 12:42:19');
INSERT INTO `sys_loginfo` VALUES ('170', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-04 05:41:16');
INSERT INTO `sys_loginfo` VALUES ('171', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-04 05:53:51');
INSERT INTO `sys_loginfo` VALUES ('172', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-04 06:05:05');
INSERT INTO `sys_loginfo` VALUES ('173', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-04 06:09:22');
INSERT INTO `sys_loginfo` VALUES ('174', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-04 06:11:16');
INSERT INTO `sys_loginfo` VALUES ('175', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-07 12:46:39');
INSERT INTO `sys_loginfo` VALUES ('176', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-07 13:16:26');
INSERT INTO `sys_loginfo` VALUES ('177', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-07 13:24:32');
INSERT INTO `sys_loginfo` VALUES ('178', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-07 13:46:32');
INSERT INTO `sys_loginfo` VALUES ('179', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-10 03:20:22');
INSERT INTO `sys_loginfo` VALUES ('180', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-10 03:23:14');
INSERT INTO `sys_loginfo` VALUES ('181', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-16 11:19:57');
INSERT INTO `sys_loginfo` VALUES ('182', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-16 11:24:07');
INSERT INTO `sys_loginfo` VALUES ('183', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-16 11:56:25');
INSERT INTO `sys_loginfo` VALUES ('184', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-18 06:02:02');
INSERT INTO `sys_loginfo` VALUES ('185', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-20 03:53:24');
INSERT INTO `sys_loginfo` VALUES ('186', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-20 06:09:52');
INSERT INTO `sys_loginfo` VALUES ('187', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-20 06:13:14');
INSERT INTO `sys_loginfo` VALUES ('188', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-23 05:44:06');
INSERT INTO `sys_loginfo` VALUES ('189', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-23 09:38:22');
INSERT INTO `sys_loginfo` VALUES ('190', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-23 12:28:17');
INSERT INTO `sys_loginfo` VALUES ('191', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-23 12:37:38');
INSERT INTO `sys_loginfo` VALUES ('192', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-23 12:46:49');
INSERT INTO `sys_loginfo` VALUES ('193', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-24 04:56:34');
INSERT INTO `sys_loginfo` VALUES ('194', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-24 13:08:05');
INSERT INTO `sys_loginfo` VALUES ('195', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-25 06:56:06');
INSERT INTO `sys_loginfo` VALUES ('196', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-25 11:46:54');
INSERT INTO `sys_loginfo` VALUES ('197', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-25 12:15:06');
INSERT INTO `sys_loginfo` VALUES ('198', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 04:53:25');
INSERT INTO `sys_loginfo` VALUES ('199', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 05:59:16');
INSERT INTO `sys_loginfo` VALUES ('200', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 07:25:38');
INSERT INTO `sys_loginfo` VALUES ('201', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 07:32:04');
INSERT INTO `sys_loginfo` VALUES ('202', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 08:19:42');
INSERT INTO `sys_loginfo` VALUES ('203', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 08:24:49');
INSERT INTO `sys_loginfo` VALUES ('204', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 08:51:53');
INSERT INTO `sys_loginfo` VALUES ('205', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 08:58:25');
INSERT INTO `sys_loginfo` VALUES ('206', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 09:08:52');
INSERT INTO `sys_loginfo` VALUES ('207', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 09:17:25');
INSERT INTO `sys_loginfo` VALUES ('208', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 13:00:18');
INSERT INTO `sys_loginfo` VALUES ('209', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 13:50:43');
INSERT INTO `sys_loginfo` VALUES ('210', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 13:54:37');
INSERT INTO `sys_loginfo` VALUES ('211', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 13:55:42');
INSERT INTO `sys_loginfo` VALUES ('212', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 14:06:58');
INSERT INTO `sys_loginfo` VALUES ('213', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 14:17:13');
INSERT INTO `sys_loginfo` VALUES ('214', '下来-xl', '0:0:0:0:0:0:0:1', '2020-02-26 14:19:38');
INSERT INTO `sys_loginfo` VALUES ('215', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-26 14:21:49');
INSERT INTO `sys_loginfo` VALUES ('216', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 06:51:04');
INSERT INTO `sys_loginfo` VALUES ('217', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 07:11:05');
INSERT INTO `sys_loginfo` VALUES ('218', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 08:42:30');
INSERT INTO `sys_loginfo` VALUES ('219', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 09:23:38');
INSERT INTO `sys_loginfo` VALUES ('220', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 09:56:31');
INSERT INTO `sys_loginfo` VALUES ('221', '张三-zs', '0:0:0:0:0:0:0:1', '2020-02-27 09:58:44');
INSERT INTO `sys_loginfo` VALUES ('222', '孙七-sq', '0:0:0:0:0:0:0:1', '2020-02-27 09:59:36');
INSERT INTO `sys_loginfo` VALUES ('223', '李四-ls', '0:0:0:0:0:0:0:1', '2020-02-27 09:59:54');
INSERT INTO `sys_loginfo` VALUES ('224', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 10:01:59');
INSERT INTO `sys_loginfo` VALUES ('225', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 10:33:10');
INSERT INTO `sys_loginfo` VALUES ('226', '张三-zs', '0:0:0:0:0:0:0:1', '2020-02-27 10:33:58');
INSERT INTO `sys_loginfo` VALUES ('227', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 10:35:13');
INSERT INTO `sys_loginfo` VALUES ('228', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 11:21:05');
INSERT INTO `sys_loginfo` VALUES ('229', '张三-zs', '0:0:0:0:0:0:0:1', '2020-02-27 11:26:25');
INSERT INTO `sys_loginfo` VALUES ('230', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-27 11:27:11');
INSERT INTO `sys_loginfo` VALUES ('231', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:07:00');
INSERT INTO `sys_loginfo` VALUES ('232', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:16:58');
INSERT INTO `sys_loginfo` VALUES ('233', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:22:09');
INSERT INTO `sys_loginfo` VALUES ('234', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:25:05');
INSERT INTO `sys_loginfo` VALUES ('235', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:26:28');
INSERT INTO `sys_loginfo` VALUES ('236', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 08:30:32');
INSERT INTO `sys_loginfo` VALUES ('237', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 09:11:22');
INSERT INTO `sys_loginfo` VALUES ('238', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 09:27:32');
INSERT INTO `sys_loginfo` VALUES ('239', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 10:08:37');
INSERT INTO `sys_loginfo` VALUES ('240', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 10:27:27');
INSERT INTO `sys_loginfo` VALUES ('241', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 11:39:36');
INSERT INTO `sys_loginfo` VALUES ('242', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 12:05:46');
INSERT INTO `sys_loginfo` VALUES ('243', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 13:21:25');
INSERT INTO `sys_loginfo` VALUES ('244', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 14:20:55');
INSERT INTO `sys_loginfo` VALUES ('245', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 14:27:14');
INSERT INTO `sys_loginfo` VALUES ('246', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 14:43:51');
INSERT INTO `sys_loginfo` VALUES ('247', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 14:50:21');
INSERT INTO `sys_loginfo` VALUES ('248', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-28 14:52:27');
INSERT INTO `sys_loginfo` VALUES ('249', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 02:45:09');
INSERT INTO `sys_loginfo` VALUES ('250', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 04:04:45');
INSERT INTO `sys_loginfo` VALUES ('251', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 04:05:43');
INSERT INTO `sys_loginfo` VALUES ('252', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 06:53:19');
INSERT INTO `sys_loginfo` VALUES ('253', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 08:06:05');
INSERT INTO `sys_loginfo` VALUES ('254', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 08:38:06');
INSERT INTO `sys_loginfo` VALUES ('255', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 08:41:34');
INSERT INTO `sys_loginfo` VALUES ('256', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 09:02:12');
INSERT INTO `sys_loginfo` VALUES ('257', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 09:04:06');
INSERT INTO `sys_loginfo` VALUES ('258', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 10:41:27');
INSERT INTO `sys_loginfo` VALUES ('259', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 10:49:06');
INSERT INTO `sys_loginfo` VALUES ('260', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 10:57:57');
INSERT INTO `sys_loginfo` VALUES ('261', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 11:43:59');
INSERT INTO `sys_loginfo` VALUES ('262', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 11:56:21');
INSERT INTO `sys_loginfo` VALUES ('263', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 12:05:21');
INSERT INTO `sys_loginfo` VALUES ('264', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 12:11:18');
INSERT INTO `sys_loginfo` VALUES ('265', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 12:27:07');
INSERT INTO `sys_loginfo` VALUES ('266', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 12:49:07');
INSERT INTO `sys_loginfo` VALUES ('267', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 13:31:39');
INSERT INTO `sys_loginfo` VALUES ('268', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 13:47:53');
INSERT INTO `sys_loginfo` VALUES ('269', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 13:54:07');
INSERT INTO `sys_loginfo` VALUES ('270', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 13:59:50');
INSERT INTO `sys_loginfo` VALUES ('271', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-02-29 14:12:03');
INSERT INTO `sys_loginfo` VALUES ('272', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 05:02:40');
INSERT INTO `sys_loginfo` VALUES ('273', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 05:09:37');
INSERT INTO `sys_loginfo` VALUES ('274', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 05:12:41');
INSERT INTO `sys_loginfo` VALUES ('275', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 05:17:16');
INSERT INTO `sys_loginfo` VALUES ('276', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 07:12:59');
INSERT INTO `sys_loginfo` VALUES ('277', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 08:24:03');
INSERT INTO `sys_loginfo` VALUES ('278', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 09:09:49');
INSERT INTO `sys_loginfo` VALUES ('279', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 09:26:22');
INSERT INTO `sys_loginfo` VALUES ('280', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 10:29:57');
INSERT INTO `sys_loginfo` VALUES ('281', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 11:30:53');
INSERT INTO `sys_loginfo` VALUES ('282', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 11:44:52');
INSERT INTO `sys_loginfo` VALUES ('283', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 11:54:25');
INSERT INTO `sys_loginfo` VALUES ('284', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 12:04:31');
INSERT INTO `sys_loginfo` VALUES ('285', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 12:23:04');
INSERT INTO `sys_loginfo` VALUES ('286', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 12:27:48');
INSERT INTO `sys_loginfo` VALUES ('287', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 12:41:54');
INSERT INTO `sys_loginfo` VALUES ('288', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 13:04:38');
INSERT INTO `sys_loginfo` VALUES ('289', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 13:17:44');
INSERT INTO `sys_loginfo` VALUES ('290', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 13:25:03');
INSERT INTO `sys_loginfo` VALUES ('291', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 13:40:53');
INSERT INTO `sys_loginfo` VALUES ('292', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 14:04:16');
INSERT INTO `sys_loginfo` VALUES ('293', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 14:12:46');
INSERT INTO `sys_loginfo` VALUES ('294', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 14:20:54');
INSERT INTO `sys_loginfo` VALUES ('295', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 14:23:29');
INSERT INTO `sys_loginfo` VALUES ('296', '超级管理员-system', '127.0.0.1', '2020-03-01 14:42:06');
INSERT INTO `sys_loginfo` VALUES ('297', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 14:44:50');
INSERT INTO `sys_loginfo` VALUES ('298', '超级管理员-system', '127.0.0.1', '2020-03-01 14:55:24');
INSERT INTO `sys_loginfo` VALUES ('299', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:25:10');
INSERT INTO `sys_loginfo` VALUES ('300', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:34:22');
INSERT INTO `sys_loginfo` VALUES ('301', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:37:47');
INSERT INTO `sys_loginfo` VALUES ('302', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:41:44');
INSERT INTO `sys_loginfo` VALUES ('303', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:49:41');
INSERT INTO `sys_loginfo` VALUES ('304', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:53:32');
INSERT INTO `sys_loginfo` VALUES ('305', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 15:57:01');
INSERT INTO `sys_loginfo` VALUES ('306', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 16:01:02');
INSERT INTO `sys_loginfo` VALUES ('307', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-01 16:06:58');
INSERT INTO `sys_loginfo` VALUES ('308', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 04:58:43');
INSERT INTO `sys_loginfo` VALUES ('309', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:03:17');
INSERT INTO `sys_loginfo` VALUES ('310', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:09:12');
INSERT INTO `sys_loginfo` VALUES ('311', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:11:43');
INSERT INTO `sys_loginfo` VALUES ('312', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:34:32');
INSERT INTO `sys_loginfo` VALUES ('313', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:38:14');
INSERT INTO `sys_loginfo` VALUES ('314', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 05:39:54');
INSERT INTO `sys_loginfo` VALUES ('315', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:02:36');
INSERT INTO `sys_loginfo` VALUES ('316', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:04:28');
INSERT INTO `sys_loginfo` VALUES ('317', '超级管理员-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:05:28');
INSERT INTO `sys_loginfo` VALUES ('318', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:13:22');
INSERT INTO `sys_loginfo` VALUES ('319', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:18:43');
INSERT INTO `sys_loginfo` VALUES ('320', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:20:30');
INSERT INTO `sys_loginfo` VALUES ('321', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:26:15');
INSERT INTO `sys_loginfo` VALUES ('322', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:37:02');
INSERT INTO `sys_loginfo` VALUES ('323', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:45:13');
INSERT INTO `sys_loginfo` VALUES ('324', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:46:14');
INSERT INTO `sys_loginfo` VALUES ('325', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:50:15');
INSERT INTO `sys_loginfo` VALUES ('326', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:51:45');
INSERT INTO `sys_loginfo` VALUES ('327', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:53:10');
INSERT INTO `sys_loginfo` VALUES ('328', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:53:49');
INSERT INTO `sys_loginfo` VALUES ('329', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:55:54');
INSERT INTO `sys_loginfo` VALUES ('330', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 06:59:28');
INSERT INTO `sys_loginfo` VALUES ('331', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:01:08');
INSERT INTO `sys_loginfo` VALUES ('332', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:01:32');
INSERT INTO `sys_loginfo` VALUES ('333', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:02:29');
INSERT INTO `sys_loginfo` VALUES ('334', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:03:15');
INSERT INTO `sys_loginfo` VALUES ('335', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:15:55');
INSERT INTO `sys_loginfo` VALUES ('336', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:30:13');
INSERT INTO `sys_loginfo` VALUES ('337', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:32:43');
INSERT INTO `sys_loginfo` VALUES ('338', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:37:44');
INSERT INTO `sys_loginfo` VALUES ('339', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:42:06');
INSERT INTO `sys_loginfo` VALUES ('340', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-02 07:42:33');
INSERT INTO `sys_loginfo` VALUES ('341', '超级管理员大大-system', '0:0:0:0:0:0:0:1', '2020-03-28 05:21:00');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `createtime` datetime DEFAULT NULL,
  `opername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '关于系统V1.3更新公告', '2', '2018-08-07 00:00:00', '管理员');
INSERT INTO `sys_notice` VALUES ('10', '关于系统V1.2更新公告', '12312312<img src=\"/resources/layui/images/face/42.gif\" alt=\"[抓狂]\">', '2018-08-07 00:00:00', '超级管理员');
INSERT INTO `sys_notice` VALUES ('11', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2018-08-07 00:00:00', '超级管理员');
INSERT INTO `sys_notice` VALUES ('16', '13123113', 'wqeweqwe2', '2020-02-04 06:07:10', '超级管理员');
INSERT INTO `sys_notice` VALUES ('22', '', '', '2020-02-04 06:15:49', '超级管理员');
INSERT INTO `sys_notice` VALUES ('23', '', '', '2020-02-04 06:23:59', '超级管理员');
INSERT INTO `sys_notice` VALUES ('24', '', '', '2020-02-07 12:47:04', '超级管理员');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '权限类型[menu/permission]',
  `title` varchar(255) DEFAULT NULL,
  `percode` varchar(255) DEFAULT NULL COMMENT '权限编码[只有type= permission才有  user:view]',
  `icon` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', 'menu', '尚学堂进销存管理系统', null, '&#xe68e;', '', '', '1', '1', '1');
INSERT INTO `sys_permission` VALUES ('2', '1', 'menu', '基础管理', null, '&#xe857;', '', '', '0', '2', '1');
INSERT INTO `sys_permission` VALUES ('3', '1', 'menu', '进货管理', null, '&#xe645;', '', null, '0', '3', '1');
INSERT INTO `sys_permission` VALUES ('4', '1', 'menu', '销售管理', null, '&#xe611;', '', '', '0', '4', '1');
INSERT INTO `sys_permission` VALUES ('5', '1', 'menu', '系统管理', null, '&#xe614;', '', '', '0', '5', '1');
INSERT INTO `sys_permission` VALUES ('6', '1', 'menu', '其它管理', null, '&#xe628;', '', '', '0', '6', '1');
INSERT INTO `sys_permission` VALUES ('7', '2', 'menu', '客户管理', null, '&#xe651;', '/bus/toCustomerManager', '', '0', '7', '1');
INSERT INTO `sys_permission` VALUES ('8', '2', 'menu', '供应商管理', null, '&#xe658;', '/bus/toProviderManager', '', '0', '8', '1');
INSERT INTO `sys_permission` VALUES ('9', '2', 'menu', '商品管理', null, '&#xe657;', '/bus/toGoodsManager', '', '0', '9', '1');
INSERT INTO `sys_permission` VALUES ('10', '3', 'menu', '商品进货', null, '&#xe756;', '/bus/toInportManager', '', '0', '10', '1');
INSERT INTO `sys_permission` VALUES ('11', '3', 'menu', '商品退货查询', null, '&#xe65a;', '/bus/toOutportManager', '', '0', '11', '1');
INSERT INTO `sys_permission` VALUES ('12', '4', 'menu', '商品销售', null, '&#xe65b;', '/bus/toSaleManager', '', '0', '12', '1');
INSERT INTO `sys_permission` VALUES ('13', '4', 'menu', '销售退货查询', null, '&#xe770;', '/bus/toSaleBackManager', '', '0', '13', '1');
INSERT INTO `sys_permission` VALUES ('14', '5', 'menu', '部门管理', null, '&#xe770;', '/sys/toDeptManager', '', '0', '14', '1');
INSERT INTO `sys_permission` VALUES ('15', '5', 'menu', '菜单管理', null, '&#xe857;', '/sys/toMenuManager', '', '0', '15', '1');
INSERT INTO `sys_permission` VALUES ('16', '5', 'menu', '权限管理', '', '&#xe857;', '/sys/toPermissionManager', '', '0', '16', '1');
INSERT INTO `sys_permission` VALUES ('17', '5', 'menu', '角色管理', '', '&#xe650;', '/sys/toRoleInfo', '', '0', '17', '1');
INSERT INTO `sys_permission` VALUES ('18', '5', 'menu', '用户管理', '', '&#xe612;', '/sys/toUserManager', '', '0', '18', '1');
INSERT INTO `sys_permission` VALUES ('21', '6', 'menu', '登陆日志', null, '&#xe675;', '/sys/toLogInfo', '', '0', '21', '1');
INSERT INTO `sys_permission` VALUES ('22', '6', 'menu', '系统公告', null, '&#xe756;', '/sys/toNoticeInfo', null, '0', '22', '1');
INSERT INTO `sys_permission` VALUES ('23', '6', 'menu', '图标管理', null, '&#xe670;', '../resources/page/icon.html', null, '0', '23', '1');
INSERT INTO `sys_permission` VALUES ('30', '14', 'permission', '添加部门', 'dept:create', '', null, null, '0', '24', '1');
INSERT INTO `sys_permission` VALUES ('31', '14', 'permission', '修改部门', 'dept:update', '', null, null, '0', '26', '1');
INSERT INTO `sys_permission` VALUES ('32', '14', 'permission', '删除部门', 'dept:delete', '', null, null, '0', '27', '1');
INSERT INTO `sys_permission` VALUES ('34', '15', 'permission', '添加菜单', 'menu:create', '', '', '', '0', '29', '1');
INSERT INTO `sys_permission` VALUES ('35', '15', 'permission', '修改菜单', 'menu:update', '', null, null, '0', '30', '1');
INSERT INTO `sys_permission` VALUES ('36', '15', 'permission', '删除菜单', 'menu:delete', '', null, null, '0', '31', '1');
INSERT INTO `sys_permission` VALUES ('38', '16', 'permission', '添加权限', 'permission:create', '', null, null, '0', '33', '1');
INSERT INTO `sys_permission` VALUES ('39', '16', 'permission', '修改权限', 'permission:update', '', null, null, '0', '34', '1');
INSERT INTO `sys_permission` VALUES ('40', '16', 'permission', '删除权限', 'permission:delete', '', null, null, '0', '35', '1');
INSERT INTO `sys_permission` VALUES ('42', '17', 'permission', '添加角色', 'role:create', '', null, null, '0', '37', '1');
INSERT INTO `sys_permission` VALUES ('43', '17', 'permission', '修改角色', 'role:update', '', null, null, '0', '38', '1');
INSERT INTO `sys_permission` VALUES ('44', '17', 'permission', '角色删除', 'role:delete', '', null, null, '0', '39', '1');
INSERT INTO `sys_permission` VALUES ('46', '17', 'permission', '分配权限', 'role:selectPermission', '', null, null, '0', '41', '1');
INSERT INTO `sys_permission` VALUES ('47', '18', 'permission', '添加用户', 'user:create', '', 'inport', null, '0', '42', '1');
INSERT INTO `sys_permission` VALUES ('48', '18', 'permission', '修改用户', 'user:update', '', null, null, '0', '43', '1');
INSERT INTO `sys_permission` VALUES ('49', '18', 'permission', '删除用户', 'user:delete', '', null, null, '0', '44', '1');
INSERT INTO `sys_permission` VALUES ('51', '18', 'permission', '用户分配角色', 'user:selectRole', '', null, null, '0', '46', '1');
INSERT INTO `sys_permission` VALUES ('52', '18', 'permission', '重置密码', 'user:resetPwd', null, null, null, '0', '47', '1');
INSERT INTO `sys_permission` VALUES ('53', '14', 'permission', '部门查询', 'dept:view', null, null, null, '0', '48', '1');
INSERT INTO `sys_permission` VALUES ('54', '15', 'permission', '菜单查询', 'menu:view', null, null, null, '0', '49', '1');
INSERT INTO `sys_permission` VALUES ('55', '16', 'permission', '权限查询', 'permission:view', null, null, null, '0', '50', '1');
INSERT INTO `sys_permission` VALUES ('56', '17', 'permission', '角色查询', 'role:view', null, null, null, '0', '51', '1');
INSERT INTO `sys_permission` VALUES ('57', '18', 'permission', '用户查询', 'user:view', null, null, null, '0', '52', '1');
INSERT INTO `sys_permission` VALUES ('68', '7', 'permission', '客户查询', 'customer:view', null, null, null, null, '60', '1');
INSERT INTO `sys_permission` VALUES ('69', '7', 'permission', '客户添加', 'customer:create', null, null, null, null, '61', '1');
INSERT INTO `sys_permission` VALUES ('70', '7', 'permission', '客户修改', 'customer:update', null, null, null, null, '62', '1');
INSERT INTO `sys_permission` VALUES ('71', '7', 'permission', '客户删除', 'customer:delete', null, null, null, null, '63', '1');
INSERT INTO `sys_permission` VALUES ('73', '21', 'permission', '日志查询', 'info:view', null, null, null, null, '65', '1');
INSERT INTO `sys_permission` VALUES ('74', '21', 'permission', '日志删除', 'info:delete', null, null, null, null, '66', '1');
INSERT INTO `sys_permission` VALUES ('75', '21', 'permission', '日志批量删除', 'info:batchdelete', null, null, null, null, '67', '1');
INSERT INTO `sys_permission` VALUES ('76', '22', 'permission', '公告查询', 'notice:view', null, null, null, null, '68', '1');
INSERT INTO `sys_permission` VALUES ('77', '22', 'permission', '公告添加', 'notice:create', null, null, null, null, '69', '1');
INSERT INTO `sys_permission` VALUES ('78', '22', 'permission', '公告修改', 'notice:update', null, null, null, null, '70', '1');
INSERT INTO `sys_permission` VALUES ('79', '22', 'permission', '公告删除', 'notice:delete', null, null, null, null, '71', '1');
INSERT INTO `sys_permission` VALUES ('81', '8', 'permission', '供应商查询', 'provider:view', null, null, null, null, '73', '1');
INSERT INTO `sys_permission` VALUES ('82', '8', 'permission', '供应商添加', 'provider:create', null, null, null, null, '74', '1');
INSERT INTO `sys_permission` VALUES ('83', '8', 'permission', '供应商修改', 'provider:update', null, null, null, null, '75', '1');
INSERT INTO `sys_permission` VALUES ('84', '8', 'permission', '供应商删除', 'provider:delete', null, null, null, null, '76', '1');
INSERT INTO `sys_permission` VALUES ('86', '22', 'permission', '公告查看', 'notice:viewnotice', null, null, null, null, '78', '1');
INSERT INTO `sys_permission` VALUES ('91', '9', 'permission', '商品查询', 'goods:view', null, null, null, '0', '79', '1');
INSERT INTO `sys_permission` VALUES ('92', '9', 'permission', '商品添加', 'goods:create', null, null, null, '0', '80', '1');
INSERT INTO `sys_permission` VALUES ('97', '9', 'permission', '商品删除', 'goods:delete', null, null, null, '0', '81', '1');
INSERT INTO `sys_permission` VALUES ('98', '6', 'menu', '缓存管理', null, '&#xe656;', '/sys/toCacheManager', '', '1', '82', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '拥有所有菜单权限', '1', '2019-04-10 14:06:32');
INSERT INTO `sys_role` VALUES ('4', '基础数据管理员', '基础数据管理员', '1', '2019-04-10 14:06:32');
INSERT INTO `sys_role` VALUES ('5', '仓库管理员', '仓库管理员', '1', '2019-04-10 14:06:32');
INSERT INTO `sys_role` VALUES ('6', '销售管理员', '销售管理员', '1', '2019-04-10 14:06:32');
INSERT INTO `sys_role` VALUES ('7', '系统管理员', '系统管理员', '1', '2019-04-10 14:06:32');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`rid`) USING BTREE,
  KEY `FK_tcsr9ucxypb3ce1q5qngsfk33` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '3');
INSERT INTO `sys_role_permission` VALUES ('1', '4');
INSERT INTO `sys_role_permission` VALUES ('1', '5');
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('1', '7');
INSERT INTO `sys_role_permission` VALUES ('1', '8');
INSERT INTO `sys_role_permission` VALUES ('1', '9');
INSERT INTO `sys_role_permission` VALUES ('1', '10');
INSERT INTO `sys_role_permission` VALUES ('1', '11');
INSERT INTO `sys_role_permission` VALUES ('1', '12');
INSERT INTO `sys_role_permission` VALUES ('1', '13');
INSERT INTO `sys_role_permission` VALUES ('1', '14');
INSERT INTO `sys_role_permission` VALUES ('1', '15');
INSERT INTO `sys_role_permission` VALUES ('1', '16');
INSERT INTO `sys_role_permission` VALUES ('1', '17');
INSERT INTO `sys_role_permission` VALUES ('1', '18');
INSERT INTO `sys_role_permission` VALUES ('1', '21');
INSERT INTO `sys_role_permission` VALUES ('1', '22');
INSERT INTO `sys_role_permission` VALUES ('1', '23');
INSERT INTO `sys_role_permission` VALUES ('1', '31');
INSERT INTO `sys_role_permission` VALUES ('1', '34');
INSERT INTO `sys_role_permission` VALUES ('1', '35');
INSERT INTO `sys_role_permission` VALUES ('1', '36');
INSERT INTO `sys_role_permission` VALUES ('1', '38');
INSERT INTO `sys_role_permission` VALUES ('1', '39');
INSERT INTO `sys_role_permission` VALUES ('1', '40');
INSERT INTO `sys_role_permission` VALUES ('1', '42');
INSERT INTO `sys_role_permission` VALUES ('1', '43');
INSERT INTO `sys_role_permission` VALUES ('1', '44');
INSERT INTO `sys_role_permission` VALUES ('1', '46');
INSERT INTO `sys_role_permission` VALUES ('1', '47');
INSERT INTO `sys_role_permission` VALUES ('1', '48');
INSERT INTO `sys_role_permission` VALUES ('1', '49');
INSERT INTO `sys_role_permission` VALUES ('1', '51');
INSERT INTO `sys_role_permission` VALUES ('1', '52');
INSERT INTO `sys_role_permission` VALUES ('1', '53');
INSERT INTO `sys_role_permission` VALUES ('1', '54');
INSERT INTO `sys_role_permission` VALUES ('1', '55');
INSERT INTO `sys_role_permission` VALUES ('1', '56');
INSERT INTO `sys_role_permission` VALUES ('1', '57');
INSERT INTO `sys_role_permission` VALUES ('1', '68');
INSERT INTO `sys_role_permission` VALUES ('1', '69');
INSERT INTO `sys_role_permission` VALUES ('1', '70');
INSERT INTO `sys_role_permission` VALUES ('1', '71');
INSERT INTO `sys_role_permission` VALUES ('1', '73');
INSERT INTO `sys_role_permission` VALUES ('1', '74');
INSERT INTO `sys_role_permission` VALUES ('1', '75');
INSERT INTO `sys_role_permission` VALUES ('1', '76');
INSERT INTO `sys_role_permission` VALUES ('1', '77');
INSERT INTO `sys_role_permission` VALUES ('1', '78');
INSERT INTO `sys_role_permission` VALUES ('1', '79');
INSERT INTO `sys_role_permission` VALUES ('1', '81');
INSERT INTO `sys_role_permission` VALUES ('1', '82');
INSERT INTO `sys_role_permission` VALUES ('1', '83');
INSERT INTO `sys_role_permission` VALUES ('1', '84');
INSERT INTO `sys_role_permission` VALUES ('1', '86');
INSERT INTO `sys_role_permission` VALUES ('1', '91');
INSERT INTO `sys_role_permission` VALUES ('1', '92');
INSERT INTO `sys_role_permission` VALUES ('4', '1');
INSERT INTO `sys_role_permission` VALUES ('4', '2');
INSERT INTO `sys_role_permission` VALUES ('4', '5');
INSERT INTO `sys_role_permission` VALUES ('4', '6');
INSERT INTO `sys_role_permission` VALUES ('4', '7');
INSERT INTO `sys_role_permission` VALUES ('4', '8');
INSERT INTO `sys_role_permission` VALUES ('4', '9');
INSERT INTO `sys_role_permission` VALUES ('4', '14');
INSERT INTO `sys_role_permission` VALUES ('4', '15');
INSERT INTO `sys_role_permission` VALUES ('4', '16');
INSERT INTO `sys_role_permission` VALUES ('4', '17');
INSERT INTO `sys_role_permission` VALUES ('4', '18');
INSERT INTO `sys_role_permission` VALUES ('4', '21');
INSERT INTO `sys_role_permission` VALUES ('4', '22');
INSERT INTO `sys_role_permission` VALUES ('4', '23');
INSERT INTO `sys_role_permission` VALUES ('4', '30');
INSERT INTO `sys_role_permission` VALUES ('4', '31');
INSERT INTO `sys_role_permission` VALUES ('4', '32');
INSERT INTO `sys_role_permission` VALUES ('4', '34');
INSERT INTO `sys_role_permission` VALUES ('4', '35');
INSERT INTO `sys_role_permission` VALUES ('4', '36');
INSERT INTO `sys_role_permission` VALUES ('4', '38');
INSERT INTO `sys_role_permission` VALUES ('4', '39');
INSERT INTO `sys_role_permission` VALUES ('4', '40');
INSERT INTO `sys_role_permission` VALUES ('4', '42');
INSERT INTO `sys_role_permission` VALUES ('4', '43');
INSERT INTO `sys_role_permission` VALUES ('4', '44');
INSERT INTO `sys_role_permission` VALUES ('4', '46');
INSERT INTO `sys_role_permission` VALUES ('4', '47');
INSERT INTO `sys_role_permission` VALUES ('4', '48');
INSERT INTO `sys_role_permission` VALUES ('4', '49');
INSERT INTO `sys_role_permission` VALUES ('4', '51');
INSERT INTO `sys_role_permission` VALUES ('4', '52');
INSERT INTO `sys_role_permission` VALUES ('4', '53');
INSERT INTO `sys_role_permission` VALUES ('4', '54');
INSERT INTO `sys_role_permission` VALUES ('4', '55');
INSERT INTO `sys_role_permission` VALUES ('4', '56');
INSERT INTO `sys_role_permission` VALUES ('4', '57');
INSERT INTO `sys_role_permission` VALUES ('4', '68');
INSERT INTO `sys_role_permission` VALUES ('4', '69');
INSERT INTO `sys_role_permission` VALUES ('4', '70');
INSERT INTO `sys_role_permission` VALUES ('4', '71');
INSERT INTO `sys_role_permission` VALUES ('4', '73');
INSERT INTO `sys_role_permission` VALUES ('4', '74');
INSERT INTO `sys_role_permission` VALUES ('4', '75');
INSERT INTO `sys_role_permission` VALUES ('4', '76');
INSERT INTO `sys_role_permission` VALUES ('4', '77');
INSERT INTO `sys_role_permission` VALUES ('4', '78');
INSERT INTO `sys_role_permission` VALUES ('4', '79');
INSERT INTO `sys_role_permission` VALUES ('4', '81');
INSERT INTO `sys_role_permission` VALUES ('4', '82');
INSERT INTO `sys_role_permission` VALUES ('4', '83');
INSERT INTO `sys_role_permission` VALUES ('4', '84');
INSERT INTO `sys_role_permission` VALUES ('4', '86');
INSERT INTO `sys_role_permission` VALUES ('4', '91');
INSERT INTO `sys_role_permission` VALUES ('4', '92');
INSERT INTO `sys_role_permission` VALUES ('5', '1');
INSERT INTO `sys_role_permission` VALUES ('5', '5');
INSERT INTO `sys_role_permission` VALUES ('5', '6');
INSERT INTO `sys_role_permission` VALUES ('5', '14');
INSERT INTO `sys_role_permission` VALUES ('5', '15');
INSERT INTO `sys_role_permission` VALUES ('5', '16');
INSERT INTO `sys_role_permission` VALUES ('5', '17');
INSERT INTO `sys_role_permission` VALUES ('5', '18');
INSERT INTO `sys_role_permission` VALUES ('5', '21');
INSERT INTO `sys_role_permission` VALUES ('5', '22');
INSERT INTO `sys_role_permission` VALUES ('5', '23');
INSERT INTO `sys_role_permission` VALUES ('5', '30');
INSERT INTO `sys_role_permission` VALUES ('5', '31');
INSERT INTO `sys_role_permission` VALUES ('5', '32');
INSERT INTO `sys_role_permission` VALUES ('5', '34');
INSERT INTO `sys_role_permission` VALUES ('5', '35');
INSERT INTO `sys_role_permission` VALUES ('5', '36');
INSERT INTO `sys_role_permission` VALUES ('5', '38');
INSERT INTO `sys_role_permission` VALUES ('5', '39');
INSERT INTO `sys_role_permission` VALUES ('5', '40');
INSERT INTO `sys_role_permission` VALUES ('5', '42');
INSERT INTO `sys_role_permission` VALUES ('5', '43');
INSERT INTO `sys_role_permission` VALUES ('5', '44');
INSERT INTO `sys_role_permission` VALUES ('5', '46');
INSERT INTO `sys_role_permission` VALUES ('5', '47');
INSERT INTO `sys_role_permission` VALUES ('5', '48');
INSERT INTO `sys_role_permission` VALUES ('5', '49');
INSERT INTO `sys_role_permission` VALUES ('5', '51');
INSERT INTO `sys_role_permission` VALUES ('5', '52');
INSERT INTO `sys_role_permission` VALUES ('5', '53');
INSERT INTO `sys_role_permission` VALUES ('5', '54');
INSERT INTO `sys_role_permission` VALUES ('5', '55');
INSERT INTO `sys_role_permission` VALUES ('5', '56');
INSERT INTO `sys_role_permission` VALUES ('5', '57');
INSERT INTO `sys_role_permission` VALUES ('5', '73');
INSERT INTO `sys_role_permission` VALUES ('5', '74');
INSERT INTO `sys_role_permission` VALUES ('5', '75');
INSERT INTO `sys_role_permission` VALUES ('5', '76');
INSERT INTO `sys_role_permission` VALUES ('5', '77');
INSERT INTO `sys_role_permission` VALUES ('5', '78');
INSERT INTO `sys_role_permission` VALUES ('5', '79');
INSERT INTO `sys_role_permission` VALUES ('5', '86');
INSERT INTO `sys_role_permission` VALUES ('6', '1');
INSERT INTO `sys_role_permission` VALUES ('6', '4');
INSERT INTO `sys_role_permission` VALUES ('6', '6');
INSERT INTO `sys_role_permission` VALUES ('6', '12');
INSERT INTO `sys_role_permission` VALUES ('6', '13');
INSERT INTO `sys_role_permission` VALUES ('6', '21');
INSERT INTO `sys_role_permission` VALUES ('6', '22');
INSERT INTO `sys_role_permission` VALUES ('6', '23');
INSERT INTO `sys_role_permission` VALUES ('6', '73');
INSERT INTO `sys_role_permission` VALUES ('6', '74');
INSERT INTO `sys_role_permission` VALUES ('6', '75');
INSERT INTO `sys_role_permission` VALUES ('6', '76');
INSERT INTO `sys_role_permission` VALUES ('6', '77');
INSERT INTO `sys_role_permission` VALUES ('6', '78');
INSERT INTO `sys_role_permission` VALUES ('6', '79');
INSERT INTO `sys_role_permission` VALUES ('6', '86');
INSERT INTO `sys_role_permission` VALUES ('7', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '5');
INSERT INTO `sys_role_permission` VALUES ('7', '6');
INSERT INTO `sys_role_permission` VALUES ('7', '14');
INSERT INTO `sys_role_permission` VALUES ('7', '15');
INSERT INTO `sys_role_permission` VALUES ('7', '16');
INSERT INTO `sys_role_permission` VALUES ('7', '17');
INSERT INTO `sys_role_permission` VALUES ('7', '18');
INSERT INTO `sys_role_permission` VALUES ('7', '21');
INSERT INTO `sys_role_permission` VALUES ('7', '31');
INSERT INTO `sys_role_permission` VALUES ('7', '32');
INSERT INTO `sys_role_permission` VALUES ('7', '36');
INSERT INTO `sys_role_permission` VALUES ('7', '39');
INSERT INTO `sys_role_permission` VALUES ('7', '43');
INSERT INTO `sys_role_permission` VALUES ('7', '52');
INSERT INTO `sys_role_permission` VALUES ('7', '53');
INSERT INTO `sys_role_permission` VALUES ('7', '74');
INSERT INTO `sys_role_permission` VALUES ('7', '75');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `FK_203gdpkwgtow7nxlo2oap5jru` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '2');
INSERT INTO `sys_role_user` VALUES ('4', '3');
INSERT INTO `sys_role_user` VALUES ('4', '5');
INSERT INTO `sys_role_user` VALUES ('5', '4');
INSERT INTO `sys_role_user` VALUES ('5', '6');
INSERT INTO `sys_role_user` VALUES ('6', '10');
INSERT INTO `sys_role_user` VALUES ('7', '11');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '1',
  `ordernum` int(11) DEFAULT NULL,
  `type` int(255) DEFAULT NULL COMMENT '用户类型[0超级管理员1，管理员，2普通用户]',
  `imgpath` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_3rrcpvho2w1mx1sfiuuyir1h` (`deptid`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`deptid`) REFERENCES `sys_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '超级管理员大大', 'system', '系统深处的女人', '1', '超级管理员', '8f1bb89098788abc753cabe8bc96575e', '1', '2018-06-25 11:06:34', null, '1', '1', '0', '/resources/images/02DA19982B374A79BF94B60ED64BD9AA.jpg', '76AC54E118834D8791AFA9BBA15DA379');
INSERT INTO `sys_user` VALUES ('2', '李四', 'ls', '武汉', '0', 'KING', 'b07b848d69e0553b80e601d31571797e', '1', '2018-06-25 11:06:36', null, '1', '2', '1', '/resources/images/userface2.jpg', 'FC1EE06AE4354D3FBF7FDD15C8FCDA71');
INSERT INTO `sys_user` VALUES ('3', '王五', 'ww', '武汉', '1', '管理员', '3c3f971eae61e097f59d52360323f1c8', '3', '2018-06-25 11:06:38', '2', '1', '3', '1', '/resources/images/userface3.jpg', '3D5F956E053C4E85B7D2681386E235D2');
INSERT INTO `sys_user` VALUES ('4', '赵六', 'zl', '武汉', '1', '程序员', '2e969742a7ea0c7376e9551d578e05dd', '4', '2018-06-25 11:06:40', '3', '1', '4', '1', '/resources/images/userface4.jpg', '6480EE1391E34B0886ACADA501E31145');
INSERT INTO `sys_user` VALUES ('5', '孙七', 'sq', '武汉', '1', '程序员', '47b4c1ad6e4b54dd9387a09cb5a03de1', '2', '2018-06-25 11:06:43', '4', '1', '5', '1', '/resources/images/userface5.jpg', 'FE3476C3E3674E5690C737C269FCBF8E');
INSERT INTO `sys_user` VALUES ('6', '刘八', 'lb', '深圳', '1', '程序员', 'bcee2b05b4b591106829aec69a094806', '4', '2018-08-06 11:21:12', '3', '1', '6', '1', '/resources/images/defaultusertitle.jpg', 'E6CCF54A09894D998225878BBD139B20');
INSERT INTO `sys_user` VALUES ('10', '小刘', 'xl', '上海', '1', '的说法是电风扇', '4f1e7d115f5b32310e3a9aefbb7ca374', '18', '2020-02-27 06:57:11', '3', '1', '7', '1', null, '6583D0245A7C4EFAAA12E7BD5837C376');
INSERT INTO `sys_user` VALUES ('11', '张三', 'zs', '北京', '0', '成熟度成熟度', '9ad0ae2921cfcdc941d477cf7813efcb', '2', '2020-02-27 06:58:05', '3', '0', '8', '1', null, '3286DE8A95814DA2A897263B83B00C52');
