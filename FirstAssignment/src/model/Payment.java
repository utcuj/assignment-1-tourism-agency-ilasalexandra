package model;

import java.sql.Date;

public class Payment {


	private int totalPayment;
	private Date paymentDate;
	/***************************************************************
	 *  Getters and Setters
	 ***************************************************************/
		
	public int getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(int totalPayment) {
		this.totalPayment = totalPayment;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	
	public Payment(int totalPayment, Date paymentDate) {
		this.totalPayment = totalPayment;
		this.paymentDate = paymentDate;
	}
	
	public Payment(int parseInt) {
		this.totalPayment=parseInt;
	}

}
