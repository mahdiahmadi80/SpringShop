package org.example.springshop.repository;

import org.example.springshop.model.FavoriteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {
}
