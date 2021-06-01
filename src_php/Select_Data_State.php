<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $query = "SELECT State_Code FROM Data_State ORDER BY Data_Upload_DateTime DESC LIMIT 1";

  $result = mysqli_query($connect, $query);
  $data = array();

  if($result){
    while($row=mysqli_fetch_array($result)){
      array_push($data,array('State_Code'=>$row[0]));
    }
    header('Content-Type: application/json; charset=utf8');

    $json = json_encode(array("Data_State"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

    echo $json;
  }
  // $response = array();
  // $response["success"] = true;
   
  mysqli_close($connect);

?>