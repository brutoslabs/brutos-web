package org.brandao.examples;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class SalesMemoryEntityAccess {

	private Map<Long,SaleTransaction> sales;
	
	private long id;
	
	public SalesMemoryEntityAccess(){
		this.sales = new HashMap<Long, SaleTransaction>();
		this.id    = 1;
		SaleTransaction entity = new SaleTransaction();
		entity.setDate(new Date());
		entity.setPrice(1200L);
		
		AirService service = new AirService();
		service.setAirplane("boeing 737");
		service.setArrivalDate(new Date());
		service.setDepartureDate(new Date());
		service.setPrice(1200L);
		service.setSeat("A1");
		entity.setService(service);
		
		this.save(entity);
		
		entity = new SaleTransaction();
		entity.setDate(new Date());
		entity.setPrice(1222L);
		
		HostingService service2 = new HostingService();
		service2.setHotel("hotel name");
		service2.setCheckin(new Date());
		service2.setCheckout(new Date());
		service2.setMealPlan("room only");
		service2.setPrice(1200L);
		service2.setRoom("Single");
		entity.setService(service2);
		
		this.save(entity);
	}
	
	public synchronized void save(SaleTransaction entity){
		if(entity.getId() == null){
			entity.setId(id++);
			entity.setDate(new Date());
		}
		
		this.sales.put(entity.getId(), entity);
	}
	
	public SaleTransaction getById(Long id){
		return this.sales.get(id);
	}
	
	public void remove(SaleTransaction entity){
		this.sales.remove(entity.getId());
	}
	
	public List<SaleTransaction> getAll(){
		return new ArrayList<SaleTransaction>(this.sales.values());
	}
	
}
