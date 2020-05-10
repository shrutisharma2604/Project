package com.example.EcommerceApp.scheduler;

import com.example.EcommerceApp.services.EmailService;
import com.example.EcommerceApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync(proxyTargetClass = true)
@Component
public class Scheduler {
    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

   // Logger logger= LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(cron = "31 15 * * * *",zone = "Indian/Maldives")
   /* public void sendEmailToSeller() throws InterruptedException{
        System.out.println("scheduling>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Iterable<Seller> sellers=sellerRepository.findAll();
        Iterator<Seller>  sellerIterator=sellers.iterator();
        while(sellerIterator.hasNext()){
            Seller seller=sellerIterator.next();
            logger.trace("running scheduler");
            emailNotificationService.sendNotification("Accepted/Rejected","order details",seller.getEmail());
        }
    }*/
    public void run(){
        emailService.saveEmailReport();
        productService.autoUpdateVariationQuantity();
    }

}
