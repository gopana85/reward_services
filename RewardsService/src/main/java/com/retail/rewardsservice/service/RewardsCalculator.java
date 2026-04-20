package com.retail.rewardsservice.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class RewardsCalculator {

    public BigDecimal calculateRewardPoints(BigDecimal amount) {
        BigDecimal points = BigDecimal.ZERO;

        if (amount.compareTo(new BigDecimal("100")) > 0) {
            points = points.add(amount.subtract(new BigDecimal("100")).multiply(new BigDecimal("2")));
            points = points.add(new BigDecimal("50"));
        } else if (amount.compareTo(new BigDecimal("50")) > 0) {
            points = points.add(amount.subtract(new BigDecimal("50")));
        }

        return points;
    }
}
