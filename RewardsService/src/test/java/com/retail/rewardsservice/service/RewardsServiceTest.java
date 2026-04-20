package com.retail.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootTest
class RewardsServiceTest {

    @Autowired
    private RewardsService rewardsService;

    @Test
    void shouldAggregateMonthlyAndTotalRewards() {
        assertEquals(new BigDecimal("386.65"), rewardsService.calculateRewardsForCustomer(1L,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 31)).getTotalRewardPoints());
    }

    @Test
    void shouldUseLastThreeMonthsWhenDatesAreMissing() {
        assertEquals("2026-01-09", rewardsService.calculateRewardsForCustomer(1L, null, null).getPeriodStartDate());
        assertEquals("2026-04-09", rewardsService.calculateRewardsForCustomer(1L, null, null).getPeriodEndDate());
        assertEquals(new BigDecimal("296.65"), rewardsService.calculateRewardsForCustomer(1L, null, null).getTotalRewardPoints());
    }

    @TestConfiguration
    static class FixedClockConfiguration {

        @Bean
        @Primary
        public Clock fixedClock() {
            return Clock.fixed(Instant.parse("2026-04-09T00:00:00Z"), ZoneId.of("UTC"));
        }
    }
}
