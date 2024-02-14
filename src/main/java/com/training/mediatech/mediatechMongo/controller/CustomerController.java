package com.training.mediatech.mediatechMongo.controller;


import com.training.mediatech.mediatechMongo.CustomerEvent;
import com.training.mediatech.mediatechMongo.RequestType;
import com.training.mediatech.mediatechMongo.config.KafkaConfig;
import com.training.mediatech.mediatechMongo.model.CustomerModel;
import com.training.mediatech.mediatechMongo.service.CustomerService;
import com.training.mediatech.mediatechMongo.service.KafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    KafkaConfig kafkaConfig;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerModel> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerModel getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

   /* @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerModel customer) {
        try {
            customer.setCreatedDate(new Date(System.currentTimeMillis()));
            customerService.createCustomer(customer);

            return new ResponseEntity<CustomerModel>(customer, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }*/

    @PostMapping
    public CustomerModel createCustomer(@RequestBody CustomerModel customerModel) {
        customerService.createCustomer(customerModel);

        // create a producer record
        Producer<String, CustomerEvent> producer = new KafkaProducer<>(kafkaConfig.kafkaProperties());
        CustomerEvent customerEvent = CustomerEvent.newBuilder()
                .setId(customerModel.getId())
                .setRequestType(RequestType.CREATED)
                .setFirstname(customerModel.getFirstname())
                .setLastname(customerModel.getLastname())
                .setPhone(customerModel.getPhone())
                .build();
        kafkaService.sendToKafkaTopic(customerEvent, "topic_0", "CREATED", producer);
        return customerModel;
    }

    @PutMapping("/{id}")
    public CustomerModel updateCustomer(@PathVariable String id, @RequestBody CustomerModel customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/byField")
    public ResponseEntity<?> getCustomerByField(
            @RequestParam String fieldName,
            @RequestParam String fieldValue) {

        List<CustomerModel> customers;

        switch (fieldName.toLowerCase()) {
            case "id":
                customers = Collections.singletonList(customerService.getCustomerById(fieldValue));
                break;
            case "firstname":
                customers = customerService.getCustomerByFirstName(fieldValue);
                break;
            case "lastname":
                customers = customerService.getCustomerByLastName(fieldValue);
                break;
            // Add more cases for other fields as needed
            default:
                return ResponseEntity.badRequest().body("Invalid field name");
        }

        if (customers == null || customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No customer found for the specified " + fieldName);
        } else {
            return ResponseEntity.ok(customers);
        }
    }
}
