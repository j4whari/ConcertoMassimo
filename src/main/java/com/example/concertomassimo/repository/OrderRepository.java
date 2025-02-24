package com.example.concertomassimo.repository;

import com.example.concertomassimo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.concertomassimo.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findTopByUserOrderByCreatedAtDesc(User user);
}
