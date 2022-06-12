<?php
	session_start();
	

	if(trim($_SESSION['user'])=='admin'){

		header('location:server0/admin/dist/index.php');
	}else{
		switch (trim($_SESSION['stid'])) {
  		case "ST1":
   		 	header('location:server1/admin/dist/index.php');
  	  		break;
 		case "ST2":
  		  	header('location:server2/admin/dist/index.php');
  			break;
	}

   
}

?>