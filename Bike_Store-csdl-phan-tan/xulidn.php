<?php
	session_start();

	$serverName ="HOWARD-WAYNE\SERVER0";

	$databaseName ="Bike_Store";
	$connectionInfo = array( "Database"=>$databaseName);
	$conn = sqlsrv_connect( $serverName, $connectionInfo);
	
	$_SESSION['user']=$_GET['user'];
	$_SESSION['pass']=$_GET['pass'];
	$_SESSION['stid'] = 0;
    $sql = " SELECT * from sales_staffs where username='".$_GET['user']."'";
	$abc= sqlsrv_query($conn , $sql) ;
	$row= sqlsrv_fetch_array($abc) ;

	
	if(($_SESSION['user']==$row['username'])&&($_SESSION['pass']==$row['pass']))
	{	
		if($row['store_id'] != NULL){
			$_SESSION['stid'] = trim($row['store_id']);
		}
		$_SESSION['stid'] = $row['store_id'];
		echo $_SESSION['stid'];
		header('location:chuyentrang.php');
	}
	else{
		$_SESSION['user']="";
		$_SESSION['pass']="";
		header('location:login.php');
	}


?>