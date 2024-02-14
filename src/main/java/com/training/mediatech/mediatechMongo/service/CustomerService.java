package com.training.mediatech.mediatechMongo.service;

import com.training.mediatech.mediatechMongo.model.CustomerModel;
import com.training.mediatech.mediatechMongo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerModel getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerModel>  getCustomerByFirstName(String firstname) {
        return customerRepository.findByFirstname(firstname);
    }

    public List<CustomerModel> getCustomerByLastName(String lastname) {
        return customerRepository.findByLastname(lastname);
    }

    public CustomerModel createCustomer(CustomerModel customer) {
        // Vous pouvez ajouter ici la logique de validation des données si nécessaire
        return customerRepository.save(customer);
    }

    public CustomerModel updateCustomer(String id, CustomerModel customer) {
        // Vérifier si le client existe
        if (!customerRepository.existsById(id)) {
            return null;
        }
        // Mettre à jour les champs du client
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
