package com.netcracker.jobs;

import com.netcracker.services.ApartmentSubBillService;
import com.netcracker.services.ScheduleJobService;
import lombok.extern.log4j.Log4j;
import oracle.sql.TIMESTAMP;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Log4j
@Component
public class DebtPaymentsJob implements Job {
    private final Trigger trigger;

    private final ApartmentSubBillService apartmentSubBillService;

    @Autowired
    public DebtPaymentsJob(ApartmentSubBillService apartmentSubBillService,
                           ScheduleJobService scheduledJobService) {
        this.apartmentSubBillService = apartmentSubBillService;

        trigger = new CronTrigger("0 0 0 1 1/1 *");   //Every first day of month
        scheduledJobService.addJobToScheduler(1, this.getJob(), trigger);
    }

    @Override
    public Runnable getJob() {
        return () -> {
            apartmentSubBillService.updateApartmentSubBillsByDebt();

            log.log(Level.INFO, new Date() + " DebtPaymentsJob with "
                    + " on thread " + Thread.currentThread().getName());
        };
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }


}
