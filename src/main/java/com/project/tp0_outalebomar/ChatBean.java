package com.project.tp0_outalebomar;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("chatBean")
@ViewScoped
public class ChatBean implements Serializable {
    private String roleApi = "";
    private boolean roleFige = false;

    private String question;
    private String reponse;

    private final List<String> conversation = new ArrayList<>();

    // Actions
    public void figerRole() {
        if (roleApi != null && !roleApi.isBlank()) {
            roleFige = true;
        }
    }

    public void envoyer() {
        if (question == null || question.isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "La question est obligatoire.", "Saisis une question avant d'envoyer."));
            return;
        }
        if (!roleFige) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Choisis d'abord un rôle.", "Sélectionne le rôle de l’API."));
            return;
        }

        // Traitement "perso" du TP : change la casse + rôle, encadré par ||
        String traite = switch (roleApi) {
            case "assistant"   -> question.trim().toUpperCase();
            case "traducteur"  -> question.trim().toLowerCase(); // (simulé)
            case "guide"       -> new StringBuilder(question.trim()).reverse().toString();
            default -> question.trim();
        };
        reponse = "||" + roleApi + "|| " + traite;

        conversation.add("Q: " + question);
        conversation.add("R: " + reponse);

        // vider la question après envoi (UX)
        question = "";
    }

    public void effacerDernier() {
        if (conversation.size() >= 2) {
            // supprime dernière réponse + dernière question
            conversation.remove(conversation.size() - 1);
            conversation.remove(conversation.size() - 1);
            reponse = "";
        }
    }

    public String nouveauChat() {
        // Redirection = change de view -> nouvel objet @ViewScoped
        return "index.xhtml?faces-redirect=true";
    }

    // Getters/Setters pour JSF
    public String getRoleApi() { return roleApi; }
    public void setRoleApi(String roleApi) { this.roleApi = roleApi; }
    public boolean isRoleFige() { return roleFige; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getReponse() { return reponse; }

    public String getHistoriqueTexte() {
        return String.join(System.lineSeparator(), conversation);
    }
}

