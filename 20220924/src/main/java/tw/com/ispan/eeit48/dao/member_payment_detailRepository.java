package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.model.member_payment_detail;

@Repository
public interface member_payment_detailRepository extends JpaRepository<member_payment_detail,Integer>{

}
