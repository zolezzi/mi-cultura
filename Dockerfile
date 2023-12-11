FROM node:18.15.0 AS angular-build

WORKDIR /app/frontend/web-app
COPY frontend/web-app/package.json frontend/web-app/package-lock.json ./
RUN npm install -g npm@9.6.2
RUN npm install
COPY frontend/web-app .
RUN npm run build.prod

FROM adoptopenjdk/openjdk11:latest

WORKDIR /app
COPY . .
COPY --from=angular-build /app/frontend/web-app/dist /app/frontend/web-app/dist
RUN ./gradlew build

# Cambio para invalidar la caché
RUN echo "Cambiar algo en este paso"

# Copiar los archivos de la aplicación
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Establecer variables de entorno
ENV TZ=America/Argentina/Buenos_Aires

# Esperar a que la base de datos esté disponible y luego iniciar la aplicación
CMD java -cp .:lib/* $JAVA_OPTS unq.edu.li.pdes.micultura.MiCulturaApplication --spring.profiles.active=${SPRING_PROFILE:-prod}