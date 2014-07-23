jndi-utils
==========

Test if a JNDI is available and returns it value

## How?
###### Edit the `JNDICheck.java` file and change the properties:
`String name = "JNDI-DEFINITION-NAME";` 

Change JNDI-DEFINITION-NAME to  your jndi name for example `someService/1.0.0/soap/url`

`props.put (Context.PROVIDER_URL, "JNDI-SERVER");`

Change JNDI-SERVER to your server for example `jnp://dev-jndi:8080`



##TODO's
* Move to properties or as command line parameters
* Has the eclipse project and other stuff to run on eclipse IDE
