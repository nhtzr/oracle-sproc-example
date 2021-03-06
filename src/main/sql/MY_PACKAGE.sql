CREATE OR REPLACE PACKAGE BODY "MY_PACKAGE" IS

  PROCEDURE TEST(
    P_ARG1 IN  NUMBER,
    P_ARG2 IN  MY_NARRAY,
    P_ARG3 OUT MY_ARRAY,
    P_ERRM OUT VARCHAR2) IS

    BEGIN
      SELECT MY_TABLE_OBJ(PK, COLUMN_1, COLUMN_2)
      BULK COLLECT INTO P_ARG3
      FROM MY_TABLE
      WHERE ROWNUM < 2;

      P_ERRM := 'SQL-00: ALL OK';
    END TEST;

END "MY_PACKAGE";
