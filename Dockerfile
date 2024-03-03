FROM openjdk:20
WORKDIR /app
EXPOSE 8080
COPY target/extractor-micro.jar extractor-micro.jar
ENTRYPOINT [ "java" ,"-jar","extractor-micro.jar" ]