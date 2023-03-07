package com.example.demo.Project;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="Payment")
public class Payment 
{
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        int id;
        private String paymentId;
        private String orderId;
        private long amount;
        private String receipt;
        private String status;
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name="Date")
        Date d;
        @ManyToOne
        @JoinColumn(name="User_Id")
        private User user2;
        public Payment() {
			// TODO Auto-generated constructor stub
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getPaymentId() {
			return paymentId;
		}
		public void setPaymentId(String paymentId) {
			this.paymentId = paymentId;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public long getAmount() {
			return amount;
		}
		public void setAmount(long amount) {
			this.amount = amount;
		}
		public String getReceipt() {
			return receipt;
		}
		public void setReceipt(String receipt) {
			this.receipt = receipt;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Date getD() {
			return d;
		}
		public void setD(Date d) {
			this.d = d;
		}
		public User getUser2() {
			return user2;
		}
		public void setUser2(User user2) {
			this.user2 = user2;
		}
		public Payment(int id, String paymentId, String orderId, long amount, String receipt, String status, Date d,
				User user2) {
			super();
			this.id = id;
			this.paymentId = paymentId;
			this.orderId = orderId;
			this.amount = amount;
			this.receipt = receipt;
			this.status = status;
			this.d = d;
			this.user2 = user2;
		}
		@Override
		public String toString() {
			return "Payment [id=" + id + ", paymentId=" + paymentId + ", orderId=" + orderId + ", amount=" + amount
					+ ", receipt=" + receipt + ", status=" + status + ", d=" + d + ", user2=" + user2 + "]";
		}
		
}
