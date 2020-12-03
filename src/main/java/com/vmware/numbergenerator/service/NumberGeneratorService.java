
package com.vmware.numbergenerator.service;

import com.vmware.numbergenerator.model.GenerateNumberListRequest;
import com.vmware.numbergenerator.model.GenerateNumberListResponse;
import com.vmware.numbergenerator.model.GetNumberListResponse;
import com.vmware.numbergenerator.model.GetTaskStatusResponse;
import com.vmware.numbergenerator.model.enumeration.ResponseStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * This is the service class for number generation
 **/
@Service
public class NumberGeneratorService {
	static ExecutorService executor = Executors.newCachedThreadPool();


	/**
	 * @param numberGeneratorRequest 
	 * @return UUID of the generated task
	 * This method creates a task for the provided goal and step and submits it for asynchronous processing
	 */
	public GenerateNumberListResponse
	       createNumberSeries(List<GenerateNumberListRequest> numberGeneratorRequest) {

		String randomUUID = String.valueOf(UUID.randomUUID());
		numberGeneratorRequest.forEach(generateNumberListRequest -> {
			NumberGenerator generator = new NumberGenerator(generateNumberListRequest.getGoal(),
			                                                generateNumberListRequest.getStep());

			Callable<List<String>> task = new NumberGeneratorCallable(generator, randomUUID);
			executor.submit(task);
		});
		CachingService.putIdInCache(randomUUID, numberGeneratorRequest.size());
		return GenerateNumberListResponse.builder()
		        .uuid(randomUUID)
		        .build();
	}

	/**
	 * @param uuid 
	 * @return status
	 * This method returns the current status of the Task
	 */
	public GetTaskStatusResponse getTaskStatus(String uuid) {
		ResponseStatus status;
		if (CachingService.getDataFromCache()
		        .containsKey(uuid) && Objects.isNull(CachingService.getIdFromCache(uuid)))
			status = ResponseStatus.SUCCESS;
		else if (Objects.nonNull(CachingService.getIdFromCache(uuid)))
			status = ResponseStatus.IN_PROGRESS;
		else
			status = ResponseStatus.ERROR;
		return GetTaskStatusResponse.builder()
		        .result(status)
		        .build();
	}


	/**
	 * @param uuid 
	 * @return generated List of number sequence
	 * This method returns the list of number sequence associated with the uuid
	 */
	public GetNumberListResponse getNumberList(String uuid) {
		List<String> numList = CachingService.getDataFromCache()
		        .get(uuid);

		if (Objects.nonNull(CachingService.getIdFromCache(uuid))) {
			return GetNumberListResponse.builder()
			        .result(ResponseStatus.IN_PROGRESS.name())
			        .build();

		} else if (CollectionUtils.isEmpty(numList)) {
			return GetNumberListResponse.builder()
			        .result(ResponseStatus.ERROR.name())
			        .build();
		} else if (numList.size() > 1) {
			return GetNumberListResponse.builder()
			        .results(numList)
			        .build();
		} else {
			return GetNumberListResponse.builder()
			        .result(numList.stream()
			                .findFirst()
			                .orElse(ResponseStatus.ERROR.name()))
			        .build();
		}
	}
}
