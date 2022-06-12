<?php
$serverName ="HOWARD-WAYNE\SERVER0";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

$id= $_GET['id'];
$name=$_GET['name'];
$year= $_GET['year'];
$price=$_GET['price'];
$sl=$_GET['sl'];
$cat = $_GET['cat'];
$brand = $_GET['brand'];
$sqladd="INSERT INTO production_products(product_id,product_name,model_year,list_price,quantity,category_id,brand_id,store_id) VALUES ('$id','$name','$year','$price','$sl','$cat','$brand','ST1')";
$conaa=sqlsrv_query($conn,$sqladd);

header('location:index.php')
?>