<?php
$serverName ="HOWARD-WAYNE\SERVER2";

$databaseName ="Bike_Store";
$connectionInfo = array( "Database"=>$databaseName);
$conn = sqlsrv_connect( $serverName, $connectionInfo);
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
            <a class="navbar-brand" href="index.php">Bike Stores</a>
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
                        <a class="dropdown-item" href="http://localhost:4430/csdl/login.php">Logout</a>
                    </div>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Các Trang Quản Lí</div>
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
                                <i class="fas fa-table mr-1"></i>
                                Table Product
                                <a href="addproduct.php"  class="btn btn-info">Add Product</a>
                            </div>
                            
                            <div >
                                 <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Product id</th>
                                                <th>Name Product</th>
                                                <th>Model Year</th>
                                                <th>Price</th>
                                                <th>Category</th>
                                                <th>brand</th>
                                                <th>Quantity</th>
                                                <th>Edit</th>
                                            </tr>
                                        </thead>
                                        <?php 
                                            $sqlSelect = "SELECT * FROM production_products";
                                            $result = sqlsrv_query($conn,$sqlSelect);
                                            $count= 0 ;
                                            while ($row = sqlsrv_fetch_array($result)) {
                                                $sql2 = "SELECT * FROM production_categories where category_id='".$row['category_id']."'";
                                                $bcd = sqlsrv_query($conn, $sql2);
                                                $row2 = sqlsrv_fetch_array($bcd);
                                                $sql3 = "SELECT * FROM production_brands where brand_id='".$row['brand_id']."'";
                                                $bcd2 = sqlsrv_query($conn, $sql3);
                                                $row3 = sqlsrv_fetch_array($bcd2);
                                                $count++;
                                                  
                                         ?>
                                        <tbody>
                                            <tr>
                                                <td><?php echo $count ?></td>
                                                <td><?php echo $row["product_id"]?></td>
                                                <td><?php echo $row["product_name"]?></td>
                                                <td><?php echo $row["model_year"]?></td>
                                                <td><?php echo $row["list_price"]?></td>
                                                <td><?php echo $row2["category_name"]?></td>
                                                <td><?php echo $row3["brand_name"]?></td>
                                                <td><?php echo $row["quantity"]?></td>
                                                <td>
                                                    <a href="editproduct.php?id=<?php echo $row['product_id'] ?>">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="delproduct.php?id=<?php echo $row['product_id'] ?>">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                             <?php 
                                        } ?>
                                        </tbody>
                                        
                                    </table>
                                    </div>
                                </div>
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
