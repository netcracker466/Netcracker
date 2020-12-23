package com.netcracker.exception;

import java.math.BigInteger;

public interface ErrorCodes {
    BigInteger APARTMENT_OPERATION_FAIL_TO_INSERT_ = BigInteger.valueOf(171);
    BigInteger APARTMENT_OPERATION_FAIL_TO_UPDATE = BigInteger.valueOf(172);
    BigInteger APARTMENT_OPERATION_FAIL_TO_SELECT = BigInteger.valueOf(173);

    BigInteger _FAIL_TO_INSERT_COMMENT = BigInteger.valueOf(41);
    BigInteger _FAIL_TO_UPDATE_COMMENT = BigInteger.valueOf(42);
    BigInteger _FAIL_TO_SELECT_COMMENT = BigInteger.valueOf(43);
    BigInteger _FAIL_TO_DELETE_COMMENT = BigInteger.valueOf(44);

    BigInteger _FAIL_TO_INSERT_MANAGER_SPENDING_OPERATION = BigInteger.valueOf(181);
    BigInteger _FAIL_TO_UPDATE_MANAGER_SPENDING_OPERATION = BigInteger.valueOf(182);
    BigInteger _FAIL_TO_SELECT_MANAGER_SPENDING_OPERATION = BigInteger.valueOf(183);
    BigInteger _FAIL_TO_DELETE_MANAGER_SPENDING_OPERATION = BigInteger.valueOf(184);

    BigInteger _FAIL_TO_INSERT_MANAGER_BILL = BigInteger.valueOf(91);
    BigInteger _FAIL_TO_UPDATE_MANAGER_BILL = BigInteger.valueOf(92);
    BigInteger _FAIL_TO_SELECT_MANAGER_BILL = BigInteger.valueOf(93);
    BigInteger _FAIL_TO_DELETE_MANAGER_BILL = BigInteger.valueOf(94);

    BigInteger _FAIL_TO_INSERT_APARTMENT_SUB_BILL = BigInteger.valueOf(131);
    BigInteger _FAIL_TO_UPDATE_APARTMENT_SUB_BILL = BigInteger.valueOf(132);
    BigInteger _FAIL_TO_SELECT_APARTMENT_SUB_BILL = BigInteger.valueOf(133);

    BigInteger _FAIL_TO_INSERT_APARTMENT = BigInteger.valueOf(71);
    BigInteger _FAIL_TO_UPDATE_APARTMENT_ = BigInteger.valueOf(72);
    BigInteger _FAIL_TO_SELECT_APARTMENT = BigInteger.valueOf(73);
    BigInteger _FAIL_TO_INSERT_COMMUNAL_UTILITY = BigInteger.valueOf(111);
    BigInteger _FAIL_TO_UPDATE_COMMUNAL_UTILITY = BigInteger.valueOf(112);
    BigInteger _FAIL_TO_SELECT_COMMUNAL_UTILITY = BigInteger.valueOf(113);


    BigInteger _FAIL_TO_SELECT_ACCOUNT = BigInteger.valueOf(23);


}
