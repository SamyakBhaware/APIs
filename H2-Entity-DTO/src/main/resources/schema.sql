create table student (
  id int primary key auto_increment,
  name varchar(255) not null,
  age int not null,
  email varchar(255) not null unique,
  class_name varchar(255) not null
);

insert into student (name, age, email, class_name) values ('Alice', 20, 'alice@gmail.com', 'CS101');
insert into student (name, age, email, class_name) values ('Bob', 22, 'bob@gmail.com', 'CS102');
