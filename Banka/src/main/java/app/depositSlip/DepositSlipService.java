package app.depositSlip;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import app.depositSlip.DepositSlip.Type;

public interface DepositSlipService {
	
	public List<DepositSlip> findAll();

	public DepositSlip save(DepositSlip depositSlip);

	public DepositSlip findOne(Long id);	
		
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfReceiver,String billOfDeptor,double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfDeptor,double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(Date date,Type type,String billOfReceiver,double amount);

	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,String billOfReceiver,String billOfDeptor,double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,Type type,String billOfReceiver,String billOfDeptor);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLike(Date date,Type type,String billOfDeptor);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLike(Date date,Type type,String billOfReceiver);

	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,String billOfReceiver,String billOfDeptor);

	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Type type,String billOfReceiver,String billOfDeptor,double amount);
	
	List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmount(Type type,String billOfDeptor,double amount);
	
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmount(Type type,String billOfReceiver,double amount);

	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(String billOfReceiver,String billOfDeptor,double amount);

	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Type type,String billOfReceiver,String billOfDeptor);
	
	List<DepositSlip> findByTypeAndBillOfDeptorLike(Type type, String billOfDeptor);
	
	List<DepositSlip> findByTypeAndBillOfReceiverLike(Type type,String billOfReceiver);

	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLike(String billOfReceiver,String billOfDeptor);
}