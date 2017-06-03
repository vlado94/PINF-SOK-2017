package app.user.banker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bank.Bank;
import app.bank.BankService;
import app.bill.Bill;
import app.bill.BillService;
import app.closingBill.ClosingBill;
import app.closingBill.ClosingBillService;
import app.dailyBalance.DailyBalance;
import app.depositSlip.DepositSlip;
import app.depositSlip.DepositSlipService;
import app.modelView.Excerpt;
import app.modelView.ExcerptForBills;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/banker")
public class BankerController {

	private final BankerService bankerService;
	private final BankService bankService;
	private final BillService billService;
	private HttpSession httpSession;
	private final ClosingBillService closingBillService;
	private final DepositSlipService depositSlipService;
	

	private JavaMailSender mailSender;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, 
							final BillService billService, final ClosingBillService closingBillService,
							final DepositSlipService depositSlipService,final BankService bankService,JavaMailSender mailSender) {
		this.bankerService = bankerService;
		this.bankService = bankService;
		this.billService = billService;
		this.httpSession = httpSession;
		this.closingBillService = closingBillService;
		this.depositSlipService = depositSlipService;
		this.mailSender = mailSender;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public Banker checkRights() throws AuthenticationException {
		try {
			Banker banker = bankerService.findOneById(((Banker) httpSession.getAttribute("user")).getId());
			return banker;
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
	
	@GetMapping("/getBillsForBank")
	@ResponseStatus(HttpStatus.OK)
	public List<Bill> getBillsForBank() throws AuthenticationException {
		try {
			Banker banker = bankerService.findOneById(((Banker) httpSession.getAttribute("user")).getId());
			return banker.getBank().getBills();
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
	
	@PutMapping("/updateProfile/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Banker update(@PathVariable Long id,@RequestBody Banker banker) {
		Banker bankerForEdit = bankerService.findOneById(id);
		if(bankerForEdit != null) {
			bankerForEdit.setAttributes(banker);
			banker = bankerService.save(bankerForEdit);
			return banker;		
		}
		else {
			throw new NotFoundException();
		}
	}
	
	@GetMapping("/getDepositSlipsForBill/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<DepositSlip> getDepositSlipsForBill(@PathVariable Long id) {
		List<DepositSlip> retVal = new ArrayList<DepositSlip>();
		Bill bill = billService.findOne(id);
		if(bill.getDailyBalances() == null)
			bill.setDailyBalances(new ArrayList<DailyBalance>());
		for(int i=0;i<bill.getDailyBalances().size();i++) {
			retVal.addAll(bill.getDailyBalances().get(i).getDepositSlips());
		}
	
		return retVal;

	}
	
	@GetMapping("/makePDF")
	@ResponseStatus(HttpStatus.OK)
	public void handleSimpleReportMulti2() throws JRException, FileNotFoundException {
		
		ProductModel pr = new ProductModel();
	    String outputFile ="D:\\JasperTableExample2.pdf";
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(pr.findAll());

        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ItemDataSource", itemsJRBean);

        /* Using compiled version(.jasper) of Jasper report to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\products1.jasper", parameters, new JREmptyDataSource());

       
        /* outputStream to create PDF */
        OutputStream outputStream = new FileOutputStream(new File(outputFile));
        /* Write content to PDF file */
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	
	//srediti izvestaj za dammy podatke
	//na frontu odabrati datume a pre toga korisnika
	//namestiti da fajlovi budu ispod app properties
	@PutMapping(path ="/makePDFForClient/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void makePDFForClient(@PathVariable Long id,  @RequestBody Map<String, String> mapOfDays,HttpServletResponse response) throws JRException, IOException {
		Date from = null;
		Date to = null;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			from = format.parse(mapOfDays.get("first"));
			to = format.parse(mapOfDays.get("last"));
			Calendar c = Calendar.getInstance(); 
			c.setTime(to); 
			c.add(Calendar.DATE, 1);
			to = c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<DepositSlip> slips = new ArrayList<DepositSlip>();
		long BankID = (((Banker)httpSession.getAttribute("user")).getBank().getId());
		Bank bank = bankService.findOne(BankID);
		
		for (Bill bill : bank.getBills()) {
			if(bill.getClient().getId() == id) {
				for(DailyBalance db : bill.getDailyBalances()) {
					if(db.getDate() != null && db.getDate().before(to) && db.getDate().after(from)) {
						slips.addAll(db.getDepositSlips());
					}
				}
			}
		}
		String outputFile ="D:\\ExcerptForClient.pdf";
		Excerpt ex = new Excerpt();
	    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(slips);
		
        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ItemDataSource", itemsJRBean);
        parameters.put("to", ex.getToDate());
        parameters.put("from", ex.getFromDate());

        /* Using compiled version(.jasper) of Jasper report to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\excerpt.jasper", parameters, new JREmptyDataSource());

       
        /* outputStream to create PDF */
        File file = new File(outputFile);
        
        OutputStream outputStream = new FileOutputStream(file);
        /* Write content to PDF file */
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        httpSession.setAttribute("file", file);
        
        MimeMessage message = mailSender.createMimeMessage();

		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setText("Excerpt");
			helper.setFrom("poslovnainfo@gmail.com");
			helper.setTo("vladimir94@gmail.com");
			helper.setSubject("test");
			helper.setText(String.format(
				simpleMailMessage.getText(), " ", " "));
				helper.addAttachment("excerpt.pdf", file);
	     }catch (MessagingException e) {
	    	 throw new MailParseException(e);
	     }
	     mailSender.send(message);
	}
	
	@GetMapping("/makePDFForClient")
	@ResponseStatus(HttpStatus.OK)
	public void makePDFForClient(HttpServletResponse response) throws JRException, IOException {
		File file = (File)httpSession.getAttribute("file");
		response.setContentType("application/pdf");
		InputStream inputStream = new FileInputStream(file);
		IOUtils.copy(inputStream, response.getOutputStream());
	         
	}
	
	
	@GetMapping("/makePDFForBank")
	@ResponseStatus(HttpStatus.OK)
	public void getReportForBank(HttpServletResponse response) throws JRException, IOException {
		Bank bank = bankService.findOne(((Banker)httpSession.getAttribute("user")).getBank().getId());
		ExcerptForBills ex = new ExcerptForBills(bank);
	    String outputFile ="D:\\ExcerptForBank.pdf";
	    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ex.setBills());
		
        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ItemDataSource", itemsJRBean);
        parameters.put("BankName", bank.getName());
        parameters.put("CurrencyCode", bank.getCurrencyCode());

        JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\excerptBank.jasper", parameters, new JREmptyDataSource());
        File file = new File(outputFile);
        OutputStream outputStream = new FileOutputStream(file);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        response.setContentType("application/pdf");
		InputStream inputStream = new FileInputStream(file);
		IOUtils.copy(inputStream, response.getOutputStream());
	}
	
	@PostMapping(path = "/closeBill")
	@ResponseStatus(HttpStatus.CREATED)
	public ClosingBill closeBill(@Valid @RequestBody ClosingBill closingBill) {
		Bill billForClosing = closingBill.getBill();
		String billSuccessor = closingBill.getBillSuccessor();
		DepositSlip depositSlip = new DepositSlip(billForClosing,closingBill,billSuccessor);
		//novac za prenos dobijam iz dnevnog stanja racuna kog zatvaraju 
		List<DailyBalance> dbs = billForClosing.getDailyBalances();
		if(!dbs.isEmpty()){
			DailyBalance db = dbs.get(dbs.size()-1);
			depositSlip.setAmount(db.getNewState());
		}
		//poziva obradu izvoda
		depositSlipService.save(depositSlip);
		closingBill.setDepositSlip(depositSlip);
		ClosingBill savedClosingBill = closingBillService.save(closingBill);
		if(savedClosingBill != null){//uspjesno zatvoren racuna
			billForClosing.setStatus(false);
			billService.save(billForClosing);
			return savedClosingBill;
		}else{
			return null;
		}
	}	
	

}