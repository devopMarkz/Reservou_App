FROM amazoncorretto:17.0.14-alpine

ENV TZ=America/Sao_Paulo

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

CMD java -jar \
    -Duser.timezone=America/Sao_Paulo \
    app.jar \
    --spring.profiles.active=hmg