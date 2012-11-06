package com.takeone.webapp;


import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.springframework.stereotype.Service;

@Service
public class RecommenderService {	
	
	/**
	 * 추천 평가한 값을 가져온다.
	 * 
	 * @param dataModel 데이터 모델
	 * @param userSimilarity 사용자 유사도 평가 알고리즘 
	 * @param neighborhoodCount 이웃의 수
	 * @param trainingPercentage 평가에 사용할 임계치
	 * @return
	 * @throws TasteException
	 * @throws IOException
	 */
	public double getRecommenderScore(DataModel dataModel, final UserSimilarity userSimilarity, final int neighborhoodCount, double trainingPercentage) throws TasteException, IOException {
		// 테스트를 위해 결과가 항상 동일하도록 설정
		RandomUtils.useTestSeed();
		
		// 추천기 평가 생성
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		
		// 추천기 생성
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighborhoodCount, userSimilarity, dataModel);
				
				return new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity);
			}
		};
		
		return evaluator.evaluate(recommenderBuilder, null, dataModel, trainingPercentage, 1.0);
	}
}
