package com.indo.GymManagement.serviceimpl;

import org.springframework.stereotype.Service;

import com.indo.GymManagement.configuration.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public void sendOtp(String toPhoneNumber, String otp) {
        String messageBody = "Your OTP code is: " + otp;

        try {
            Message message = Message.creator(
                    new PhoneNumber("+"+toPhoneNumber),  
                    new PhoneNumber(twilioConfig.getFromPhoneNumber()), 
                    messageBody
            ).create();

            System.out.println("Sent message SID: " + message.getSid());
        } catch (ApiException e) {
            // Twilio-specific exception
            System.err.println("Twilio API exception: " + e.getMessage());
        } catch (Exception e) {
            // General exception
            System.err.println("Error sending OTP: " + e.getMessage());
        }
    }

}
