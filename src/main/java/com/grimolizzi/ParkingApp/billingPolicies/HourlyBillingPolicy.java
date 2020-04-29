package com.grimolizzi.ParkingApp.billingPolicies;

import java.util.Date;

public class HourlyBillingPolicy extends BillingPolicy {

	public HourlyBillingPolicy(int hourlyRate) {
		super(hourlyRate);
	}

	@Override
	public long computeBill(Date arrivalDate, Date departureDate) {
		return super.getHoursBetween(arrivalDate, departureDate) * this.hourlyRate;
	}
}
