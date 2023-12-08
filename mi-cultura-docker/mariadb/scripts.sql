CREATE DATABASE mi_cultura;
USE mi_cultura;

CREATE USER mi_cultura IDENTIFIED BY 'micultura123';
GRANT ALL ON mi_cultura.* TO 'mi_cultura'@'%' IDENTIFIED BY 'micultura123';
FLUSH PRIVILEGES;