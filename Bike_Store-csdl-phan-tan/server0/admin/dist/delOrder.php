<?php 
 $serverName ="HOWARD-WAYNE\SERVER0";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $sqldel = "DELETE FROM sales_order_items WHERE item_id ='$id'";
        sqlsrv_query($conn,$sqldel);
        header('location:order.php');
        
    }
 ?>