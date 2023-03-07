package com.example.demo.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface Repo extends CrudRepository<User,Integer>
{
	@Query("from User where em=:email")
    public User findByEm(@Param("email")String ema);
}
