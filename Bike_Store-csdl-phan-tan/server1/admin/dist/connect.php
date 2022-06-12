<?php
$serverName ="HOWARD-WAYNE\SERVER1";
$uid ="sa";
$pwd ="123";
$databaseName ="Bike_Store";
$connectionInfo = array("UID" => $uid, "PWD" => $pwd, "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

if( $conn ) {
     echo "Connection established.<br />";
}else{
     echo "Connection could not be established.<br />";
     die( print_r( sqlsrv_errors(), true));
}
?>