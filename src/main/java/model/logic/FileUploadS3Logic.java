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
	private static String fileObjKeyName;//fileObjKeyName
//	String fileObjKeyName="/upload/1563062226948_test.jpg";// x "/"4 key
//	String fileName="1563062226948_test.jpg";//
	private static AmazonS3 s3Client;
	private static final String bucketName=System.getenv("AWS_S3_BUCKETNAME");
	private static final String accessKey =System.getenv("AWS_S3_UPLOAD_ACCESSKEY");
	private static final String secretKey =System.getenv("AWS_S3_UPLOAD_SECRETKEY");
//環境変数、.env(local)設定、動作確認済み--(以下3行消去可能)
//	private static String bucketName="cec-il-n-aws";
//	private static final String accessKey ="AKIASBWLPNNSVST5PB7J";
//	private static final String secretKey ="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";
//  未使用
//	static final String S3_SERVICE_END_POINT    = "s3.dualstack.ap-northeast-1.amazonaws.com";
//    static final String S3_REGION               = "AP_NORTHEAST_1";

	public boolean s3Upload(String newFileName,String tmpPath){

		fileObjKeyName="upload/"+ newFileName;
		File file = new File(tmpPath);

		PutObjectResult putObjectResult = null;
		try {
			//1104追加
			// AWSの認証情報
			 AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);

			 //エンドポイント設定 xerror:Only one of Region or EndpointConfiguration may be set.
//			 EndpointConfiguration endpointConfiguration = new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);

//			 s3Client = AmazonS3ClientBuilder.standard()
//					.withRegion(clientRegion)
//			        .build();
			//1104追加
			 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
//					.withEndpointConfiguration(endpointConfiguration)
					.withRegion(clientRegion)
					.build();

			 //1104 add
			 final PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileObjKeyName, file);
//			 putRequest.setCannedAcl(CannedAccessControlList.PublicRead);//=アクセス権の変更=これを入れるならエンティティにACL権を付与しなければいけない
			 putObjectResult = s3Client.putObject(putRequest);
//			 putObjectResult=s3Client.putObject(new PutObjectRequest(bucketName, fileObjKeyName, file));

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
			//1104追加
			// AWSの認証情報

			s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(sessionCredencials))
//					.withEndpointConfiguration(endpointConfiguration)
					.withRegion(clientRegion)
					.build();

			//1104 add
			final PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileObjKeyName, file);
			putRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			putObjectResult = s3Client.putObject(putRequest);
//			 putObjectResult=s3Client.putObject(new PutObjectRequest(bucketName, fileObjKeyName, file));

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
//1105 https://docs.aws.amazon.com/ja_jp/AmazonS3/latest/dev/AuthUsingTempSessionTokenJava.html
	public BasicSessionCredentials MakingRequestsWithIAMTempCredentials() {
		// Creating the STS client <- 一時認証を獲得する
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
		// Package the temporary security credentials for an Amazon S3 client object to use.
		BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(
                sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(),
                sessionCredentials.getSessionToken());

		return basicSessionCredentials;

	}

}
