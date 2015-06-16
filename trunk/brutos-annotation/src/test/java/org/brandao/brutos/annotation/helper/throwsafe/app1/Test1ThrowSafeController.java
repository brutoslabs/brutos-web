package org.brandao.brutos.annotation.helper.throwsafe.app1;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.DispatcherType;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.annotation.ThrowSafeList;

@Controller("/controller")
@ThrowSafe(target=Exception1.class)
@ThrowSafeList({
	@ThrowSafe(target=Exception2.class, dispatcher=DispatcherType.INCLUDE, name="ex"),
	@ThrowSafe(target=Exception3.class, rendered=false)
})
public class Test1ThrowSafeController {

	public void test1Action() throws Exception4{
		throw new Exception4();
	}

	public void test2Action(
			@Any(
				metaBean=@Basic(bean="exceptionType"),
				metaType=String.class,
				metaValues={
					@MetaValue(name="exception1", target=Exception1.class),
					@MetaValue(name="exception2", target=Exception2.class),
					@MetaValue(name="exception3", target=Exception3.class),
					@MetaValue(name="exception4", target=Exception4.class),
				}
			)
			Throwable trw) throws Throwable {
		throw trw;
	}
	
	@ThrowSafeList({
		@ThrowSafe(target=Exception1.class, rendered=false),
		@ThrowSafe(target=Exception3.class, resolved=true, view="/test.jsp")
	})
	public void test3Action(
			@Any(
				metaBean=@Basic(bean="exceptionType"),
				metaType=String.class,
				metaValues={
					@MetaValue(name="exception1", target=Exception1.class),
					@MetaValue(name="exception2", target=Exception2.class),
					@MetaValue(name="exception3", target=Exception3.class),
					@MetaValue(name="exception4", target=Exception4.class),
				}
			)
			Throwable trw) throws Throwable {
		throw trw;
	}
	
}
