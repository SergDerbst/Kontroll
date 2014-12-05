package com.tmt.kontroll.context.request.data;

import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

/**
 * <p>
 * Basic type of all request param DTOs. These are created by {@link RequestHandlingService}s out
 * of request parameters for further processing, for example during form submits.
 * </p>
 * <p>
 * <i>Note: </i>The field names of request param DTOs must always match the keys of request parameters.
 * </p>
 *
 * @author SergDerbst
 *
 */
public interface RequestParamDto {

}
