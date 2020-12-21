package com.netcracker.services;

import com.netcracker.dao.ApartmentSubBillDao;
import com.netcracker.models.*;
import com.netcracker.models.PojoBuilder.ApartmentSubBillBuilder;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


@Service
@Transactional
public class ApartmentSubBillService {
    private final ApartmentSubBillDao apartmentSubBillDao;
    private final ApartmentOperationService apartmentOperationService;
    private final ApartmentInfoService apartmentInfoService;
    private final ApartmentPaymentService apartmentPaymentService;
    private final DebtPaymentOperationService debtPaymentOperationService;

    @Autowired
    public ApartmentSubBillService(ApartmentSubBillDao apartmentSubBillDao, ApartmentOperationService apartmentOperationService,
                                   ApartmentInfoService apartmentInfoService, ApartmentPaymentService apartmentPaymentService,
                                   DebtPaymentOperationService debtPaymentOperationService) {
        this.apartmentSubBillDao = apartmentSubBillDao;
        this.apartmentOperationService = apartmentOperationService;
        this.apartmentInfoService = apartmentInfoService;
        this.apartmentPaymentService = apartmentPaymentService;
        this.debtPaymentOperationService = debtPaymentOperationService;
    }

    public List<ApartmentSubBill> getAllApartmentSubBills() {
        return apartmentSubBillDao.getAllApartmentSubBills();
    }

    public ApartmentSubBill getApartmentSubBill(BigInteger apartmentSubBillId) {
        return apartmentSubBillDao.getApartmentSubBillById(apartmentSubBillId);
    }

    public void createApartmentSubBillTransfer(ApartmentSubBill transferFrom, ApartmentSubBill transferTo, Double value) {

    }

    public void createApartmentSubBill(CommunalUtility communalUtility) {
        for (Apartment apartment : apartmentInfoService.getAllApartments()) {
            apartmentSubBillDao.createApartmentSubBill(new ApartmentSubBillBuilder()
                    .withApartment(apartment)
                    .withCommunalUtility(communalUtility)
                    .build());
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
