# IrcServer
An Irc server based on RFC 1459. 

Developer: Ryan Bruno <ryanbruno506@gmail.com>
## How To Use
1) Clone the repository
```
git clone https://github.com/JRoll506/IrcServer.git
```
2) Compile source code 
```
mvn install
```
3) Move jar to separate folder
```
cp IrcServer/target/IrcServer-<Version>.jar test/ && cd test/
```
4) Run the jar
```
java -jar IrcServer-<Version>.jar
```
## Creating Plugins
1) Compile and Install the IrcServer (See Above)
2) Create a pom.xml  
\**Must have the following lines*\*
```
<dependencies>
	<dependency>
		<groupId>com.rbruno</groupId>
		<artifactId>IrcServer</artifactId>
		<version>v1.0-RELEASE</version>
		<type>jar</type>
		<scope>compile</scope>
	</dependency>
</dependencies>
```
3) Create a Plugin class  
\**See IrcServer/src/main/java/com/rbruno/irc/plugin/Plugin.java for more Details*\*
4) Create a config.txt
```
main=Path.to.Main.Class
name=<PluginName>
```
