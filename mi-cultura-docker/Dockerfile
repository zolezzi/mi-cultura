FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

# Copiar los archivos de la aplicación
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Establecer variables de entorno
ENV TZ=America/Argentina/Buenos_Aires

# Esperar a que la base de datos esté disponible y luego iniciar la aplicación
CMD java -cp .:lib/* $JAVA_OPTS unq.edu.li.pdes.micultura.MiCulturaApplication --spring.profiles.active=${SPRING_PROFILE:-prod}