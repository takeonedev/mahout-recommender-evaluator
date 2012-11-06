package com.takeone.webapp;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	private RecommenderService recommenderService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws TasteException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, ModelAndView mav) throws TasteException, IOException {
		
		RecommenderForm recommenderForm = new RecommenderForm();
		recommenderForm.setNeighborhoodCount(3);
		recommenderForm.setTrainingPercentage(0.95);
		
		mav.addObject("recommenderForm", recommenderForm);
		
		mav.setViewName("home");
		return mav;
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView process(@Valid RecommenderForm recommenderForm, HttpServletRequest request, ModelAndView mav, BindingResult result) throws IOException, TasteException {
        if (result.hasErrors()) {
        	mav.setViewName("/");
            return mav;
        }		
		// 이웃은
		int neighborhoodCount = recommenderForm.getNeighborhoodCount();
		
		// 임계치
		double trainingPercentage = recommenderForm.getTrainingPercentage();

		// 파일로 데이터 모델 생성
		DataModel dataModel = new FileDataModel(new File(request.getSession().getServletContext().getRealPath("resources/ua.base")));

		mav.addObject("neighborhoodCount", neighborhoodCount);		
		mav.addObject("trainingPercentage", trainingPercentage);
		
		// 각 알고리즘 별 평가치
		mav.addObject("euclideanDistanceSimilarity", recommenderService.getRecommenderScore(dataModel,  new EuclideanDistanceSimilarity(dataModel), neighborhoodCount, trainingPercentage));
		mav.addObject("pearsonCorrelationSimilarity", recommenderService.getRecommenderScore(dataModel,  new PearsonCorrelationSimilarity(dataModel), neighborhoodCount, trainingPercentage));
		mav.addObject("logLikelihoodSimilarity", recommenderService.getRecommenderScore(dataModel,  new LogLikelihoodSimilarity(dataModel), neighborhoodCount, trainingPercentage));
		mav.addObject("tanimotoCoefficientSimilarity", recommenderService.getRecommenderScore(dataModel,  new TanimotoCoefficientSimilarity(dataModel), neighborhoodCount, trainingPercentage));
		
		mav.setViewName("home");
		return mav;
	}
	
}
