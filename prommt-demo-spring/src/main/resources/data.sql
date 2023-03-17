INSERT INTO PAYMENT(created_date, payer_email, status, currency, amount, paid_date) VALUES (PARSEDATETIME('04/05/2023', 'dd/MM/yyyy'), 'pepe@prommt.com', 'CREATED', 'USD', 12, null);
INSERT INTO PAYMENT(created_date, payer_email, status, currency, amount, paid_date) VALUES (PARSEDATETIME('06/07/2023', 'dd/MM/yyyy'), 'jose@prommt.com', 'PAID', 'EUR', 22, PARSEDATETIME('07/08/2023', 'dd/MM/yyyy'));
