package ca.jrvs.apps.grep;

//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JavaGrepImp implements JavaGrep{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {

        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        }catch (Exception ex){
            javaGrepImp.logger.error(ex.getMessage(), ex);
        }
    }





    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<String>();
        for(File file : this.listFiles(this.getRootPath())){
            for (String string : readLines(file)){
                if (this.containPattern(string)){
                    matchedLines.add(string);
                }
            }
        }
        this.writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<>();
        File dir = new File(rootDir);
        for (File file : dir.listFiles()){
            if (file.isDirectory()){
                files.addAll(this.listFiles(file.getPath()));
            }else{
                files.add(file);
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
           this.logger.error(e.getMessage(), e);
        }
        return lines;


    }

    @Override
    public boolean containPattern(String line) {
        return line.matches(this.getRegex());
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.getOutFile()));

        for (String line : lines){
            writer.write(line);
            writer.newLine();
        }
        writer.close();

    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath=rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex=regex;
    }

    @Override
    public String getOutFile() {
        return this.outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile=outFile;
    }
}
