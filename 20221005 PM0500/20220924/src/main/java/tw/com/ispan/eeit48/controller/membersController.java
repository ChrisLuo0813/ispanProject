package tw.com.ispan.eeit48.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ispan.eeit48.dao.membersRepository;
import tw.com.ispan.eeit48.dao.rate_listRepository;
import tw.com.ispan.eeit48.model.members;
import tw.com.ispan.eeit48.model.rate_list;

//跨域需求要加這個Annoation
@CrossOrigin
@RestController
@RequestMapping

public class membersController {
	@Autowired
	membersRepository membersRepository;
	@Autowired
	rate_listRepository rateRepository;
	@Autowired
	members member,member1,member2;
	@Autowired
	rate_list rateList;
	@Autowired
	HttpSession localSession;
	@Autowired
	JavaMailSender mailSender;
	String number = "";
	
	BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder() ;
	Date date = new Date();
	SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//帳號建立
	    @CrossOrigin
	    @PostMapping(path = {"/doRegister"})
	    public String SaveAndCheckAccount(members createMember) {
	//account password email要和前端AJAX傳來的KEY名稱一致    
	//驗證帳號是否重複 重複會SYSOUT account exist    
	    	if(membersRepository.queryBymember_account(createMember.getMember_account())!=null) {
	            return "此帳號已被使用";
	    }else{      
	    			member.setMember_account(createMember.getMember_account());
	    	        member.setMember_email(createMember.getMember_email());
	    	        //將user輸入的密碼進行加密
	    	        member.setMember_password(bcpe.encode(createMember.getMember_password()));
	    	        //寫入創帳號日期
	    	        member.setAccount_create_time(dd.format(date));
	    	        member.setMember_rank("2");
	    	        membersRepository.saveAndFlush(member);
	    	        System.out.println("insertOK");
	    	        return "帳號創立成功！請重新登入";
	    	    }
	    }
	    
//登入	    
	    @CrossOrigin
	    @PostMapping(path = {"/loginRegister"})
	    public String LoginAndCheckAccount( members loginMember,HttpSession session) {
	 //在接受方法上宣告HttpSession session，並將這個session存到localSession裡面，後面都用localSession呼叫參數 	
	//account password要和前端AJAX傳來的KEY名稱一致    
	//驗證帳號密碼是否正確    
	    	member = membersRepository.queryBymember_account(loginMember.getMember_account());
	    	System.out.println(member.toString());
	    	if(member == null) {
	            System.out.println("帳號或密碼輸入錯誤");
	            return "帳號或密碼輸入錯誤";
	    }else{   
	    	Boolean	isright = bcpe.matches(loginMember.getMember_password(), member.getMember_password());
    		if(isright) {
			System.out.println("Login OK");
			member.setLast_login_time(dd.format(date));
			membersRepository.saveAndFlush(member);
			localSession = session;
			localSession.setAttribute("member", member);
			return "歡迎回來" + member.getMember_nickname();
					}
					System.out.println("帳號或密碼輸入錯誤");
			        return "帳號或密碼輸入錯誤";
	    	    }
	    	}

//前端頁面跳轉時確認登入狀況
	    @CrossOrigin
	    @GetMapping(path = {"/getRegister"})
	    public members GetAccount() {
	    	member = (members) localSession.getAttribute("member");
	    	if(member==null) {
	    	member1.setMember_id(0);
	    		return member1;
	    	}
	    return  member;
	    }
	    
//登出
	    @CrossOrigin
	    @GetMapping(path = {"/logoutRegister"})
	    public String LogoutAccount() {
	    	localSession.removeAttribute("member");
	    return  "帳號已登出";
	    
	    }
	    
//更新帳號資料
	    @CrossOrigin
	    @PostMapping(path = {"/changeRegister"})
	    public String changeAccount(@RequestParam("uploadImg") MultipartFile file,members changeMember) {
	    	member = (members) localSession.getAttribute("member");
	    	if (member == null) {
	            return "請先登入帳號";
	        }
	    	 String fileOldName = file.getOriginalFilename();
	    	 System.out.println(fileOldName);
	    	 String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(date) + "_" + fileOldName;
	         String path = "C:/Users/user/Documents/GitHub/ispanProject/HTML-master/PocketRoamingHTML/img/" + fileName;
	         File localSave = new File(path);
	         if (!localSave.getParentFile().exists()) {
	        	 localSave.getParentFile().mkdir();
	         }
	         try {
	             //上傳檔案
	             file.transferTo(localSave); //儲存檔案
//	             url = "http://188.131.163.160:8087" + "/file/image/" + fileName;//外部訪問路徑，對應本地的c:/fileupload
	        } catch (Exception e) {
	          System.out.println(e.toString());
	         }
	         
	         member.setMember_nickname(changeMember.getMember_nickname());
	         member.setMember_region(changeMember.getMember_region());
	         member.setMember_introduction(changeMember.getMember_introduction());
	         if(fileOldName != "") { member.setMember_icon(fileName);}
	         member.setMember_birthday(changeMember.getMember_birthday());
	    	membersRepository.saveAndFlush(member);
	    	localSession.setAttribute("member", member);
	    return  "帳號資料已更新";
	    
	    }
	    
//忘記密碼
	    @CrossOrigin
	    @PostMapping(path = {"/forgetRegister"})
	    public String forgetAccount(members forgetMember) {
	    	member = membersRepository.queryBymember_account(forgetMember.getMember_account());
	    	if(member == null) {	
	    		return  "帳號輸入錯誤";
	    }else {
	    	int n = 6;
			int[] Lottery = new int[n];
			int temp;
			boolean isRepeat;
			for(int i=0;i<Lottery.length;i++) {
					do {
						temp = (int)((Math.random()*48+1));				
						// 檢查機制
						 isRepeat = false;
						 	for (int j = 0; j < i; j++) {
						 			if (temp == Lottery[j]) {
								// 發生重複了
						 				isRepeat = true;
						 				break;
						 				}
						 			}	
					}while(isRepeat);
				Lottery[i] = temp;			
				}
			
			int a = 0;
			for(int num :Lottery) {
				a++;
				if(a<6) {
					number	+= num;				
				}
			}
			SimpleMailMessage message = new SimpleMailMessage();  
			  
			  message.setTo(member.getMember_email(),"tel2855973@gmail.com");
			  //前面信箱為收件人，後面信箱為寄件人
			  message.setSubject("口袋漫遊網：密碼遺失驗證信");
			  message.setText("您的驗證碼為:" + number + "。\n" + "請點擊以下網站輸入驗證碼。\n" + "http://127.0.0.1:5500/html/check_code.html" );
			  mailSender.send(message);
			  System.out.println(number);
	    	return  "請至信箱確認驗證碼，並點擊網址輸入驗證碼與新的密碼";
	    	}
	    } 
	    
//確認驗證碼
	    @CrossOrigin
	    @PostMapping(path = {"/checkCode"})
	    public String checkCode(String member_account,String checkCode,String newPassword) {
	    	System.out.println(checkCode);
	    	if(number.equals(checkCode)) {
	    		member = membersRepository.queryBymember_account(member_account);
	    		member.setMember_password(bcpe.encode(newPassword));
	    		membersRepository.saveAndFlush(member);
	    		number = "";
	    		 return  "帳號資料已更新，請用新密碼重新登入";
	    	}else {
	    	 return  "驗證碼輸入錯誤";
	    	}
	    }
	    
//搜尋評價列表
	    @CrossOrigin
	    @PostMapping(path = {"/checkRate"})
	    public List<rate_list> checkRate() {
	    	member = (members) localSession.getAttribute("member");
	    		Integer rated_id = member.getMember_id();	
	    		List<rate_list> rate = rateRepository.queryByrated_member(rated_id);
	    		 return rate;
	    }
	    
}