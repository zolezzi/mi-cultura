#!/bin/bash

# Función para verificar la conexión
check_database_connection() {
    # Intenta conectar a la base de datos
    if docker exec -it mariadb_container mysql -h $DATABASE_HOST -u $DATABASE_USER -p$DATABASE_PASSWORD -P $DATABASE_PORT -D $DATABASE_NAME -e "SELECT 1;" &> /dev/null; then
        echo "La conexión a la base de datos se ha establecido correctamente."
        exit 0
    else
        echo "Error: No se pudo conectar a la base de datos."
        exit 1
    fi
}

# Llama a la función de verificación de la conexión
check_database_connection