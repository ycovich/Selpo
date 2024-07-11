create schema if not exists catalog;

create table catalog.t_product
(
    id        serial primary key,
    c_title   varchar(50) not null check (length(trim(c_title)) >= 2),
    c_details varchar(1000)
);