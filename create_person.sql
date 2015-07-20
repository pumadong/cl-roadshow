#BEGIN*************************表单列表***************************BEGIN
#1. student
#2. teacher
#END***************************表单列表***************************END


#学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
`name`  varchar(50) NOT NULL DEFAULT '' COMMENT '姓名' ,
`teacher_id`  int(11) NOT NULL DEFAULT 0 COMMENT '老师ID' ,
`score`  int(11) NOT NULL DEFAULT 0 COMMENT '分数' ,
`create_person`  varchar(30) NOT NULL DEFAULT '' COMMENT '记录生成人' ,
`create_date`  datetime NOT NULL COMMENT '记录生成时间' ,
INDEX `idx_student_name` (`name`) USING BTREE ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表'
;

INSERT INTO `student`(id,name,teacher_id,score,create_person,create_date) 
VALUES
(1,'张三','1',90,'system',NOW()),
(2,'李四','2',95,'system',NOW()),
(3,'王五','2',80,'system',NOW())
;

#老师表
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
`name`  varchar(50) NOT NULL DEFAULT '' COMMENT '姓名' ,
`create_person`  varchar(30) NOT NULL DEFAULT '' COMMENT '记录生成人' ,
`create_date`  datetime NOT NULL COMMENT '记录生成时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='老师表'
;

INSERT INTO `teacher`(id,name,create_person,create_date) 
VALUES
(1,'张老师','system',NOW()),
(2,'王老师','system',NOW()),
(3,'李老师','system',NOW())
;