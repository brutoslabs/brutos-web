package org.brandao.examples;

import java.util.Date;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;

public class SaleTransaction {

	private Long id;

	@Transient
	private Date date;

	private Long price;

	@Any(
		metaBean = 
			@Basic(bean = "serviceType"),
		metaType=
			String.class,
		metaValues = {
			@MetaValue(name = "air", target = AirService.class),
			@MetaValue(name = "hosting", target = HostingService.class) 
		}
	)
	private Service service;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
