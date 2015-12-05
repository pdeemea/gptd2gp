CREATE EXTERNAL WEB TABLE  test.t2
(tablename TEXT,
databasename TEXT,
creatorname TEXT,
numero INT

)
EXECUTE 'java -cp /home/gpadmin/gplink/jar/terajdbc4.jar:/home/gpadmin/gplink/jar/tdgssconfig.jar:/home/gpadmin/gplink/jar
gptd2gp
"select trim(tablename),trim(databasename),trim(creatorname),1 from dbc.tables" "192.168.0.16"'  on master
FORMAT 'TEXT' (DELIMITER '|')
