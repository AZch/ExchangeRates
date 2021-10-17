CREATE DATABASE todo_db;

USE todo_db;

create table rate (
    id       int auto_increment,
    major    varchar(5)  not null,
    minor    varchar(5)  not null,
    sell     double      not null,
    buy      double      not null,
    created  datetime    not null,
    resource varchar(70) null,
    constraint rate_id_uindex unique (id)
);

alter table rate add primary key (id);


create table rate_action (
    id      int auto_increment,
    major   varchar(5)   not null,
    minor   varchar(5)   not null,
    sell    double       not null,
    buy     double       not null,
    created datetime     not null,
    action  varchar(200) not null,
    constraint rate_id_uindex unique (id)
);

alter table rate_action add primary key (id);
