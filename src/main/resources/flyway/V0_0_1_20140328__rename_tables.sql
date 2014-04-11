-- rename tables
alter table ipet_users rename users;
alter table ipet_follow_relations rename user_relation;
alter table ipet_friend_relations rename user_friend;
alter table ipet_user_profiles rename user_profile;
alter table ipet_user_settings rename user_setting;
alter table ipet_last_locations rename user_last_location;
alter table ipet_shops rename shop;
alter table ipet_photos rename photo;
alter table ipet_comments rename comment;
alter table ipet_favors rename favor;
alter table ipet_feedbacks rename feedback;
alter table ipet_apps rename app;
