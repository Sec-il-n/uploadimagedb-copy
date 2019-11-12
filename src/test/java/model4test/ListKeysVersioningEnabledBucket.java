package model4test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

public class ListKeysVersioningEnabledBucket {
	private static Regions clientRegion = Regions.AP_NORTHEAST_1;
	private static String bucketName="cec-il-n-aws";
//	private static String fileObjKeyName;//fileObjKeyName
//	private static AmazonS3 s3Client;
//get ful list of versions
	@BeforeAll//&@AfterAll
	public Map<String,String> getListvarsionig() {
		Map<String,String> s3Keys=new HashMap<>();

		try {
			AmazonS3 s3client= AmazonS3ClientBuilder.standard()
					.withCredentials(new ProfileCredentialsProvider())
					.withRegion(clientRegion)
			        .build();
			ListVersionsRequest req= new ListVersionsRequest()
					.withBucketName(bucketName)
					.withMaxResults(2);
			VersionListing listing=s3client.listVersions(req);
//			int numVersions=0, numPages=0;
			while(true) {
//				numPages++;
				for(S3VersionSummary summary :
						listing.getVersionSummaries()) {
					s3Keys.put(summary.getKey(), summary.getVersionId());
//					numVersions++;
				}
				if(listing.isTruncated()) {//continous l47 'versionListing'
					listing=s3client.listNextBatchOfVersions(listing);

				}else {
					break;
				}
			}
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
		return s3Keys;
	}



	@Test
	void test() {
		fail("まだ実装されていません");
	}

}
