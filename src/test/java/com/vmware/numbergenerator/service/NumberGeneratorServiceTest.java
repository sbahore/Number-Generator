
package com.vmware.numbergenerator.service;

import com.vmware.numbergenerator.model.GenerateNumberListRequest;
import com.vmware.numbergenerator.model.GenerateNumberListResponse;
import com.vmware.numbergenerator.model.GetNumberListResponse;
import com.vmware.numbergenerator.model.GetTaskStatusResponse;
import com.vmware.numbergenerator.model.enumeration.ResponseStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith({SpringExtension.class, MockitoExtension.class})

class NumberGeneratorServiceTest {
	@InjectMocks
	NumberGeneratorService numberGeneratorService;

	@Test
	void testCreateNumberSeries() {

		List<GenerateNumberListRequest> numberGeneratorRequest = getGenerateNumberListRequest();
		GenerateNumberListResponse generateNumberListResponse
		    = numberGeneratorService.createNumberSeries(numberGeneratorRequest);
		Assert.assertNotNull(generateNumberListResponse.getUuid());
	}

	@Test
	void testGetTaskStatus() throws InterruptedException {
		List<GenerateNumberListRequest> generateNumberListRequest = getGenerateNumberListRequest();
		GenerateNumberListResponse generateNumberListResponse
		    = numberGeneratorService.createNumberSeries(generateNumberListRequest);
		GetTaskStatusResponse taskStatusResponse
		    = numberGeneratorService.getTaskStatus(generateNumberListResponse.getUuid());
		Assert.assertEquals(taskStatusResponse.getResult(), ResponseStatus.IN_PROGRESS);
	}

	@Test
	void testSuccessTaskStatus() throws InterruptedException {
		List<GenerateNumberListRequest> generateNumberListRequest = getGenerateNumberListRequest();
		GenerateNumberListResponse generateNumberListResponse
		    = numberGeneratorService.createNumberSeries(generateNumberListRequest);
		Thread.sleep(30000);
		GetTaskStatusResponse taskStatusResponse
		    = numberGeneratorService.getTaskStatus(generateNumberListResponse.getUuid());
		Assert.assertEquals(taskStatusResponse.getResult(), ResponseStatus.SUCCESS);
	}

	@Test
	void testGetNumberListForSingleRecord() throws InterruptedException {
		List<GenerateNumberListRequest> numberGeneratorRequest = getGenerateNumberListRequest();
		GenerateNumberListResponse generateNumberListResponse
		    = numberGeneratorService.createNumberSeries(numberGeneratorRequest);
		Thread.sleep(30000);
		GetNumberListResponse getNumberListResponse
		    = numberGeneratorService.getNumberList(generateNumberListResponse.getUuid());
		Assert.assertEquals("10,8,6,4,2,0", getNumberListResponse.getResult());
	}

	@Test
	void testGetNumberListForBulkRecord() throws InterruptedException {
		List<GenerateNumberListRequest> numberGeneratorRequest
		    = getGenerateNumberListRequestForBulk();
		GenerateNumberListResponse generateNumberListResponse
		    = numberGeneratorService.createNumberSeries(numberGeneratorRequest);
		Thread.sleep(30000);
		GetNumberListResponse getNumberListResponse
		    = numberGeneratorService.getNumberList(generateNumberListResponse.getUuid());
		Assert.assertEquals(Arrays.asList("10,8,6,4,2,0", "100,90,80,70,60,50,40,30,20,10,0"),
		                    getNumberListResponse.getResults());
	}

	private List<GenerateNumberListRequest> getGenerateNumberListRequest() {
		List<GenerateNumberListRequest> numberGeneratorRequest = new ArrayList<>();
		GenerateNumberListRequest generateNumberListRequest = new GenerateNumberListRequest();
		generateNumberListRequest.setGoal(10L);
		generateNumberListRequest.setStep(2L);
		numberGeneratorRequest.add(generateNumberListRequest);
		return numberGeneratorRequest;
	}

	private List<GenerateNumberListRequest> getGenerateNumberListRequestForBulk() {
		List<GenerateNumberListRequest> numberGeneratorRequest = new ArrayList<>();
		GenerateNumberListRequest generateNumberListRequest = new GenerateNumberListRequest();
		GenerateNumberListRequest generateNumberListRequest1 = new GenerateNumberListRequest();
		generateNumberListRequest.setGoal(10);
		generateNumberListRequest.setStep(02);
		generateNumberListRequest1.setGoal(100);
		generateNumberListRequest1.setStep(10);
		numberGeneratorRequest.add(generateNumberListRequest);
		numberGeneratorRequest.add(generateNumberListRequest1);
		return numberGeneratorRequest;
	}

}
