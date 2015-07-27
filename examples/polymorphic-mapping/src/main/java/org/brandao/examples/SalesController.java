package org.brandao.examples;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.View;

@Controller("/sales")@Action(value="/new", view=@View("new"))
@RequestScoped
public class SalesController {

	@Inject@Transient
	private SalesMemoryEntityAccess salesMemoryEntityAccess;
	
	@Action("/save")@Result("entity")
	public SaleTransaction save(@Basic(bean="entity") 
		SaleTransaction entity){
		this.salesMemoryEntityAccess.save(entity);
		return entity;
	}
	
	@Action("/show/{id}")@Result("entity")
	public SaleTransaction show(@Basic(bean="id") Long id){
		SaleTransaction entity = this.salesMemoryEntityAccess.getById(id);
		return entity;
	}

	@Action("/remove/{id}")
	public void remove(@Basic(bean="id") 
		Long id){
		SaleTransaction entity = this.salesMemoryEntityAccess.getById(id);
		this.salesMemoryEntityAccess.remove(entity);
	}
	
	@Transient
	public List<SaleTransaction> getAll(){
		return this.salesMemoryEntityAccess.getAll();
	}
}
