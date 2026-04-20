package com.retail.rewardsservice.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.retail.rewardsservice.domain.Customer;
import com.retail.rewardsservice.domain.Transaction;
import com.retail.rewardsservice.dto.CustomerRewardsResponse;
import com.retail.rewardsservice.dto.MonthlyRewardSummary;
import com.retail.rewardsservice.exception.CustomerNotFoundException;
import com.retail.rewardsservice.exception.InvalidDateRangeException;
import com.retail.rewardsservice.repository.CustomerRepository;
import com.retail.rewardsservice.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardsCalculator rewardsCalculator;
    private final Clock clock;

    public RewardsService(CustomerRepository customerRepository, TransactionRepository transactionRepository,
            RewardsCalculator rewardsCalculator, Clock clock) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.rewardsCalculator = rewardsCalculator;
        this.clock = clock;
    }

    public CustomerRewardsResponse calculateRewardsForCustomer(Long customerId, LocalDate startDate, LocalDate endDate) {
        LocalDate[] resolvedDateRange = resolveDateRange(startDate, endDate);
        LocalDate resolvedStartDate = resolvedDateRange[0];
        LocalDate resolvedEndDate = resolvedDateRange[1];
        validateDateRange(resolvedStartDate, resolvedEndDate);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        LOGGER.info("Calculating rewards for customer {} between {} and {}", customerId, resolvedStartDate, resolvedEndDate);
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateRange(customerId, resolvedStartDate, resolvedEndDate);
        return buildResponse(customer, transactions, resolvedStartDate, resolvedEndDate);
    }

    private LocalDate[] resolveDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now(clock);
        LocalDate resolvedEndDate = endDate != null ? endDate : today;
        LocalDate resolvedStartDate = startDate != null ? startDate : resolvedEndDate.minusMonths(3);
        return new LocalDate[]{resolvedStartDate, resolvedEndDate};
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("Start date must be before or equal to end date");
        }
    }

    private CustomerRewardsResponse buildResponse(Customer customer, List<Transaction> transactions,
            LocalDate startDate, LocalDate endDate) {
        List<Transaction> sortedTransactions = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .collect(Collectors.toList());

        Map<YearMonth, List<Transaction>> transactionsByMonth = sortedTransactions.stream()
                .collect(Collectors.groupingBy(transaction -> YearMonth.from(transaction.getTransactionDate()),
                        LinkedHashMap::new, Collectors.toList()));

        List<MonthlyRewardSummary> monthlyRewards = transactionsByMonth.entrySet().stream()
                .map(entry -> {
                    BigDecimal monthlyPoints = entry.getValue().stream()
                            .map(transaction -> rewardsCalculator.calculateRewardPoints(transaction.getAmount()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new MonthlyRewardSummary(entry.getKey().format(MONTH_FORMATTER), monthlyPoints);
                })
                .collect(Collectors.toList());

        BigDecimal totalRewardPoints = monthlyRewards.stream()
                .map(MonthlyRewardSummary::getRewardPoints)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CustomerRewardsResponse(
                customer.getCustomerId(),
                customer.getCustomerCode(),
                customer.getCustomerName(),
                customer.getEmail(),
                startDate.format(DATE_FORMATTER),
                endDate.format(DATE_FORMATTER),
                sortedTransactions.size(),
                totalRewardPoints,
                monthlyRewards);
    }
}
