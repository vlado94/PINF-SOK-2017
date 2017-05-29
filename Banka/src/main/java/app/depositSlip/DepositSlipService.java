package app.depositSlip;

import java.sql.Date;
import java.util.List;

import app.enums.Type;

public interface DepositSlipService {
	
	public List<DepositSlip> findAll();

	public DepositSlip save(DepositSlip depositSlip);

	public DepositSlip findOne(Long id);	
		
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfReceiver,String billOfDeptor,Double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfDeptor,Double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(Date date,Type type,String billOfReceiver,Double amount);

	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,String billOfReceiver,String billOfDeptor,Double amount);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,Type type,String billOfReceiver,String billOfDeptor);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLike(Date date,Type type,String billOfDeptor);

	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLike(Date date,Type type,String billOfReceiver);

	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,String billOfReceiver,String billOfDeptor);

	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Type type,String billOfReceiver,String billOfDeptor,Double amount);
	
	List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmount(Type type,String billOfDeptor,Double amount);
	
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmount(Type type,String billOfReceiver,Double amount);

	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(String billOfReceiver,String billOfDeptor,Double amount);

	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Type type,String billOfReceiver,String billOfDeptor);
	
	List<DepositSlip> findByTypeAndBillOfDeptorLike(Type type, String billOfDeptor);
	
	List<DepositSlip> findByTypeAndBillOfReceiverLike(Type type,String billOfReceiver);

	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLike(String billOfReceiver,String billOfDeptor);
}