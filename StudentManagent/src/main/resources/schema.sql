create table studentDB(
    id int primary key auto_increment,
    name varchar(255) not null,
    age int not null,
    email varchar(255) not null unique,
    password varchar(255) not null
);