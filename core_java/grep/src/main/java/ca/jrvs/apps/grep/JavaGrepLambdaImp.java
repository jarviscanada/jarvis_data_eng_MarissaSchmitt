package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{
    public static void main(String[] args) {
        if(args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            javaGrepLambdaImp.process();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> readLines(File inputFile)
    {
        List<String> lines = new ArrayList<>();
        String fileName = inputFile.toString();
        Path path = Paths.get(fileName);
        try {
            lines = Files.lines(path).collect(Collectors.toList());

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return lines;
    }

    @Override
    public List<File> listFiles(String rootDir)
    {
        List<File> files = new ArrayList<>();
        try{
            List<File> temp = new ArrayList<>();
            //placing all directories into a temp list
            temp = Files.list(Paths.get(rootDir)).map(Path::toFile).collect(Collectors.toList());
            //creating a consumer function to see if the directory contains a file or if the function
                //need to be called recursively for the subdirectories
            Consumer<File> dirComparison = file ->{
                if(file.isFile())
                {
                    files.add(file);
                }
                else
                {
                    String dirPath = file.toString();
                    //retrieving all recursive files and storing them within a list of files
                    List<File> recursiveFiles = listFiles(dirPath);
                    files.addAll(recursiveFiles);
                }
            };
            temp.forEach(dirComparison);//check if the items retrieved from the lambda were directories that contain files
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return files;
    }
}
