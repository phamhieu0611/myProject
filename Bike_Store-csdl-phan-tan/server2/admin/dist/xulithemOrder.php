<?php
$serverName ="HOWARD-WAYNE\SERVER2";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

$id= $_GET['id'];
$sl=$_GET['sl'];
$price=$_GET['price'];
$count = $_GET['discount'];
$product = $_GET['product'];
$sqladd="INSERT INTO sales_order_items(item_id,quantity,list_price,discount,product_id,order_id) VALUES ('$id','$price','$sl','$count','$product','OD-1')";
$conaa=sqlsrv_query($conn,$sqladd);

header('location:order.php')
?>