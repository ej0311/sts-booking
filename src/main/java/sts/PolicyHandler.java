package sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import sts.config.kafka.KafkaProcessor;

@Service
public class PolicyHandler{
	@Autowired
    BookingRepository bookingRepository;
	
	@StreamListener(KafkaProcessor.INPUT)
    public void paymentApproved(@Payload PaymentApproved paymentApproved){
		System.out.println("11111111111");
        if(paymentApproved.isMe()){
        	bookingRepository.findById(paymentApproved.getBookingId())
		            .ifPresent(
		            		booking -> {
		            			booking.setStatus(BookingStatus.BookingApproved.name());;
		            			bookingRepository.save(booking);
		                    }
		            )
		    ;
        }
    }

}
