# Introduction
(50-100 words)
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)\
This application is used to simulate the grep command available within the Linux operating systems. 
It was developed using lambdas, stream APIs, Java, Maven, Intellij, Docker, and GitHub. The grep application is used to search for lines
that match the specified regex in the directory, then writes all the matching line results to an out file. The regex, root
directory, and outfile are all specified by the user. The root directory's subdirectories are searched recursively. (continue?)
The app is designed to recursively search the corresponding subdirectories in the specified root path to find lines that match the 
regex as declared by the user.

# Quick Start
How to use your apps?\
The grep application requires three command line arguments. (continue)
```bash
# App Usage
regex rootPath outFile
```
which are as follows:
- regex: The specified regex pattern to find within the files in the root directory or subdirectories. Only the matching line is written to the outfile.
- rootPath: The root directory path to search the subdirectories for files in
- outFile: The output file to write the matching lines - file will be created if it does not currently exist

There are two different ways to run the grep app, such as running it via the use of the jar file or by using the Docker image created from the built grep app.
```bash
# JAR file approach
java -jar grep-1.0-SNAPSHOT.jar ".*Romeo.*Juliet.*" ./data ./out/grep_result.txt

# Docker image approach
# write docker stuff here
```
Within both quick start commands above, the example regex is defining the match to be of lines that contain both 
Romeo and Juliet on the same line. The core java grep application will query files and directories within the root path
to find files to read lines from and see if the line matches. All matching lines will be written to the specified out file,
./out/grep_result in the above example.

# Implemenation
This section defines the implementation of the core Java grep application.
## Pseudocode
write `process` method pseudocode.\
The process method is defined to call all the other methods as (continue)
```
//Process method pseudocode
matchedLines = []; //String array to hold all matching lines retrieved from files
        
for file in listFilesRecursively(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
        
writeToFile(matchedLines)
```

## Performance Issue
(30-60 words)
Discuss the memory issue and how would you fix it

# Test
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)

# Deployment
How you dockerize your app for easier distribution?

# Improvement
List three things you can improve in this project.

