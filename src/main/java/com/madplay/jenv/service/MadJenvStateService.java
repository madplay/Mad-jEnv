package com.madplay.jenv.service;

import com.intellij.openapi.components.ServiceManager;
import com.madplay.jenv.config.MadJenvState;

/**
 * @author madplay
 */
public class MadJenvStateService {

	private MadJenvState state;

	MadJenvStateService() {
		state = new MadJenvState();
	}

	public static MadJenvStateService getInstance() {
		return ServiceManager.getService(MadJenvStateService.class);
	}

	public MadJenvState getState() {
		return state;
	}
}
