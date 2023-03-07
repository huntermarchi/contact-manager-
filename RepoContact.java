package com.example.demo.Project;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface RepoContact extends JpaRepository<Contact,Integer>
{
       @Query("from Contact as c where c.u.id=:id1")
       public Page<Contact>findContactsById(@Param("id1")int i,Pageable pq);
     
       public List<Contact>findByCnameContainingAndU(String name,User u);
}
