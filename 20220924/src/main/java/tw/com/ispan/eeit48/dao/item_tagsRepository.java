package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.model.item_tags;

@Repository
public interface item_tagsRepository extends JpaRepository<item_tags, Integer>{

}