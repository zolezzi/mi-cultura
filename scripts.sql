CREATE DATABASE mi_cultura;
USE mi_cultura;

CREATE USER mi_cultura IDENTIFIED BY 'micultura123';
-- Otorgar privilegios desde localhost
GRANT ALL ON mi_cultura.* TO 'mi_cultura'@'localhost';
-- Otorgar privilegios desde cualquier host
GRANT ALL ON mi_cultura.* TO 'mi_cultura'@'%';
FLUSH PRIVILEGES;