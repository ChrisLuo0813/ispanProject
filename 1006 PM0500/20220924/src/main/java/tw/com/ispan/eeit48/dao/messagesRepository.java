package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.eeit48.model.messages;
@Repository
public interface messagesRepository  extends JpaRepository<messages, Integer>{

}

