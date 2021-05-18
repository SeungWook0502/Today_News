<?php
  $db_host = 'localhost'; 
  $db_user = 'todaynews'; 
  $db_passwd = 'coding1998!';
  $db_name = 'todaynews';

  header('Content-Type: text/html;charset=UTF-8');

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $Article_Title = $_GET['Article_Title'];
  $Article_Content = $_GET['Article_Content'];
  $Article_Sidnum = $_GET['Article_Sidnum'];
  $Article_URL = $_GET['Article_URL'];

  $query = "INSERT into Article(Article_Title,Article_Content,Article_Sidnum,Article_URL) values ('$Article_Title','$Article_Content','$Article_Sidnum','$Article_URL')";
  $result = mysqli_query($connect, $query);
     
  $response = array();
  $response["success"] = true;
  
  echo json_encode($response);

  mysqli_close($connect);

?>