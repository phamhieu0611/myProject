<?php  
 //load_data.php 

 $connect = mysqli_connect("localhost", "root", "", "dacs5");  
 mysqli_set_charset($connect,"utf8");
 $output = '';  
 
  $sql = "SELECT * FROM student";    
  $result = mysqli_query($connect, $sql);  
  if (mysqli_num_rows($result) > 0) {
      // output data of each row
      $output.='<table class="table" id="show_product">';
      $output.='  <thead>';
      $output.='    <tr>';
      $output.='      <th>ID</th>';
      $output.='      <th>ID Sinh Viên</th>';
      $output.='      <th>Họ & Tên</th>';
      $output.='      <th>Hình Ảnh</th>';
      $output.='      <th>Trạng Thái</th>';
      $output.='      <th>Xóa</th>';
      $output.='    </tr>';
      $output.='  </thead>';
      $output.='  <tbody>';
      
      while($row = mysqli_fetch_assoc($result)) {
        $output.= "<tr>"; 
        $output.= "<td>".$row['id']."</td>"; 
        $output.= "<td>".$row['idstudent']."</td>"; 
        $output.= "<td>".$row['name']."</td>"; 
        $output.= "<td><img src=".$row['img']." style='width: 150px; height: 150px'></td>";
        if ($row['status'] == 0)
          $output.= "<td>Vắng</td>";
        else
          $output.= "<td>Có Mặt</td>";
        // $output.= "<td>".$row['status']."</td>";
        $output.= '<td><a href="delete.php?id='.$row['id'].'">Xóa</a></td>';
        $output.= "</tr>";
      }
      $output.='</tbody>';
      $output.= "</table>";
 } 
 echo $output; 
?> 