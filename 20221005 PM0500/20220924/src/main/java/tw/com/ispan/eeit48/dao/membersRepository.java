package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.model.members;
@Repository
public interface membersRepository extends JpaRepository<members, Integer>{
	@Query(value = "select * from members where member_account = ?1", nativeQuery = true)
    members queryBymember_account(String member_account);
	@Query(value = "select * from members where member_account = ?1 and member_password = ?2", nativeQuery = true)
    members queryBymember_password(String member_account,String member_password);
}

