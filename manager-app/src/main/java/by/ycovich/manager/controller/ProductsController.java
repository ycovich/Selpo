package by.ycovich.manager.controller;

import by.ycovich.manager.client.BadRequestException;
import by.ycovich.manager.client.ProductClient;
import by.ycovich.manager.entity.Product;
import by.ycovich.manager.controller.payload.NewProductPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/catalog/products")
public class ProductsController {
    private final ProductClient client;

    @GetMapping({"", "/", "/list"})
    public String getProductsList(Model model, @RequestParam(name = "filter", required = false) String filter){
        model.addAttribute("products", client.findAllProducts(filter));
        model.addAttribute("filter", filter);
        return "catalog/products/list";
    }

    @GetMapping("/create")
    public String getNewProductPage(){
        return "catalog/products/add";
    }

    @PostMapping("/create")
    public String createProduct(NewProductPayload payload,
                                Model model){
            try {
                Product product = client.createProduct(payload.title(), payload.details());
                return "redirect:/catalog/products/%d".formatted(product.id());
            } catch (BadRequestException exception) {
                model.addAttribute("payload", payload);
                model.addAttribute("errors", exception.getErrors());
                return "catalog/products/add";
            }
    }
}
