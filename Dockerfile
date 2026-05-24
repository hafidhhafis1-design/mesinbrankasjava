FROM openjdk:17-jdk-alpine
COPY *.java .
RUN javac -d . *.java
CMD ["java", "belajaroop.MesinBankServer"]
