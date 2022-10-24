package tw.com.ispan.eeit48.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecpay.payment.integration.AllInOne;
//import ecpay.payment.integration.ExampleAllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.QueryTradeInfoObj;

@RestController
@RequestMapping(path = { "/pay" })
public class payController {
	public  AllInOne all;
	
	@PostMapping
	public String ECpay(String orderID,String orderDate,Integer amount,String Item){
		System.out.println(orderID + orderDate + amount + Item);
		all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(orderID);
		obj.setMerchantTradeDate(orderDate);
		obj.setTotalAmount(amount.toString());
		obj.setTradeDesc("test Description");
		obj.setItemName(Item);
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setClientBackURL("http://localhost:8080/html/member_myorder.html");
		obj.setNeedExtraPaidInfo("Y");
		String form = all.aioCheckOut(obj, null);
		return form;
	}
	
//	@CrossOrigin
//	@RequestMapping(path = {"/result"})
//	public void ECpayResult(HttpServletRequest request) {
//		System.out.println("123");
//		System.out.println(request);
//	}
	
	@PostMapping(path = {"/query"})
	public String ECpayQuery(String TradeNo) {
		all = new AllInOne("");
		QueryTradeInfoObj obj = new QueryTradeInfoObj();
		obj.setMerchantTradeNo(TradeNo);
		System.out.println(all.queryTradeInfo(obj));
		return all.queryTradeInfo(obj);
	}
}
