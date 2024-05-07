package com.greymatter.accounts.service;

import com.greymatter.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
