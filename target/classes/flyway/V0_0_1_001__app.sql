
--应用表增加版本检查相关字段

alter table ipet_apps add user_name varchar(255);

alter table ipet_apps add version_code int default 0;

alter table ipet_apps add version_name varchar(255);

alter table ipet_apps add app_download_url varchar(500);