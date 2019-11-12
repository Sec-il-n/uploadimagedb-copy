package model.logic;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

/**
 * Servlet implementation class FileDeleteS3Logic
 */
public class DeleteFileS3Logic {
	private static final String bucketName=System.getenv("AWS_S3_BUCKETNAME");
	private static final String accessKey =System.getenv("AWS_S3_UPLOAD_ACCESSKEY");
	private static final String secretKey =System.getenv("AWS_S3_UPLOAD_SECRETKEY");
	private static final Regions clientRegion = Regions.AP_NORTHEAST_1;
	String fileObjKeyName;

	AmazonS3 s3Client;
	//<=>putObject(return PutObjectResult)
	public void s3Delete(String newFileName){
		AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);
		fileObjKeyName="upload/"+ newFileName;

		try {
			s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(clientRegion)
					.build();

			DeleteObjectRequest deleteObjectRequest=new DeleteObjectRequest(bucketName,fileObjKeyName);
			s3Client.deleteObject(deleteObjectRequest);

		} catch (AmazonServiceException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SdkClientException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}


}
