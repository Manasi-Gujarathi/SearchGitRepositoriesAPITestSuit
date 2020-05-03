package com.git.apis.search.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.git.apis.global.exception.CustomException;

public class ConfigUtil {

	//Global Setup Variables
	public static String jsonPathTerm;
	static Properties prop;
	static Logger log=LogManager.getLogger();

	//Sets Base URI
	public static String getBaseURI () {
		return prop.getProperty("BaseURI");
	}


	public static Properties getProperties() throws  CustomException {
		if (prop == null) {
			return loadProperties();
		} else {
			return prop;
		}

	}

	public static Properties loadProperties() throws CustomException {
		FileInputStream fis = null;
		File file=null;
		try {
			if(System.getProperty("envPropFileLocation")!=null && !System.getProperty("envPropFileLocation").isEmpty()){
				file = new File(System.getProperty("envPropFileLocation").replace("/", "//"));
				fis=new FileInputStream(file);
				log.info("Properties are loaded from file :" + file.getName());
			}
			else if(System.getProperty("env")==null || System.getProperty("env").isEmpty()){
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\prod.properties");
				log.warn("No environment is specified, loading default PROD environment properties");
			}
			else {
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+System.getProperty("environment")+".properties");
				log.info("Properties loaded for env:" + System.getProperty("env"));
			}

			prop=new Properties();
			prop.load(fis);
		}catch (FileNotFoundException fileNotFoundException) {
			throw new CustomException("PROPERTY_FILE_NOT_FOUND", "Environment property file not found in the path specified", fileNotFoundException.getMessage());
		}
		catch (IOException ioException) {
			throw new CustomException("PROPERTY_FILE_CANNOT_READ", "Environment property file cannot be read", ioException.getMessage());
		}
		catch (Exception e) {
			throw new CustomException("PROPERTY_FILE_NOT_FOUND", "Environment property file not found in the path specified", e.getMessage());
		}finally {
			try {
				if(fis!=null) fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new CustomException("INTERNAL_ERROR", "IO exception occured while closing the FileInpurStream", e);
			}
		}
		return prop;

	}
	public static void specifyMaximumResultCount() throws CustomException {
		String resultCount="";
		if(System.getProperty("maxcount")==null || System.getProperty("maxcount").isEmpty()){
			resultCount="5";
			getProperties().setProperty("resultCount", resultCount);
			log.warn("maxcount property is not specified :");
		}
		else {
			resultCount=System.getProperty("maxcount");
			getProperties().setProperty("resultCount", resultCount);
		}
		log.info("Maximum result count is set to :"+resultCount);
	}

}
