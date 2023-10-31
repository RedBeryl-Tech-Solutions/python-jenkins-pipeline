CREATE TABLE profile  (
    id serial PRIMARY KEY,
    bytea_column bytea,
    integer_column integer,
    numeric_column numeric(10, 2),
    decimal_column decimal(8, 4)
);


CREATE TABLE company.employees (
    employee_id serial PRIMARY KEY,  
    first_name VARCHAR(50),         
    last_name VARCHAR(50),         
    age integer,                   
    salary real,                    
    bonus numeric(8, 2),            
    is_active boolean,              
    profile_picture bytea,          
    hire_date timestamp              
);

