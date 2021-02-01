package com.meritamerica.bankcapstone.services;


import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.meritamerica.bankcapstone.models.CDAccount;
import com.meritamerica.bankcapstone.models.CDOffering;
import com.meritamerica.bankcapstone.models.CheckingAccount;
import com.meritamerica.bankcapstone.models.DBAAccount;
import com.meritamerica.bankcapstone.models.PersonalCheckingAccount;
import com.meritamerica.bankcapstone.models.RegularIRA;
import com.meritamerica.bankcapstone.models.RolloverIRA;
import com.meritamerica.bankcapstone.models.RothIRA;
import com.meritamerica.bankcapstone.models.SavingsAccount;
import com.meritamerica.bankcapstone.models.User;
import com.meritamerica.bankcapstone.repositories.CDAccountRepository;
import com.meritamerica.bankcapstone.repositories.CDOfferingRepository;
import com.meritamerica.bankcapstone.repositories.CheckingAccountRepository;
import com.meritamerica.bankcapstone.repositories.DBARepository;
import com.meritamerica.bankcapstone.repositories.PersonalCheckingAccountRepository;
import com.meritamerica.bankcapstone.repositories.RegularIRARepository;
import com.meritamerica.bankcapstone.repositories.RolloverIRARepository;
import com.meritamerica.bankcapstone.repositories.RothIRARepository;
import com.meritamerica.bankcapstone.repositories.SavingsAccountRepository;
import com.meritamerica.bankcapstone.repositories.UserRepository;

@Service
public class UserAccountService {

	// Repositories:
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SavingsAccountRepository savingsAccountRepository;
	
	@Autowired
	RothIRARepository rothIRARepository;
	
	@Autowired
	RolloverIRARepository rolloverIRARepository;
	
	@Autowired
	RegularIRARepository regularIRARepository;
	
	@Autowired
	PersonalCheckingAccountRepository personalCheckingAccountRepository;
	
	@Autowired
	DBARepository dbaRepository;
	
	@Autowired
	CheckingAccountRepository checkingAccountRepository;
	
	@Autowired
	CDOfferingRepository cdOfferingRepository;
	
	@Autowired
	CDAccountRepository cdAccountRepository;
	
	PasswordEncoder passwordEncoder;
	
	// Constructor:
	
	public UserAccountService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	// User methods:
	
	public User addUser(User user) {
		String encodedPassword = this.passwordEncoder.encode(user.getPassword()); // Before saving, we encode the password!
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(String userName) {
		return userRepository.findUserById(userName);
	}
	
	public void removeUserById(long id) {
		userRepository.deleteById(id);
	}
	
	// Savings account methods:
	
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount, String userName) {
		getUserById(userName).setSavingsAccount(savingsAccount);
		savingsAccountRepository.save(savingsAccount);
		return savingsAccount;
	}
	
	public List<SavingsAccount> getSavingsAccounts(){
		return savingsAccountRepository.findAll();
	}
	
	// Checking account methods:
	
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount, String userName) {
		getUserById(userName).addCheckingAccount(checkingAccount);
		checkingAccountRepository.save(checkingAccount);
		return checkingAccount;
	}
	
	public List<CheckingAccount> getCheckingAccounts(){
		return checkingAccountRepository.findAll();
	}
	
	public Optional<CheckingAccount> getCheckingAccountById(long id) {
		return checkingAccountRepository.findById(id);
	}
	
	public void removeCheckingAccountById(long id) {
		checkingAccountRepository.deleteById(id);
	}
	
	// CD Account methods:
	
	public CDAccount addCDAccount(CDAccount cdAccount, String userName) {
		getUserById(userName).addCdAccount(cdAccount);
		cdAccountRepository.save(cdAccount);
		return cdAccount;
	}
	
	public List<CDAccount> getCDAccounts(){
		return cdAccountRepository.findAll();
	}
	
	public Optional<CDAccount> getCDAccountById(long id) {
		return cdAccountRepository.findById(id);
	}
	
	public void removeCDAccountById(long id) {
		cdAccountRepository.deleteById(id);
	}
	
	// CD Offering methods:
	
	public CDOffering addCDOffering(CDOffering cdOffering, long id) {
		return cdOfferingRepository.save(cdOffering);
	}
	
	public List<CDOffering> getCDOfferings(){
		return cdOfferingRepository.findAll();
	}
	
	public Optional<CDOffering> getCDOfferingsById(long id) {
		return cdOfferingRepository.findById(id);
	}
	
	public void removeCDOfferingById(long id) {
		cdOfferingRepository.deleteById(id);
	}
	
	// DBA Account methods:
	
	public DBAAccount addDBAAccount(DBAAccount dbaAccount, String userName) {
		getUserById(userName).addDbaAccount(dbaAccount);
		dbaRepository.save(dbaAccount);
		return dbaAccount;
	}
	
	public List<DBAAccount> getDBAAccount(){
		return dbaRepository.findAll();
	}
	
	public Optional<DBAAccount> getDBAAccountById(long id) {
		return dbaRepository.findById(id);
	}
	
	public void removeDBAAccountById(long id) {
		dbaRepository.deleteById(id);
	}
	
	// RegularIRAccount methods:
	
	public RegularIRA addRegularIraAccount(RegularIRA iraAccount, String userName) {
		getUserById(userName).setRegularIra(iraAccount);
		regularIRARepository.save(iraAccount);
		return iraAccount;
	}
	
	public List<RegularIRA> getRegularIRAccount(){
		return regularIRARepository.findAll();
	}
	
	public Optional<RegularIRA> getRegularIRAccountById(long id) {
		return regularIRARepository.findById(id);
	}
	
	public void removeRegularIRAccountById(long id) {
		regularIRARepository.deleteById(id);
	}
	
	// RolloverIRAccount methods:
	
	public RolloverIRA addRolloverIraAccount(RolloverIRA iraAccount, String userName) {
		getUserById(userName).setRolloverIra(iraAccount);
		rolloverIRARepository.save(iraAccount);
		return iraAccount;
	}
	
	public List<RolloverIRA> getRolloverIRAccount(){
		return rolloverIRARepository.findAll();
	}
	
	public Optional<RolloverIRA> getRolloverIRAccountById(long id) {
		return rolloverIRARepository.findById(id);
	}
	
	public void removeRolloverIRAccountById(long id) {
		rolloverIRARepository.deleteById(id);
	}
	
	// RothIRAccount methods:
	
	public RothIRA addRothIraAccount(RothIRA iraAccount, String userName) {
		getUserById(userName).setRothIra(iraAccount);
		rothIRARepository.save(iraAccount);
		return iraAccount;
	}
	
	public List<RothIRA> getRothIRAccount(){
		return rothIRARepository.findAll();
	}
	
	public Optional<RothIRA> getRothIRAccountById(long id) {
		return rothIRARepository.findById(id);
	}
	
	public void removeRothIRAccountById(long id) {
		rothIRARepository.deleteById(id);
	}
	
	// Personal Checking Account Methods
	
	public PersonalCheckingAccount addPersonalCheckingAccount(PersonalCheckingAccount personalCheckingAccount, String userName) {
		getUserById(userName).setPersonalCheckingAccount(personalCheckingAccount);
		personalCheckingAccountRepository.save(personalCheckingAccount);
		return personalCheckingAccount;
	}
}
