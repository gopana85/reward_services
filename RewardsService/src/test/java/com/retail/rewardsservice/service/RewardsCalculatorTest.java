package com.retail.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class RewardsCalculatorTest {

    private final RewardsCalculator rewardsCalculator = new RewardsCalculator();

    @Test
    void shouldCalculateZeroPointsForAmountAtOrBelowFifty() {
        assertEquals(new BigDecimal("0.99"), rewardsCalculator.calculateRewardPoints(new BigDecimal("50.99")));
    }

    @Test
    void shouldCalculatePointsBetweenFiftyAndHundred() {
        assertEquals(new BigDecimal("26.45"), rewardsCalculator.calculateRewardPoints(new BigDecimal("76.45")));
    }

    @Test
    void shouldCalculatePointsAboveHundred() {
        assertEquals(new BigDecimal("90.00"), rewardsCalculator.calculateRewardPoints(new BigDecimal("120.00")));
    }

    @Test
    void shouldIncludeFractionalCentsWhenCalculatingRewards() {
        assertEquals(new BigDecimal("49.99"), rewardsCalculator.calculateRewardPoints(new BigDecimal("99.99")));
    }
}
