package model4test;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeAll;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.DeleteObjectsResult.DeletedObject;

public class DeleteObjectsforTest {
	private static Regions clientRegion = Regions.AP_NORTHEAST_1;
	private static String bucketName = "cec-il-n-aws";
	private static AmazonS3 s3;
	static int successfulDeletes;

	@BeforeAll
	public static void deleteObject(List<String> toDelete) {
		try {
			s3=AmazonS3ClientBuilder.standard()
			.withCredentials(new ProfileCredentialsProvider())
			.withRegion(clientRegion)
			.build();
			 String bucketVersionStatus = s3.getBucketVersioningConfiguration(bucketName).getStatus();
			 
			if (!bucketVersionStatus.equals(BucketVersioningConfiguration.ENABLED)) {
			     JOptionPane.showMessageDialog(new JDialog(), bucketName + " is not versioning-enabled !!");
			 } else {
				 deleteRemoveDeleteMarkers(objectsWithoutVersions(toDelete));
			 }
		
		} catch (SdkClientException | HeadlessException e) {
			 JOptionPane.showMessageDialog(new JDialog(), " faild to delete object !! ");
			e.printStackTrace();
		}
	}
	
	//with versions
	public static void objectsWithVersions(Map<String,String> toDelete) {

		ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
		for(Map.Entry<String,String> d : toDelete.entrySet()) {
			keys.add(new KeyVersion(d.getKey(),d.getValue()));
		}
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
	                .withKeys(keys)
	                .withQuiet(false);
		DeleteObjectsResult delObjRes = s3.deleteObjects(multiObjectDeleteRequest);
	     	successfulDeletes = delObjRes.getDeletedObjects().size();
	     	
		if(successfulDeletes==8) {
	 		JOptionPane.showMessageDialog(new JDialog(), "roll back success!!");
	 	}else {
	 		JOptionPane.showMessageDialog(new JDialog(), "faild!!");
	     	}
	}
	
	/**
	 * delete
	 */
	public static DeleteObjectsResult objectsWithoutVersions(List<String> keysList) {
		String[] keys= keysList.toArray(new String[keysList.size()]);
		DeleteObjectsRequest multiObjectDeleteRequest =
        	new DeleteObjectsRequest(bucketName).withKeys(keys).withQuiet(false);//When it's true, only errors will be returned in the service response.
        	DeleteObjectsResult delObjRes=s3.deleteObjects(multiObjectDeleteRequest);
        	successfulDeletes = delObjRes.getDeletedObjects().size();

		if(successfulDeletes==2) {
			JOptionPane.showMessageDialog(new JDialog(), "successfulDeletes!!");
		}else {
			JOptionPane.showMessageDialog(new JDialog(), "faildDelete!!");
		}
		return delObjRes;
	}
	/**
	 * delete key mark
	 */
	public static void deleteRemoveDeleteMarkers(DeleteObjectsResult response) {
		List<KeyVersion> keyList = new ArrayList<KeyVersion>();
		
		for (DeletedObject deletedObject : response.getDeletedObjects()) {
            		keyList.add(new KeyVersion(deletedObject.getKey(), deletedObject.getDeleteMarkerVersionId()));
            		DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName).withKeys(keyList).withQuiet(false);
            		DeleteObjectsResult delObjRes = s3.deleteObjects(deleteRequest);
            		successfulDeletes = delObjRes.getDeletedObjects().size();
        	}
		if(successfulDeletes==2) {
  			JOptionPane.showMessageDialog(new JDialog(), "successfulDeletes Markers!!");
         	}else {
  			JOptionPane.showMessageDialog(new JDialog(), "faildDelete Markers!!");
        	}
	
	}
	public static void dialog(boolean b) {
		if(b) {
			JOptionPane.showMessageDialog(new JDialog(), "roll back success!!");
		}else {
			JOptionPane.showMessageDialog(new JDialog(), "faild!!");
		}
	
	}

}
