FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=17777"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]