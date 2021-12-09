create table data_type
(
    id smallserial
        constraint data_type_pk
            primary key,
    array_int integer[],
    array_text text[]
);

comment on table data_type is '数据类型';

comment on column data_type.array_int is '数值数组';

comment on column data_type.array_text is '文本数组';

alter table data_type owner to postgres;

