package com.retail.rewardsservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRewardsForSingleCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/customers/1")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.customerName", is("Ava Thompson")))
                .andExpect(jsonPath("$.totalTransactions", is(4)))
                .andExpect(jsonPath("$.totalRewardPoints", is(386.65)))
                .andExpect(jsonPath("$.monthlyRewards", hasSize(2)));
    }

    @Test
    void shouldDefaultToLastThreeMonthsWhenDatesAreMissing() throws Exception {
        mockMvc.perform(get("/api/rewards/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.periodStartDate", is("2026-01-09")))
                .andExpect(jsonPath("$.periodEndDate", is("2026-04-09")))
                .andExpect(jsonPath("$.totalTransactions", is(3)))
                .andExpect(jsonPath("$.totalRewardPoints", is(296.65)));
    }

    @Test
    void shouldRejectInvalidDateRange() throws Exception {
        mockMvc.perform(get("/api/rewards/customers/1")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Start date must be before or equal to end date")));
    }

    @Test
    void shouldReturnNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/customers/999")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Customer not found for id 999")));
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
