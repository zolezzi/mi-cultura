FROM adoptopenjdk/openjdk11:latest

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENV TZ=America/Argentina/Buenos_Aires
WORKDIR /app

ENTRYPOINT java -cp .:lib/* $JAVA_OPTS unq.edu.li.pdes.micultura.MiCulturaApplication	