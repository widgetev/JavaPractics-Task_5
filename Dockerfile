FROM openjdk:17
RUN mkdir /app
RUN rm -f /app/*.*
WORKDIR /app
COPY target/Task_5-1.0-SNAPSHOT.jar Task_5.jar
EXPOSE 8181
CMD ["java", "-jar", "Task_5.jar"]