package ido.style.mapper;


import ido.style.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDTO> selectProducts(
            Integer categoryNo, // 상품의 카테고리
            String sort // 상품의 정렬 방식
    );

    ProductDTO selectProductByNo(Integer productNo);

    List<CategoryDTO> selectCategories();


    // 스타일 메이크 전용 카테고리

    @Select("SELECT no, name FROM style_store_category")
    List<StyleStoreCategoryDTO> selectStyleStoreCategories();

    // 찜 상품 불러오기
    List<LovesDTO> selectLovesByUser(
            Integer categoryNo,
            UserDTO user,
            String sort
    );

    void insertLoves(LovesDTO loves); // 찜 목록에 상품 정보 추가

    void deleteLoves(LovesDTO loves); // 찜 목록에 상품 정보 삭제

    void insertStyles(StylesDTO styles); // 스타일 리스트 추가

    // 스타일 불러오기
    List<StylesDTO> selectStylesByUser(
            UserDTO user
    );

    StylesDTO selectStyleByUser(UserDTO user, Integer styleNo);

}

    
    
