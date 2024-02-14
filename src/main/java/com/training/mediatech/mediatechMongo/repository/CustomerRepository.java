package com.training.mediatech.mediatechMongo.repository;

import com.training.mediatech.mediatechMongo.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerModel, String> {

    public List<CustomerModel> findByFirstname(String firstName);
    public List<CustomerModel> findByLastname(String lastName);
}
