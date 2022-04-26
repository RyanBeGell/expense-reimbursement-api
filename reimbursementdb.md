# Reimbursement Database
![Reimbursement_db_erd.png](https://i.imgur.com/VoTXjaT.png)

### PostgreSQL create table statements
~~~postgresql 
create table employee(
employee_id serial primary key, 
first_name varchar(20),
last_name varchar(20)
);

create table expense(
expense_id serial primary key,
amount decimal,
emp_id int references employee(employee_id),
approval varchar(10),
expense_date bigInt,
expense_description varchar(50)
);
~~~
