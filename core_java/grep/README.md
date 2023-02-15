# Java Grep App

## Introduction
#### What does this project do?
This project is an implementation of the popular Linux command `grep` in Java. 
It allows users to search for specific strings in files or within a directory, 
making it easier and more efficient to find the desired information.
#### Who are the users?
This project is intended for anyone looking to quickly search for specific strings in files. 
With the grep app, users can filter files and find target strings with greater efficiency, 
saving time in the process.
#### Technologies used?
This project uses Java's *Stream API* and *lambda functions* to efficiently search for 
specific strings within files. Dependency management is handled through *Maven*, including the use 
of *slf4j* and *log4j* for log management. The Java `BufferedReader` and `BufferedWriter` classes are 
utilized for reading and writing data from files, respectively. The project is built and 
packaged into a jar file using Maven, and the jar file is then packaged into a *Docker* image 
for convenient deployment. The Docker image is pushed to Docker Hub for easy access.
## Quick Start
Retrieve the Docker image from Docker Hub and run it within a container that has connections 
to both input and output volumes.
```shell
#Syntax
docker run --rm -v [source volume] -v [output volume] en44zo/grep [regex pattern] [source path] [output path]
```
```shell
#Example
regex_pattern=".*Romeo.*Juliet.*"
src_dir="./data"
outfile=grep_$(date +%F_%T).txt
docker run --rm -v `pwd`/data:/data -v `pwd`/out:/out en44zo/grep ${regex_pattern} ${src_dir} /out/${outfile}
```
## Implementation
### Pseudocode
Process Method
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
ListFileRecursively
```
Files = []
    for file in rootdir;
        if file is directory;
            listFileRecursively(file);
        else Files add file;
Return Files;
            
```
ReadLines
```
lines = [];
    while scanner has next line;
        lines add nextline;
return lines;
```
ContainsPattern
```
return if line matches regex;
   
```
WriteToFile
```
for line in lines;
    write line to output file;
```
### Performance Issue
The OutOfMemoryError may occur when the available heap memory is insufficient to process large files. 
For instance, if the heap memory size is set to 5MB and a text file, such as shakespeare.txt, 
exceeds 5.3MB, the program will cause an OutOfMemoryError while reading the file. To address this 
performance issue, one solution is to divide the data files into smaller chunks and read them 
separately using the Stream API and BufferedReader. Another solution is to increase the heap memory
size using the Java -Xmx flag while running the program.

## Test
The Java grep app was thoroughly tested through manual testing using IntelliJ's debug mode. 
The testing process involved using a sample text file, such as shakespeare.txt, as input data and 
running the grep app manually. The results were then compared to a sample output file to ensure 
accuracy and proper functioning of the app.

## Deployment
Deployment under docker
```shell
docker login

cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

#Package the java app 
mvn clean package

#Build docker image locally
docker build -t ${docker_user}/grep .

#Push to dockerhub
docker push en44zo/grep
```

## Improvement
1. Automatically detect the size of the file and use buffering to divide large files into
smaller chunks, processing each chunk separately.
2. Conduct unit testing of the functions within the grep app using JUnit4.
3. In the `readLine` function, use BufferedReader instead of Scanner. This is because BufferedReader 
is generally faster and more efficient for reading data, as it reads data in larger chunks
compared to the Scanner class, which reads data one token at a time.