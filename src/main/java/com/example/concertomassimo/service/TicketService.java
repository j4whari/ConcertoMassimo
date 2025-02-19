package com.example.concertomassimo.service;

import com.example.concertomassimo.dto.TicketRequest;
import com.example.concertomassimo.dto.TicketResponse;
import com.example.concertomassimo.model.Order;
import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.OrderRepository;
import com.example.concertomassimo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public TicketService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public TicketResponse createTicket(TicketRequest request) {
        // Salva i dati dell'utente
        User user = new User();
        user.setTipologia(request.getTipologia());
        user.setNome(request.getNome());
        user.setCognome(request.getCognome());
        user.setDataNascita(request.getDataNascita());
        user.setEmail(request.getEmail());
        user.setNazione(request.getNazione());
        user.setProvincia(request.getProvincia());
        user.setCitta(request.getCitta());
        user.setIndirizzo(request.getIndirizzo());
        user.setCivico(request.getCivico());
        user.setCap(request.getCap());
        user.setPrivacyMarketing(request.getPrivacyMarketing());

        User savedUser = userRepository.save(user);

        // Salva i dati dell'ordine
        Order order = new Order();
        order.setUser(savedUser);
        order.setMetodoConsegna(request.getMetodoConsegna());
        order.setFatturaDifferente(request.isFatturaDifferente());
        if(request.isFatturaDifferente()){
            order.setFatturaIndirizzo(request.getFatturaIndirizzo());
            order.setFatturaCivico(request.getFatturaCivico());
            order.setFatturaCap(request.getFatturaCap());
            order.setFatturaCitta(request.getFatturaCitta());
        }
        order.setBiglietto(request.getBiglietto());
        order.setCommissioni(request.getCommissioni());
        order.setIva(request.getIva());
        order.setTotale(request.getTotale());

        Order savedOrder = orderRepository.save(order);

        // Prepara la risposta
        TicketResponse response = new TicketResponse();
        response.setUserId(savedUser.getId());
        response.setOrderId(savedOrder.getOrderId());

        return response;
    }
}

