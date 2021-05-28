<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $query = "UPDATE Data_State set State_Code = 1 where State_Code = 0";
  
  $result = mysqli_query($connect, $query);
     
  $response = array();
  $response["success"] = $result;
  
  echo json_encode($response);
   
  mysqli_close($connect);

?>