# Author : Jan

library(RPostgreSQL)

driver <- dbDriver("PostgreSQL")

connection <- dbConnect(driver, dbname={{data}}, host={{host}}, port={{port}}, user={{user}}, password={{pass}})

dbExistsTable(connection, "public.movie"){{retrieve}}