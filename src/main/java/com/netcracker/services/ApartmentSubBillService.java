
package com.netcracker.services;

import com.netcracker.dao.ApartmentSubBillDao;
import com.netcracker.exception.NotBelongToAccountException;
import com.netcracker.models.*;
import com.netcracker.models.PojoBuilder.ApartmentOperationBuilder;
import com.netcracker.models.PojoBuilder.ApartmentSubBillBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ApartmentSubBillService {
    private final ApartmentSubBillDao apartmentSubBillDao;
    private final ApartmentOperationService apartmentOperationService;
    private final ApartmentInfoService apartmentInfoService;
    private final ApartmentPaymentService apartmentPaymentService;
    private final DebtPaymentOperationService debtPaymentOperationService;
    private final CommunalUtilityService communalUtilityService;

    @Autowired
    public ApartmentSubBillService(ApartmentSubBillDao apartmentSubBillDao, ApartmentOperationService apartmentOperationService,
                                   ApartmentInfoService apartmentInfoService, ApartmentPaymentService apartmentPaymentService,
                                   DebtPaymentOperationService debtPaymentOperationService, CommunalUtilityService communalUtilityService) {
        this.apartmentSubBillDao = apartmentSubBillDao;
        this.apartmentOperationService = apartmentOperationService;
        this.apartmentInfoService = apartmentInfoService;
        this.apartmentPaymentService = apartmentPaymentService;
        this.debtPaymentOperationService = debtPaymentOperationService;
        this.communalUtilityService = communalUtilityService;
    }

    public List<ApartmentSubBill> getAllApartmentSubBills() {
        List<ApartmentSubBill> apartmentSubBills = apartmentSubBillDao.getAllApartmentSubBills();
        for (ApartmentSubBill apartmentSubBill : apartmentSubBills) {
            BigInteger apartmentSubBillId = apartmentSubBill.getSubBillId();
            apartmentSubBill.setApartmentOperations(apartmentOperationService.getAllApartmentOperationsBySubBillId(apartmentSubBillId));
            apartmentSubBill.setDebtPaymentOperations(debtPaymentOperationService.getDebtPaymentOperationsByApartmentSubBillId(apartmentSubBillId));
        }
        return apartmentSubBills;
    }

    public ApartmentSubBill getApartmentSubBill(BigInteger apartmentSubBillId) {
        ApartmentSubBill apartmentSubBill = apartmentSubBillDao.getApartmentSubBillById(apartmentSubBillId);
        apartmentSubBill.setApartmentOperations(apartmentOperationService.getAllApartmentOperationsBySubBillId(apartmentSubBillId));
        apartmentSubBill.setDebtPaymentOperations(debtPaymentOperationService.getDebtPaymentOperationsByApartmentSubBillId(apartmentSubBillId));
        return apartmentSubBill;
    }


    public void createApartmentSubBill(CommunalUtility communalUtility) {
        for (Apartment apartment : apartmentInfoService.getAllApartments()) {
            apartmentSubBillDao.createApartmentSubBill(new ApartmentSubBillBuilder()
                    .withApartment(apartment)
                    .withCommunalUtility(communalUtility)
                    .build());
        }
    }

    public void addApartmentSubBillsToApartment(Apartment apartment) {
        for (CommunalUtility communalUtility : communalUtilityService.getAllCommunalUtilities(CommunalUtility.Status.Enabled)) {
            apartmentSubBillDao.createApartmentSubBill(new ApartmentSubBillBuilder()
                    .withApartment(apartment)
                    .withCommunalUtility(communalUtility)
                    .withDept((double) 0)
                    .withBalance((double) 0)
                    .build());
        }
    }

    public void createApartmentSubBillTransfer(ApartmentSubBill transferFrom, ApartmentSubBill transferTo, Double value) {
        if (transferFrom.getApartment().getAccountId().equals(transferTo.getApartment().getAccountId())) {
            ApartmentSubBill subBillFrom = apartmentSubBillDao.getApartmentSubBillById(transferFrom.getSubBillId());
            ApartmentSubBill subBillTo = apartmentSubBillDao.getApartmentSubBillById(transferTo.getSubBillId());
            if (subBillFrom != null && subBillTo != null
                    && subBillFrom.getBalance() >= value) {
                subBillFrom.setBalance(subBillFrom.getBalance() - value);
                subBillTo.setBalance(subBillTo.getBalance() + value);
                apartmentSubBillDao.updateApartmentSubBill(subBillFrom);
                apartmentSubBillDao.updateApartmentSubBill(subBillTo);
                apartmentOperationService.createApartmentOperation(new ApartmentOperationBuilder()
                        .withApartmentSubBill(transferTo)
                        .withCreatedAt(new Date())
                        .withSum(value)
                        .build());
                apartmentOperationService.createApartmentOperation(new ApartmentOperationBuilder()
                        .withApartmentSubBill(transferFrom)
                        .withCreatedAt(new Date())
                        .withSum(-value)
                        .build());
            } else {
                IllegalArgumentException e = new IllegalArgumentException("Wrong transfer Data");
                throw e;
            }
        } else {
            NotBelongToAccountException e = new NotBelongToAccountException("Wrong transfer SubBills");
            throw e;
        }
    }

    public void updateApartmentSubBillByApartmentOperation(ApartmentOperation apartmentOperation) {
        ApartmentSubBill apartmentSubBill = apartmentSubBillDao.getApartmentSubBillById(apartmentOperation.getApartmentSubBill().getSubBillId());


        apartmentSubBill.setBalance(apartmentSubBill.getBalance() + apartmentOperation.getSum());
        apartmentSubBillDao.updateApartmentSubBill(apartmentSubBill);

        apartmentOperationService.createApartmentOperation(apartmentOperation);

        Double oldBalance = apartmentSubBill.getBalance();
        Double oldDebt = apartmentSubBill.getDebt();

        if (oldBalance >= oldDebt) {
            apartmentSubBill.setDebt(0d);
            apartmentSubBill.setBalance(oldBalance - oldDebt);
            apartmentSubBillDao.updateApartmentSubBill(apartmentSubBill);
            debtPaymentOperationService.createDebtPaymentOperation(apartmentSubBill, oldDebt);
        }
    }


    public void updateApartmentSubBillsByDebt() {

    }

    public List<ApartmentSubBill> getAllApartmentSubBillsByAccountId(BigInteger accountId) {
        return apartmentSubBillDao.getAllApartmentSubBillsByAccountId(accountId);
    }

}