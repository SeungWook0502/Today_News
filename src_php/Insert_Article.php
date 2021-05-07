<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
  $data_stream = "'".$_POST["Article_Title"]."','".$_POST['Article_Content']."','".$_POST['Article_Sidnum']."','".$_POST['Article_URL']."'";
    $query = "insert into Article(Article_Title,Article_Content,Article_Sidnum,Article_URL) values (".$data_stream.")";
    $result = mysqli_query($connect, $query);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($connect);

?>