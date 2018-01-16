package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happybus.beans.SearchBusParams;
import com.happybus.integration.dao.SearchBusDAO;
import com.happybus.util.JsonUtil;
@Service
public class SearchBusServiceImpl implements SearchBusService {
private static Logger logger=Logger.getLogger(SearchBusServiceImpl.class);
	@Autowired
	private SearchBusDAO searchBusDAO;
	@Override
	public String searchBusesForPassengers(String jsonPassenger) {
		//logger
        logger.info("Entered into searchBusses  : "+jsonPassenger);
        
        //converting json input into java object
        SearchBusParams sourceDestinationDate =JsonUtil.convertJsonToJava(jsonPassenger, SearchBusParams.class);
       
        //logger
        logger.info("Entered into searchBusses  : "+sourceDestinationDate.getSource()+" "+sourceDestinationDate.getDestination()+" "+sourceDestinationDate.getDate_of_journey());
        
        //Interacting with Search bus DAO
		List searchedBusesList= searchBusDAO.searchBusesForPassengers(sourceDestinationDate);
		
		//logger
		logger.info("List : "+searchedBusesList+" size : "+searchedBusesList.size()); 
		
		//converting resulted list of buses into json
		String jsonSearchBussesList=JsonUtil.convertJavaListToJson(searchedBusesList);
		
		//logger
		logger.info("Response of  searchBusses  : "+jsonSearchBussesList);

		return jsonSearchBussesList ;
	}

}
