package com.yeahmobi.ssdb.client.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

  private static final String DEFAULT_FILE_NAME = "config.properties";
  private static Properties properties = new Properties();

  static {
    properties = getProperties(DEFAULT_FILE_NAME);
  }

  public static Properties getDefaultConfig() {
    return properties;
  }


  public static Properties getProperties(String fileName) {
    InputStream inputStream = null;
    Properties properties = new Properties();
    try {
      ClassLoader classLoader = ConfigUtil.class.getClassLoader();
      inputStream = classLoader.getResourceAsStream(fileName);
      if (null != inputStream) {
        properties.load(inputStream);
      }
      else {
        System.out.println("Unable to find configuration file: config.properties");
        throw new FileNotFoundException("config.properties not found in classpath");
      }
    }
    catch (IOException e) {
      System.out.println("Exception:" + e.getMessage());
    }
    finally {
      if (null != inputStream) {
        try {
          inputStream.close();
        }
        catch (IOException e) {
          //Ignore.
        }
      }
    }
    return properties;
  }

}