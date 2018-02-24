package com.appian.microservices.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.appian.microservices.inventory.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
}