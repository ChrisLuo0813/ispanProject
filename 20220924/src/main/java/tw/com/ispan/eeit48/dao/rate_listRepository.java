package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.model.rate_list;
@Repository
public interface rate_listRepository extends JpaRepository<rate_list, Integer>{

}
