package com.example.demo.Project;
import org.springframework.data.repository.CrudRepository;
public interface RepoPayment extends CrudRepository<Payment,Integer>
{
     public Payment findByOrderId(String s);
}
