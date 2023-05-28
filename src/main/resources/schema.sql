DROP TABLE IF EXISTS memo;
create table memo (
    id INT not null primary key auto_increment,
    text varchar(50) not null,
    created_at timestamp not null ,
    updated_at timestamp
);
DROP TABLE IF EXISTS diary;
create table diary(
    id int not null primary key auto_increment,
    weather varchar(50) not null,
    icon varchar(50) not null,
    temperature double not null,
    text varchar(500) not null,
    date Date not null,
    created_at timestamp not null ,
    updated_at timestamp
);
