import com.app.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/something")
public class Controller {

	private ItemDao itemDao;

	@Autowired
	public Controller(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	// CREATE

	@GetMapping("/new")
	public String createForm(@ModelAttribute("item") Item item) {
		return "items/new";
	}

	@PostMapping() 
	public String create(@ModelAttribute("item") @Valid Item item,
						 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "items/new";
		}
		itemDao.save(item);
		return "redirect:items/all";
	}

	// READ

	@GetMapping("/all")
	public String getAllItems(Model model) {
		model.addAttribute("item", itemDao.getItem());
		return "items/all";
	}

	@GetMapping("/item/{id}")
	public String getOneItem(@PathVariable("id") int id, Model model) {
		model.addAttribute("item", itemDao.getItem(id));
		return "items/item";
	}

	// UPDATE

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("item", itemDao.getItem(id));
		return "items/edit";
	}

	@PatchMapping("/{id}")
	public String update(@PathVariable("id") int id,
						 @ModelAttribute("person") @Valid Item item,
						 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "items/edit";
		}
		itemDao.update(id, item);
		return "redirect:/items/all";
	}

	// DELETE

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		itemDao.delete(id);
		return "redirect:/items/all";
	}	
}
