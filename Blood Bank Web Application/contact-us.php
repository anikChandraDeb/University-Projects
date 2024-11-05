<?php require_once('inc/top.php');?>
  <body>
    <?php require_once('inc/header.php');?>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                   
                    <div class="comment-box">
                       <h2>Contact box</h2>
                        <div class="row">
                            <div class="col-md-12">
                               <?php
                                if(isset($_POST['submit'])){
                                    $name=mysqli_real_escape_string($con,$_POST['name']);
                                    $email=mysqli_real_escape_string($con,$_POST['email']);
                                    $website=mysqli_real_escape_string($con,$_POST['website']);
                                    $comment=mysqli_real_escape_string($con,$_POST['comment']);
                                    $to="anikprem222@gmail.com";
                                    $header = "From: $name<$email>";
                                    $subject="Massage from $name";
                                    $message="Name: $name \n\nEmail: $email \n\nTitle: $website \n\nMessage:\n $comment";
                                    if(empty($name) or empty($email) or empty($comment)){
                                        $error = "All (*) Feilds are required";
                                    }
                                    else{
                                        if(mail($to,$subject,$message,$header)){
                                            $msg="Message Has Been Sent";
                                        }
                                        else{
                                            $error="Message Has Not Been Sent";
                                        }
                                    }
                                    
                                }
                                ?>
                                <form action="" method="post">
                                    <div class="form-group">
                                        <label for="full-name">Full name*</label>
                                        <?php 
                                        if(isset($error))
                                        {
                                            echo"<span class='pull-right' style='color:red;'>$error</span>";
                                        }
                                        else if(isset($msg))
                                        {
                                            echo"<span class='pull-right' style='color:green;'>$msg</span>";
                                        }
                                        ?>
                                        <input type="text" id="full-name" class="form-control" placeholder="Full name" name="name">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email address*</label>
                                        <input type="eamil" id="email" class="form-control" placeholder="Email Address" name="email">
                                    </div>
                                    <div class="form-group">
                                        <label for="website">Message Title</label>
                                        <input type="text" id="website" class="form-control" placeholder="Message title" name="website">
                                    </div>
                                    <div class="form-group">
                                        <label for="comment">Message*</label>
                                        <textarea name="comment" class="form-control" id="" cols="30" rows="10" placeholder="Your message should write here"></textarea>
                                    </div>
                                    <input type="submit" class="btn btn-primary" value="Submit" name="submit">
                                </form>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="col-md-4">
                    <?php require_once('inc/sidebar.php');?>

                </div>
            </div>
        </div>
    </section>
<?php require_once('inc/footer.php');?>
