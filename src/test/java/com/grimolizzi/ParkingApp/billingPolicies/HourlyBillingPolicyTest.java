package com.grimolizzi.ParkingApp.billingPolicies;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class HourlyBillingPolicyTest {

    @Test
    public void shouldCreateHourlyBillingPolicy() {

        Date arrival = new Date(1575190800000L);    // Sun Dec 01 10:00:00 CET 2019
        Date departure = new Date(1575198000000L);  // Sun Dec 01 12:00:00 CET 2019

        HourlyBillingPolicy policy = new HourlyBillingPolicy(2);

        long bill = policy.computeBill(arrival, departure);
        assert(bill == 4L);
    }
}
