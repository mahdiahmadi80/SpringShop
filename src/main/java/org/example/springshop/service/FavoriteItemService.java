package org.example.springshop.service;

import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.FavoriteItem;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.FavoriteItemRequestModel;
import org.example.springshop.model.dto.responsemodel.FavoriteItemResponseModel;
import org.example.springshop.repository.FavoriteItemRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteItemService {
    private final FavoriteItemRepository favoriteItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FavoriteItemService(FavoriteItemRepository favoriteItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.favoriteItemRepository = favoriteItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<FavoriteItemResponseModel> listFavorite() {
        List<FavoriteItemResponseModel> favoriteItemResponseModels = new ArrayList<>();
        favoriteItemRepository.findAll().forEach(favoriteItem -> {
            FavoriteItemResponseModel favoriteItemResponseModel = FavoriteItemResponseModel.builder().favoriteItem(favoriteItem).build();
            favoriteItemResponseModels.add(favoriteItemResponseModel);
        });
        return favoriteItemResponseModels;
    }

    public FavoriteItemResponseModel addFavorite(FavoriteItemRequestModel favoriteItemRequestModel) {
        User user = userRepository.findById(favoriteItemRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Product product = productRepository.findById(favoriteItemRequestModel.getProductId()).orElseThrow(() -> new ProductNotFoundException("product not found"));
        FavoriteItem favoriteItem = FavoriteItem.favoriteBuilder().user(user).product(product).build();
        favoriteItemRepository.save(favoriteItem);
        return FavoriteItemResponseModel.builder().favoriteItem(favoriteItem).build();
    }

//    public FavoriteItemResponseModel editList(Long id, FavoriteItemRequestModel favoriteItemRequestModel) {
//
//        FavoriteItem updateFavoriteItem = favoriteItemRepository.findById(id).orElseThrow();
//        List<Product> itemsList = updateFavoriteItem.getProduct();
//        for (Long productId : favoriteItemRequestModel.getProduct()) {
//            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("product not found"));
//            itemsList.add(product);
//        }
//        updateFavoriteItem.setProduct(itemsList);
//        favoriteItemRepository.save(updateFavoriteItem);
//        return FavoriteItemResponseModel.builder().favoriteItem(updateFavoriteItem).build();
//    }

//    public String deleteFavoriteList(Long id) {
//        FavoriteItem favoriteItem = favoriteItemRepository.findById(id).orElseThrow();
//        favoriteItem.getProduct().clear();
//        favoriteItemRepository.save(favoriteItem);
//        favoriteItemRepository.delete(favoriteItem);
//        return "list is deleted";
//    }
}
