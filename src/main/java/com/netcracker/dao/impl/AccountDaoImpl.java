package com.netcracker.dao.impl;

import com.netcracker.dao.AccountDao;
import com.netcracker.dao.mapper.AccountMapper;
import com.netcracker.exception.DaoAccessException;
import com.netcracker.exception.DaoAccessExceptionBuilder;
import com.netcracker.exception.ErrorCodes;
import com.netcracker.models.Account;
import com.netcracker.models.Apartment;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;


@Repository
@Log4j
public class AccountDaoImpl implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Account getAccount(BigInteger id) throws DaoAccessException {
        try {
            return jdbcTemplate.queryForObject(GET_ACCOUNT_BY_ID, new AccountMapper(), id);
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes._FAIL_TO_SELECT_ACCOUNT)
                    .withMessage(EXCEPTION_GET_ACCOUNT_BY_ID)
                    .withCause(e.getCause())
                    .build();
            log.error("IN getAccount " + accessException.getMessage(), e);
            throw accessException;
        }
    }

    @Override
    public Account getAccountByEmail(String email) throws DataAccessException {
        try {
            return jdbcTemplate.queryForObject(GET_ACCOUNT_BY_EMAIL, new AccountMapper(), email);
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes._FAIL_TO_SELECT_ACCOUNT)
                    .withMessage(EXCEPTION_GET_ACCOUNT_BY_EMAIL + email)
                    .withCause(e.getCause())
                    .build();
            log.error("getAccountByEmail " + accessException.getMessage(), e);
            throw accessException;
        }
    }

    @Override
    public Account updateAccount(Account account) throws DaoAccessException {
        try {
            jdbcTemplate.update(UPDATE_ACCOUNT,
                    account.getEmail(),
                    account.getPassword(),
                    account.getLastName(),
                    account.getFirstName(),
                    account.getPhoneNumber(),
                    account.getAccountId());
        } catch (DataAccessException e) {
            e = new DaoAccessExceptionBuilder()
                    .withMessage(EXCEPTION_UPDATE_ACCOUNT)
                    .withCause(e.getCause())
                    .withId(account.getAccountId())
                    .withErrorMessage(BigInteger.valueOf(72))
                    .build();
            log.log(Level.ERROR, e.getMessage(), e);
            throw e;
        }
        return account;
    }
}
