CREATE TABLE Payment_Methods (
Id int,
Payment_Method varchar(25),
CONSTRAINT PK_Payment_Methods PRIMARY KEY(Id)
);

CREATE TABLE EMIs_Master(
Id int,
Customer_Id int,
Loan_Plan_Id int,
EMI_Amount float(10),
EMI_Start date,
Number_Of_EMIs int,
Customer_Name varchar(25),
Customer_Phone varchar(10),
Customer_Address varchar(100),
Customer_PAN varchar(12),
EMI_Status varchar(10),
CONSTRAINT PK_EMIs_Master PRIMARY KEY(Id)
);

CREATE TABLE EMI_Payments(
Id int,
Amount float(10),
Payment_Date date,
Late_Fee float(10),
Payment_Method_Id int,
EMIs_Id int,
CONSTRAINT PK_EMI_Payments PRIMARY KEY(Id),
CONSTRAINT FK_Payment_Methods_EMI_Payments FOREIGN KEY(Payment_Method_Id) REFERENCES Payment_Methods(Id),
CONSTRAINT FK_EMIsMaster_EMI_Payments FOREIGN KEY(EMIs_Id) REFERENCES EMIs_Master(Id)
);
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL
);