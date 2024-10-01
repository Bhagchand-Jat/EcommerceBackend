/*
 * package com.ecommerce.webconfiguration;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.transaction.PlatformTransactionManager; import
 * org.springframework.transaction.annotation.EnableTransactionManagement;
 * import org.springframework.transaction.jta.JtaTransactionManager;
 * 
 * import com.arjuna.ats.jta.TransactionManager; import
 * com.arjuna.ats.jta.UserTransaction;
 * 
 *//**
	* 
	*//*
		 * @Configuration
		 * 
		 * @EnableTransactionManagement public class JtaConfig {
		 * 
		 * @Bean public PlatformTransactionManager transactionManager() {
		 * JtaTransactionManager transactionManager = new JtaTransactionManager();
		 * transactionManager.setTransactionManager(TransactionManager.
		 * transactionManager());
		 * transactionManager.setUserTransaction(UserTransaction.userTransaction());
		 * return transactionManager; }
		 * 
		 * @Bean public jakarta.transaction.UserTransaction userTransaction() { return
		 * com.arjuna.ats.jta.UserTransaction.userTransaction(); }
		 * 
		 * @Bean public jakarta.transaction.TransactionManager
		 * narayanaTransactionManager() { return
		 * com.arjuna.ats.jta.TransactionManager.transactionManager(); } }
		 */