package org.brandao.examples;

import java.util.Date;

import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.Transient;

public class AirService extends AbstractService{

	private String airplane;
	
	private String seat;
	
	@Temporal("yyyy-MM-dd hh:mm")
	private Date departureDate;
	
	@Temporal("yyyy-MM-dd hh:mm")
	private Date arrivalDate;

	public String getAirplane() {
		return airplane;
	}

	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Transient
	public String getServiceType() {
		return "air";
	}
	
	public String toString(){
		return "Air";
	}
	
}
