File:       gptd2gp.java
Purpose:    External tabel based on a JDBC conection to teradata with these arg
              arg[0] select to be executed
	      arg[1] ip of the Teradata host

EXAMPLE

CREATE EXTERNAL WEB TABLE  test.t1
(tablename TEXT,
databasename TEXT,
creatorname TEXT
)
EXECUTE 'java -cp /home/gpadmin/gplink/jar/terajdbc4.jar:/home/gpadmin/gplink/jar/tdgssconfig.jar:/home/gpadmin/gplink/jar gptd2gp "select trim(tablename),trim(databasename),trim(creatorname) from dbc.tables" "192.168.0.16"'  on master
FORMAT 'TEXT' (DELIMITER '|')
