package com.cg.paymentwallet.service;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.IPaymentWalletException;
import com.cg.paymentwallet.exception.PaymentWalletException;
import com.cg.paymentwallet.repo.IWalletRepo;
import com.cg.paymentwallet.repo.WalletRepoImpl;

public class WalletServiceImpl implements IWalletService {

	private static IWalletRepo dao = null;
	
	static {
		dao=new WalletRepoImpl();
	}

	public String createAccount(Customer customerBean) {
		return dao.createAccount(customerBean);
	}

	public Customer showBalance(String custContact) {
		return dao.showBalance(custContact);
	}

	public boolean withdrawAmount(double withdrawAmt, String custContact) {
		return dao.withdrawAmount(withdrawAmt, custContact);
	}

	public boolean depositAmount(double depositAmt, String custContact) {
		return dao.depositAmount(depositAmt, custContact);
	}

	public boolean fundTransfer(String senderCont, String receiverCont, double custBalance)
			throws PaymentWalletException {
		return dao.fundTransfer(senderCont, receiverCont, custBalance);
	}

	public StringBuilder printTransactions(String mob) {
		return dao.printTransactions(mob);
	}

	public boolean validateDetails(Customer customerBean) throws PaymentWalletException {
		boolean result = false;
		String regex = "[A-Z]{1}[a-z]+";
		String regex2 = "^\\d{10}$";
		String regex3 = "[a-z]{1}[a-z0-9_.]*@gmail.com";
		String regex4 = "[0-9]{2}";
		if (customerBean.getName().matches(regex)) {
			if (customerBean.getMobileNo().matches(regex2)) {
				if (customerBean.getEmailId().matches(regex3)) {
					if (String.valueOf(customerBean.getAge()).matches(regex4)) {
						if (customerBean.getGender().matches("male") || customerBean.getGender().matches("female")) {
							result = true;
						} else
							throw new PaymentWalletException(IPaymentWalletException.ERROR5);
					} else
						throw new PaymentWalletException(IPaymentWalletException.ERROR4);
				} else
					throw new PaymentWalletException(IPaymentWalletException.ERROR3);
			} else
				throw new PaymentWalletException(IPaymentWalletException.ERROR2);
		} else
			throw new PaymentWalletException(IPaymentWalletException.ERROR1);
		return result;
	}

	public Customer login(String id, String password) throws PaymentWalletException {
		return dao.login(id, password);
	}
}
