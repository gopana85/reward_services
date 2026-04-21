package com.retail.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RewardsCalculatorTest {

    private final RewardsCalculator rewardsCalculator = new RewardsCalculator();

    @Test
    @DisplayName("Calculate points for amount above 50 but below or equal to 100")
    void shouldCalculatePointsForAmountAboveFifty() {
        assertEquals(new BigDecimal("0.99"), rewardsCalculator.calculateRewardPoints(new BigDecimal("50.99")));
    }

    @Test
    @DisplayName("Calculate points for amount between 50 and 100")
    void shouldCalculatePointsBetweenFiftyAndHundred() {
        assertEquals(new BigDecimal("26.45"), rewardsCalculator.calculateRewardPoints(new BigDecimal("76.45")));
    }

    @Test
    @DisplayName("Calculate points for amount above 100")
    void shouldCalculatePointsAboveHundred() {
        assertEquals(new BigDecimal("90.00"), rewardsCalculator.calculateRewardPoints(new BigDecimal("120.00")));
    }

    @Test
    @DisplayName("Include fractional cents when calculating rewards")
    void shouldIncludeFractionalCentsWhenCalculatingRewards() {
        assertEquals(new BigDecimal("49.99"), rewardsCalculator.calculateRewardPoints(new BigDecimal("99.99")));
    }
}
