package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
//need to adopt Logic I made
class FileUploadS3LogicTest {
	AmazonS3 s3 ;
//static initializer : be called at once when called class
	static {
		try {
			Files.createDirectories(Paths.get("data/s3"));
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

	@Test
	void test() throws IOException {
		String ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE";
		String CECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

		s3 =AmazonS3Client.builder()
				.withEndpointConfiguration(new EndpointConfiguration("http://localhost:9444/s3", null))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY,CECRET_KEY)))
				.withPathStyleAccessEnabled(true)
				.withClientConfiguration(new ClientConfiguration().withSignerOverride("S3SignerType"))
				.build();

		s3.putObject(new PutObjectRequest("bucket","s3test2.jpg",new File("src/test/resources/1563062226948_test.jpg")));

		try(S3Object s3object=s3.getObject(new GetObjectRequest("bucket","s3test.jpg"));
			InputStream input=s3object.getObjectContent()){
			assertThat(IOUtils.toByteArray(input),is(Files.readAllBytes(Paths.get("src/test/resources/1563062226948_test.jpg"))));
		}
	}

	@AfterEach
	void deleteFile() throws Exception {
		s3.deleteObject(new DeleteObjectRequest("bucket","s3test.jpg"));
		//s3.deleteObject(new DeleteObjectRequest(bucketName,fileObjKeyName));
	}
}
