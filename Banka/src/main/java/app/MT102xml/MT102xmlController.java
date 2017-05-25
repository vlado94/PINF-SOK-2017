package app.MT102xml;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import app.MyValidationEventHandler;
import app.depositSlip.DepositSlip;
import app.depositSlip.DepositSlipService;
import app.depositSlip.DepositSlipXml;
import app.depositSlip.HeaderMT102xml;
import app.interbankTransfer.InterbankTransfer;
import app.interbankTransfer.InterbankTransferService;




@RestController
@RequestMapping("/mt102")
public class MT102xmlController {

	private final MT102xmlService MT102xmlService;
	private final DepositSlipService depositSlipService;
	private final InterbankTransferService interbankTransferService;
	private HttpSession httpSession;
	
	@Autowired
	public MT102xmlController(final InterbankTransferService interbankTransferService,final MT102xmlService mt102Service,HttpSession httpSession,final DepositSlipService depositSlipService) {
		this.MT102xmlService = mt102Service;
		this.httpSession = httpSession;
		this.depositSlipService = depositSlipService;
		this.interbankTransferService = interbankTransferService;
	}
	
	
	@GetMapping("/getMT102Xml")
	@ResponseStatus(HttpStatus.OK)
	public void getXml() {
		try {
			File file = new File("C:\\mt102xml.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(MT102xml.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); //unmarshaller
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = null;
			try {
				schema = schemaFactory.newSchema(new File("C:\\mt102xml.xsd"));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jaxbUnmarshaller.setSchema(schema);
			jaxbUnmarshaller.setEventHandler(new MyValidationEventHandler());
			
			MT102xml mt102 = (MT102xml) jaxbUnmarshaller.unmarshal(file);
			System.out.println(mt102);
			/*MT102Service.save(mt102);*/

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
	}
	
	@GetMapping("/makeMT102Xml")
	@ResponseStatus(HttpStatus.OK)
	public void makeXml() {
		
		DepositSlip depositSlip = depositSlipService.findOne((long)5);
		DepositSlipXml xml =  new DepositSlipXml();
		xml.setMessageId("ndsk2fdsmfs");
		xml.setDeptor(depositSlip.getDeptor());
		xml.setReceiver(depositSlip.getReceiver());
		xml.setPurposeOfPayment(depositSlip.getPurposeOfPayment());
		xml.setBillOfDeptor(depositSlip.getBillOfDeptor());
		xml.setModelAssignment(depositSlip.getModelAssignment());
		xml.setReferenceNumberAssignment(depositSlip.getReferenceNumberAssignment());
		xml.setBillOfReceiver(depositSlip.getBillOfReceiver());
		xml.setModelApproval(depositSlip.getModelApproval());
		xml.setReferenceNumberApproval(depositSlip.getReferenceNumberApproval());
		xml.setAmount(depositSlip.getAmount());
		xml.setCodeOfCurrency(depositSlip.getCodeOfCurrency());
		
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    Date parsed = null;
			parsed = format.parse(depositSlip.getDepositSlipDate().toString());
			java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
			   xml.setDepositSlipDate(sqlDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		HeaderMT102xml header = new HeaderMT102xml();
		header.setMessageId("fsf1ds2ds");
		header.setDebtorBankswiftCode("12312352");
	    header.setDebtorBankclearingAccount("181818181818181818");
		header.setReceiverBankswiftCode("89898989");
		header.setReceiverBankclearingAccount("281318181818181818");
		header.setAmount(new BigDecimal(12.23).setScale(2, RoundingMode.CEILING));
		header.setCurrencyCode("rsd");
		
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    Date parsedCurrencyDate = null;
		    Date parsedDate = null;
		    parsedCurrencyDate = format.parse(depositSlip.getCurrencyDate().toString());
		    parsedDate = format.parse(depositSlip.getDepositSlipDate().toString());
			java.sql.Date sqlCurrencyDate = new java.sql.Date(parsedCurrencyDate.getTime());
			java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
			   header.setCurrencyDate(sqlCurrencyDate);
			   header.setDate(sqlDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MT102xml mt102 = new MT102xml();
		mt102.setHeaderMT102xml(header);
		mt102.getDepositSlipXml().add(xml);
       
		try {

			File file = new File("generisanMT102.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(MT102xml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(mt102, file);
			jaxbMarshaller.marshal(mt102, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }
	   
	
	}
	
	@GetMapping(path = "/exportDepositSlips")
	@ResponseStatus(HttpStatus.OK)
	public void exportDepositSlips() {
		List<InterbankTransfer> interbankTransfers = interbankTransferService.findAllAndDateTimeIsNull();
		
		
		for(int i = 0; i < interbankTransfers.size(); i++){//svaki interbankTransfer je jedan paket odnosno
			MT102xml mt102 = new MT102xml();				//mt102 poruka
			InterbankTransfer transfer = interbankTransfers.get(i);
			
			HeaderMT102xml header = new HeaderMT102xml();
			header.setMessageId("fsf1ds2ds");
			header.setDebtorBankswiftCode(transfer.getBankSender().getSwiftCode().toString());
			header.setDebtorBankclearingAccount(transfer.getBankSender().getClearingAccount().toString());
			header.setReceiverBankswiftCode(transfer.getBankReciever().getSwiftCode().toString());
			header.setReceiverBankclearingAccount(transfer.getBankReciever().getClearingAccount().toString());
			header.setAmount((new BigDecimal(transfer.getAmount())).setScale(2, RoundingMode.CEILING));
			header.setCurrencyCode(transfer.getCurrencyCode());
			
			
			try {
				Date dateFromTimeStap = new Date();
			    java.sql.Date sqlCurrencyDate = new java.sql.Date(dateFromTimeStap.getTime());
				java.sql.Date sqlDate = new java.sql.Date(dateFromTimeStap.getTime());
				   header.setCurrencyDate(sqlCurrencyDate);
				   header.setDate(sqlDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			mt102.setHeaderMT102xml(header);
			
			for(int j = 0; j< transfer.getDepositSlips().size(); j++){ //za svaki deposit slip iz tranfera 
			
				DepositSlip depositSlip = transfer.getDepositSlips().get(j);
				
				DepositSlipXml xml =  new DepositSlipXml();
				xml.setMessageId("ndsk2fdsmfs");
				xml.setDeptor(depositSlip.getDeptor());
				xml.setReceiver(depositSlip.getReceiver());
				xml.setPurposeOfPayment(depositSlip.getPurposeOfPayment());
				xml.setBillOfDeptor(depositSlip.getBillOfDeptor());
				xml.setModelAssignment(depositSlip.getModelAssignment());
				xml.setReferenceNumberAssignment(depositSlip.getReferenceNumberAssignment());
				xml.setBillOfReceiver(depositSlip.getBillOfReceiver());
				xml.setModelApproval(depositSlip.getModelApproval());
				xml.setReferenceNumberApproval(depositSlip.getReferenceNumberApproval());
				xml.setAmount(depositSlip.getAmount());
				xml.setCodeOfCurrency(depositSlip.getCodeOfCurrency());
				
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				    Date parsed = null;
					parsed = format.parse(depositSlip.getDepositSlipDate().toString());
					java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
					   xml.setDepositSlipDate(sqlDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				mt102.getDepositSlipXml().add(xml);
			
			} //kraj fora za deposit slip
		
			try {

				File file = new File("mt102Poslovna.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(MT102xml.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				jaxbMarshaller.marshal(mt102, file);
				jaxbMarshaller.marshal(mt102, System.out);

			      } catch (JAXBException e) {
				e.printStackTrace();
			      }
		   
		} //kraj for-a
		
		
		
	}
	
}
