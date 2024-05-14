package com.javaweb.service;


import com.javaweb.model.dto.CustomerProfileDto;
import com.javaweb.model.dto.CustomerSignUpDto;
import com.javaweb.model.dto.FeedbackDto;
import com.javaweb.model.dto.MembershipDto;
import com.javaweb.model.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAllCustomers();
    boolean existCustomer(String email, String password);
    boolean existEmail(String email);
    void createCustomer(CustomerSignUpDto customerSignUpDto);
    boolean registerMembership(MembershipDto membershipDto);
    CustomerProfileDto getCustomerProfile(Integer customerId);
    void giveFeedback(FeedbackDto feedbackDto);
}
