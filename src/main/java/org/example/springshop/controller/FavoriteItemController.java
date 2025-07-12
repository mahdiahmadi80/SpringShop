package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.FavoriteItemRequestModel;
import org.example.springshop.model.dto.responsemodel.FavoriteItemResponseModel;
import org.example.springshop.service.FavoriteItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/favorite")
public class FavoriteItemController {
    private final FavoriteItemService favoriteItemService;

    public FavoriteItemController(FavoriteItemService favoriteItemService) {
        this.favoriteItemService = favoriteItemService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private List<FavoriteItemResponseModel> listFavoriteItem() {
        return favoriteItemService.listFavorite();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public FavoriteItemResponseModel addFavoriteItem(@RequestBody FavoriteItemRequestModel favoriteItemRequestModel) {
        return favoriteItemService.addFavorite(favoriteItemRequestModel);
    }
}
