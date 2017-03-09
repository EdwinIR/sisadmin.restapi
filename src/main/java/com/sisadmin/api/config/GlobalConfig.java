package com.sisadmin.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class GlobalConfig {
	
		//toma valores del portal.properties
		@Value("${sisfe.emitterId}")
		public  String GlobalId;

		public String getGlobalId() {
			return GlobalId;
		}

		public void setGlobalId(String globalId) {
			GlobalId = globalId;
		}
		
		
		
		

}
