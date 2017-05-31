package app.accountStatement;

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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

@RestController
@RequestMapping("/accountStatement")
public class AccountStatementController {

	private final AccountStatementService accountStatementService;
	private HttpSession httpSession;
	
	@Autowired
	public AccountStatementController(final AccountStatementService accountStatementService,HttpSession httpSession) {
		this.accountStatementService = accountStatementService;
		this.httpSession = httpSession;
		
	}
	

	@GetMapping("/getAccountStatementXml")
	@ResponseStatus(HttpStatus.OK)
	public void getXml() {
		try {
			File file = new File("C:\\accountStatement.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(AccountStatement.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); //unmarshaller
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = null;
			try {
				schema = schemaFactory.newSchema(new File("C:\\accountStatement.xsd"));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jaxbUnmarshaller.setSchema(schema);
			jaxbUnmarshaller.setEventHandler(new MyValidationEventHandler());
			
			AccountStatement mt102 = (AccountStatement) jaxbUnmarshaller.unmarshal(file);
			System.out.println(mt102);
			/*MT102Service.save(mt102);*/

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
	}
	
	
}
