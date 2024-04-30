INSERT INTO Payment_Methods (Id, Payment_Method)
VALUES
    (1, 'Card'),
    (2, 'NetBanking'),
    (3, 'UPI')
;
INSERT INTO EMIs_Master (Id, Customer_Id, Loan_Plan_Id, EMI_Amount, EMI_Start, Number_Of_EMIs, Customer_Name, Customer_Phone, Customer_Address, Customer_PAN, EMI_Status)
VALUES
    (1, 101, 201, 1000.00, '2024-02-28', 12, 'John Doe', '1234567890', '123 Main St', 'ABCDE1234F', 'Paid'),
    (2, 102, 202, 1500.00, '2024-02-28', 24, 'Jane Smith', '9876543210', '456 Elm St', 'FGHIJ5678K', 'Pending')
;
INSERT INTO EMI_Payments (Id, Amount, Payment_Date, Late_Fee, Payment_Method_Id, EMIs_Id)
VALUES
    (1, 1000.00, '2024-03-15', 50.00, 1, 1),
    (2, 1500.00, '2024-03-20', 75.00, 2, 2)
;
INSERT INTO users(username, password, role) VALUES
 ('admin', 'myPassword', 'Bank_Manager'),
 ('user', 'yourPassword', 'Customer')
;
