package com.example.concertomassimo.repository;

import com.example.concertomassimo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
