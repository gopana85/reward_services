package com.retail.rewardsservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyRewardSummary {

    String month;
    BigDecimal rewardPoints;
}
