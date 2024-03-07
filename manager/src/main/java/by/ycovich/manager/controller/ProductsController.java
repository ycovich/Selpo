package by.ycovich.manager.controller;

import by.ycovich.manager.entity.Product;
import by.ycovich.manager.controller.payload.NewProductPayload;
import by.ycovich.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/catalog/products")
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/list")
    public String getProductsList(Model model){
        model.addAttribute("products", productService.findAllProducts());
        return "catalog/products/list";
    }

    @GetMapping("/create")
    public String getNewProductPage(){
        return "catalog/products/add";
    }

    @PostMapping("/create")
    public String createProduct(NewProductPayload payload){
        Product product = productService.createProduct(payload.title(), payload.details());
        return "redirect:/catalog/products/%d".formatted(product.getId());
    }




}
