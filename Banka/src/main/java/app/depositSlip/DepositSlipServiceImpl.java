package app.depositSlip;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			Date date, Type type, String billOfReceiver, String billOfDeptor, double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(date, type, billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(Date date, Type type,
			String billOfDeptor, double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(date, type, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(Date date, Type type,
			String billOfReceiver, double amount) {
		return depositSlipRepository.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(date, type, billOfReceiver, amount);
	}

	@Override
	public List<DepositSlip> findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(Date date,
			String billOfReceiver, String billOfDeptor, double amount) {
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
			String billOfReceiver, String billOfDeptor, double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(type, billOfReceiver, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfDeptorLikeAndAmount(Type type, String billOfDeptor, double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfDeptorLikeAndAmount(type, billOfDeptor, amount);
	}

	@Override
	public List<DepositSlip> findByTypeAndBillOfReceiverLikeAndAmount(Type type, String billOfReceiver, double amount) {
		// TODO Auto-generated method stub
		return depositSlipRepository.findByTypeAndBillOfReceiverLikeAndAmount(type, billOfReceiver, amount);
	}

	@Override
	public List<DepositSlip> findByBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(String billOfReceiver,
			String billOfDeptor, double amount) {
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

}