package com.netcracker.dao.impl;

import com.netcracker.dao.VotingOptionDao;
import com.netcracker.dao.mapper.VotingOptionMapper;
import com.netcracker.exception.DaoAccessException;
import com.netcracker.exception.DaoAccessExceptionBuilder;
import com.netcracker.exception.ErrorCodes;
import com.netcracker.models.VotingOption;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collection;

@Log4j
@Repository
@Transactional
public class VotingOptionDaoImpl implements VotingOptionDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<VotingOption> getAllVotingOptionsByHouseVotingId(BigInteger id) throws DaoAccessException {
        try {
            return jdbcTemplate.query(GET_ALL_VOTING_OPTIONS_BY_HOUSE_VOTING_ID, new VotingOptionMapper(), id);
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes._FAIL_TO_SELECT_VOTING_OPTION)
                    .withMessage(EXCEPTION_GET_ALL_VOTING_OPTIONS_BY_HOUSE_VOTING_ID)
                    .withId(id)
                    .withCause(e.getCause())
                    .build();
            log.error("VotingOptionDaoImpl method getAllVotingOptionsByHouseVotingId: " + accessException.getMessage());
            throw accessException;
        }
    }

    @Override
    public void createVotingOption(VotingOption votingOption) throws DaoAccessException {
        try {
            jdbcTemplate.update(CREATE_VOTING_OPTION_OBJECT,
                    votingOption.getHouseVoting().getHouseVotingId());
            jdbcTemplate.update(CREATE_VOTING_OPTION_ATTRIBUTES,
                    votingOption.getName());
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes._FAIL_TO_INSERT_VOTING_OPTION)
                    .withMessage(EXCEPTION_CREATE_VOTING_OPTION)
                    .withCause(e.getCause())
                    .build();
            log.error("VotingOptionDaoImpl method createVotingOption: " + accessException.getMessage());
            throw accessException;
        }
    }

    @Override
    public void addVote(BigInteger votingOptionId, BigInteger accountId) throws DaoAccessException {
        try {
            jdbcTemplate.update(ADD_VOTE,
                    votingOptionId,
                    accountId);
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes._FAIL_TO_INSERT_VOTE_REF)
                    .withMessage(EXCEPTION_ADD_VOTE)
                    .withCause(e.getCause())
                    .build();
            log.error("VotingOptionDaoImpl method addVote: " + accessException.getMessage());
            throw accessException;
        }
    }

    @Override
    public Collection<BigInteger> getApartmentIdsByVotingOptionId(BigInteger id) throws DaoAccessException {
        try {
            return jdbcTemplate.queryForList(GET_ALL_APARTMENT_IDS_BY_VOTING_OPTION_ID, BigInteger.class, id);
        } catch (DataAccessException e) {
            DaoAccessException accessException = new DaoAccessExceptionBuilder()
                    .withErrorMessage(ErrorCodes.APARTMENT_OPERATION_FAIL_TO_SELECT)
                    .withMessage(EXCEPTION_GET_ALL_APARTMENT_IDS_BY_VOTING_OPTION_ID)
                    .withId(id)
                    .withCause(e.getCause())
                    .build();
            log.error("VotingOptionDaoImpl method getApartmentIdsByVotingOptionId: " + accessException.getMessage());
            throw accessException;
        }
    }
}
