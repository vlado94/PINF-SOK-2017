package app.depositSlip;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.enums.Status;
import app.enums.Type;


public interface DepositSlipRepository extends PagingAndSortingRepository<DepositSlip, Long> {
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.billOfDeptor like ?4 and d.amount=?5 and d.status=?6")
	List<DepositSlip> findAllDepositSlipsForBank(int bankCode);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.billOfDeptor like ?4 and d.amount=?5")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfReceiver,String billOfDeptor,Double amount);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfDeptor like ?3 and d.amount=?4")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(Date date,Type type,String billOfDeptor,Double amount);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.amount=?4")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(Date date,Type type,String billOfReceiver,Double amount);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.amount=?4")
	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,String billOfReceiver,String billOfDeptor,Double amount);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.billOfDeptor like ?4")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,Type type,String billOfReceiver,String billOfDeptor);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfDeptor like ?3")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLike(Date date,Type type,String billOfDeptor);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLike(Date date,Type type,String billOfReceiver);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3")
	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,String billOfReceiver,String billOfDeptor);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.amount=?4")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Type type,String billOfReceiver,String billOfDeptor,Double amount);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfDeptor like ?2 and d.amount=?3")
	List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmount(Type type,String billOfDeptor,Double amount);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.amount=?3")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmount(Type type,String billOfReceiver,Double amount);

	@Query("select d from DepositSlip d where d.billOfReceiver like ?1 and d.billOfDeptor like ?2 and d.amount=?3")
	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(String billOfReceiver,String billOfDeptor,Double amount);

	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Type type,String billOfReceiver,String billOfDeptor);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfDeptor like ?2")
	List<DepositSlip> findByTypeAndBillOfDeptorLike(Type type, String billOfDeptor);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2")
	List<DepositSlip> findByTypeAndBillOfReceiverLike(Type type,String billOfReceiver);

	@Query("select d from DepositSlip d where d.billOfReceiver like ?1 and d.billOfDeptor like ?2")
	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLike(String billOfReceiver,String billOfDeptor);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.billOfDeptor like ?4 and d.amount=?5 and d.status=?6")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(Date date,Type type,String billOfReceiver,String billOfDeptor,double amount,Status status);

	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfDeptor like ?3 and d.amount=?4 and d.status=?5")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmountAndStatus(Date date,Type type,String billOfDeptor,Double amount,Status status);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.amount=?4 and d.status=?5")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmountAndStatus(Date date,Type type,String billOfReceiver,Double amount,Status status);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.amount=?4 and d.status=?5")
	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(Date date,String billOfReceiver,String billOfDeptor,Double amount,Status status);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.billOfDeptor like ?4 and d.status=?5")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Date date,Type type,String billOfReceiver,String billOfDeptor,Status status);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfDeptor like ?3 and d.status=?4")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndStatus(Date date,Type type,String billOfDeptor,Status status);
	
	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.type=?2 and d.billOfReceiver like ?3 and d.status=?4")
	List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndStatus(Date date,Type type,String billOfReceiver,Status status);

	@Query("select d from DepositSlip d where d.depositSlipDate=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.status=?4")
	List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Date date,String billOfReceiver,String billOfDeptor,Status status);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.amount=?4 and d.status=?5")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(Type type,String billOfReceiver,String billOfDeptor,Double amount,Status status);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfDeptor like ?2 and d.amount=?3 and d.status=?4")
	List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmountAndStatus(Type type,String billOfDeptor,Double amount,Status status);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.amount=?3 and d.status=?4")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmountAndStatus(Type type,String billOfReceiver,Double amount,Status status);

	@Query("select d from DepositSlip d where d.billOfReceiver like ?1 and d.billOfDeptor like ?2 and d.amount=?3 and d.status=?4")
	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(String billOfReceiver,String billOfDeptor,Double amount,Status status);

	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.billOfDeptor like ?3 and d.status=?4")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Type type,String billOfReceiver,String billOfDeptor,Status status);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfDeptor like ?2 and d.status=?3")
	List<DepositSlip> findByTypeAndBillOfDeptorLikeAndStatus(Type type, String billOfDeptor,Status status);
	
	@Query("select d from DepositSlip d where d.type=?1 and d.billOfReceiver like ?2 and d.status=?3")
	List<DepositSlip> findByTypeAndBillOfReceiverLikeAndStatus(Type type,String billOfReceiver,Status status);

	@Query("select d from DepositSlip d where d.billOfReceiver like ?1 and d.billOfDeptor like ?2 and d.status=?3")
	List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(String billOfReceiver,String billOfDeptor,Status status);

	

	

	
}
