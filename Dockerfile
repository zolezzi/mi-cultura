FROM adoptopenjdk/openjdk11:latest

# Descargar wait-for-it.sh y hacerlo ejecutable
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Copiar los archivos de la aplicación
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# Establecer variables de entorno
ENV TZ=America/Argentina/Buenos_Aires
WORKDIR /app

# Esperar a que la base de datos esté disponible y luego iniciar la aplicación
CMD /app/wait-for-it.sh mariadb:3306 && java -cp .:lib/* $JAVA_OPTS unq.edu.li.pdes.micultura.MiCulturaApplication