<?php
  $db_host = 'localhost'; 
  $db_user = 'todaynews'; 
  $db_passwd = 'coding1998!';
  $db_name = 'todaynews';

  header('Content-Type: text/html;charset=UTF-8');

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);


  $query = "delete from Article";
  $result = mysqli_query($connect, $query);
     
  $response = array();
  $response["success"] = true;
  
  echo json_encode($response);

  mysqli_close($connect);

?>