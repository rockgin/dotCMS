ALTER TABLE CONTENT_RATING ADD IDENTIFIER INT;
UPDATE CONTENT_RATING SET IDENTIFIER = (INODE - 1);
ALTER TABLE CONTENT_RATING DROP COLUMN INODE;


ALTER TABLE inode ADD ownertmp varchar2(100);
update inode i1 set ownertmp = (select owner from inode i2 where i2.inode = i1.inode);
ALTER TABLE inode DROP COLUMN owner;
ALTER TABLE inode RENAME COLUMN ownertmp TO owner;


alter table web_form add user_inode numeric(19,0) default 0;
alter table web_form add categories varchar(255);


ALTER TABLE contentlet RENAME COLUMN number1 TO float1;
ALTER TABLE contentlet RENAME COLUMN number2 TO float2;
ALTER TABLE contentlet RENAME COLUMN number3 TO float3;
ALTER TABLE contentlet RENAME COLUMN number4 TO float4;
ALTER TABLE contentlet RENAME COLUMN number5 TO float5;
ALTER TABLE contentlet RENAME COLUMN number6 TO float6;
ALTER TABLE contentlet RENAME COLUMN number7 TO float7;
ALTER TABLE contentlet RENAME COLUMN number8 TO float8;
ALTER TABLE contentlet RENAME COLUMN number9 TO float9;
ALTER TABLE contentlet RENAME COLUMN number10 TO float10;
ALTER TABLE contentlet RENAME COLUMN number11 TO float11;
ALTER TABLE contentlet RENAME COLUMN number12 TO float12;
ALTER TABLE contentlet RENAME COLUMN number13 TO float13;
ALTER TABLE contentlet RENAME COLUMN number14 TO float14;
ALTER TABLE contentlet RENAME COLUMN number15 TO float15;
ALTER TABLE contentlet RENAME COLUMN number16 TO float16;
ALTER TABLE contentlet RENAME COLUMN number17 TO float17;
ALTER TABLE contentlet RENAME COLUMN number18 TO float18;
ALTER TABLE contentlet RENAME COLUMN number19 TO float19;
ALTER TABLE contentlet RENAME COLUMN number20 TO float20;
ALTER TABLE contentlet RENAME COLUMN number21 TO float21;
ALTER TABLE contentlet RENAME COLUMN number22 TO float22;
ALTER TABLE contentlet RENAME COLUMN number23 TO float23;
ALTER TABLE contentlet RENAME COLUMN number24 TO float24;
ALTER TABLE contentlet RENAME COLUMN number25 TO float25;


ALTER TABLE content_rating ADD user_ip varchar(255);
ALTER TABLE content_rating ADD long_live_cookie_id varchar(255);