create table department
(
    id integer not null
        constraint department_pkey
            primary key,
    dept char(50) not null,
    emp_id integer not null
);

alter table department owner to postgres;

