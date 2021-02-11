package com.meritamerica.bankcapstone.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.bankcapstone.models.CDAccount;
import com.meritamerica.bankcapstone.models.CDOffering;
import com.meritamerica.bankcapstone.models.CheckingAccount;
import com.meritamerica.bankcapstone.models.DBAAccount;
import com.meritamerica.bankcapstone.models.JwtRequest;
import com.meritamerica.bankcapstone.models.JwtResponse;
import com.meritamerica.bankcapstone.models.PersonalCheckingAccount;
import com.meritamerica.bankcapstone.models.RegularIRA;
import com.meritamerica.bankcapstone.models.RolloverIRA;
import com.meritamerica.bankcapstone.models.RothIRA;
import com.meritamerica.bankcapstone.models.SavingsAccount;
import com.meritamerica.bankcapstone.models.Transaction;
import com.meritamerica.bankcapstone.models.User;
import com.meritamerica.bankcapstone.services.MyUserDetailsService;
import com.meritamerica.bankcapstone.services.UserAccountService;
import com.meritamerica.bankcapstone.utility.JWTUtility;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserAccountController {

	@Autowired
	UserAccountService service;

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	// User APIs =======================================================

	@PostMapping(value = "/authenticate")
	@ResponseStatus(HttpStatus.OK)
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

		// Debugging:
		System.out.println("========================================");
		System.out.println("Authentication requested by:");
		System.out.println(jwtRequest.getUserName());
		System.out.println("Using password:");
		System.out.println(jwtRequest.getPassword());
		System.out.println("========================================");
		// /Debugging

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials.", e);
		}

		// Now that authentication is done, let's create a token. First, get the user
		// details:

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUserName());

		// Now we generate a token using the user details as a parameter:

		final String token = jwtUtility.generateToken(userDetails);

		// Return the token in a JwtResponse:

		return new JwtResponse(token);
	}

	// Get all users:

	@GetMapping(value = "/Users")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUsers() {
		return service.getUsers();
	}

	// Check if user exists

	@GetMapping("/Users/{userName}/valid")
	@ResponseStatus(HttpStatus.OK)
	public boolean userExists(@PathVariable("userName") String userName) {
		if (service.userExists(userName)) {
			return true;
		}
		return false;
	}

	// Get user by id:

	@GetMapping("/Users/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public User fetchUserById(@PathVariable("userName") String userName) {
		return service.getUserById(userName);
	}

	// Post user:

	@PostMapping(value = "/Users")
	@ResponseStatus(HttpStatus.CREATED)
	public User addUser(@RequestBody User user) { // validation goes here, too.
		if (service.userExists(user.getUserName())) {
			return null;
		}
		service.addUser(user);
		return user;
	}

	// Delete user:

	@DeleteMapping("/Users/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public void removeUserById(@PathVariable("userName") String userName) {
		service.removeUserById(userName);
	}

	// Get APIs =======================================================

	@GetMapping(value = "/Users/{userName}/Checking Account")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccounts(@PathVariable("userName") String userName) {
		return service.getUserById(userName).getCheckingAccounts();
	}

	@GetMapping(value = "/Users/{userName}/Savings Account")
	@ResponseStatus(HttpStatus.OK)
	public SavingsAccount getSavingsAccount(@PathVariable("userName") String userName) {
		return service.getUserById(userName).getSavingsAccount();
	}

	/*
	 * @GetMapping(value = "")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<CDOffering> getCDOfferings() {
	 * service.addCDOfferings(); }
	 */

	// Post APIs ======================================================

	@PostMapping(value = "/Users/{userName}/Checking Account")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount postCheckingAccount(@PathVariable("userName") String userName,
			@RequestBody @Valid CheckingAccount checkingAccount) {
		return service.addCheckingAccount(checkingAccount, userName);
	}

	@PostMapping(value = "/Users/{userName}/Savings Account")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount postSavingsAccount(@PathVariable("userName") String userName,
			@RequestBody @Valid SavingsAccount savingsAccount) {
		return service.addSavingsAccount(savingsAccount, userName);
	}

	@PostMapping(value = "/Users/{userName}/Personal Checking Account")
	@ResponseStatus(HttpStatus.CREATED)
	public PersonalCheckingAccount postPersonalCheckingAccount(@PathVariable("userName") String userName,
			@RequestBody @Valid PersonalCheckingAccount personalCheckingAccount) {
		return service.addPersonalCheckingAccount(personalCheckingAccount, userName);
	}

	@PostMapping(value = "/Users/{userName}/DBA Checking Account")
	@ResponseStatus(HttpStatus.CREATED)
	public DBAAccount postDBAAccount(@PathVariable("userName") String userName,
			@RequestBody @Valid DBAAccount dbaAccount) {
		return service.addDBAAccount(dbaAccount, userName);
	}

	@PostMapping(value = "/Users/{userName}/Certificate of Deposit Account")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount postCDAccount(@PathVariable("userName") String userName, @RequestBody @Valid CDAccount cdAccount) {
		return service.addCDAccount(cdAccount, userName);
	}

	@PostMapping(value = "/Users/{userName}/Regular IRA")
	@ResponseStatus(HttpStatus.CREATED)
	public RegularIRA postRegularIraAccount(@PathVariable("userName") String userName,
			@RequestBody @Valid RegularIRA regularIra) {
		return service.addRegularIraAccount(regularIra, userName);
	}

	@PostMapping(value = "/Users/{userName}/Rollover IRA")
	@ResponseStatus(HttpStatus.CREATED)
	public RolloverIRA postRolloverIRA(@PathVariable("userName") String userName,
			@RequestBody @Valid RolloverIRA rolloverIra) {
		return service.addRolloverIraAccount(rolloverIra, userName);
	}

	@PostMapping(value = "/Users/{userName}/Roth IRA")
	@ResponseStatus(HttpStatus.CREATED)
	public RothIRA postRothIRA(@PathVariable("userName") String userName, @RequestBody @Valid RothIRA rothIra) {
		return service.addRothIraAccount(rothIra, userName);
	}

	// Patch Mapping ======================================================

	@PatchMapping(value = "/Users/{userName}/Checking Accounts/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeCheckingAccount(@PathVariable("userName") String userName, @PathVariable("id") Long id) {
		service.getUserById(userName).deleteCheckingAccount(id);
		service.deleteCheckingAccount(id, userName);
	}

	@PatchMapping(value = "/Users/{userName}/Savings Account/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeSavingsAccount(@PathVariable("userName") String userName, @PathVariable("id") Long id) {
		service.deleteSavingsAccount(id, userName);
	}

	@PatchMapping(value = "/Users/{userName}/Personal Checking Account/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removePersonalAccount(@PathVariable("userName") String userName, @PathVariable("id") Long id) {
		service.getUserById(userName).deletePersonalCheckingAccount();
		service.deletePersonalCheckingAccount(id, userName);
	}

	@PatchMapping(value = "/Users/{userName}/DBA Checking Accounts/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeDBAAccount(@PathVariable("userName") String userName, @PathVariable("id") Long id) {
		service.getUserById(userName).deleteDbaAccount(id);
		service.deleteDbaAccount(id, userName);
	}

	@PatchMapping(value = "/Users/{userName}/Certificate of Deposit Accounts/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeCDAccount(@PathVariable("userName") String userName, @PathVariable("id") Long id,
			@PathVariable("closingTo") String closingTo) {
		service.getUserById(userName).deleteCdAccount(id, closingTo);
		service.deleteCdAccount(id, userName, closingTo);
	}

	@PatchMapping(value = "/Users/{userName}/Regular IRA/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeRegularIra(@PathVariable("userName") String userName, @PathVariable("id") Long id,
			@PathVariable("closingTo") String closingTo) {
		service.getUserById(userName).deleteRegularIra(closingTo);
		service.deleteRegularIra(id, userName, closingTo);
	}

	@PatchMapping(value = "/Users/{userName}/Rollover IRA/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeRolloverIra(@PathVariable("userName") String userName, @PathVariable("id") Long id,
			@PathVariable("closingTo") String closingTo) {
		service.getUserById(userName).deleteRolloverIra(closingTo);
		service.deleteRolloverIra(id, userName, closingTo);
	}

	@PatchMapping(value = "/Users/{userName}/Roth IRA/{id}/{closingTo}")
	@ResponseStatus(HttpStatus.OK)
	public void removeRothIra(@PathVariable("userName") String userName, @PathVariable("id") Long id,
			@PathVariable("closingTo") String closingTo) {
		service.getUserById(userName).deleteRothIra(closingTo);
		service.deleteRothIra(id, userName, closingTo);
	}

	// Transaction APIs ============================================================

	// Return a list of transactions for a particular user:
	@GetMapping(value = "/Users/{userName}/transactions")
	@ResponseStatus(HttpStatus.OK)
	public List<Transaction> getTransactions(@PathVariable("userName") String userName) {
		return service.getTransactionsByUser(userName);
	}

	// Return a list of all transactions in the database:
	@GetMapping(value = "/Users/transactions")
	@ResponseStatus(HttpStatus.OK)
	public List<Transaction> getAllTransactions() {
		return service.getTransactions();
	}

	// Add transaction for a particular user:
	@PostMapping(value = "/Users/{userName}/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction postTransaction(@PathVariable("userName") String userName,
			@RequestBody Transaction transaction) {
		return service.addTransaction(transaction, userName);
	}

}