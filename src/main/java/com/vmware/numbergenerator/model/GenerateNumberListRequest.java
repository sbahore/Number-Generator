
package com.vmware.numbergenerator.model;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Number List Request POJO
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateNumberListRequest {
	@NonNull
	@Positive(message = "Step must be greater than 0")
	private long step;
	@NonNull
	@PositiveOrZero(message = "Goal can not be negative")
	private long goal;
}
