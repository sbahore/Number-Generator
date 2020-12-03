
package com.vmware.numbergenerator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Number List Response POJO
 **/
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetNumberListResponse {
	private String result;
	private List<String> results;
}
