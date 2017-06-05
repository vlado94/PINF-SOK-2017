package app.depositSlip;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.enums.Status;
import app.enums.Type;

@Service
@Transactional
public class DepositSlipServiceImpl implements DepositSlipService{
	
	private final DepositSlipRepository depositSlipRepository;
	
	@Autowired
	public DepositSlipServiceImpl(final DepositSlipRepository depositSlipRepository) {
		this.depositSlipRepository = depositSlipRepository;
	}
	
	@Override
	public List<DepositSlip> findAll() {
		return (List<DepositSlip>) depositSlipRepository.findAll();
	}
	
	@Override
	public DepositSlip save(DepositSlip dailyBalance) {
		return depositSlipRepository.save(dailyBalance);
	}

	@Override
	public DepositSlip findOne(Long id) {
		return depositSlipRepository.findOne(id);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(
			Date date, Type type, String billOfReceiver, String billOfDeptor, Double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(date, type, billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(Date date, Type type,
			String billOfDeptor, Double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(date, type, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(Date date, Type type,
			String billOfReceiver, Double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(date, type, billOfReceiver, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,
			String billOfReceiver, String billOfDeptor, Double amount) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(date, billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Date date, Type type,
			String billOfReceiver, String billOfDeptor) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(date, billOfReceiver, billOfDeptor);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLike(Date date, Type type,
			String billOfDeptor) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfDeptorLike(date, type, billOfDeptor);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLike(Date date, Type type,
			String billOfReceiver) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLike(date, type, billOfReceiver);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(Date date,
			String billOfReceiver, String billOfDeptor) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(date, billOfReceiver, billOfDeptor);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Type type,
			String billOfReceiver, String billOfDeptor, Double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(type, billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmount(Type type, String billOfDeptor, Double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfDeptorLikeAndAmount(type, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmount(Type type, String billOfReceiver, Double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndAmount(type, billOfReceiver, amount);
	}

	@Override
	public List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(String billOfReceiver,
			String billOfDeptor, Double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(Type type, String billOfReceiver,
			String billOfDeptor) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(type, billOfReceiver, billOfDeptor);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfDeptorLike(Type type, String billOfDeptor) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfDeptorLike(type, billOfDeptor);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLike(Type type, String billOfReceiver) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLike(type, billOfReceiver);
	}

	@Override
	public List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLike(String billOfReceiver, String billOfDeptor) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByBillOfReceiverLikeAndBillOfDeptorLike(billOfReceiver, billOfDeptor);
	}
	
	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(
			Date date, Type type, String billOfReceiver, String billOfDeptor, double amount,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(date, type, billOfReceiver, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmountAndStatus(Date date, Type type,
			String billOfDeptor, Double amount,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmountAndStatus(date, type, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmountAndStatus(Date date, Type type,
			String billOfReceiver, Double amount,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmountAndStatus(date, type, billOfReceiver, amount,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(Date date,
			String billOfReceiver, String billOfDeptor, Double amount,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(date, billOfReceiver, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Date date, Type type,
			String billOfReceiver, String billOfDeptor,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(date, billOfReceiver, billOfDeptor,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndStatus(Date date, Type type,
			String billOfDeptor,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndStatus(date, type, billOfDeptor,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndStatus(Date date, Type type,
			String billOfReceiver,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndStatus(date, type, billOfReceiver,status);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Date date,
			String billOfReceiver, String billOfDeptor,Status status) {
		return depositSlipRepository.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(date, billOfReceiver, billOfDeptor,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(Type type,
			String billOfReceiver, String billOfDeptor, Double amount,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(type, billOfReceiver, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmountAndStatus(Type type, String billOfDeptor, Double amount,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfDeptorLikeAndAmountAndStatus(type, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmountAndStatus(Type type, String billOfReceiver, Double amount,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndAmountAndStatus(type, billOfReceiver, amount,status);
	}

	@Override
	public List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(String billOfReceiver,
			String billOfDeptor, Double amount,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(billOfReceiver, billOfDeptor, amount,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(Type type, String billOfReceiver,
			String billOfDeptor,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(type, billOfReceiver, billOfDeptor,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfDeptorLikeAndStatus(Type type, String billOfDeptor,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfDeptorLikeAndStatus(type, billOfDeptor,status);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndStatus(Type type, String billOfReceiver,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndStatus(type, billOfReceiver,status);
	}

	@Override
	public List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(String billOfReceiver, String billOfDeptor,Status status) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(billOfReceiver, billOfDeptor,status);
	}

	

}