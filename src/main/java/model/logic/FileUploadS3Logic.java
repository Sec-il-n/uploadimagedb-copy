package model.logic;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

public class FileUploadS3Logic {
	Regions clientRegion = Regions.AP_NORTHEAST_1;
	private static String fileObjKeyName;
	private static AmazonS3 s3Client;
	private static final String bucketName=System.getenv("AWS_S3_BUCKETNAME");
	private static final String accessKey =System.getenv("AWS_S3_UPLOAD_ACCESSKEY");
	private static final String secretKey =System.getenv("AWS_S3_UPLOAD_SECRETKEY");

	public boolean s3Upload(String newFileName,String tmpPath){

		fileObjKeyName="upload/"+ newFileName;
		File file = new File(tmpPath);

		PutObjectResult putObjectResult = null;
		
		try {
			 AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);

			 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(clientRegion)
					.build();

			 final PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileObjKeyName, file);
			 putObjectResult = s3Client.putObject(putRequest);

		 } catch (AmazonServiceException e) {
			e.printStackTrace();

		 } catch (SdkClientException e) {
	        	e.printStackTrace();
		 }
		 
		 if(putObjectResult!=null){
		 	return true;
		 }

		 return false;
	 }
	
	public boolean s3Upload(String newFileName,String tmpPath,BasicSessionCredentials sessionCredencials){

		fileObjKeyName="upload/"+ newFileName;
		File file = new File(tmpPath);

		PutObjectResult putObjectResult = null;
		try {

			s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(sessionCredencials))
					.withRegion(clientRegion)
					.build();

			final PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileObjKeyName, file);
			putRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			putObjectResult = s3Client.putObject(putRequest);

		} catch (AmazonServiceException e) {
			e.printStackTrace();

		} catch (SdkClientException e) {
			e.printStackTrace();
		}
		if(putObjectResult!=null){
			return true;
		}

		return false;
	}

	public BasicSessionCredentials MakingRequestsWithIAMTempCredentials() {

		AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
			.withCredentials(new ProfileCredentialsProvider())
			.withRegion(clientRegion)
			.build();
		
		GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest().withDurationSeconds(7200);
		GetSessionTokenResult sessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);
		
		Credentials sessionCredentials = sessionTokenResult
			.getCredentials()
			.withSessionToken(sessionTokenResult.getCredentials().getSessionToken())
			.withExpiration(sessionTokenResult.getCredentials().getExpiration());

		BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(
                sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(),
                sessionCredentials.getSessionToken());

		return basicSessionCredentials;

	}


}
