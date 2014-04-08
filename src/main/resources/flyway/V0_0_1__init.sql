
    drop table if exists ipet_apps;

    drop table if exists ipet_comments;

    drop table if exists ipet_favors;

    drop table if exists ipet_follow_relations;

    drop table if exists ipet_friend_relations;

    drop table if exists ipet_last_locations;

    drop table if exists ipet_photos;

    drop table if exists ipet_shops;

    drop table if exists ipet_user_profiles;

    drop table if exists ipet_user_settings;

    drop table if exists ipet_users;

    create table ipet_apps (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        app_key varchar(255),
        app_secret varchar(255),
        name varchar(255),
        type integer,
        user_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_comments (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        photo_id varchar(255),
        text varchar(255),
        user_id varchar(255),
        user_name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_favors (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        photo_id varchar(255),
        text varchar(255),
        user_id varchar(255),
        user_name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_follow_relations (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        user_ida varchar(255),
        user_idb varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_friend_relations (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        user_ida varchar(255),
        user_idb varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_last_locations (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        address varchar(255),
        geo_hash varchar(255),
        latitude bigint,
        longitude bigint,
        user_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_photos (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        avatar48 varchar(255),
        comment_count int default 0,
        favor_count int default 0,
        forword_from_id varchar(255),
        address varchar(255),
        geo_hash varchar(255),
        latitude bigint,
        longitude bigint,
        originalurl varchar(255),
        smallurl varchar(255),
        text varchar(255),
        user_id varchar(255),
        user_name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_shops (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        address varchar(255),
        geo_hash varchar(255),
        latitude bigint,
        longitude bigint,
        name varchar(255),
        user_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_user_profiles (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        user_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_user_settings (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        user_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    create table ipet_users (
        id varchar(40) not null,
        create_at datetime,
        update_at datetime,
        app_count int default 0,
        avatar32 varchar(255),
        avatar48 varchar(255),
        comment_count int default 0,
        display_name varchar(50),
        email varchar(50),
        favor_count int default 0,
        follow_count int default 0,
        follower_count int default 0,
        friend_count int default 0,
        last_location_id bigint,
        login_name varchar(50),
        login_num bigint default 0,
        password varchar(50),
        phone varchar(15),
        photo_count int default 0,
        roles varchar(255),
        salt varchar(50),
        shop_count int default 0,
        user_profile_id bigint,
        user_setting_id bigint,
        user_state integer,
        primary key (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    alter table ipet_apps
        add constraint UK_n3db6ibibxrvdkuvfeggqp1p3  unique (app_key);

    create index ipet_apps_userId on ipet_apps (user_id);

    create index ipet_comments_userId on ipet_comments (user_id);

    create index ipet_comments_photoId on ipet_comments (photo_id);

    create index ipet_favors_userId on ipet_favors (user_id);

    create index ipet_favors_photoId on ipet_favors (photo_id);

    alter table ipet_follow_relations
        add constraint UK_5val2n14c08yjy92eytji7v4a  unique (user_ida, user_idb);

    alter table ipet_friend_relations
        add constraint UK_4jdvsegda2r1jnct3iv3wcjvn  unique (user_ida, user_idb);

    create index ipet_last_locations_userId on ipet_last_locations (user_id);

    create index ipet_photos_createAt on ipet_photos (create_at);

    create index ipet_photos_updateAt on ipet_photos (update_at);

    create index ipet_photos_forwordFromId on ipet_photos (forword_from_id);

    create index ipet_photos_userId on ipet_photos (user_id);

    create index ipet_shops_userId on ipet_shops (user_id);

    create index ipet_user_profiles_userId on ipet_user_profiles (user_id);

    create index ipet_user_settings_userId on ipet_user_settings (user_id);

    alter table ipet_users
        add constraint UK_dnwtfsjk7ueifg64j25i6vvb2  unique (email);

    alter table ipet_users
        add constraint UK_ltr9wv46rty0dy3py0f6g8g7g  unique (login_name);

    alter table ipet_users
        add constraint UK_rrlm55vyyb0hvm29w1ifl7ilp  unique (phone);

    create index ipet_users_createAt on ipet_users (create_at);

    create index ipet_users_updateAt on ipet_users (update_at);

    create index ipet_users_userProfileId on ipet_users (user_profile_id);

    create index ipet_users_userSettingId on ipet_users (user_setting_id);

    create index ipet_users_lastLocationId on ipet_users (last_location_id);
