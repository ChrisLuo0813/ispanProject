package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.model.order_list;
@Repository
public interface order_listRepository extends JpaRepository<order_list, Integer>{

}
