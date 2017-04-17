INSERT INTO CURRENCY(NAME,CODE) VALUES('eur','001');
INSERT INTO CURRENCY(NAME,CODE) VALUES('rsd','002');
INSERT INTO CURRENCY(NAME,CODE) VALUES('km','003');

INSERT INTO COUNTRY(CODE, NAME) VALUES('srb','Srbija');
INSERT INTO COUNTRY(CODE, NAME) VALUES('bih','Bosna i Hercegovina');

INSERT INTO COUNTRY_CURRENCIES(COUNTRY_ID,CURRENCY_ID) VALUES(1,1);
INSERT INTO COUNTRY_CURRENCIES(COUNTRY_ID,CURRENCY_ID) VALUES(1,2);
INSERT INTO COUNTRY_CURRENCIES(COUNTRY_ID,CURRENCY_ID) VALUES(2,3); 

INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE) VALUES(1,1,1,1);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE) VALUES(2,2,2,2);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE) VALUES(3,3,3,3);

INSERT INTO EXCHANGE_RATE(DATE,START_DATE,NUMBER_OF_EXCHANGE_RATE) VALUES('2016-03-03','2016-03-03',1);
INSERT INTO EXCHANGE_RATE(DATE,START_DATE,NUMBER_OF_EXCHANGE_RATE) VALUES('2016-03-03','2016-03-03',2);

INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY) VALUES(1,1);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY) VALUES(1,2);


INSERT INTO BANK(NAME,CODE,SWIFT_CODE,CLEARING_ACCOUNT) VALUES('bank1','123','12345678',123456789123456789);
INSERT INTO BANK(NAME,CODE,SWIFT_CODE,CLEARING_ACCOUNT) VALUES('bank2','456','88344696',543216789123456987);

INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE) VALUES('123852369874512365',true,'2016-11-11');
INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE) VALUES('456752378574512664',true,'2016-12-21');
INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE) VALUES('123758369874575393',true,'2016-03-03');

INSERT INTO BANK_BILLS(BANK_ID,BILL_ID) VALUES(1,2);

INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('1','1','1','1',1);
INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('1@1','1','1','1',1);
INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('2','2','1','1',1);



INSERT INTO CODE_BOOK_ACTIVITIES (CODE, NAME) VALUES(12365,'djelatnost1');
INSERT INTO CODE_BOOK_ACTIVITIES (CODE, NAME) VALUES(75893,'djelatnost2');


INSERT INTO CLIENT(APPLICANT,JMBG,ADDRESS, PHONE, FAX, MAIL, DELIVERY_ADDRESS,DELIVERY_BY_MAIL, TYPE, SHORT_NAME,PIB, MIB, TAX_AUTHORITY, RESPONSIBLE_PERSON) VALUES('Firma 2',14785,'adresa2',null, 06585963, 'firma22@gmail.com','Mise Dimitrijevica 34', true, 'PRAVNO', null, null, null, null, null);




INSERT INTO POPULATED_PLACE(NAME, PTT_CODE) VALUES('Novi Sad', '21000');
INSERT INTO POPULATED_PLACE_COUNTRY(POPULATED_PLACE_ID, COUNTRY_ID) VALUES(1, 1);

INSERT INTO BANK_EXCHANGE_RATE(BANK_ID,EXCHANGE_RATE_ID) VALUES(1,1);
INSERT INTO BANK_EXCHANGE_RATE(BANK_ID,EXCHANGE_RATE_ID) VALUES(1,2);



