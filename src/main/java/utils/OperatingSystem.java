package utils;

public class OperatingSystem {

    public static OS getOperatingSystem()
    {
        // detecting the operating system using the `os.name` system property.
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return OS.WINDOWS;
        }

        else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return OS.LINUX;
        }

        else if (os.contains("mac")) {
            return OS.MAC;
        }

        else if (os.contains("sunos")) {
            return OS.SOLARIS;
        }

        return null;
    }

}
