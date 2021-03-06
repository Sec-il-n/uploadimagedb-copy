package model4test;

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

	//get ful list of versions
	@BeforeAll
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
			
			while(true) {
				for(S3VersionSummary summary :
						listing.getVersionSummaries()) {
					s3Keys.put(summary.getKey(), summary.getVersionId());
				}
				
				if(listing.isTruncated()) {
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



}
