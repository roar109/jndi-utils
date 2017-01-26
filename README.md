jndi-utils
==========

Test if a JNDI is available and returns it value

## How?
###### Edit the `properties.properties` file and change the properties:

Add a value to the property `jndi` for example `someService/1.0.0/soap/url`

Add a value to the property `server` for example `jnp://dev-jndi:8080`, also you can use multiple jndi definitions separated by comma. i.e. `someService/1.0.0/soap/url,someXService/1.1.0/soap/url`

Run the `JndiConsumerMain` main class by some IDE or run the fat jar:
	
	java -jar target\jndi-utils-jar-with-dependencies.jar
