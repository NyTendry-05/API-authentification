package mg.itu.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SessionController {

    @GetMapping("/session")
    public String viewSessionVariables(HttpSession session, Model model) {
        // Récupérer toutes les variables de session dans une Map
        Map<String, Object> sessionAttributes = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            sessionAttributes.put(attributeName, session.getAttribute(attributeName));
        }

        // Ajouter les attributs de session au modèle
        model.addAttribute("sessionAttributes", sessionAttributes);

        // Retourner la vue
        return "sessionView";
    }
}
