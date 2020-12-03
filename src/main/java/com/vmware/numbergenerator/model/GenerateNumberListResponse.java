
package com.vmware.numbergenerator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Generate Number List Response POJO
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GenerateNumberListResponse {
	@JsonProperty("task")
	private String uuid;
}
