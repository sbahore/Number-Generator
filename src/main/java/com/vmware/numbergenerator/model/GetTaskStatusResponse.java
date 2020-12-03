
package com.vmware.numbergenerator.model;

import com.vmware.numbergenerator.model.enumeration.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Task Status Response POJO
 **/
@Builder
@Getter
public class GetTaskStatusResponse {
	private ResponseStatus result;
}
