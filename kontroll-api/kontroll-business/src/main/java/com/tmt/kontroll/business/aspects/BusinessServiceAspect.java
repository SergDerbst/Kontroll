package com.tmt.kontroll.business.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.business.BusinessPersistenceEntityValueConverter;
import com.tmt.kontroll.business.annotations.BusinessService;
import com.tmt.kontroll.business.exceptions.BusinessServiceInvocationFailedException;

/**
 * <p>
 * Aspect for all dao services annotated with {@link BusinessService} and whose name ends with
 * <code>BusinessService</code>. The pointcut refers to execution of any public method within
 * these classes.
 * </p>
 * <p>
 * The advice calls the {@link BusinessPersistenceEntityValueConverter} to assure that all entities
 * passed to or returned from any of these methods will be converted from persistence to business
 * entities and vice versa. Values passed or returned that aren't entities, will not be touched.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Aspect
@Component
public class BusinessServiceAspect {

	@Autowired
	BusinessPersistenceEntityValueConverter	entityConverter;

	@Pointcut("within(@com.tmt.kontroll.business.annotations.BusinessService *)")
	public void businessService() {}

	@Pointcut("execution(public * *(..))")
	public void allPublicMethods() {}

	@Around("businessService() && allPublicMethods()")
	public Object adviceBusinessServiceMethods(final ProceedingJoinPoint joinPoint) {
		try {
			System.out.println("####### Before proceed: " + joinPoint.getTarget().getClass().getName());
			final Object result = joinPoint.proceed();
			System.out.println("####### After proceed: " + result.toString());
			return this.entityConverter.convert(result);

		} catch (final Throwable e) {
			throw new BusinessServiceInvocationFailedException(e);
		}
	}
}
