package com.grimolizzi.ParkingApp.billingPolicies;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HourlyPlusFixedBillingPolicyTest {

    @Test
    public void shouldCreateBillingPolicy() {

        Date arrival = new Date(1575190800000L);    // Sun Dec 01 10:00:00 CET 2019
        Date departure = new Date(1575198000000L);  // Sun Dec 01 12:00:00 CET 2019

        HourlyPlusFixedBillingPolicy policy = new HourlyPlusFixedBillingPolicy(2, 3);

        assertEquals(7L, policy.computeBill(arrival, departure));
    }
}
