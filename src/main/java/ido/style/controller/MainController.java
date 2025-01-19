package ido.style.controller;

import ido.style.dto.*;
import ido.style.service.ProductService;
import ido.style.service.StyleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StyleProductService styleProductService;


    @GetMapping("/") // localhost:8080
    public String get_home(Model model){
        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();
        List<CategoryDTO> categories = productService.get_categories();

        model.addAttribute("styleCategories", styleCategories);
        model.addAttribute("categories", categories);
        return "main/home"; // 홈페이지로 가라
    }

    // 스타일 상품 리스트 화면
    @GetMapping("/styleProduct")
    public String get_stylecateogry(
            @RequestParam(defaultValue = "1") Integer categoryNo,
            String sort,
            Model model
    ){
        List<StyleProductDTO> styleProducts = styleProductService.get_style_products(categoryNo, sort);

        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();
        List<CategoryDTO> categories = productService.get_categories();

        model.addAttribute("styleProducts", styleProducts);
        model.addAttribute("styleCategories", styleCategories);
        model.addAttribute("categories", categories);

        return "main/styleCategory";
    }

    // 스타일 상품 하나의 화면
    @GetMapping("/styleProduct/{productNo}")
    public String get_styleProduct(
            @PathVariable Integer productNo,
            Model model
    ) {
        StyleProductDTO styleProduct = styleProductService.get_style_product(productNo);

        List<CategoryDTO> categories = productService.get_categories();
        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();

        model.addAttribute("styleProduct", styleProduct);
        model.addAttribute("categories", categories);
        model.addAttribute("styleCategories", styleCategories);

        return "main/styleProduct";
    }


    // 모든 상품 리스트 화면
    @GetMapping("/product")
    public String get_cateogry(
            @RequestParam(defaultValue = "1") Integer categoryNo,
            String sort,
            Model model
    ){
        model.addAttribute("categoryNo", categoryNo); // 정렬 a태그에 사용


        List<ProductDTO> products = productService.get_products(categoryNo, sort);

        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();
        List<CategoryDTO> categories = productService.get_categories();

        model.addAttribute("products", products);

        model.addAttribute("styleCategories", styleCategories);
        model.addAttribute("categories", categories);

        return "main/category";
    }


    // 상품 하나의 화면
    @GetMapping("/product/{productNo}")
    public String get_product(
            @PathVariable Integer productNo,
            Model model
    ) {
        ProductDTO product = productService.get_product(productNo);

        List<CategoryDTO> categories = productService.get_categories();
        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("styleCategories", styleCategories);

        return "main/product";
    }

    /****************************************************************************/
    // 스타일 코디
    @GetMapping("/style-make")
    public String get_style_make(
            Model model
    ){


        List<CategoryDTO> categories = productService.get_categories();
        List<StyleCategoryDTO> styleCategories = styleProductService.get_categories();

        model.addAttribute("categories", categories);
        model.addAttribute("styleCategories", styleCategories);

        return "main/style-make";
    }

    // 스타일 코디 - style-make 안에 사용되는 IFRAME 1 (스토어 페이지)
    @GetMapping("/style-store")
    public String get_style_store(
            @RequestParam(defaultValue = "1") Integer categoryNo,
            String sort,
            Model model
    ){

        model.addAttribute("categoryNo", categoryNo); // 정렬 a태그에 사용

        List<ProductDTO> products = productService.get_products(categoryNo, sort);
        List<StyleStoreCategoryDTO> styleStoreCategories = productService.get_style_store_categories();

        model.addAttribute("products", products);
        model.addAttribute("styleStoreCategories", styleStoreCategories);
        return "main/style-store";
    }

    // 스타일 코디 - style-make 안에 사용되는 IFRAME 2 (찜 페이지)
    @GetMapping("/style-dibs")
    public String get_user_dibs(
            @RequestParam(defaultValue = "1") Integer categoryNo,
            String sort,

            Authentication authentication,
            @AuthenticationPrincipal UserDTO user,

            Model model
    ){
        if(!(Objects.nonNull(authentication))){
            return "redirect:/user/login";
        }

        model.addAttribute("categoryNo", categoryNo); // 정렬 a태그에 사용

        List<DibsDTO> dibs = productService.get_dibs_by_user(categoryNo, user, sort);
        List<StyleStoreCategoryDTO> styleStoreCategories = productService.get_style_store_categories();

        model.addAttribute("dibs", dibs);
        model.addAttribute("styleStoreCategories", styleStoreCategories);


        return "main/style-dibs";
    }
}
