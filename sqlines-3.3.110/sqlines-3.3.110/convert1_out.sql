-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE profile  (
    id int PRIMARY KEY,
    bytea_column blob,
    integer_column integer,
    numeric_column numeric(10, 2),
    decimal_column decimal(8, 4)
);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE company.employees (
    employee_id int PRIMARY KEY,  
    first_name VARCHAR(50),         
    last_name VARCHAR(50),         
    age integer,                   
    salary real,                    
    bonus numeric(8, 2),            
    is_active boolean,              
    profile_picture blob,          
    hire_date datetime              
);

