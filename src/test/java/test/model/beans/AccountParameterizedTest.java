package test.model.beans;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import model.beans.Account;

public class AccountParameterizedTest {
	private Account ac1;
	private Account ac2;

	@BeforeEach
	void set() {
//		ac1=new Account("Shima5","135790","島紀香",34);
		ac2=new Account("Shima3","135790","島七重",34);
	}

	@ParameterizedTest
	@CsvFileSource(resources = { "/csv/Account.csv" }, numLinesToSkip = 1)
//x only String
//	@CsvSource({"userId,Shima5","pass,135790","name,島紀香","age,34"})
	void csvSource(String userId,String pass,String name,int age) {
		ac1=new Account(userId,pass,name,age);
//		assertEquals(ac2,ac1.getUserId());
		assertEquals(ac2.getName(),ac1.getName());
		assertEquals(ac2.getAge(),ac1.getAge());
		assertThat(ac2,samePropertyValuesAs(ac1));
		//<-lang.SecurityException: class "org.hamcrest.Matchers"'s signer information does not match signer information of other classes in the same package
	}
}
