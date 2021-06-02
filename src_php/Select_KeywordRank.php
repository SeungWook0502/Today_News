<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $query = "SELECT Keyword_Word from Keyword_Rank";

  $result = mysqli_query($connect, $query);
  $data = array();

  if($result){
    while($row=mysqli_fetch_array($result)){
      array_push($data,array('Keyword_Word'=>$row[0]));
    }
    header('Content-Type: application/json; charset=utf8');

    $json = json_encode(array("Keyword_Rank"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

    echo $json;
  }
  // $response = array();
  // $response["success"] = true;
   
  mysqli_close($connect);

?>