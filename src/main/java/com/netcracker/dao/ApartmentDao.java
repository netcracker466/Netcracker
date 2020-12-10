package com.netcracker.dao;

import com.netcracker.models.Apartment;

import java.math.BigInteger;
import java.util.List;

public interface ApartmentDao {

    List<Apartment> getAllApartments();

    Apartment getApartmentById(BigInteger apartmentId);

    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    String GET_ALL_APARTMENTS = "SELECT APRT.OBJECT_ID account_id,\n" +
            "                               APTNUM.VALUE   apartment_number,\n" +
            "                               SQUARE.VALUE   square_metres,\n" +
            "                               FLOOR.VALUE    floor,\n" +
            "                               PPLCOUNT.VALUE people_count,\n" +
            "                               EMAIL.VALUE    email,\n" +
            "                               PASW.VALUE     password,\n" +
            "                               FNAME.VALUE    first_name,\n" +
            "                               LNAME.VALUE    last_name,\n" +
            "                               PHNUM.VALUE    phone_number,\n" +
            "                               ROLE.VALUE  role_name\n" +
            "                        FROM OBJECTS APRT,\n" +
            "                             ATTRIBUTES APTNUM,\n" +
            "                             ATTRIBUTES SQUARE,\n" +
            "                             ATTRIBUTES FLOOR,\n" +
            "                             ATTRIBUTES PPLCOUNT,\n" +
            "                             ATTRIBUTES EMAIL,\n" +
            "                             ATTRIBUTES PASW,\n" +
            "                             ATTRIBUTES FNAME,\n" +
            "                             ATTRIBUTES LNAME,\n" +
            "                             ATTRIBUTES PHNUM,\n" +
            "                             ATTRIBUTES ROLE\n" +
            "                        WHERE APRT.OBJECT_TYPE_ID = 7\n" +
            "                          AND APTNUM.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND APTNUM.ATTR_ID = 15\n" +
            "                          AND SQUARE.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND SQUARE.ATTR_ID = 16\n" +
            "                          AND FLOOR.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND FLOOR.ATTR_ID = 17\n" +
            "                          AND PPLCOUNT.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND PPLCOUNT.ATTR_ID = 18\n" +
            "                          AND EMAIL.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND EMAIL.ATTR_ID = 2\n" +
            "                          AND PASW.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND PASW.ATTR_ID = 3\n" +
            "                          AND FNAME.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND FNAME.ATTR_ID = 4\n" +
            "                          AND LNAME.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND LNAME.ATTR_ID = 5\n" +
            "                          AND PHNUM.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND PHNUM.ATTR_ID = 6\n" +
            "                          AND ROLE.OBJECT_ID = APRT.OBJECT_ID\n" +
            "                          AND ROLE.ATTR_ID = 1";

    String GET_APARTMENT_BY_ID = "SELECT APRT.OBJECT_ID account_id,\n" +
            "       APTNUM.VALUE   apartment_number,\n" +
            "       SQUARE.VALUE   square_metres,\n" +
            "       FLOOR.VALUE    floor,\n" +
            "       PPLCOUNT.VALUE people_count,\n" +
            "       EMAIL.VALUE    email,\n" +
            "       PASW.VALUE     password,\n" +
            "       FNAME.VALUE    first_name,\n" +
            "       LNAME.VALUE    last_name,\n" +
            "       PHNUM.VALUE    phone_number,\n" +
            "       ROLE.VALUE     role_name\n" +
            "FROM OBJECTS APRT,\n" +
            "     ATTRIBUTES APTNUM,\n" +
            "     ATTRIBUTES SQUARE,\n" +
            "     ATTRIBUTES FLOOR,\n" +
            "     ATTRIBUTES PPLCOUNT,\n" +
            "     ATTRIBUTES EMAIL,\n" +
            "     ATTRIBUTES PASW,\n" +
            "     ATTRIBUTES FNAME,\n" +
            "     ATTRIBUTES LNAME,\n" +
            "     ATTRIBUTES PHNUM,\n" +
            "     ATTRIBUTES ROLE\n" +
            "WHERE APRT.OBJECT_ID = ?\n" +
            "  AND APRT.OBJECT_TYPE_ID = 7\n" +
            "  AND APTNUM.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND APTNUM.ATTR_ID = 15\n" +
            "  AND SQUARE.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND SQUARE.ATTR_ID = 16\n" +
            "  AND FLOOR.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND FLOOR.ATTR_ID = 17\n" +
            "  AND PPLCOUNT.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND PPLCOUNT.ATTR_ID = 18\n" +
            "  AND EMAIL.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND EMAIL.ATTR_ID = 2\n" +
            "  AND PASW.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND PASW.ATTR_ID = 3\n" +
            "  AND FNAME.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND FNAME.ATTR_ID = 4\n" +
            "  AND LNAME.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND LNAME.ATTR_ID = 5\n" +
            "  AND PHNUM.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND PHNUM.ATTR_ID = 6\n" +
            "  AND ROLE.OBJECT_ID = APRT.OBJECT_ID\n" +
            "  AND ROLE.ATTR_ID = 1";

    String CREATE_APARTMENT_OBJECT = "MERGE INTO OBJECTS old\n" +
            "USING (SELECT seq_obj_next()                 OBJECT_ID,\n" +
            "              NULL                           PARENT_ID,\n" +
            "              7                              OBJECT_TYPE_ID,\n" +
            "              'Apartment_' || seq_obj_curr() NAME,\n" +
            "              NULL                           DESCRIPTION\n" +
            "       FROM DUAL) new\n" +
            "ON (old.OBJECT_ID = new.OBJECT_ID)\n" +
            "WHEN MATCHED THEN\n" +
            "    UPDATE\n" +
            "    SET old.PARENT_ID      = new.PARENT_ID,\n" +
            "        old.OBJECT_TYPE_ID = new.OBJECT_TYPE_ID,\n" +
            "        old.NAME           = new.NAME,\n" +
            "        old.DESCRIPTION    = new.DESCRIPTION\n" +
            "    WHERE old.PARENT_ID <> new.PARENT_ID\n" +
            "       OR old.OBJECT_TYPE_ID <> new.OBJECT_TYPE_ID\n" +
            "       OR old.NAME <> new.NAME\n" +
            "       OR old.DESCRIPTION <> new.DESCRIPTION\n" +
            "WHEN NOT MATCHED THEN\n" +
            "    INSERT (old.OBJECT_ID, old.PARENT_ID, old.OBJECT_TYPE_ID, old.NAME, old.DESCRIPTION)\n" +
            "    VALUES (seq_obj_curr(), new.PARENT_ID, new.OBJECT_TYPE_ID, new.NAME, new.DESCRIPTION)";

    String CREATE_APARTMENT_ATTRIBUTES = "MERGE INTO ATTRIBUTES old\n" +
            "USING (SELECT 1 ATTR_ID, seq_obj_curr() OBJECT_ID, ? VALUE, NULL DATE_VALUE\n" +
            "       FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 2, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 3, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 4, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 5, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 6, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 15, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 16, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 17, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            "       UNION ALL\n" +
            "       SELECT 18, seq_obj_curr(), ?, NULL FROM DUAL\n" +
            ") new\n" +
            "ON (old.OBJECT_ID = new.OBJECT_ID AND old.ATTR_ID = new.ATTR_ID)\n" +
            "WHEN MATCHED THEN\n" +
            "    UPDATE\n" +
            "    SET old.VALUE      = new.VALUE,\n" +
            "        old.DATE_VALUE = new.DATE_VALUE\n" +
            "    WHERE old.VALUE <> new.VALUE\n" +
            "       OR old.DATE_VALUE <> new.DATE_VALUE\n" +
            "WHEN NOT MATCHED THEN\n" +
            "    INSERT (old.ATTR_ID, old.OBJECT_ID, old.VALUE, old.DATE_VALUE)\n" +
            "    VALUES (new.ATTR_ID, new.OBJECT_ID, new.VALUE, new.DATE_VALUE)";

    String UPDATE_APARTMENT = "MERGE INTO ATTRIBUTES x\n" +
            "USING (\n" +
            "    SELECT 15 ATTR_ID, ? VALUE FROM DUAL\n" +
            "    UNION ALL\n" +
            "    SELECT 16, ? FROM DUAL\n" +
            "    UNION ALL\n" +
            "    SELECT 17, ? FROM DUAL\n" +
            "    UNION ALL\n" +
            "    SELECT 18, ?  FROM DUAL\n" +
            ") y\n" +
            "ON (x.OBJECT_ID = ? AND x.ATTR_ID = y.ATTR_ID)\n" +
            "WHEN MATCHED THEN\n" +
            "    UPDATE\n" +
            "    SET x.VALUE = y.VALUE\n" +
            "    WHERE x.VALUE <> y.VALUE";
}
