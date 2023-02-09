package ca.jrvs.apps.practice;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc{
    @Override
    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("(?i).+\\.(jpg|jpeg)$");
        return pattern.matcher(filename).matches();
    }

    @Override
    public boolean matchIp(String ip) {
        Pattern pattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
        return pattern.matcher(ip).matches();
    }

    @Override
    public boolean isEmptyLine(String line) {
        Pattern pattern = Pattern.compile("^\\s*$");
        return pattern.matcher(line).matches();
    }

    public static void main(String[] args) {
        RegexExcImp matching = new RegexExcImp();
        System.out.println(matching.matchJpeg("image.jpg"));
        System.out.println(matching.matchJpeg("image.jpeg"));
        System.out.println(matching.matchIp("192.168.1.1"));
        System.out.println(matching.matchIp("256.256.256.256"));
        System.out.println(matching.matchIp("1000.0.1.1"));
        System.out.println(matching.isEmptyLine("sdfdf"));
        System.out.println(matching.isEmptyLine("   "));
    }
}



