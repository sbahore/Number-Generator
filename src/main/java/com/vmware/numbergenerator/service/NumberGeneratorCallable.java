
package com.vmware.numbergenerator.service;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * Callable class for generating sequence of numbers asynchronously
 **/
@AllArgsConstructor
public class NumberGeneratorCallable implements Callable<List<String>> {

	private NumberGenerator numberGenerator;
	private String uuid;

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public List<String> call() throws Exception {
		StringBuilder sb = new StringBuilder();
		while (numberGenerator.isNotFinished()) {
			sb.append(numberGenerator.getNext())
			        .append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		updateNumberMap(uuid, sb.toString());
		return CachingService.getDataFromCache()
		        .get(uuid);
	}

	/**
	 * @param uuid 
	 * @param ids
	 * This method updates the ids and number sequence in the cache
	 * Also purges ids from cache once task is completed
	 */
	private void updateNumberMap(String uuid, String ids) {
		Integer idFromCache = CachingService.getIdFromCache(uuid);
		Optional.ofNullable(idFromCache)
		        .ifPresent(id -> CachingService.putIdInCache(uuid, id - 1));
		Optional.ofNullable(CachingService.getIdFromCache(uuid))
		        .filter(value -> value == 0)
		        .ifPresent(value -> CachingService.purgeIdFromCache(uuid));
		CachingService.getDataFromCache()
		        .computeIfAbsent(uuid, numList -> new ArrayList<>())
		        .add(ids);
	}
}
