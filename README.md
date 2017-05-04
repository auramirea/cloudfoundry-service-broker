
# Demo

## Clone the repo and switch to the checked out repo directory:

```
git clone https://github.com/auramirea/cloudfoundry-service-broker.git
cd cloudfoundry-service-broker
```

## Running the script:
```
chmod +x go.sh
. go.sh
```

# Overview

Simple service broker that provides a virusscanner service with only one plan (free). 
The virusscanner tests if a file contains a virus by checking the suffix of the filename. If the filename ends in '.virus' then 
the file contains a virus, otherwise not.
To demo the service broker, a test application (demoFileUploader) is used. 

## Deploy the Service Broker to Cloud Foundry
Build it and push it:
```
./gradlew build & cf push
```

Register the service broker using the default username and the password obtained from the previous step:
```
cf create-service-broker generic-service-broker admin admin http://generic-service-broker.local.pcfdev.io
```

Check the list of service brokers:
```
cf service-brokers
```

Enable access to the service broker offering for all plans:
```
cf enable-service-access virusscanner
```

Check that your service is in the marketplace:
```
cf marketplace
```

## Push the virusscanner application
Build and push it:
```
./mvnw install & cf push
```

If you get error on mvn install run with the wrapper then set you jdk home. Ex:
```export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_112.jdk/Contents/Home/```

Create service instance:
`cf create-service virusscanner free free-virusscanner`


## Push the demo application
Build and push it:
```
./mvnw install & cf push
```

Call the app URL and upload a file to test if it contains a virus or not.
