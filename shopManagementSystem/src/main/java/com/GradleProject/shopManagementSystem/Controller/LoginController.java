package com.GradleProject.shopManagementSystem.Controller;

import com.GradleProject.shopManagementSystem.Model.DAO.ProductDao;
import com.GradleProject.shopManagementSystem.Model.DAO.SellsManDao;
import com.GradleProject.shopManagementSystem.Model.Product;
import com.GradleProject.shopManagementSystem.Model.SellsManDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "LoginView")
public class LoginController {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private SellsManDao sellsManDao;

    private List<Product> selectedProductList;
    private List<Product> sellProducts = new ArrayList<>();
    private boolean isLoggedin = false;
    private double totalAmount = 0;
    private Product updateProduct;

    private String getErrors;

    private String checkedLoginRoll;
    private int sellsManLogInId = -1;

    private String setTitleForSellsDetails = "Sells Table";

    // Login Sector
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String LoginView(Model model) {
        model.addAttribute("title", "Log In");
        return "LoginSystem/LoginView";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processLoginView(@RequestParam("username") String username, @RequestParam("password") String password) {

        if (username.equals("admin") && password.equals("123")) {
            isLoggedin = true;
            checkedLoginRoll = "admin";

            // This is for Initial sells List
            for (SellsManDetails s : sellsManDao.findAll()) {
                sellProducts.addAll(s.getProducts());
            }

            return "redirect:/LoginView/LoggedinView";
        } else {
            List<SellsManDetails> sellsManDetails = sellsManDao.findAll();

            for (SellsManDetails s : sellsManDetails) {
                if (s.getName().equals(username)) {
                    if (s.getPassword().equals(password)) {
                        isLoggedin = true;
                        checkedLoginRoll = s.getName();
                        sellsManLogInId = s.getId();
                        return "redirect:/LoginView/LoggedinView";
                    }
                }
            }
        }
        return "LoginSystem/LoginView";
    }
    // Login Sector End

    // Product List Sector || LoggedIn View
    @RequestMapping(value = "LoggedinView", method = RequestMethod.GET)
    public String LoginedView(Model model) {
        if (isLoggedin == true) {
            if (checkedLoginRoll.equals("admin")) {
                if(checkedLoginRoll != null)
                {
                    model.addAttribute("errors", getErrors);
                    model.addAttribute("title", "Product List");
                    model.addAttribute("productList", productDao.findAll());
                    getErrors = null;
                    return "LoginSystem/LoggedinView";
                }
            } else {
                if(sellsManLogInId > -1)
                {
                    model.addAttribute("errors", getErrors);
                    model.addAttribute("title", "Product List");
                    model.addAttribute("productList", productDao.findAll());
                    getErrors = null;
                    return "LoginSystem/sellsManLoggedinView";
                }
            }
        }
        return "redirect:";
    }

    @RequestMapping(value = "LoggedinView", params = "Add to Cart", method = RequestMethod.POST)
    public String processSellLoginedView(@RequestParam(value = "selectedProduct", required = false) int[] selectedProduct, Model model) {
        if (selectedProduct != null) {
            totalAmount = 0;
            selectedProductList = new ArrayList<>();
            for (int i : selectedProduct) {

                for (Product p : productDao.findAll()) {

                    if (i == p.getId()) {
                        selectedProductList.add(p);
                        totalAmount += p.getProductPrice();
                        if (!checkedLoginRoll.equals("admin")) {
                            SellsManDetails sellsManDetails = sellsManDao.findOne(sellsManLogInId);
                            sellsManDetails.addProduct(p);
                            sellsManDao.save(sellsManDetails);
                        }
                    }
                }
            }
        } else {
            getErrors = "Product must not be unmarked!";
            return "redirect:/LoginView/LoggedinView";
        }
        return "redirect:/LoginView/selectedProduct";
    }

    @RequestMapping(value = "LoggedinView", params = "Delete Product", method = RequestMethod.POST)
    public String processDeleteLoginedView(@RequestParam(value = "selectedProduct", required = false) int[] selectedProduct, Model model) {
        if (selectedProduct != null) {
            for (int i : selectedProduct) {
                productDao.delete(i);
            }
        } else {
            getErrors = "Product must not be unmarked!";
            return "redirect:/LoginView/LoggedinView";
        }
        return "redirect:/LoginView/LoggedinView";
    }

    @RequestMapping(value = "LoggedinView", params = "Update Product", method = RequestMethod.POST)
    public String processUpdateLoginedView(@RequestParam(value = "selectedProduct", required = false) int[] selectedProduct, Model model) {
        if (selectedProduct != null) {
            if (selectedProduct.length == 1) {
                for (int i : selectedProduct) {
                    updateProduct = productDao.findOne(i);
                }
            } else {
                getErrors = "Must be selected single product for  update!";
                return "redirect:/LoginView/LoggedinView";
            }
        } else {
            getErrors = "Product must not be unmarked!";
            return "redirect:/LoginView/LoggedinView";
        }
        return "redirect:/LoginView/updateProduct";
    }
    // Product List Sector || LoggedIn View End

    // Add Product Sector
    @RequestMapping(value = "addProduct", method = RequestMethod.GET)
    public String addProduct(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Add Product");
            model.addAttribute("product", new Product());
            return "LoginSystem/addProduct";
        }
        return "redirect:";
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public String processaddProduct(@ModelAttribute @Valid Product product, Errors errors) {

        if (errors.hasErrors()) {
            return "LoginSystem/addProduct";
        }

        productDao.save(product);
        return "redirect:/LoginView/LoggedinView";
    }
    // Add Product Sector End

    // Select Product Sector
    @RequestMapping(value = "selectedProduct")
    public String selectedProduct(Model model) {
        if (isLoggedin == true) {
            if (checkedLoginRoll.equals("admin")) {
                model.addAttribute("title", "Selected product for sell");
                model.addAttribute("productList", selectedProductList);
                model.addAttribute("totalAmount", totalAmount);
                return "LoginSystem/selectedProduct";
            } else {
                model.addAttribute("title", "Selected product for sell");
                model.addAttribute("productList", selectedProductList);
                model.addAttribute("totalAmount", totalAmount);
                return "LoginSystem/sellsManSelectedProduct";
            }
        }

        return "redirect:";
    }
    // Select Product Sector End

    // Update Product Sector
    @RequestMapping(value = "updateProduct", method = RequestMethod.GET)
    public String updateProduct(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Update Product");
            model.addAttribute("product", new Product());
            model.addAttribute("updateProductId", updateProduct.getId());
            model.addAttribute("updateProductName", updateProduct.getProductName());
            model.addAttribute("updateProductPrice", updateProduct.getProductPrice());
            return "LoginSystem/updateProduct";
        }
        return "redirect:";
    }

    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public String processUpdateProduct(@ModelAttribute @Valid Product getUpdateProduct, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("updateProductId", updateProduct.getId());
            model.addAttribute("updateProductName", updateProduct.getProductName());
            model.addAttribute("updateProductPrice", updateProduct.getProductPrice());
            return "LoginSystem/updateProduct";
        }
        Product product = productDao.findOne(getUpdateProduct.getId());
        product.setProductName(getUpdateProduct.getProductName());
        product.setProductPrice(getUpdateProduct.getProductPrice());
        productDao.save(product);
        return "redirect:/LoginView/LoggedinView";
    }
    // Update Product Sector End

    // Add Sells Man Sector
    @RequestMapping(value = "addSellsMan", method = RequestMethod.GET)
    public String addSellsMan(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Add Sells Man");
            model.addAttribute("sellsManDetails", new SellsManDetails());
            return "LoginSystem/addSellsMan";
        }
        return "redirect:";
    }

    @RequestMapping(value = "addSellsMan", method = RequestMethod.POST)
    public String processaddSellsMan(@ModelAttribute @Valid SellsManDetails sellsManDetails, Errors errors) {

        if (errors.hasErrors()) {
            return "LoginSystem/addSellsMan";
        }

        sellsManDao.save(sellsManDetails);
        return "redirect:/LoginView/sellsManTable";
    }
    // Add Sells Man Sector

    // Sells Man Table Sector
    @RequestMapping(value = "sellsManTable", method = RequestMethod.GET)
    public String displaySellsMan(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Sells Man Table");
            model.addAttribute("sellsManList", sellsManDao.findAll());

            return "LoginSystem/sellsManTable";
        }
        return "redirect:";
    }

    @RequestMapping(value = "sellsManTable", method = RequestMethod.POST)
    public String processSellsManEdit(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Sells Man Table");
            model.addAttribute("sellsManList", sellsManDao.findAll());

            return "LoginSystem/sellsManTable";
        }
        return "redirect:";
    }

    @RequestMapping(value = "sellsManTable", params = "Delete", method = RequestMethod.POST)
    public String processSellsManDelete(@RequestParam(value = "selectedSellsMan", required = false) int[] selectedSellsMan) {
        if (selectedSellsMan != null) {
            for (int i : selectedSellsMan) {
                sellsManDao.delete(i);
            }
        } else {
            getErrors = "Sells Man must not be unmarked!";
            return "redirect:/LoginView/sellsManTable";
        }
        return "redirect:/LoginView/sellsManTable";
    }
    // Sells Man Table Sector End

    // Total Sells Details Sector For Admin
    @RequestMapping(value = "sellsTable", method = RequestMethod.GET)
    public String displaySellTable(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", setTitleForSellsDetails);
            model.addAttribute("sellsManList", sellsManDao.findAll());
            model.addAttribute("product", sellProducts);

            return "LoginSystem/sellsTable";
        }
        return "redirect:";
    }

    @RequestMapping(value = "sellsTable", params = "Show", method = RequestMethod.POST)
    public String processSellTable(@RequestParam(value = "sellsMan") int sellsMan, @RequestParam(value = "sellsMan") String sellsMans) {
        if (isLoggedin == true) {
            if (sellsMans.equals("00220022")) {
                sellProducts.clear();
                for (SellsManDetails s : sellsManDao.findAll()) {
                    sellProducts.addAll(s.getProducts());
                }
                setTitleForSellsDetails = "Sells Table";
                return "redirect:/LoginView/sellsTable";
            } else {
                SellsManDetails s = sellsManDao.findOne(sellsMan);
                setTitleForSellsDetails = "Sells Table : " + s.getName();
                sellProducts = s.getProducts();
                return "redirect:/LoginView/sellsTable";
            }
        }
        return "redirect:";
    }

    @RequestMapping(value = "sellsTable", params = "Clear", method = RequestMethod.POST)
    public String processSellTableClear(@RequestParam(value = "sellsMan") int sellsMan) {
        if (isLoggedin == true) {
            return "redirect:/LoginView/sellsTable";
        }
        return "redirect:";
    }
    // Total Sells Details Sector For Admin End

    // Total Sells Details Sector For Sells Man
    @RequestMapping(value = "sellsManSells", method = RequestMethod.GET)
    public String sellsManSells(Model model) {
        if (isLoggedin == true) {
            model.addAttribute("title", "Sells Table");
            SellsManDetails sellsManDetails = sellsManDao.findOne(sellsManLogInId);
            model.addAttribute("product", sellsManDetails.getProducts());

            return "LoginSystem/sellsManSells";
        }
        return "redirect:";
    }
    // Total Sells Details Sector For Sells Man

    // User Details Sector
    @RequestMapping(value = "sellsManDetails", method = RequestMethod.GET)
    public String userDetails(Model model)
    {
        if(isLoggedin = true)
        {
            if(sellsManLogInId > -1)
            {
                SellsManDetails sellsManDetails = sellsManDao.findOne(sellsManLogInId);
                model.addAttribute("title","My Details : " + sellsManDetails.getName());
                model.addAttribute("userDetails", sellsManDetails);
                return "LoginSystem/sellsManDetails";
            }
        }
        return "redirect:";
    }
    // User Details Sector End

    // Logout Sector
    @RequestMapping(value = "Logout")
    public String processLogout() {
        isLoggedin = false;
        sellProducts.clear();
        return "redirect:";
    }
    // Logout Sector End
}
