# Bienvenido a MiCultura

[![Tests](https://github.com/zolezzi/mi-cultura/actions/workflows/micultura_cicd.yml/badge.svg)](https://github.com/zolezzi/mi-cultura/actions/workflows/micultura_cicd.yml)
![coverage](.github/badges/jacoco.svg)
![branches coverage](.github/badges/branches.svg)

**Micultura** Explora la riqueza cultural de nuestro país a través de nuestra plataforma en línea, que te brinda acceso a una amplia gama de información proveniente del Ministerio de Cultura y otras entidades gubernamentales. Descubre una extensa lista de museos, instituciones culturales destacadas y convocatorias relevantes, todo en un solo lugar. Mantente al tanto de las últimas noticias y eventos culturales, sumérgete en nuestro patrimonio cultural y encuentra oportunidades imperdibles, todo desde nuestra plataforma web fácil de usar.

## Descripción

**Micultura** Es desarrollado con Java 11 en su backend y con Angular v15 en su frontend. Además utiliza otros frameworks/tecnologías como Spring Boot, Docker, TypeScript, Gradle, Flyway, MySQL.

## Instalación

Para la instalación del **backend** debemos tener instalado **Java** en nuestra máquina si no lo tienes podes bajar acá:  [link](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html), también debe tener **Gradle** si tenes windows o linux podes ver este: [tutorial](https://gradle.org/install/)  el proyecto cuenta con una base de datos dockerizada lo cual deberán tener docker instalado en su ambiente: [link windows](https://docs.docker.com/desktop/install/windows-install/) o [link Linux](https://docs.docker.com/engine/install/ubuntu/)

- Se debe ejecutar en la raíz del proyecto el comando ```gradle clean build docker -Penv=dev```
- Se debe ejecutar en la raíz del proyecto el comando ```docker compose up```
- Dirigirse a [http://localhost:8080/](http://localhost:8080/) para ver la pagina web
- Para ver documentación de la API Rest dirigirse a [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Licencia

La Licencia Pública General de GNU versión 3 (GPL-3.0) es una licencia de software de código abierto que fue creada por la Free Software Foundation (FSF). La GPL-3.0 es una de las licencias más populares y utilizadas para proyectos de software libre debido a sus términos y condiciones. [Aquí](https://www.gnu.org/licenses/gpl-3.0.html) hay algunas razones por las que puedes considerar utilizar la licencia GPL-3.0

## UML
![Diagrama UML del proyecto MiCultura](/documentation/diagram-micultura.png "Diagrama UML del proyecto MiCultura")