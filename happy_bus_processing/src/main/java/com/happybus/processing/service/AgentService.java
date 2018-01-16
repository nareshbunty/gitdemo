/*
] * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

/**
 * AgentssDAO is used to perform persistence operations on Agents Table
 * @author srinath
 * @since 1.0
 *
 */
public interface AgentService {
	public  String registerAgent(String jsonAgent,Long userId,Object token);
	 public String searchAgents(Long agentId,String phoneNumber1,Long userId,String token,String userRole);
	 public String updateAgents(String jsonAgent);
}
