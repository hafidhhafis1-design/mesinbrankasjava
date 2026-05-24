FROM openjdk:17-jdk-slim
COPY *.java .
RUN javac -d . *.java
CMD ["java", "belajaroop.MesinBankServer"]
