package com.aksh.marketlog.res.it;

public class BaseIT {
	
	protected String getServerUrl(){
		if(System.getProperty("docker.host.address")!=null){
			return "http://"+System.getProperty("docker.host.address")+":"+System.getProperty("server.port");
		}else if (System.getProperty("heroku.appName")!=null){
			return "https://"+System.getProperty("heroku.appName")+".herokuapp.com";
		}else{
			return "http://localhost:8080";
		}
	}
	

}
