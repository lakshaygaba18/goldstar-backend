package goldstar_backend.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public String generateTryOn(String customerImage,
                                String garmentImage,
                                String prompt) {

        // OpenAI API call yahan hoga

        return "AI Generated Image URL";
    }

}