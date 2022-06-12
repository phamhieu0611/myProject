<?php
$serverName ="HOWARD-WAYNE\SERVER0";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

$id= $_GET['id'];
$name1=$_GET['name'];
$mail= $_GET['mail'];
$phone1=$_GET['phone'];
$user=$_GET['user'];
$pass = $_GET['pass'];
$sqladd="INSERT INTO sales_staffs(staff_id,name,email,phone,username,pass) VALUES ('$id','$name1','$mail','$phone1','$user','$pass')";
$conaa=sqlsrv_query($conn,$sqladd);

header('location:staff.php')
?>