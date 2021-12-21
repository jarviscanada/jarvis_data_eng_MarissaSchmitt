package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaGrepImp implements JavaGrep{
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        //checking if there was 3 command line arguments given
        if(args.length != 3)
        {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        BasicConfigurator.configure();

        //creating a new local instance of the JavaGrepImp object that implements the JavaGrep interface
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException
    {
        try {
            //declaring a list of strings to hold all lines within the corresponding files that match the defined regex
            List<String> matchedLines = new ArrayList<>();
            for (File file : listFiles(getRootPath())) {
                for (String line : readLines(file)) {
                    if (containsPattern(line)) {
                        matchedLines.add(line);
                    }
                }
            }
            writeToFile(matchedLines);
        } catch (Exception ex)
        {
            //log error
            logger.error("Error:", ex);
        }
    }

    @Override
    public List<File> listFiles(String rootDir)
    {
        List<File> files = new ArrayList<>();
        File directoryOfFiles = new File(rootDir);
        File[] tempList = directoryOfFiles.listFiles();

        for(File file : tempList)
        {
            if(file.isFile())
            {
                files.add(file);
            }
            else
            {
                String dirPath = file.toString();
                //retrieving all recursive files and storing them within a list of files
                List<File> recursiveFiles = listFiles(dirPath);
                files.addAll(recursiveFiles); //adding files retrieved recursively to the files list
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile)
    {
        List<String> fileLines = new ArrayList<>();

        try {
            FileReader reader = new FileReader(inputFile); //creating a new file reader for the specified input file
            BufferedReader bufferedReader = new BufferedReader(reader); //creating a new buffered reader
            String nextLine; //declaring a string to hold the nextLine in the file when being read

            //ensuring the next line is not null
            while((nextLine = bufferedReader.readLine()) != null)
            {
                fileLines.add(nextLine);
            }
            reader.close();//closing the file reader
        } catch (Exception ex) {
            //logging possible IllegalArgumentException
            logger.error("Error:", ex);
        }

        return fileLines;
    }

    @Override
    public boolean containsPattern(String line)
    {
        //returning true if the line contains the regex pattern
        return line.matches(getRegex());
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException
    {
        File outFile = new File(getOutFile());
        if(!outFile.exists())//if the outfile does not currently exist...
        {
            outFile.createNewFile();//create the new file as specified in the command line arguments
        }
        OutputStream outStream = new FileOutputStream(getOutFile());
        OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
        for(String fileLine : lines)
        {
            writer.write(fileLine + "\n");//write line to file with new line at the end of each line
        }
        writer.close();//closing file output stream writer for specified outfile
    }

    @Override
    public String getRootPath() { return rootPath; }

    @Override
    public void setRootPath(String rootPath) { this.rootPath = rootPath; }

    @Override
    public String getRegex() { return regex; }

    @Override
    public void setRegex(String regex) { this.regex = regex; }

    @Override
    public String getOutFile() { return outFile; }

    @Override
    public void setOutFile(String outFile) { this.outFile = outFile; }
}
