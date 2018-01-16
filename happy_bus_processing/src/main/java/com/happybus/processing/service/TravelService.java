/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;
/**
 * 
 * 
 * @author sathish.bandi
 * @since 1.0
 */
public interface TravelService {
public  String registerTravel(String jsonTravel,Long userId,String token);
/**
 *  This method contains travel register number alredy register or not operation performed
 * @param travelRegisterNumber
 * @return
 */

public String checkTravelRegisterNumber(String travelRegisterNumber);
public String searchTravels(String jsonSearchTravels);


}










