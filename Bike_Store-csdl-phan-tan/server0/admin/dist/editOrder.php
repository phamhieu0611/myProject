<?php 
$serverName ="HOWARD-WAYNE\SERVER0";
$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);

    $sqlSelectProduct = "SELECT * FROM production_products";
    $resultProduct = sqlsrv_query($conn,$sqlSelectProduct)  ;
    $rowProduct = sqlsrv_fetch_array($resultProduct);
    

  
  
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $sqlGetId ="SELECT * FROM sales_order_items WHERE item_id ='$id'";
        $result = sqlsrv_query($conn,$sqlGetId);
        $row = sqlsrv_fetch_array($result);

    }

    if (isset($_POST['submit'])) {
    $quantity1 =$_POST['quantity'];
    $price = $_POST['price'];
    $discount1 = $_POST['discount'];
    $product = $_POST['product'];
    

    $sqledit = "UPDATE sales_order_items SET quantity ='$quantity1',list_price='$price',discount='$discount1',product_id='$product' WHERE item_id ='$id'";
    $resultedit = sqlsrv_query($conn,$sqledit) ;
    header('location:order.php');
        
    }
 ?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="css/styles.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand" href="index.php">EDIT</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" />
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="button"><i class="fas fa-search"></i></button>
                    </div>
                </div>
            </form>
            <!-- Navbar-->
            <ul class="navbar-nav ml-auto ml-md-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="#">Settings</a>
                        <a class="dropdown-item" href="#">Activity Log</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="login.html">Logout</a>
                    </div>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
             <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">C??c Trang Qu???n L??</div>
                            <a class="nav-link" href="index.php">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Product Manage
                            </a>
                            <a class="nav-link" href="staff.php">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Staff Manage
                            </a>
                            <a class="nav-link" href="order.php">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Order Manage
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Start Bootstrap
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                        <h1 class="mt-4">Product Manage</h1>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-upload"></i>
                                Edit
                            </div>
                            <div class="card-body">
                            <form action="#" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label for="inputtencasi">Quantity:</label>
                                  <input type="text" class="form-control" placeholder="Quantity" name="quantity" value="<?php echo $row["quantity"];?>">
                                </div>
                                <div class="form-group">
                                    <label for="inputtencasi">Price:</label>
                                  <input type="text" class="form-control" placeholder="Price" name="price" value="<?php echo  $row["list_price"];?>">
                                </div>
                                <div class="form-group">
                                    <label for="inputtencasi">Discount:</label>
                                  <input type="text" class="form-control" placeholder="Price" name="discount" value="<?php echo  $row["discount"];?>">
                                </div>
                                <div class="form-group">
                                    <label for="selectalbum">Product_id:</label>
                                  <select name="product" id="idAlbum" class="form-control">

                                      <?php 
                                        while ($rowProduct = sqlsrv_fetch_array($resultProduct)) {
                                            ?>
                                            <option value="<?php echo $rowProduct['product_id'] ?>"><?php echo $rowProduct['product_name'] ?></option>
                                            <?php  
                                        }
                                       ?>
                                  </select>                                 
                                </div>
                                <br>
                                <input type="submit" name="submit" class="btn btn-success">
                            </form>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2020</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="assets/demo/chart-area-demo.js"></script>
        <script src="assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
        <script src="assets/demo/datatables-demo.js"></script>
    </body>
</html>
