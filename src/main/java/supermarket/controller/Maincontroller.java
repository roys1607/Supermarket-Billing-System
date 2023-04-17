package supermarket.controller;

import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import supermarket.dao.ProductDao;
import supermarket.model.Product;

@Controller
public class Maincontroller {
	
	@Autowired
	private ProductDao productdao;
	
	@RequestMapping("/")
	public String Home(Model m)
	{
		
		List<Product> products = productdao.getProducts();
		m.addAttribute("products", products);
		
		return "index";
	}
	
	// show add product form
	@RequestMapping("/add-product")
	public String addProduct(Model m)
	{
		m.addAttribute("title","Add Product");
		return "add_Product_form";
	}
	
	// handle add Product form
	
	
	@RequestMapping(value="/handle-product",method = RequestMethod.POST)
	public RedirectView handleProduct(@ModelAttribute Product product,HttpServletRequest request)
	{
		System.out.println(product);
		productdao.createProduct(product);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		return redirectView;
		}
	
	
	// Delete handler
	@RequestMapping("/delete/{productId}")
	public RedirectView deleteProduct(@PathVariable("productId") int productId,HttpServletRequest request)
	{
		this.productdao.deleteProduct(productId);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath()+"/");
		return redirectView;
	}
    // update handler
	@RequestMapping("/update/{productId}")
	public String updateForm(@PathVariable("productId") int pid,Model model) 
	{
		Product product = this.productdao.getProduct(pid);
		model.addAttribute("product", product);
		return "update_form";
	}
}
