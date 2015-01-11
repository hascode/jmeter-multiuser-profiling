package com.hascode.tutorial.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

@Singleton
@Startup
public class ApplicationConfigurationBean {
	private final Logger log = Logger.getLogger(ApplicationConfigurationBean.class.getName());
	// creates 5 users: admin, ted, lisa, marge, bart
	private final String[] createUserStatements = { "INSERT INTO `users` VALUES('admin', 'test')", "INSERT INTO users_groups VALUES('administrators','admin')",
			"INSERT INTO `users` VALUES('ted', 'foo123')", "INSERT INTO users_groups VALUES('administrators','ted')", "INSERT INTO `users` VALUES('lisa', 'xyz123')",
			"INSERT INTO users_groups VALUES('administrators','lisa')", "INSERT INTO `users` VALUES('marge', 'redrum')", "INSERT INTO users_groups VALUES('administrators','marge')",
			"INSERT INTO `users` VALUES('bart', 'eatmyshorts')", "INSERT INTO users_groups VALUES('administrators','bart')" };

	@Resource(lookup = "jdbc/hascode_test_db")
	private DataSource ds;

	@PostConstruct
	protected void onStartup() throws SQLException {
		log.info("initializing users and roles..");
		// use a migration framework here - this is just for the purpose of
		// demonstration, works only once ;)
		String createUserTable = "CREATE TABLE `users` (`userid` varchar(255) NOT NULL, `password` varchar(255) NOT NULL, PRIMARY KEY (`userid`))";
		String createGroupTable = "CREATE TABLE `users_groups` ( `groupid` varchar(20) NOT NULL, `userid` varchar(255) NOT NULL)";

		Connection con = ds.getConnection();

		con.prepareCall(createUserTable).execute();
		con.prepareCall(createGroupTable).execute();
		for (String sql : createUserStatements) {
			con.prepareCall(sql).execute();
		}
		log.info("user and roles setup completed");
	}
}