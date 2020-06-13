package main.mrs.verification_handler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import main.mrs.model.Pacijent;
import main.mrs.model.VerificationToken;
import main.mrs.service.EmailService;
import main.mrs.service.VerificationTokenService;

@Component
public class EmailListenerConfirmation implements ApplicationListener<OnAccessLinkEvent>{
	
	
	@Autowired
	private VerificationTokenService tokenService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void onApplicationEvent(OnAccessLinkEvent event) {
		this.confirmRegistration(event);
		
	}
	private void confirmRegistration(OnAccessLinkEvent event) {
		try {
			
			Pacijent user = event.getPacijent();
			
			String token = UUID.randomUUID().toString();
			
		    VerificationToken newUserToken = new VerificationToken(token, user);
		   
			tokenService.save(newUserToken);
						
			String recipient = user.getEmail();
			String subject = "Potvrda registracije";
	        String url 
	          = event.getPutanja() + "/potvrdiRegistraciju/" + token;
	        
	        emailService.sendNotificaitionAsync(url, recipient, subject);
	        
			
		}catch(Exception e) {
			System.out.println("USO OVDE i napravio gresku ");
		}
		
	}
}
