<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $Keyword_Word = $_GET['Keyword_Word'];
  $Keyword_Count = $_GET['Keyword_Count'];

  $query = "INSERT into Keyword_Rank(Keyword_Word,Keyword_Count) values ('$Keyword_Word','$Keyword_Count')";
  $result = mysqli_query($connect, $query);
     
  $response = array();
  $response["success"] = true;
  
  echo json_encode($response);
   
  mysqli_close($connect);

?>