
package com.vmware.numbergenerator.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * This class has core logic for generating sequence of numbers
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NumberGenerator {
	private Long step;
	private AtomicLong goal;
	private Random random = new Random();
	private LongUnaryOperator updateFunction = number -> number - step;

	public NumberGenerator(Long goal, Long step) {
		this.goal = new AtomicLong(goal);
		this.step = step;
	}

	/**
	 * @return Next Sequence of Number
	 * @throws InterruptedException
	 * This method returns the next number in sequence till the number reaches 0
	 */
	public String getNext() throws InterruptedException {
		Thread.sleep(getSimulatedTime());
		return Optional.of(goal.getAndUpdate(updateFunction))
		        .map(String::valueOf)
		        .orElse("");
	}


	/**
	 * @return status
	 * This method checks if the sequence has reached 0 or not
	 */
	public boolean isNotFinished() {
		return goal.get() >= 0;
	}


	/**
	 * @return random time for simulations
	 */
	private long getSimulatedTime() {
		return OptionalLong.of(random.longs(1000, 3000)
		        .findAny()
		        .orElse(0L))
		        .getAsLong();
	}

}
