<?php
  $db_host = "localhost"; 
  $db_user = "todaynews"; 
  $db_passwd = "coding1998!";
  $db_name = "todaynews";

  header("Content-Type: text/html;charset=UTF-8");

  $connect = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);
  $data_stream = "'".$_POST["Keyword_Word"]."','".$_POST['Keyword_Count']."'";
    $query = "insert into Keyword_Rank(Keyword_Word,Keyword_Count) values (".$data_stream.")";
    $result = mysqli_query($connect, $query);
     
    if($result)
      echo "1";
    else
      echo "-1";
     
    mysqli_close($connect);

?>