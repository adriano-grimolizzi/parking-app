package com.grimolizzi.ParkingApp.billingPolicies;

import java.util.Date;

public class HourlyPlusFixedBillingPolicy extends BillingPolicy {

    private int fixedAmount;

    public HourlyPlusFixedBillingPolicy(int hourlyRate, int fixedAmount) {
        super(hourlyRate);
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long computeBill(Date arrivalDate, Date departureDate) {
        return super.getHoursBetween(arrivalDate, departureDate) * this.hourlyRate + this.fixedAmount;
    }
}
