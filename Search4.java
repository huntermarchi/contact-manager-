package com.example.demo.Project;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Search4 
{
	     @Autowired
	     Repo ro1;
	     @Autowired
	     RepoContact rc1;
	     @Autowired
	     RepoPayment rp;
         @GetMapping("/search/{query}")
         public ResponseEntity<?>searchBar(@PathVariable("query")String s,Principal p)
         {
        	 User u=ro1.findByEm(p.getName());
        	 List<Contact>l=rc1.findByCnameContainingAndU(s, u);
        	 return ResponseEntity.ok(l);
         }
         @PostMapping("/payment")
         @ResponseBody
         public String newPayment(@RequestBody Map<String,Object>m,Principal p) throws RazorpayException
         {
        	 int amo=Integer.parseInt(m.get("amount").toString());
        	 User u=ro1.findByEm(p.getName());
        	 var rz=new RazorpayClient("rzp_test_I1RpzPpF69qLm5","iJt2irljjWi2rlByTV0X4Y42");
        	 JSONObject orderRequest = new JSONObject();
        	 orderRequest.put("amount", amo*100); // amount in the smallest currency unit
        	 orderRequest.put("currency", "INR");
        	 orderRequest.put("receipt", "order_recipt_"+u.getNa());
        	 Order order =rz.Orders.create(orderRequest);
        	 System.out.println(order);
        	 //save data into database
        	 Payment p1=new Payment();
        	 int am=order.get("amount");
        	 java.sql.Timestamp t=new java.sql.Timestamp(System.currentTimeMillis());
        	 java.sql.Date d=new java.sql.Date(t.getTime());
        	 p1.setAmount(am/100);
        	 p1.setOrderId(order.get("id").toString());
        	 p1.setReceipt(order.get("receipt"));
        	 p1.setStatus(order.get("status"));
        	 p1.setD(d);
        	 p1.setUser2(u);
        	 rp.save(p1);
        	 return order.toString();
         }
         @PostMapping("/paymentUpdate")
         public ResponseEntity<?>updatePayment(@RequestBody Map<String,Object>m)
         {
        	 java.sql.Timestamp t=new java.sql.Timestamp(System.currentTimeMillis());
        	 java.sql.Date d=new java.sql.Date(t.getTime());
        	 Payment ro=rp.findByOrderId(m.get("oi").toString());
        	 ro.setPaymentId(m.get("pi").toString());
        	 ro.setStatus(m.get("s").toString());
        	 ro.setD(d);
        	 rp.save(ro);
        	 return ResponseEntity.status(HttpStatus.ACCEPTED).build();
         }
}
