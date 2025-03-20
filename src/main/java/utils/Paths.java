package utils;

import cache.PropertiesCache;
import constants.OTPConstants;

public class Paths {

    /**
     * The local-application.properties file, should be a copy of the template-application.properties,
     * but renamed as: local-application.properties
     *
     * This folder has been added to .gitignore, in order to avoid pushing local configurations into the master branch.
     */
//    public static final String APPLICATION_PROPERTIES_IN_USE = PropertiesCache.getInstance().getProperty(OTPConstants.APPLICATION_PROPERTIES_IN_USE);
    public static final String APPLICATION_PROPERTIES_IN_USE = "jenkins/application.properties";
}
