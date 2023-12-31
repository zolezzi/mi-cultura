FROM node:18.15.0 AS angular-build

WORKDIR /app/frontend/web-app
COPY frontend/web-app/package.json frontend/web-app/package-lock.json ./
RUN npm install -g npm@9.6.2
RUN npm install
COPY frontend/web-app .
RUN npm run build.prod

FROM gradle:latest AS builder
WORKDIR /app
COPY . .
RUN gradle build

FROM adoptopenjdk/openjdk11:latest

COPY . /app
WORKDIR /app
COPY --from=angular-build /app/frontend/web-app/dist /app/frontend/web-app/dist
#RUN chmod +x gradlew
#RUN ./gradlew build

# Copiar los archivos de la aplicación
#ARG DEPENDENCY=build/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Establecer variables de entorno
ENV TZ=America/Argentina/Buenos_Aires

# Esperar a que la base de datos esté disponible y luego iniciar la aplicación
#CMD java -cp .:lib/* $JAVA_OPTS unq.edu.li.pdes.micultura.MiCulturaApplication --spring.profiles.active=${SPRING_PROFILE:-prod}
#CMD java -jar /build/libs/micultura.jar --spring.profiles.active=${SPRING_PROFILE:-prod}
COPY --from=builder /app/build/libs/micultura.jar ./micultura.jar
#EXPOSE 8080
CMD ["java", "-jar", "micultura.jar", "--spring.profiles.active=${SPRING_PROFILE:prod}"]