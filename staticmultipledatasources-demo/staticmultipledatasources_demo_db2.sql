CREATE DATABASE db2;

use db2;

CREATE TABLE task
(
    id INT NOT NULL COMMENT '主键ID',
    name VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
)CHARSET=utf8 ENGINE=InnoDB COMMENT '任务表';

INSERT INTO task (id, name, age, email) VALUES
(1, '李白', 18, 'test1@baomidou.com'),
(2, '露娜', 20, 'test2@baomidou.com'),
(3, '韩信', 28, 'test3@baomidou.com'),
(4, '橘右京', 21, 'test4@baomidou.com'),
(5, '百里玄刺', 24, 'test5@baomidou.com');

select * from task;