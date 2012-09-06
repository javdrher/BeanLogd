BeanLogd
========

Remote logging system developed at Next Generation Innovation. Includes an appender for log4j to dump it's messages in beanstalkd (a small, flexible queue), and an application to pull the messages back from the queue en store them in a RDBMS.