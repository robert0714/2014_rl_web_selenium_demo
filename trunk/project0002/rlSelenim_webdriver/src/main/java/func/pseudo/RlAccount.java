package func.pseudo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

public class RlAccount {
	public static  Account getRandomAccount(){
		return new RlAccount().getPseduAccounts().get(RandomUtils.nextInt(20));
	}
	private List<Account>   getPseduAccounts(){
		final List<Account> result =new ArrayList<>();
		for (int i = 1; i <= 20; ++i) {
			String data = String.format("RF12030%02d%n", i);
			result.add(new Account(data,data) );
		}		
		return result;
	}
	public class Account {		
		public Account(String user, String passwd) {
			super();
			this.user = user;
			this.passwd = passwd;
		}
		private String user ;
		private String passwd ;
		public String getUser() {
			return user;
		}
		public String getPasswd() {
			return passwd;
		}
		
	}
	
}
