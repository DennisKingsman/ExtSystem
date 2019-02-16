drop table if exists cr_address_person;
drop table if exists cr_person;
drop table if exists cr_address;
drop table if exists cr_district;
drop table if exists cr_street;



create table cr_district(
    district_code integer not null,
    district_name varchar(300),
    primary key(district_code)
);

insert into cr_district(district_code, district_name)
values (1, 'Выборгский район');


create table cr_street(
    street_code integer not null,
    street_name varchar(300),
    primary key (street_code)
);

insert into cr_street(street_code, street_name)
values (1, 'Сампсоньевский проспект');


create table cr_address(
    address_id SERIAL,
    district_code integer not null,
    street_code integer not null,
    building varchar(10) not null,
    extension varchar(10),
    apartment varchar(10),
    primary key(address_id),
    foreign key(district_code) references cr_district(district_code) on delete restrict,
    foreign key(street_code) references cr_street(street_code) on delete restrict
);

insert into cr_address(district_code, street_code, building, extension, apartment)
values (1, 1, '10', '2', '121');


create table cr_person(
    person_id SERIAL,
    sur_name varchar(100) not null,
    given_name varchar(100) not null,
    patronymic varchar(100) not null,
    date_of_birth date not null,
    passport_seria varchar(10),
    passport_number varchar(10),
    passport_date date,
    certificate_number varchar(10),
    certificate_date date,
    primary key(person_id)
);

insert into cr_person (sur_name, given_name, patronymic, date_of_birth,
passport_seria, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильев', 'Павел', 'Николаевич', '1995-03-18' ,'1234', '123456', '2015-04-11', null, null);

insert into cr_person (sur_name, given_name, patronymic, date_of_birth,
passport_seria, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильева', 'Ирина', 'Петровна', '1997-08-21' ,'4321', '6543321', '2017-05-22', null, null);

insert into cr_person (sur_name, given_name, patronymic, date_of_birth,
passport_seria, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильев', 'Евгения', 'Павловна', '2016-01-18' ,null , null, null, '654123', '2016-01-27');

insert into cr_person (sur_name, given_name, patronymic, date_of_birth,
passport_seria, passport_number, passport_date, certificate_number, certificate_date)
values ('Васильев', 'Александр', 'Павлович', '2018-03-15' ,null, null, null, '456123', '2018-03-22');


create table cr_address_person(
    person_address_id SERIAL,
    address_id integer not null,
    person_id integer not null,
    start_date date not null,
    end_date date,
    primary key(person_address_id),
    foreign key(address_id) references cr_address(address_id) on delete restrict,
    foreign key(person_id) references cr_person(person_id) on delete restrict
);

insert into cr_address_person (address_id, person_id, start_date, end_date)
values (1, 1, '2014-10-12', null);

insert into cr_address_person (address_id, person_id, start_date, end_date)
values (1, 2, '2014-10-12', null);

insert into cr_address_person (address_id, person_id, start_date, end_date)
values (1, 3, '2016-02-05', null);

insert into cr_address_person (address_id, person_id, start_date, end_date)
values (1, 4, '2018-04-01', null);