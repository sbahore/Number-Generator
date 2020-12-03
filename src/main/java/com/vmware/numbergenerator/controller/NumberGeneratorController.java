
package com.vmware.numbergenerator.controller;

import com.vmware.numbergenerator.model.GenerateNumberListRequest;
import com.vmware.numbergenerator.model.GenerateNumberListResponse;
import com.vmware.numbergenerator.model.GetNumberListResponse;
import com.vmware.numbergenerator.model.GetTaskStatusResponse;
import com.vmware.numbergenerator.service.NumberGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Controller for Number Generator APIs
 */
@RestController
@Api(value = "NUMBER GENERATOR")
@Validated
public class NumberGeneratorController {

	@Autowired
	NumberGeneratorService numberGeneratorService;

	@PostMapping("/api/generate")
	@ApiOperation(value = "Generate a sequence of numbers",
	              response = GenerateNumberListResponse.class)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public GenerateNumberListResponse
	       generate(@Valid @RequestBody GenerateNumberListRequest numberGeneratorRequest) {
		return numberGeneratorService
		        .createNumberSeries(Collections.singletonList(numberGeneratorRequest));
	}

	@GetMapping("/api/tasks/{uuid}/status")
	@ApiOperation(value = "Returns current status of task", response = GetTaskStatusResponse.class)
	@ResponseStatus(HttpStatus.OK)
	public GetTaskStatusResponse
	       getStatus(@Valid @PathVariable @NotBlank(message = "Task UUID can not be null") String uuid) {
		return numberGeneratorService.getTaskStatus(uuid);
	}

	@GetMapping("/api/tasks/{uuid}")
	@ApiOperation(value = "Returns generated sequence", response = GetNumberListResponse.class)
	@ResponseStatus(HttpStatus.OK)
	public GetNumberListResponse
	       getNumList(@Valid @RequestParam(defaultValue = "get_numlist") @NotBlank String action,
	                  @Valid @PathVariable @NotBlank(message = "Task UUID can not be null") String uuid) {
		return numberGeneratorService.getNumberList(uuid);
	}

	@PostMapping("/api/bulkGenerate")
	@ApiOperation(value = "Generate a sequence of numbers",
	              response = GenerateNumberListResponse.class)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public GenerateNumberListResponse
	       generateBulk(@Valid @RequestBody @NotEmpty List<@Valid GenerateNumberListRequest> numberGeneratorRequests) {
		return numberGeneratorService.createNumberSeries(numberGeneratorRequests);
	}
}
