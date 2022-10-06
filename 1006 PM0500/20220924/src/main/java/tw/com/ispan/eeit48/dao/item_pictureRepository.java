package tw.com.ispan.eeit48.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.ispan.eeit48.model.item_picture;


@Repository
public interface item_pictureRepository extends JpaRepository<item_picture, Integer>{

}