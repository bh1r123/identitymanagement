FROM openjdk:8
RUN mkdir /workspeace
COPY . /workspeace
WORKDIR /workspeace
RUN /workspeace/mvnw install 
RUN mv /workspeace/target/*.jar /workspeace/app.jar
ENTRYPOINT ["java","-jar","/workspeace/app.jar"]