package org.brandao.examples;

import java.util.Date;

import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.Transient;

public class HostingService extends AbstractService{

	private String hotel;
	
	@Temporal("yyyy-MM-dd")
	private Date checkin;
	
	@Temporal("yyyy-MM-dd")
	private Date checkout;
	
	private String mealPlan;
	
	private String room;

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public String getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(String mealPlan) {
		this.mealPlan = mealPlan;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Transient
	public String getServiceType() {
		return "hosting";
	}

	public String toString(){
		return "Hosting";
	}
	
}
