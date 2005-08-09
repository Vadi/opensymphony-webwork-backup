Instructions for deploying WebWork example applications

**********************************************************************
* IMPORTANT! These webapps are not yet ready to be deployed!!!       *
**********************************************************************

In order to save on bandwidth and file size, the webapps provided
in WebWork are not ready to be deployed initially. Rather, the wars
in this directory do not contain any of the required jar files often
found in WEB-INF/lib.

Using Ant 1.6.5+, you can easily build fully-working war files.
Simply run the "ant" command in this directory for instructions on
how to build each web application. Every war file in this directory,
exception for base.war, is ready to be converted in to a full web
application.

Note: you can download Ant at http://ant.apache.org. The "ant" command
is found in $ANT_HOME/bin and can either be added to your system path
or referenced directly from the command line.