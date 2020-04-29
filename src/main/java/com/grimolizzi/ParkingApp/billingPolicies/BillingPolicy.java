package com.grimolizzi.ParkingApp.billingPolicies;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BillingPolicy {

	protected int hourlyRate; // It's protected because we want to access it in its subclasses

	public BillingPolicy(int hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public abstract long computeBill(Date arrivalDate, Date departureDate);

	protected long getHoursBetween(Date arrivalDate, Date departureDate) {
		return (departureDate.getTime() - arrivalDate.getTime()) / 1000 / 3600;
	}
}
