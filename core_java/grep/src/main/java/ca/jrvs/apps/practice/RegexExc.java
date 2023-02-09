package ca.jrvs.apps.practice;

public interface RegexExc {
    /**
     * return true if filename extension is jpg or
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     *
     * @param ip
     * @return
     */
    public boolean matchIp(String ip);

    /**
     *
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);

}
