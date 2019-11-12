package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//mysqldump where is the best place to make dump.sql
public class MysqlDump {
//	String MYSQL_MYSQLDUMP;//path通してたらmysqldumpでOK?=>x need /usr/local/Cellar/mysql/8.0.16/bin only could do that from terminal
	String PathToBackup;
//	static String command="/usr/local/Cellar/mysql/8.0.16/bin/mysqldump -uroot -pseri331 -hlocalhost Practice_MVC"+" -r /src/test/resources/dump/dump.sql";
	static String command="/usr/local/Cellar/mysql/8.0.17_1/bin/mysqldump -uroot -pseri331 -hlocalhost --databases Practice_MVC"+" -r /tmp/dump2.sql";//can't creat without '-r'
//	x static String commandRestore="/usr/local/Cellar/mysql/8.0.16/bin/mysql --uroot --pseri331 -hlocalhost"+ "-e /tmp/dump2.sql";
//	x static String commandRestore="/usr/local/Cellar/mysql/8.0.16/bin/mysql --uroot --pseri331 -hlocalhost"+ "-e source /tmp/dump2.sql";
	static String[] com=new String[] {"/usr/local/Cellar/mysql/8.0.17_1/bin/mysql","--user=root","--password=seri331","-e","source /tmp/dump2.sql"};
//	static String[] com=new String[] {"/usr/local/Cellar/mysql/8.0.17_1/bin/sh","-c","mysql","--uroot","--pseri331","-hlocalhost","Practice_MVC","source /tmp/dump2.sql"};
	@BeforeAll
//	static void setSqlDump(String command) {
	static void setSqlDump() {
//		String file;
		Process p=null;
		Runtime runtime=Runtime.getRuntime();
		try {
			p=runtime.exec(command);
//			p=runtime.exec(com);
//			p=runtime.exec(commandRestore);
			int prosessComplete=p.waitFor();
			if(prosessComplete == 0) {
				JOptionPane.showMessageDialog(new JDialog(), "Backup created!!");
			}else{
				JOptionPane.showMessageDialog(new JDialog(), "couldn't create the sqldump!");
			}
		} catch (HeadlessException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		File f=new File("/tmp/dump2.sql");
		assertTrue(f.exists());
	}

	@Test
	void test() {

	}


}
