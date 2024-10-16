package com.greymatter.accounts.service.impl;

import com.greymatter.accounts.dto.*;
import com.greymatter.accounts.entity.Accounts;
import com.greymatter.accounts.entity.Customer;
import com.greymatter.accounts.exception.ResourceNotFoundException;
import com.greymatter.accounts.mapper.AccountsMapper;
import com.greymatter.accounts.mapper.CustomerMapper;
import com.greymatter.accounts.repository.AccountsRepository;
import com.greymatter.accounts.repository.CustomerRepository;
import com.greymatter.accounts.service.ICustomersService;
import com.greymatter.accounts.service.client.CardsFeignClient;
import com.greymatter.accounts.service.client.IdentityFeignClient;
import com.greymatter.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    private IdentityFeignClient identityFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        ResponseEntity<PersonDto> personDtoResponseEntity = identityFeignClient.findById("12345678");
        customerDetailsDto.setPersonDto(personDtoResponseEntity.getBody());


        return customerDetailsDto;

    }
}
