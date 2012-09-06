BeanLogd
========

Remote logging system developed at Next Generation Innovation. Includes an appender for log4j to dump it's messages in beanstalkd (a small, flexible queue), 
and an application to pull the messages back from the queue and store them in a RDBMS.

Installation
=======

1) Download and unpack beanstalkd (http://kr.github.com/beanstalkd) and run it with
$ ./beanstalkd -l 192.168.0.10 -p 9100
Using your own IP address and preferred port

2) Import the tiny sql file in the sql directory into a RDBMS database. When downloaded the application will connect to MySQL databases. If you wish to change the RDBMS, see below.
$ mysql -u username -p yourdatabase < sql/schema.sql

3) Configure the connection pool (uses the BoneCP library). Take a look at bonecp-config.xml, generally the default-config is ok, and modifying the <named-config name="config"> section 
is sufficient as it defines the database connections. For more information, see the BoneCP webpage (http://jolbox.com)

4) (optional) Configure the logging output of the application itself by modifying log4j.xml, in the extreme case, you can feed it to the beanstalk queue itself.

5) Compile and run the application:
$ sh run.sh 192.168.0.10 9100
Using the IP address and port used to start beanstalkd in step 1

The service should now be running



Configuring your application to log to beanstalkd
=======
Your logging platform is now running. It now comes down to telling log4j in your application to send its messages to beanstalkd. To do so you must add the beanlogd jar file in the 
target directory (the one not including the dependencies) to the classpath of your project. Next you have to configure your log4j to use the class com.nextgeninno.beanlogd.appender.BeanStalkAppender
Example if such a file:
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
  <appender name="beanstalk" class="com.nextgeninno.beanlogd.BeanStalkAppender">
  	<param name="Connection" value="192.168.0.10:9100"></param>
  </appender> 

  <root> 
    <priority value ="debug" ></priority> 
    <appender-ref ref="beanstalk" /> 
  </root>

</log4j:configuration>



Using another RDBMS (instead of MySQL)
=======
To do this, you should have a look in pom.xml for the JDBC dependency:
<dependency>
	<groupId>mysql</groupId>
    	<artifactId>mysql-connector-java</artifactId>
    	<version>5.1.21</version>
</dependency>

Change it to the correct driver (take a look at the central maven repository to find the proper artifact).
Next edit the JDBC URL in bonecp-config.xml.
