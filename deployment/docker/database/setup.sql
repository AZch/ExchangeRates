CREATE DATABASE todo_db;

USE todo_db;

create table if not exists  rate (
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


create table if not exists rate_action (
    id      int auto_increment,
    major   varchar(5)   not null,
    minor   varchar(5)   not null,
    date    datetime     not null,
    action  varchar(200) not null,
    strategy varchar(200) not null,
    rate double not null,
    constraint rate_id_uindex unique (id)
);

alter table rate_action add primary key (id);

create table if not exists cup_rate_point (
    id      int auto_increment,
    major   varchar(5) not null,
    minor   varchar(5) not null,
    high    double     not null,
    low     double     not null,
    open    double     not null,
    close   double     not null,
    start   datetime   not null,
    end     datetime   not null,
    constraint cup_rate_point_id_uindex unique (id)
);

alter table cup_rate_point add primary key (id);