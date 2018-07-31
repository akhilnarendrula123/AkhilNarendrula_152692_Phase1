package com.cg.paymentwallet.repo;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.IPaymentWalletException;
import com.cg.paymentwallet.exception.PaymentWalletException;

public class WalletRepoImpl implements IWalletRepo {
	private static HashMap<String, Customer> custInfo = null;

	private static HashMap<String, StringBuilder> printList = null;

	static {
		custInfo = new HashMap<String, Customer>();
		printList = new HashMap<String, StringBuilder>();
	}

	public String createAccount(Customer customerBean) {
		custInfo.put(customerBean.getMobileNo(), customerBean);
		StringBuilder builder = new StringBuilder("Account Created On:\t" + LocalDateTime.now() + "\n");
		printList.put(customerBean.getMobileNo(), builder);
		String phone = customerBean.getMobileNo();
		return phone;
	}

	public Customer showBalance(String custContact) {
		Customer search = null;
		if (custInfo.get(custContact) != null) {
			search = custInfo.get(custContact);
		}
		return search;
	}

	public boolean withdrawAmount(double withdrawAmt, String custContact) {
		boolean result = false;
		if (custInfo.get(custContact) != null) {
			result = true;
			if (custInfo.get(custContact).getBalance() > withdrawAmt) {
				custInfo.get(custContact).setBalance(custInfo.get(custContact).getBalance() - withdrawAmt);
				StringBuilder builder = new StringBuilder("Withdrawn\t" + withdrawAmt + "\t" + "Remaining Balance:\t"
						+ custInfo.get(custContact).getBalance() + "\n");
				printList.put(custContact, printList.get(custContact).append(builder));

			}
		}
		return result;
	}

	public boolean depositAmount(double depositAmt, String custContact) {
		boolean result = false;
		if (custInfo.get(custContact) != null) {
			result = true;
			custInfo.get(custContact).setBalance(custInfo.get(custContact).getBalance() + depositAmt);
			StringBuilder builder = new StringBuilder("Deposited\t" + depositAmt + "\t" + "Remaining Balance:\t"
					+ custInfo.get(custContact).getBalance() + "\n");
			printList.put(custContact, printList.get(custContact).append(builder));
		}
		return result;
	}

	public boolean fundTransfer(String senderCont, String receiverCont, double custAmount)
			throws PaymentWalletException {
		boolean result = false;
		if (custInfo.get(senderCont) != null && custInfo.get(receiverCont) != null) {
			result = true;
			custInfo.get(senderCont).setBalance(custInfo.get(senderCont).getBalance() - custAmount);
			StringBuilder builder = new StringBuilder("Transferred\t" + custAmount + "\t" + "Remaining Balance:\t"
					+ custInfo.get(senderCont).getBalance() + "\n");
			printList.put(senderCont, printList.get(senderCont).append(builder));
			custInfo.get(receiverCont).setBalance(custInfo.get(receiverCont).getBalance() + custAmount);
			StringBuilder builder1 = new StringBuilder("Transferred\t" + custAmount + "\t" + "Remaining Balance:\t"
					+ custInfo.get(receiverCont).getBalance() + "\n");
			printList.put(senderCont, printList.get(receiverCont).append(builder1));

		} else
			throw new PaymentWalletException(IPaymentWalletException.ERROR8);
		return result;
	}

	public Customer login(String mobileNo, String password) throws PaymentWalletException {
		Customer wall = null;
		if (custInfo.containsKey(mobileNo) && custInfo.get(mobileNo).getPassword().equals(password)) {
			wall = custInfo.get(mobileNo);
		} else
			throw new PaymentWalletException(IPaymentWalletException.ERROR7);
		return wall;
	}

	public StringBuilder printTransactions(String mob) {
		return printList.get(mob);
	}

}
