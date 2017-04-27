INSERT INTO CURRENCY(NAME,CODE) VALUES('Euro','eur');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Dinar','rs');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Konv marka','km');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Dolar','dol');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Phound','phn');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Juan','chy');
INSERT INTO CURRENCY(NAME,CODE) VALUES('Jen','jen');

INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('srb','Serbia',2);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('bih','Bosnia',3);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('ger','Germany',1);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('usa','USA',5);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('gbr','Great Britain',6);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('chn','China',7);
INSERT INTO COUNTRY(CODE, NAME,CURRENCY_ID) VALUES('jpn','Japan',8);

INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(1,1,1,1,1);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,2,2,2,2);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(1,3,3,3,1);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,2);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,3);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,4);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,5);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,6);
INSERT INTO EXCHANGE_IN_CURRENCY(SERIAL_NUMBER,SALE_RATE,MIDDLE_RATE,PURCHASING_RATE,CURRENCY_ID) VALUES(2,3,3,3,7);

INSERT INTO EXCHANGE_RATE(DATE,START_DATE,NUMBER_OF_EXCHANGE_RATE) VALUES('2016-03-03','2016-03-03',1);
INSERT INTO EXCHANGE_RATE(DATE,START_DATE,NUMBER_OF_EXCHANGE_RATE) VALUES('2016-03-03','2016-03-03',2);

INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(1,1);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(1,2);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,3);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,4);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,5);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,6);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,7);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,8);
INSERT INTO EXCHANGE_CURRENCY(EXCHANGE_RATE_ID,EXCHANGE_IN_CURRENCY_ID) VALUES(2,9);


INSERT INTO BANK(NAME,CODE,SWIFT_CODE,CLEARING_ACCOUNT) VALUES('bank1','123','12345678',123456789123456789);
INSERT INTO BANK(NAME,CODE,SWIFT_CODE,CLEARING_ACCOUNT) VALUES('bank2','456','88344696',543216789123456987);


INSERT INTO CODE_BOOK_ACTIVITIES (CODE, NAME) VALUES(12365,'djelatnost1');
INSERT INTO CODE_BOOK_ACTIVITIES (CODE, NAME) VALUES(75893,'djelatnost2');



INSERT INTO CLIENT(APPLICANT,JMBG,ADDRESS, PHONE, FAX, MAIL, DELIVERY_ADDRESS,DELIVERY_BY_MAIL, TYPE, SHORT_NAME,PIB, MIB, TAX_AUTHORITY, RESPONSIBLE_PERSON, CODE_BOOK_ACTIVITIES_ID) VALUES('Firma 2',14785,'adresa2',null, 06585963, 'firma22@gmail.com','Mise Dimitrijevica 34', true, 'PRAVNO', 'firm2', 147859661, 14785966, 'Uprava prihoda 1', 'Dragan Jovanovic',1);
INSERT INTO CLIENT(APPLICANT,JMBG,ADDRESS, PHONE, FAX, MAIL, DELIVERY_ADDRESS,DELIVERY_BY_MAIL, TYPE, SHORT_NAME,PIB, MIB, TAX_AUTHORITY, RESPONSIBLE_PERSON, CODE_BOOK_ACTIVITIES_ID) VALUES('Firma 3',14445,'adresa3',06585963, 06585963, 'firma3@gmail.com','Jovana Cvijica 3', true, 'PRAVNO', 'firm3', 999988779, 69636598, 'Uprava prihoda 2', 'Drago Simic',1);
INSERT INTO CLIENT(APPLICANT,JMBG,ADDRESS, PHONE, FAX, MAIL, DELIVERY_ADDRESS,DELIVERY_BY_MAIL, TYPE, SHORT_NAME,PIB, MIB, TAX_AUTHORITY, RESPONSIBLE_PERSON, CODE_BOOK_ACTIVITIES_ID) VALUES('Dragan Smiljanic',14445245,'adresa4',06577963, 06577963, 'dragan@gmail.com','Jakova Milovica 3', true, 'FIZICKO', null, null, null, null, null,null);
INSERT INTO CLIENT(APPLICANT,JMBG,ADDRESS, PHONE, FAX, MAIL, DELIVERY_ADDRESS,DELIVERY_BY_MAIL, TYPE, SHORT_NAME,PIB, MIB, TAX_AUTHORITY, RESPONSIBLE_PERSON, CODE_BOOK_ACTIVITIES_ID) VALUES('Jovana Josipovic',36445288,'adresa5',06577963, 06577963, 'jovana@gmail.com','Kralja Petra 6', false, 'FIZICKO', null, null, null, null, null,null);



INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE,CLIENT_ID) VALUES('123852369874512365',true,'2016-11-11',1);
INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE,CLIENT_ID) VALUES('456752378574512664',true,'2016-12-21',2);
INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE,CLIENT_ID) VALUES('123758369874575393',true,'2016-03-03',3);
INSERT INTO BILL(ACCOUNT_NUMBER,STATUS,DATE,CLIENT_ID) VALUES('456758356374575789',true,'2016-04-25',4);

INSERT INTO BANK_BILLS(BANK_ID,BILL_ID) VALUES(1,1);
INSERT INTO BANK_BILLS(BANK_ID,BILL_ID) VALUES(1,2);
INSERT INTO BANK_BILLS(BANK_ID,BILL_ID) VALUES(1,3);
INSERT INTO BANK_BILLS(BANK_ID,BILL_ID) VALUES(1,4);

INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('1','1','1','1',1);
INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('1@1','1','1','1',1);
INSERT INTO BANKER (MAIL, PASSWORD, FIRSTNAME, LASTNAME,BANK_ID) VALUES('2','2','1','1',1);





INSERT INTO POPULATED_PLACE(NAME, PTT_CODE) VALUES('Novi Sad', '21000');
INSERT INTO POPULATED_PLACE_COUNTRY(POPULATED_PLACE_ID, COUNTRY_ID) VALUES(1, 1);

INSERT INTO BANK_EXCHANGE_RATE(BANK_ID,EXCHANGE_RATE_ID) VALUES(1,1);
INSERT INTO BANK_EXCHANGE_RATE(BANK_ID,EXCHANGE_RATE_ID) VALUES(1,2);
---desa
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-03-03 12:23:00',2000,3000,0,1000,3);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-05-03 13:00:00',3000,5000,0,2000,3);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-04-26 09:33:00',0,0,0,0,4);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-04-26 10:33:00',0,0,0,0,1);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-04-27 09:33:00',0,2000,0,2000,1);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-04-27 09:33:00',0,0,0,0,2);
INSERT INTO DAILY_BALANCE(DATE,PREVIOUS_STATE,NEW_STATE,TRAFFIC_AT_EXPENSE,TRAFFIC_TO_BENEFIT,BILL_ID) VALUES('2016-04-27 14:00:00',0,5000,0,5000,2);