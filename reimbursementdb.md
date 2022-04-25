# Reimbursement Database
![Reimbursement_db_erd.png](https://i.imgur.com/3AulF07.png)

### Database implementation of business rules

1. All expenses have a single employee as the issuer
   * emp_id is a  foreign key that refers to the employee table's primary key (employee_id) to satisfy this constraint
  

2. Expenses may be approved by one employee
   * approved_by_emp_id from the expense table also refers to employee_id to satisfy this constraint
  
   
3. Expenses start as pending (null) and will be approved or denied 
   * approval_status from the expense table will be null upon initialization, and upon approval or denial set to true or false respectively 

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
is_approved boolean,
expense_date bigInt,
expense_description varchar(50)
);
~~~
