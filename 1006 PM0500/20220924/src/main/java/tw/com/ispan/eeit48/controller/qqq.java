package tw.com.ispan.eeit48.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.eeit48.dao.membersRepository;
import tw.com.ispan.eeit48.model.members;

@CrossOrigin
@RestController

public class qqq {
@Autowired
membersController m;

	
	 @CrossOrigin
	    @GetMapping(path = {"/get"})
	    public String met() {
		
	    	System.out.println(((members) m.localSession.getAttribute("member")).getMember_account());
	    return  "";
	    }
}