/*
 * Individual Retirement Account - IRA
 */

package com.meritamerica.bankcapstone.models;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
public abstract class IRAccount {

	// Class attributes:
	
	@Id
	private long id;
	private double interestRate;
	private double balance;
	//private User bankAccountHolder;
	private Date bankAccountOpened = new Date();
	private boolean isActive;
	
	// Constructors:
	
	public IRAccount() {
		
	}

	public IRAccount(double interestRate, double balance, User bankAccountHolder) {
		this.interestRate = interestRate;
		this.balance = balance;
		this.setActive(true);
	}

	public long getId() {
		return id;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
/*
	public User getBankAccountHolder() {
		return bankAccountHolder;
	}

	public void setBankAccountHolder(User bankAccountHolder) {
		this.bankAccountHolder = bankAccountHolder;
	}
*/
	public Date getBankAccountOpened() {
		return bankAccountOpened;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
		
}