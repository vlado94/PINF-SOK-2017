package app.codeBookActivities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CodeBookActivities { //sifarnik djelatnosti

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CODE_BOOK_ACTIVITIES_ID")
	private Long id;
	
	@Column
	private Integer code; //number(5) not null
	
	@Column
	private String name; //not nul
}
