begin
  execute immediate 'drop table LISTS_STAGING CASCADE CONSTRAINTS';
  exception
  when others then null;
end;
/
begin
  execute immediate 'drop table ATTRTYPE_STAGING CASCADE CONSTRAINTS';
  exception
  when others then null;
end;
/
begin
  execute immediate 'drop table OBJTYPE_STAGING CASCADE CONSTRAINTS';
  exception
  when others then null;
end;
/

/*  Classes  */
CREATE TABLE OBJTYPE_STAGING AS
SELECT *
FROM OBJTYPE;

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (2,NULL, 'ACCT', 'Account', NULL);

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (3, NULL, 'ANNC', 'Announcement', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (4, 3, 'COMT', 'Comment', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (5, 3, 'HVOT', 'HouseVoting', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (6, 5, 'VOPT', 'VotingOption', NULL);

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (7, 2, 'APT', 'Apartment', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (8, 2, 'MNG', 'Manager', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (9, NULL, 'MNGBLL', 'ManagerBill', NULL);

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (11, NULL, 'COMUNUTL', 'CommunalUtility', NULL);

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (12, 11, 'SBLL', 'SubBill', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (13, 12, 'APTSBLL', 'ApartmentSubBill', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (14, 12, 'MNGSBLL', 'ManagerSubBill', NULL);

INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (15, NULL, 'OPR', 'Operation', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (16, 15, 'DPAYOPR', 'DebtPaymentOperation', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (17, 15, 'APTOPR', 'ApartmentOperation', NULL);
INSERT INTO OBJTYPE_STAGING (OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION)
VALUES (18, 15, 'MNGOPR', 'ManagerSpendingOperation', NULL);


-- Merge with OBJTYPE table
MERGE INTO OBJTYPE x
USING (SELECT OBJECT_TYPE_ID, PARENT_ID, CODE, NAME, DESCRIPTION
       FROM OBJTYPE_STAGING) y
ON (x.OBJECT_TYPE_ID = y.OBJECT_TYPE_ID)
WHEN MATCHED THEN
    UPDATE
    SET x.PARENT_ID                   = y.PARENT_ID,
        x.CODE                        = y.CODE,
        x.NAME                        = y.NAME,
                        x.DESCRIPTION = y.DESCRIPTION
    WHERE x.PARENT_ID <> y.PARENT_ID OR 
           x.CODE <> y.CODE OR 
           x.NAME <> y.NAME OR
           x.DESCRIPTION <> y.DESCRIPTION 
WHEN NOT MATCHED THEN
    INSERT(x.OBJECT_TYPE_ID,x.PARENT_ID,x.CODE,x.NAME,x.DESCRIPTION)  
    VALUES(y.OBJECT_TYPE_ID,y.PARENT_ID,y.CODE,y.NAME,y.DESCRIPTION);
COMMIT;

/* Attributes */
CREATE TABLE ATTRTYPE_STAGING  AS 
SELECT * FROM ATTRTYPE;

--Role(OBJECT_TYPE_ID=1) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (1,2,NULL,'RNAME','role_name');

-- Account(OBJECT_TYPE_ID=2) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (2,2,NULL,'EMAIL','email');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (3,2,NULL,'PASW','password');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (4,2,NULL,'FNAME','first_name');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (5,2,NULL,'LNAME','last_name');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (6,2,NULL,'PHNUM','phone_number');

-- Announcement(OBJECT_TYPE_ID=3) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (7,3,NULL,'TITLE','title');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (8,3,NULL,'BODY','body');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (9,3,NULL,'ISOPENED','is_opened');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (10,3,NULL,'CREATEDAT','created_at');

-- Comment(OBJECT_TYPE_ID=4) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (11,4,NULL,'BODY','body');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (12,4,NULL,'CREATEDAT','created_at');

-- House Voting (OBJECT_TYPE_ID=5) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (13,5,NULL,'TITLE','title');

-- Voting Option(OBJECT_TYPE_ID=6) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (14,6,NULL,'NAME','name');

--Apartment(OBJECT_TYPE_ID=7) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (15, 7, NULL, 'APTNUM', 'apartment_number');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (16, 7, NULL, 'SQUARE', 'square_metres');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (17, 7, NULL, 'FLOOR', 'floor');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (18, 7, NULL, 'PPLCOUNT', 'people_count');

--Manager(OBJECT_TYPE_ID=8) attributes

--ManagerBill(OBJECT_TYPE_ID=9) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (19, 9, NULL, 'CARDNUM', 'card_number');

--CalculationMethod(OBJECT_TYPE_ID=10) attributes


--CommunalUtility(OBJECT_TYPE_ID=11) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (21, 11, NULL, 'COMUNNAME', 'name');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (22, 11, NULL, 'DURTYPE', 'duration_type');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (23, 11, NULL, 'STATUS', 'status');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (24, 11, NULL, 'DLINE', 'deadline');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (20, 11, NULL, 'CALCNAME', 'calculation_name');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (40, 11, NULL, 'COEFF', 'coefficient');


--SubBill(OBJECT_TYPE_ID=12) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (25, 12, NULL, 'BALANCE', 'balance');

--ApartmentSubBill(OBJECT_TYPE_ID=13) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (38, 13, NULL, 'DEBT', 'debt');
--ManagerSubBill(OBJECT_TYPE_ID=14) attributes

--Operation(OBJECT_TYPE_ID=15) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (26, 15, NULL, 'SUM', 'sum');
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (27, 15, NULL, 'CREATEDAT', 'created_at');

--DebtPaymentOperation(OBJECT_TYPE_ID=16) attributes
--ApartmentOperation(OBJECT_TYPE_ID=17) attributes

--ManagerSpendingOperation(OBJECT_TYPE_ID=18) attributes
INSERT INTO ATTRTYPE_STAGING (ATTR_ID, OBJECT_TYPE_ID, OBJECT_TYPE_ID_REF, CODE, NAME)
VALUES (28, 18, NULL, 'DESCR', 'description');

/*Simple associations*/

--OBJECT_TYPE Account(OBJECT_TYPE_ID=2)  reference simple association "Writes" with Comment(OBJECT_TYPE_ID=4)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (29,4,2,'WRITE','Writes');
--OBJECT_TYPE Account(OBJECT_TYPE_ID=2)  reference simple association "Votes" with Voting Option(OBJECT_TYPE_ID=6)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (30,6,2,'VOTE','Votes');
--OBJECT_TYPE Manager(OBJECT_TYPE_ID=8)  reference simple association "Owns" with ManagerBill(OBJECT_TYPE_ID=9)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (31,9,8,'OWN','Owns');
--OBJECT_TYPE Manager(OBJECT_TYPE_ID=8)  reference simple association "Owns" with ManagerSubBill(OBJECT_TYPE_ID=14)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (32,14,8,'OWN','Owns');
--OBJECT_TYPE Apartment(OBJECT_TYPE_ID=7)  reference simple association "Owns" with ApartmentSubBill(OBJECT_TYPE_ID=13)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (33,13,7,'OWN','Owns');

-- OBJECT_TYPE ApartmentSubBill(OBJECT_TYPE_ID=13)  reference simple association "Transfers Money" with ApartmentOperation(OBJECT_TYPE_ID=17)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (34,17,13,'TRANSFERS','Transfers Money');

-- OBJECT_TYPE ApartmentSubBill(OBJECT_TYPE_ID=13)  reference simple association "Pays Debt" with DebtPaymentOperation(OBJECT_TYPE_ID=16)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (35,16,13,'PAYS','Pays Debt');

-- OBJECT_TYPE ManagerSubBill(OBJECT_TYPE_ID=14)  reference simple association "Adds Spending" with ManagerSpendingOperation(OBJECT_TYPE_ID=18)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (36,18,14,'ADDSPENDING','Adds Spending');

-- OBJECT_TYPE ManagerSubBill(OBJECT_TYPE_ID=14)  reference simple association "Receives Paid Debt" with DebtPaymentOperation(OBJECT_TYPE_ID=16)
INSERT INTO ATTRTYPE_STAGING (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (37,16,14,'RECEIVES','Receives Paid Debt');

-- Merge with ATTRTYPE table
MERGE INTO ATTRTYPE x
USING (SELECT ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME FROM ATTRTYPE_STAGING) y
ON (x.ATTR_ID  = y.ATTR_ID)
WHEN MATCHED THEN
    UPDATE
    SET x.OBJECT_TYPE_ID     = y.OBJECT_TYPE_ID,
        x.OBJECT_TYPE_ID_REF = y.OBJECT_TYPE_ID_REF,
        x.CODE               = y.CODE,
        x.NAME               = y.NAME
    WHERE x.OBJECT_TYPE_ID <> y.OBJECT_TYPE_ID
       OR x.OBJECT_TYPE_ID_REF <> y.OBJECT_TYPE_ID_REF
       OR x.CODE <> y.CODE
       OR x.NAME <> y.NAME
WHEN NOT MATCHED THEN
    INSERT (x.ATTR_ID, x.OBJECT_TYPE_ID, x.OBJECT_TYPE_ID_REF, x.CODE, x.NAME)
    VALUES (y.ATTR_ID, y.OBJECT_TYPE_ID, y.OBJECT_TYPE_ID_REF, y.CODE, y.NAME);


/*  Adding different list's values */

CREATE TABLE LISTS_STAGING AS
SELECT *
FROM Lists;

-- List type for 'duration_type'(attr_id=22) OBJ_TYPE CommunalUtility
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (22, 1, 'Temporary');
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (22, 2, 'Constant');

-- List type for 'status'(attr_id=23) OBJ_TYPE CommunalUtility
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (23, 3, 'Enabled');
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (23, 4, 'Disabled');

-- List type for 'calculation_name'(attr_id=20) OBJ_TYPE CommunalUtility
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (20, 7, 'SquareMeters');
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (20, 8, 'PeopleCount');
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (20, 9, 'Floor');


-- List type for 'status'(attr_id=1) OBJ_TYPE Role
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (1, 5, 'OWNER');
insert into LISTS_STAGING (attr_id, list_value_id, value)
values (1, 6, 'MANAGER');

-- Merge with ATTRTYPE table
MERGE INTO Lists x
USING (SELECT attr_id, list_value_id, value
       FROM LISTS_STAGING) y
ON (x.ATTR_ID = y.ATTR_ID
    AND x.list_value_id = y.list_value_id)
WHEN MATCHED THEN
    UPDATE
    SET x.value = y.value
    WHERE x.attr_id <> y.attr_id
       OR x.list_value_id <> y.list_value_id
       OR x.value <> y.value
WHEN NOT MATCHED THEN
    INSERT (x.attr_id, x.list_value_id, x.value)
    VALUES (y.attr_id, y.list_value_id, y.value);

/* Sequence for future inserts */
begin
    execute immediate 'DROP SEQUENCE OBJ_ID_SEQ';
    execute immediate 'DROP function seq_obj_next';
    execute immediate 'DROP function seq_obj_curr';
exception
    when others then null;
end;
/
CREATE SEQUENCE OBJ_ID_SEQ START WITH 1 MAXVALUE 999999 INCREMENT BY 1 NOCACHE CYCLE;
/
CREATE FUNCTION seq_obj_next RETURN NUMBER RESULT_CACHE IS
BEGIN
    RETURN OBJ_ID_SEQ.nextval;
END seq_obj_next;
/
CREATE FUNCTION seq_obj_curr RETURN NUMBER RESULT_CACHE IS
BEGIN
    RETURN OBJ_ID_SEQ.currval;
END seq_obj_curr;
/
COMMIT;

