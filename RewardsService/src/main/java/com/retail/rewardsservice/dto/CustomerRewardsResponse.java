package com.retail.rewardsservice.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRewardsResponse {

    Long customerId;
    String customerCode;
    String customerName;
    String email;
    String periodStartDate;
    String periodEndDate;
    int totalTransactions;
    BigDecimal totalRewardPoints;
    List<MonthlyRewardSummary> monthlyRewards;
}
