package com.example.javapackage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailVerification1 {
    private int otp;
    public int sendmail(String email) {

        Random random = new Random();
        otp = 100000 + random.nextInt(900000);    //6-digit otp generation

        final String username = "abc@gmail.com";
        final String password = "eergthyj";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //props.put("mail.smtp.connectiontimeout", "5000"); // 5 seconds
        //props.put("mail.smtp.timeout", "5000"); // 5 seconds

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("OTP");
            message.setText("Dear Recipient," + "\n\n This is your OTP: " + otp);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return otp;
    }
    public int getOTP(){
        return otp;
    }
}

class S3 {
    static String accessKey = "AKIA5Q6T6OJFXMJWWDRU";
    static String secretKey = "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";
    public void Newbucket(String bucket) {
        String bucketName = bucket;
        String userName = "student";
        // Create AWS credentials using access key and secret key
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // Create an S3 client using AWS credentials
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_2)
                .build();
/*
        // Create a new S3 bucket with private access
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        s3Client.createBucket(createBucketRequest);

        // Set access control list for the bucket to allow access to a specific user
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(new CanonicalGrantee(userName), Permission.FullControl);
        s3Client.setBucketAcl(bucketName, acl);
*/

        s3Client.createBucket(bucketName);
    }


    public static void openBucket(String bucket) {
        String bucketName = bucket;
        String region = "ap-south-2";

        //String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        String url = "https://s3.console.aws.amazon.com/s3/buckets/" + bucket + "?region=" + region +"&tab=objects";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putObject(PutObjectRequest request) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_2)
                .build();
        s3Client.putObject(request);
    }

    public static void listObjects(String bucket) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_2)
                .build();
        ObjectListing objectListing = s3Client.listObjects(bucket);
        System.out.println("Objects in bucket: " + bucket);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }
}


public class javapack extends Application {
    private static final Logger LOGGER = Logger.getLogger(javapack.class.getName());
    Label label = new Label("successful");

    // create a popup
    Popup popup = new Popup();


    //creating label username
    Text text1 = new Text("Username");
    Text text11 = new Text("Username");
    //creating label password
    Text text2 = new Text("Password");
    Text text22 = new Text("Password");
    Text text222 = new Text("Re-enter Password");
    //creating label email
    Text text3 = new Text("Email");
    //creating label OTP
    Text text4 = new Text("OTP");
    //Creating Text Filed for username
    TextField textField1 = new TextField();
    TextField textField11 = new TextField();
    //Creating Text Filed for password
    PasswordField textField2 = new PasswordField();
    PasswordField textField22 = new PasswordField();
    PasswordField textField222 = new PasswordField();
    //Creating Text Filed for email
    TextField textField3 = new TextField();
    //Creating Text Filed for OTP
    TextField textField4 = new TextField();
    //Creating Buttons
    Button button1 = new Button("Login");
    Button button2 = new Button("Clear");
    Button button3 = new Button("Signup");
    Button button4 = new Button("Send OTP");
    Button button5 = new Button("Verify OTP");
    Button button6 = new Button("Create");
    Button button7 = new Button("Yes, open in browser");
    Button button8 = new Button("No, continue here");
    Button button9 = new Button("Logout");
    Button button10 = new Button("Close");
    CheckBox uploadCheckBox = new CheckBox("Upload objects");
    CheckBox deleteCheckBox = new CheckBox("Delete objects");
    CheckBox openCheckBox = new CheckBox("Open objects");
    Button confirmButton = new Button("Confirm");

    private Stage stage;
    //Creating a Grid Pane
    Alert a = new Alert(Alert.AlertType.NONE);
       // a.setContentText("successful");
       GridPane gridPane = new GridPane();
    GridPane gridPane1 = new GridPane();
    GridPane gridPane2 = new GridPane();
    GridPane gridPane3 = new GridPane();
    GridPane gridPane4 = new GridPane();
    Scene scene = new Scene(gridPane);
    Scene scene2 = new Scene(gridPane1);
    Scene scene3 = new Scene(gridPane2);
    Scene scene4 = new Scene(gridPane3);
    Scene scene5 = new Scene(gridPane4);


    private Object login(String usn, String pass) {
        int res=-1;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] data = pass.getBytes(StandardCharsets.UTF_8);
            byte[] hash = digest.digest(data);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_try", "root", "abc");
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM my_table WHERE username = ?");
            statement.setString(1, usn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                if (password.equals(Arrays.toString(hash))) {
                    //stage.close();
                    a.setContentText("Logging in...");
                    a.setHeaderText("Login successful!");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    ButtonType okButton = ButtonType.OK;
                    a.show();
                    res=1;
                    //Button okBtn = (Button) a.getDialogPane().lookupButton(okButton);
                    /*
                    a.show();
                    okBtn.setOnAction(e1 -> {
                        a.setContentText("Open my cloud storage");
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        ButtonType buttonyes = new ButtonType("Yes, open browser");
                        ButtonType buttonno = new ButtonType("No, continue here");

                        a.getButtonTypes().setAll(buttonyes, buttonno);

                        Optional<ButtonType> result = a.showAndWait();
                        if (result.get() == buttonyes){
                            S3.openBucket(usn);
                        } else {
                            returnValue.set(1);
                        }
                        a.show();
                    });

                    //a.showAndWait();


                    //a.show();*/
                } else {
                    a.setContentText("Login unsuccessful\nPlease check username/password");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Login unsuccessful");
                    a.show();
                }
            } else {
                a.setContentText("Create new account");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setHeaderText("Seems like you don't have an account :(\nCreate account to proceed");
                a.show();
                res=0;
            }
            connection.close();
        } catch (Exception e1) {
            System.out.println("An error occurred.");
            e1.printStackTrace();
        }
        return res;
    }

    @Override
    public void start(Stage stage) {
        try {
            FileHandler fileHandler = new FileHandler("log.txt");
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up logger", e);
        }
        //Setting title to the Stage
        stage.setTitle("AMCS Cloud Storage");
        //Adding scene to the stage
        stage.setScene(scene);
        // set background
        label.setStyle(" -fx-background-color: white;");

        // add the label
        popup.getContent().add(label);

        // set size of label
        label.setMinWidth(80);
        label.setMinHeight(50);

        Text scenetitle = new Text("Login");
        scenetitle.setFont(Font.font("Blue", FontWeight.SEMI_BOLD, 30));
        Text scenetitle1 = new Text("Signup");
        scenetitle1.setFont(Font.font("Blue", FontWeight.SEMI_BOLD, 30));
        Text scenetitle2 = new Text("Password");
        scenetitle2.setFont(Font.font("Blue", FontWeight.SEMI_BOLD, 30));
        Text scenetitle3 = new Text("Open my cloud storage");
        scenetitle3.setFont(Font.font("Blue", FontWeight.SEMI_BOLD, 30));
        Text scenetitle4 = new Text("Options");
        scenetitle4.setFont(Font.font("Blue", FontWeight.SEMI_BOLD, 30));

        //Setting size for the pane
        gridPane.setMinSize(600, 500);
        gridPane1.setMinSize(600, 500);
        gridPane2.setMinSize(600, 500);
        gridPane3.setMinSize(600, 500);
        gridPane4.setMinSize(600, 500);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane1.setPadding(new Insets(10, 10, 10, 10));
        gridPane2.setPadding(new Insets(10, 10, 10, 10));
        gridPane3.setPadding(new Insets(10, 10, 10, 10));
        gridPane4.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane1.setHgap(5);
        gridPane1.setVgap(5);
        gridPane2.setHgap(5);
        gridPane2.setVgap(5);
        gridPane3.setHgap(5);
        gridPane3.setVgap(5);
        gridPane4.setHgap(5);
        gridPane4.setVgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);
        gridPane1.setAlignment(Pos.CENTER);
        gridPane2.setAlignment(Pos.CENTER);
        gridPane3.setAlignment(Pos.CENTER);
        gridPane4.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(scenetitle, 0, 0);
        gridPane.add(text1, 0, 1);
        gridPane.add(textField1, 1, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(textField2, 1, 2);
        gridPane.add(button1, 0, 3);
        gridPane.add(button2, 1, 3);
        gridPane.add(button3, 0, 4);
        gridPane.add(button10, 1, 4);

        gridPane1.add(scenetitle1, 0, 0);
        gridPane1.add(text11, 0, 1);
        gridPane1.add(textField11, 1, 1);
        gridPane1.add(text3, 0, 2);
        gridPane1.add(textField3, 1, 2);
        gridPane1.add(button4, 1, 3);
        gridPane1.add(text4, 0, 4);
        gridPane1.add(textField4, 1, 4);
        gridPane1.add(button5, 1, 5);

        gridPane2.add(scenetitle2, 0, 0);
        gridPane2.add(text22, 0, 1);
        gridPane2.add(textField22, 1, 1);
        gridPane2.add(text222, 0, 2);
        gridPane2.add(textField222, 1, 2);
        gridPane2.add(button6, 1, 3);

        gridPane3.add(scenetitle3, 0, 0);
        gridPane3.add(button7, 0, 3);
        gridPane3.add(button8, 1, 3);

        gridPane4.add(scenetitle4, 0, 0);
        gridPane4.add(uploadCheckBox, 0, 1);
        //gridPane4.add(deleteCheckBox, 0, 2);
        //gridPane4.add(openCheckBox, 0, 3);
        gridPane4.add(confirmButton, 0, 2);
        gridPane4.add(button9, 0, 3);


        button1.setOnAction(e -> {
            String username= textField1.getText();
            String password= textField2.getText();
            int result = (int) login(username,password);
            if (result == 1){
                stage.setScene(scene4);
            }
            if (result == 0){
                stage.setScene(scene);
            }
        });



        button2.setOnAction(e->{
            textField1.setText("");
            textField2.setText("");
        });

        S3 bucket = new S3();

        button6.setOnAction(e -> {
            try{
                if (textField22.getText().equals(textField222.getText()))
                {
                    bucket.Newbucket(textField11.getText());
                    //stage.close();

                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] data = textField22.getText().getBytes(StandardCharsets.UTF_8);
                    byte[] hash = digest.digest(data);

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_try", "root", "abc");
                    PreparedStatement statement = connection.prepareStatement("Insert into my_table (username,password,email) values (?,?,?)");
                    statement.setString(1, textField11.getText());
                    statement.setString(2, Arrays.toString(hash));
                    statement.setString(3, textField3.getText());
                    statement.executeUpdate();

                    a.setHeaderText("Account created successfully!");
                    a.setContentText("Account has been created!");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    ButtonType okButton = ButtonType.OK;
                    Button okBtn = (Button) a.getDialogPane().lookupButton(okButton);
                    okBtn.setOnAction(e1 -> {
                        stage.setScene(scene);
                    });

                    a.showAndWait();
                }
                else {
                    a.setTitle("Warning");
                    a.setContentText("Password does not match!\nRe-enter password");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.show();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        //Creating a scene object
        //Scene scene = new Scene(gridPane);
        /*
        Image backgroundImage = new Image("https://upload.wikimedia.org/wikipedia/en/e/eb/PSG_College_of_Technology_logo.png",50,100,false,true); // load the image
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        gridPane.setStyle("-fx-background-image: url('" + backgroundImage.getUrl() + "');"); // set the background image

        //Scene scene = new Scene(gridPane, 600, 500); // create a scene with the root node
        //Region root = new Region();
        gridPane.setBackground(new Background(background));
        //gridPane.getChildren().add(gridPane);
        Scene scene = new Scene(gridPane, 600, 500);
        */


        button3.setOnAction(e -> stage.setScene(scene2));
        EmailVerification1 mail = new EmailVerification1();
        button4.setOnAction(e->{
            try{
                Pattern pattern = Pattern.compile("[0-9][0-9][pP][CWTDcwtd][0-4][0-9]", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(textField11.getText());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    a.setContentText("This login is only for students of AMCS Department\nEnter your roll number");
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.show();
                } else {
                    Pattern pattern1 = Pattern.compile("[0-9][0-9][pP][CWTDcwtd][0-4][0-9]@psgtech.ac.in", Pattern.CASE_INSENSITIVE);
                    Matcher matcher1 = pattern1.matcher(textField3.getText());
                    boolean matchFound1 = matcher1.find();
                    boolean matchFound2 = textField3.getText().contains(textField11.getText());
                    if (!matchFound1 || !matchFound2) {
                        a.setContentText("Please enter your College E-Mail ID only!");
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.show();
                    }
                    else {
                        int otp=mail.sendmail(textField3.getText());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });


        button5.setOnAction(e->{
            if (mail.getOTP() == Integer.parseInt(textField4.getText().trim())){
                stage.setScene(scene3);
            }
            else{
                a.setTitle("Warning");
                a.setHeaderText("You have entered incorrect OTP");
                a.setContentText("Incorrect OTP");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.show();
            }
        });

        button7.setOnAction(e->{
           String usn= textField1.getText();
           S3.openBucket(usn);
        });

        button8.setOnAction(e->{
            stage.setScene(scene5);
        });

        button9.setOnAction(e->{
            stage.setScene(scene);
        });

        button10.setOnAction(e->{
            System.exit(0);
        });


        confirmButton.setOnAction(event -> {
            if (uploadCheckBox.isSelected()) {
                String bucket1= textField1.getText();
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    String objectKey = selectedFile.getName();

                    // Upload file to S3 bucket
                    PutObjectRequest request = new PutObjectRequest(bucket1, objectKey, selectedFile);
                    try {
                        S3.putObject(request);  
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setTitle("Upload Successful");
                        a.setHeaderText("File uploaded");
                        a.setContentText("File upload successful");
                        a.showAndWait();
                        LOGGER.info("File uploaded");
                    } catch (AmazonServiceException e) {
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setTitle("Upload Failed");
                        a.setHeaderText("There was an error uploading the file");
                        a.setContentText(e.getMessage());
                        a.showAndWait();
                    }

                }
            }

            if (deleteCheckBox.isSelected()) {
                // Perform delete functionality
                String bucket1= textField1.getText();
                S3.listObjects(bucket1);
            }
            if (openCheckBox.isSelected()) {
                // Perform open functionality
                String bucket1= textField1.getText();
                S3.listObjects(bucket1);

            }
        });

        //Displaying the contents of the stage
        stage.show();
    }


    public static void main(String args[]) {
        launch(args);
    }
}
