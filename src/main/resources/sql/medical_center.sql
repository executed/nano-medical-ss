create database medical_center;

create table client
(
  id         uuid default uuid_generate_v4() not null
    constraint client_pkey
    primary key,
  first_name text,
  last_name  text
);

create unique index client_id_uindex
  on client (id);

create table doctor
(
  id         uuid default uuid_generate_v4() not null
    constraint doctor_pkey
    primary key,
  first_name text,
  last_name  text
);

create unique index doctor_id_uindex
  on doctor (id);

create table doctor_config
(
  id                uuid      not null
    constraint doctor_config_clone_pkey
    primary key
    constraint doctor_config_clone_doctor_id_fk
    references doctor
    on delete cascade,
  start_work        timestamp not null,
  end_work          timestamp not null,
  max_app_duration  integer,
  max_app_not_fixed boolean   not null
);

create unique index doctor_config_clone_id_uindex
  on doctor_config (id);

create table time_slot
(
  id         uuid default uuid_generate_v4() not null
    constraint time_slot_pkey
    primary key,
  client_id  uuid                            not null
    constraint time_slot_client_id_fk
    references client
    on delete cascade,
  doctor_id  uuid                            not null
    constraint time_slot_doctor_id_fk
    references doctor
    on delete cascade,
  start_time timestamp                       not null,
  end_time   timestamp                       not null
);

create unique index time_slot_id_uindex
  on time_slot (id);

create table client_config
(
  id       uuid         not null
    constraint client_config_client_id_fk
    references client
    on delete cascade,
  email    varchar(100) not null,
  password text         not null,
  admin    boolean      not null,
  username text
);

create unique index client_config_id_uindex
  on client_config (id);

create unique index client_config_email_uindex
  on client_config (email);

create unique index client_config_username_uindex
  on client_config (username);

create function uuid_nil()
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_nil
;

create function uuid_ns_dns()
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_ns_dns
;

create function uuid_ns_url()
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_ns_url
;

create function uuid_ns_oid()
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_ns_oid
;

create function uuid_ns_x500()
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_ns_x500
;

create function uuid_generate_v1()
strict
parallel safe
language c
as
-- missing source code for uuid_generate_v1
;

create function uuid_generate_v1mc()
strict
parallel safe
language c
as
-- missing source code for uuid_generate_v1mc
;

create function uuid_generate_v3(namespace uuid, name text)
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_generate_v3
;

create function uuid_generate_v4()
strict
parallel safe
language c
as
-- missing source code for uuid_generate_v4
;

create function uuid_generate_v5(namespace uuid, name text)
immutable
strict
parallel safe
language c
as
-- missing source code for uuid_generate_v5
;


