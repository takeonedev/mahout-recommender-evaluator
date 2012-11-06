package com.takeone.webapp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RecommenderForm {

	@NotNull
	@Min(value = 1)
	private Integer neighborhoodCount;
	
	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	private Double trainingPercentage;
	
	public Integer getNeighborhoodCount() {
		return neighborhoodCount;
	}
	public void setNeighborhoodCount(Integer neighborhoodCount) {
		this.neighborhoodCount = neighborhoodCount;
	}
	public Double getTrainingPercentage() {
		return trainingPercentage;
	}
	public void setTrainingPercentage(Double trainingPercentage) {
		this.trainingPercentage = trainingPercentage;
	}
}
