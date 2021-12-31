# Introduction
This application is used to simulate the grep command available within the Linux operating systems. 
It was developed using lambdas, stream APIs, Java, Maven, Intellij, Docker, and GitHub. The grep application is used to search for lines
that match the specified regex in the directory, then writes all the matching line results to an out file. The regex, root
directory, and outfile are all specified by the user. The root directory's subdirectories are searched recursively.
The app is designed to recursively search the corresponding subdirectories in the specified root path to find lines that match the 
regex as declared by the user.

# Quick Start

```bash
# App Usage
regex rootPath outFile
```
The grep application requires three command line arguments which are as follows:
- regex: The specified regex pattern to find within the files in the root directory or subdirectories. Only the matching line is written to the outfile.
- rootPath: The root directory path to search the subdirectories for files in
- outFile: The output file to write the matching lines - file will be created if it does not currently exist

There are two different ways to run the grep app, such as running it via the use of the jar file or by using the Docker image created from the built grep app.
```bash
# JAR file approach
java -jar grep-1.0-SNAPSHOT.jar ".*Romeo.*Juliet.*" ./data ./out/grep_result.txt

# Docker image approach
docker_user=your_docker_id
docker login -u ${docker_user} --password-stdin 
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep_result.txt
```
Within both quick start commands above, the example regex is defining the match to be of lines that contain both 
Romeo and Juliet on the same line. The core java grep application will query files and directories within the root path
to find files to read lines from and see if the line matches. All matching lines will be written to the specified out file,
grep_result file in the above example.

# Implementation
This section defines the implementation of the core Java grep application.
## Pseudocode
The process method is defined to call all the other methods as defined within the grep implementation, such as listFiles, 
readLines, containsPattern, and writeToFile. This is done through the process method to keep the main method 
free of any logic regarding the processing of the files.
```
//Process method pseudocode
matchedLines = []; //String array to hold all matching lines retrieved from files
        
for file in listFiles(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
        
writeToFile(matchedLines)
```

## Performance Issue
If the grep application has to process a large amount of data, it may get a runtime error of OutOfMemoryError, but this can be fixed
by using a buffered reader, stream APIs, and updating the grep interface to use a stream of strings rather
than a List.


# Test
The grep application was tested manually with the use of a sample data text document by having the lines read by the readLines method,
and comparing the matchedLines written to the outfile.
The regex pattern used to test the application was verified through the use of an online regex tester to ensure 
the proper matched line result was being written to the outfile based on the files within the root directory. 
Debugging tools of the IntelliJ IDE, such as breakpoints and the watch window for variables, were also used to ensure 
that the proper if statements were being executed based on the file line's contents.

# Deployment
The grep app was dockerized to for easier distribution through the use of the created Docker image.
This was done by creating a Dockerfile for the grep application and creating a Docker image locally to 
then uploading the image on DockerHub. The commands for creating the Dockerfile and local image are shown below.

```bash
#Create dockerfile 
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF
#Pakcage your java app
mvn clean package
#build a new docker image locally
docker build -t ${docker_user}/grep .
#verify your image
docker image ls | grep "grep"
#run docker container
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out
```

# Improvement
List three things you can improve in this project.
- Update grep interface to use stream API rather than List
- Improve exception handling, such as using finally within the try-catch blocks to ensure file IO is closed properly
- More use of the Logger class


