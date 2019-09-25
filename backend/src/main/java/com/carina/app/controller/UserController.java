package com.carina.app.controller;

import com.carina.app.exception.ResourceNotFoundException;
import com.carina.app.model.FavouritesModel;
import com.carina.app.model.UserModel;
import com.carina.app.payload.UserFavouritesRequestPayload;
import com.carina.app.repository.FavouritesRepository;
import com.carina.app.repository.UserRepository;
import com.carina.app.security.CurrentUser;
import com.carina.app.security.UserPrincipal;
import com.carina.app.template.FavouritesTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavouritesRepository favouritesRepository;

    @Autowired
    private FavouritesTemplate favouritesTemplate;

    @GetMapping("/user/settings")
    @PreAuthorize("hasRole('USER')")
    public UserModel getUserSettings(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/favourites")
    @PreAuthorize("hasRole('USER')")
    public List<FavouritesModel> getUserFavourites(@CurrentUser UserPrincipal userPrincipal) {
        return favouritesRepository.findByUserId(userPrincipal.getId());
    }

    @PutMapping("/user/favourites")
    @PreAuthorize("hasRole('USER')")
    public List<FavouritesModel> putUserFavourites(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody UserFavouritesRequestPayload userFavouritesRequestPayload
        ) {
        favouritesTemplate.insertUserFavourite(userFavouritesRequestPayload, userPrincipal.getId());
        return favouritesRepository.findByUserId(userPrincipal.getId());
    }

    @DeleteMapping("/user/favourites")
    @PreAuthorize("hasRole('USER')")
    public List<FavouritesModel> deleteUserFavourites(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody UserFavouritesRequestPayload userFavouritesRequestPayload
        ) {
        favouritesTemplate.deleteUserFavourite(userFavouritesRequestPayload, userPrincipal.getId());
        return favouritesRepository.findByUserId(userPrincipal.getId());
    }

}