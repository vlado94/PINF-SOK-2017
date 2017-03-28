package app.user.banker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banker")
public class BankerController {
	private final BankerService bankerService;

	@Autowired
	public BankerController(final BankerService bankerService) {
		this.bankerService = bankerService;
	}
}
