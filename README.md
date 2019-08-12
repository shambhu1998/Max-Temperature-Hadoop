# Max-Temperature-Hadoop
1. Test Hadoop is running or not

    $hadoop version

2. Start dfs and yarn

    $ start-dfs.sh
    $ start-yarn.sh
    $ jps

3. set hadoop class path

    $ export HADOOP_CLASSPATH=$(hadoop classpath)

4. make sure

    $ echo $HADOOP_CLASSPATH

5. create directory on HDFS

    $ hadoop fs -mkdir <dir_name>
    
    For example
    $ hadoop fs -mkdir /maxtemp

6. And create a directory inside it for the input and output

    $ hadoop fs -mkdir <HDFS_INPUT_DIR>
    
    For example
    $ hadoop fs -mkdir /maxtemp/Input
    
    $ hadoop fs -mkdir /maxtemp/Output

7. check on localhost:50070

8. Upload the input file to that directory

    $ hadoop fs -put <INPUT_FILE_NAME> <HDFS_INPUT_DIRECTORY>
    
    For example
    $ hadoop fs -put '/home/hduser/Desktop/Max/input.txt' /maxtemp/Input

9. Check in http://192.168.146.131:50070/explorer.html#/maxtemp/Input

10. Change the current directory to the Example directory

    $ cd <Example Directory>
    
    For example
    $ cd ~/Desktop/Max/

11. Compile the Java Code

    $ javac -classpath ${HADOOP_CLASSPATH} -d <CLASS_FOLDER> <EXAMPLE_JAVA_FILE>
    
    For example
    $ javac -classpath ${HADOOP_CLASSPATH} -d '/home/hduser/Desktop/Max/classfile' '/home/hduser/Desktop/Max/MaxTemperature.java'

12. Check the files in classfile directory

13. Put the output files in one jar file

    $ jar -cvf <JAVR_FILE_NAME> -C <CLASS_FOLDER>
    
    $ jar -cvf /home/hduser/Desktop/Max/mt.jar -C /home/hduser/Desktop/Max/classfile/*

14. Check tha .jar file created or not

15. Now, Run the jar file on Hadoop

    $ hadoop jar <JAR_FILE> <CLASS_NAME> <HDFS_INPUT_DIRECTORY> <HDFS_OUTPUT_DIRECTORY>
   
    For example
    $ hadoop jar '/home/hduser/Desktop/Max/mt.jar' MaxTemperature /maxtemp/Input /maxtemp/Output

16. See the Output

    $ hadoop dfs -cat <HDFS_OUTPUT_DIR>/*
    
    For example
    $ hadoop dfs -cat /maxtemp/Output/*

17. to remove dir
    
    $ hadoop fs -rm -r /maxtemp/Output
