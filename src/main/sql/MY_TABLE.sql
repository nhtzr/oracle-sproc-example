CREATE TABLE "MY_TABLE" -- Remember to set schema to whatever spring.datasource.username has.
(
  "PK"       NUMBER            NOT NULL ENABLE,
  "COLUMN_1" VARCHAR2(12 BYTE) NOT NULL ENABLE,
  "COLUMN_2" CLOB,
  CONSTRAINT "MY_TABLE_PK" PRIMARY KEY ("PK")
);
