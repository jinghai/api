#!/bin/sh
#
#
#
echo "===========backup server app...==========="
tomcat_home=/opt/apache-tomcat-7.0.52
backup_dir=/home/backup/server
mkdir -p $backup_dir

rm -rf $backup_dir/server

rm -rf $tomcat_home/webapps/server/files
\cp -a $tomcat_home/webapps/server $backup_dir/

mysqldump -pitserver ipet > $backup_dir/ipet.sql

ln -sf /home/data/files $tomcat_home/webapps/server

echo "===========backup server app ok==========="