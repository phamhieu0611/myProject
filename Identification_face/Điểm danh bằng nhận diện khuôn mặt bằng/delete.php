<?php


$connect = mysqli_connect("localhost", "root", "", "dacs5");  
mysqli_set_charset($connect,"utf8");

$id = $_GET['id']; // get id through query string

$del = mysqli_query($connect,"delete from student where id = '$id'"); // delete query

if($del)
{
    mysqli_close($db); // Close connection
    header("location:load_data.php"); // redirects to all records page
    exit;	
}
else
{
    echo "Error deleting record"; // display error message if not delete
}
?>