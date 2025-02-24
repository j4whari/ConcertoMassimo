package com.example.concertomassimo.service;

import com.example.concertomassimo.dto.TicketRequest;
import com.example.concertomassimo.dto.TicketResponse;
import com.example.concertomassimo.model.Order;
import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.OrderRepository;
import com.example.concertomassimo.repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    public byte[] generateTicket(String nome, String cognome, String infoSupplementari) throws IOException, WriterException {
        try (PDDocument document = new PDDocument()) {

            // Carica l'immagine di sfondo dal classpath
            ClassPathResource resource = new ClassPathResource("Biglietto.png");
            BufferedImage bufferedImage;
            try (InputStream inputStream = resource.getInputStream()) {
                bufferedImage = ImageIO.read(inputStream);
            }

            // Calcola le dimensioni in punti (assumendo 96 dpi)
            int widthPx = bufferedImage.getWidth();
            int heightPx = bufferedImage.getHeight();
            float dpi = 96f;
            float widthPts = (widthPx * 72) / dpi;
            float heightPts = (heightPx * 72) / dpi;

            // Crea la pagina PDF con dimensioni custom (uguali a quelle dell'immagine)
            PDPage page = new PDPage(new PDRectangle(widthPts, heightPts));
            document.addPage(page);

            // Carica il font Telegraf dal classpath
            ClassPathResource fontResource = new ClassPathResource("fonts/Telegraf_Regular.ttf");
            PDType0Font telegrafFont = PDType0Font.load(document, fontResource.getInputStream());

            try (var contentStream = new org.apache.pdfbox.pdmodel.PDPageContentStream(
                    document,
                    page,
                    org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.OVERWRITE,
                    true,
                    true)) {

                // Disegna lo sfondo
                PDImageXObject background = LosslessFactory.createFromImage(document, bufferedImage);
                contentStream.drawImage(background, 0, 0, widthPts, heightPts);

                // Imposta il colore del testo (#d8c1bc)
                contentStream.setNonStrokingColor(new Color(216, 193, 188));

                // Imposta la dimensione del font
                float fontSize = 40f;
                float leading = 130f; // spazio tra le righe

                // Inizia a scrivere il testo centrato (modifica l'offset iniziale se necessario)
                contentStream.beginText();
                contentStream.setFont(telegrafFont, fontSize);
                contentStream.setLeading(leading);
                // Imposta la posizione iniziale: qui x=400, y = heightPts - 100
                contentStream.newLineAtOffset(400, heightPts - 30);
                contentStream.newLine();       // sposta il cursore in basso di 'leading'
                contentStream.showText("Nome: " + nome);
                contentStream.newLine();
                contentStream.showText("Cognome: " + cognome);
                contentStream.endText();

                // Genera il QR code con margine ridotto (quiet zone)
                String qrUrl = "https://effervescent-gumption-ddab8e.netlify.app/";
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.MARGIN, 0); // Rimuove il margine predefinito
                BitMatrix bitMatrix = qrCodeWriter.encode(qrUrl, BarcodeFormat.QR_CODE, 200, 200, hints);
                BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                // Converti il QR code in byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(qrCodeImage, "png", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();

                // Crea il PDImageXObject per il QR code
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "QRCode");

                // Posiziona il QR code alle coordinate specificate:
                // In questo esempio: x = 930, y = 180, width = 192, height = 167
                // (modifica i valori in base alle tue esigenze)
                contentStream.drawImage(pdImage, 930, 180, 192, 167);
            }

            // Salva il PDF in un array di byte
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
}

