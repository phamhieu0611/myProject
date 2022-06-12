<?php
$serverName ="HOWARD-WAYNE\SERVER0";
$uid ="sa";
$pwd ="123";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);
if( $conn ) {
     echo "Connection established.<br />";
}else{
     echo "Connection could not be established.<br />";
     die( print_r( sqlsrv_errors(), true));
}


                                    


?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title></title>
</head>
<body>
	 <table>
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Store id</th>
                                                <th>Name Store</th>
                                                <th>Phone</th>
                                                <th>Mail</th>
                                                <th>Street</th>
                                                <th>City</th>
                                                <th>Xử lí</th>
                                            </tr>
                                        </thead>
                                        <?php 
                                            $sqlSelect = "SELECT * FROM sales_stores";
                                            $result = sqlsrv_query($conn,$sqlSelect);
                                            if (sqlsrv_num_rows($result) > 0) {  
                                                $count = 0;
                                                while ($row = sqlsrv_fetch_array($result)) {
                                                    $count++;
                                         ?>
                                        <tbody>
                                            <tr>
                                                <td><?php echo $count ?></td>
                                                <td><?php echo $row["store_id"]?></td>
                                                <td><?php echo $row["store_name"]?></td>
                                                <td><?php echo $row["phone"]?></td>
                                                <td><?php echo $row["email"]?></td>
                                                <td><?php echo $row["street"]?></td>
                                                <td><?php echo $row["city"]?></td>
                                                <td>
                                                    <a href="editsong.php?id=<?php echo $row['idBaiHat'] ?>">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="delsong.?idphp=<?php echo $row['idBaiHat'] ?>">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                             <?php }
                                             echo $result;
                                        } ?>
                                        </tbody>
                                        
                                    </table>
</body>
</html>