package model4test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
/**
 *  ※　同じfileObjKeyName(=file name)テストは失敗しないが、作成されない
 * @author secil
 *
 */
public class FileUploadS3ninjaLogic {
	private static String bucketName="bucket";
	private static String fileObjKeyName="s3test.jpg";
	String ACCESS_KEY = "AKIAJBWG4SMUOOSHBMZQ";
	String CECRET_KEY = "sbbH856KXsSuo82aJfEpN1ReN6jwdarBeZH077AC";
	String testSource = "src/test/resources/1563062226948_test.jpg";
	static {
		try {
			Files.createDirectories(Paths.get("data/s3"));//change /tmp/s3 or something
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread =new Thread(() -> {
			//Method :java.lang.refrect
			try {
				Method main = Class.forName("IPL").getMethod("main", new Class[] {String[].class}/* parameterTypes*/);
				main.invoke(null, (Object) new String[0]);//invoke:基本メソッドを、指定したパラメタで呼び出す

			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();

			}
		});
		thread.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public boolean upload(String newFileName,String tmpPath) {//newFileName=be primary,tmpPath=testSource
		fileObjKeyName=newFileName;
		AmazonS3 s3 =AmazonS3Client.builder()
				.withEndpointConfiguration(new EndpointConfiguration("http://localhost:9444/s3", null))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY,CECRET_KEY)))
				.withPathStyleAccessEnabled(true)
				.withClientConfiguration(new ClientConfiguration().withSignerOverride("S3SignerType"))
				.build();

		PutObjectResult putObjectResult=s3.putObject(new PutObjectRequest(bucketName,fileObjKeyName,new File(testSource)));
//		s3.putObject(new PutObjectRequest("bucket","s3test.jpg",new File("src/test/resources/1563062226948_test.jpg")));
		if(putObjectResult!=null){
		 	return true;
		 }

		 return false;
	}

}
