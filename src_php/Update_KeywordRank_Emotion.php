<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $query = "SET SQL_SAFE_UPDATES = 0";
  mysqli_query($connect, $query);

  $Keyword_Word = $_GET['Keyword_Word'];

  $query = "UPDATE Keyword_Rank SET Keyword_Emotion = (SELECT COUNT(*) FROM Keyword_List WHERE Keyword_Word = $Keyword_Word AND Keyword_Emotion = 1)/(SELECT COUNT(*) FROM Keyword_List WHERE Keyword_Word = $Keyword_Word) WHERE Keyword_Word = $Keyword_Word";

  $result = mysqli_query($connect, $query);
     
  $response = array();
  $response["success"] = $result;
  
  echo json_encode($response);
   
  mysqli_close($connect);

?>