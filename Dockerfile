FROM eclipse-temurin:21-jdk-alpine
COPY *.java .
RUN javac -d . *.java
CMD ["java", "belajaroop.MesinBankServer"]
