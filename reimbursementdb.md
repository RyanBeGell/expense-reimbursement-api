# Reimbursement Database
![Reimbursement_db_erd.png](https://i.imgur.com/ZLrnX7j.png)

### Database implementation of business rules

1. All expenses have a single employee as the issuer
   * emp_id is a  foreign key that refers to the employee table's primary key (employee_id) to satisfy this constraint
  

2. Expenses may be approved by one employee
   * approved_by_emp_id from the expense table also refers to employee_id to satisfy this constraint
  

3. Only employees which have admin authorization can approve or deny expenses
   * the admin boolean variable from the employee table will be used to check for authorization


4. Expenses start as pending (null) and will be approved or denied 
   * approval_status from the expense table will be null upon initialization, and upon approval or denial set to true or false respectively 

### PostgreSQL create table statements

~~~postgresql 
create table employee(
employee_id serial primary key, 
first_name varchar(50),
last_name varchar(50),
phone_number varchar(12), 
email varchar(50),
admin boolean
);

create table expense(
expense_id serial primary key,
amount decimal,
emp_id int references employee(employee_id),
approval_status boolean,
approved_by_emp_id int references employee(employee_id),
expense_date date
);
~~~
