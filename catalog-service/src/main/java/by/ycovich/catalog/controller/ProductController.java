package by.ycovich.catalog.controller;

import by.ycovich.catalog.controller.payload.UpdateProductPayload;
import by.ycovich.catalog.entity.Product;
import by.ycovich.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/catalog/products/{productId:\\d+}")
public class ProductController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute
    public Product getProduct(@PathVariable("productId") int productId) {
        return productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product){
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                           @Valid @RequestBody UpdateProductPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId){
        productService.deleteById(productId);
        return ResponseEntity.noContent()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail
                        .forStatusAndDetail(
                                HttpStatus.NOT_FOUND,
                                messageSource.getMessage(exception.getMessage(), new Object[0],
                                        exception.getMessage(), locale)));
    }

}
