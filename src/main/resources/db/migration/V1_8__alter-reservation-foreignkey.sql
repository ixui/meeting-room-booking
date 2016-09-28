alter table reservation modify column emp_no varchar(10);

alter table reservation drop foreign key reservation_ibfk_1;

alter table reservation add foreign key (emp_no) references emp_mst(emp_no) on update set null on delete set null;
