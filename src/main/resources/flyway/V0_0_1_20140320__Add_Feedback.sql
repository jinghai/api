-- 新增Feedback表

drop table if exists ipet_feedbacks;
create table ipet_feedbacks (
	id int primary key auto_increment,
	title varchar(50),
	content text,
	contact varchar(200),
	created_by varchar(40),
	created_at timestamp default current_timestamp
) engine=innodb default charset=utf8;
