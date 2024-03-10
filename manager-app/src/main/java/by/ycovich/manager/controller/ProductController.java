package by.ycovich.manager.controller;

import by.ycovich.manager.controller.payload.UpdateProductPayload;
import by.ycovich.manager.entity.Product;
import by.ycovich.manager.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
@RequestMapping("catalog/products/{productId:\\d+}")
public class ProductController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return productService.findProduct(productId).orElseThrow(() ->
                new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @GetMapping
    public String getProduct() {
        return "catalog/products/product";
    }

    @GetMapping("/edit")
    public String getProductEditPage() {
        return "catalog/products/edit";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute(value = "product", binding = false) Product product,
                                @Valid UpdateProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalog/products/edit";
        } else {
            productService.updateProduct(product.getId(), payload.title(), payload.details());
            return "redirect:/catalog/products/%d".formatted(product.getId());
        }
    }

    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteById(product.getId());
        return "redirect:/catalog/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               Model model,
                                               HttpServletResponse response,
                                               Locale locale){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }

}
