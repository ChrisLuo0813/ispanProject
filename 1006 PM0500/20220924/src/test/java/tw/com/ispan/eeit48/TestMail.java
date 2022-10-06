package tw.com.ispan.eeit48;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class TestMail {
 @Autowired
 JavaMailSender mailSender;
 @Test
 void sendToGmail() {
  SimpleMailMessage message = new SimpleMailMessage();  
  
  message.setTo("tel2855973@hotmail.com","tel2855973@gmail.com");
  message.setSubject("測試發信 請輸入驗證碼_3");
  message.setText("org.springframework.mail.SimpleMailMessage 透過 Gmail 發信。\n" +"驗證碼為:123456。\n" + "請點擊以下網站輸入驗證碼。 http://localhost:8080/" );
//  message.setText("請點擊以下網站輸入驗證碼。 http://localhost:8080/");
  
  mailSender.send(message);
 		}
}