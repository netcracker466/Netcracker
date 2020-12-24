package com.netcracker.services;


import com.netcracker.dao.CommentDao;
import com.netcracker.exception.DaoAccessException;
import com.netcracker.exception.ErrorCodes;
import com.netcracker.exception.NotBelongToAccountException;
import com.netcracker.exception.NotBelongToAccountExceptionBuilder;
import com.netcracker.models.Comment;
import com.netcracker.models.PojoBuilder.ApartmentBuilder;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.List;


@Service
@Log4j
public class CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }


    public void createComment(Comment comment, BigInteger accountId) throws DaoAccessException {
        try {
            comment.setApartment(new ApartmentBuilder().withAccountId(accountId).build());
            commentDao.createComment(comment);
        } catch (NullPointerException e) {
            log.error("IN Service method createComment: " + e.getMessage(),e);
            throw e;
        }

    }

    public void deleteComment(BigInteger commentId, BigInteger accountId) throws DaoAccessException, NotBelongToAccountException {
        try {
            if (commentDao.getCommentById(commentId).getApartment().getAccountId().equals(accountId)) {
                commentDao.deleteComment(commentId);
            } else {
                NotBelongToAccountException accountException = new NotBelongToAccountExceptionBuilder()
                        .withId(accountId)
                        .withMessage("Comment not belong to account")
                        .withErrorCode(ErrorCodes._FAIL_NOT_BELONG_TO_ACCOUNT)
                        .build();
                log.error("IN Service method deleteComment: " + accountException.getMessage(),accountException);
                throw accountException;
            }
        } catch (NullPointerException e) {
            log.error("IN Service method deleteComment: " + e.getMessage(),e);
            throw e;
        }
    }

    public void updateComment(Comment comment, BigInteger accountId) throws DaoAccessException, NotBelongToAccountException {
        try {
            if (commentDao.getCommentById(comment.getCommentId()).getApartment().getAccountId().equals(accountId)) {
                commentDao.updateComment(comment);
            } else {
                NotBelongToAccountException accountException = new NotBelongToAccountExceptionBuilder()
                        .withId(accountId)
                        .withMessage("Comment not belong to account")
                        .withErrorCode(ErrorCodes._FAIL_NOT_BELONG_TO_ACCOUNT)
                        .build();
                log.error("IN Service method updateComment: " + accountException.getMessage(),accountException);
                throw accountException;
            }
        } catch (NullPointerException e) {
            log.error("IN Service method updateComment: " + e.getMessage(),e);
            throw e;
        }

    }


    public List<Comment> getAllCommentsByAnnouncementId(BigInteger announcementId) throws DaoAccessException {

        try {
            return commentDao.getAllCommentsByAnnouncementId(announcementId);
        } catch (NullPointerException e) {
            log.error("IN Service method getAllCommentsByAnnouncementId: " + e.getMessage(),e);
            throw e;
        }
    }
}
