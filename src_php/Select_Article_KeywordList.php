<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

  $query = "SELECT Article.Article_Title, Article.Article_Content, Keyword_List.Keyword_Word, Keyword_List.Keyword_URL from Article join Keyword_List on Article.Article_URL = Keyword_List.Keyword_URL";

  $result = mysqli_query($connect, $query);
  $data = array();

  if($result){
    while($row=mysqli_fetch_array($result)){
      array_push($data,array('Article_Title'=>$row[0], 'Article_Content'=>$row[1], 'Keyword_Word'=>$row[2], 'Keyword_URL'=>$row[3]));
    }
    header('Content-Type: application/json; charset=utf8');

    $json = json_encode(array("Article_List"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

    echo $json;
  }
  // $response = array();
  // $response["success"] = true;
   
  mysqli_close($connect);

?>